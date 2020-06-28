package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


/** 
 * 클래스 명 : GameManager class
 * 설명 : 게임을 실행하는 클래스, 콘솔 창에서 사용자의 입력을 받아 처리 한다.
 * 선언 : GameManager gameManager = new GameManager();
 * 게임 시작 : gameManager.start();
 * 
 * @author sim-younghyun
 *
 */
public class GameManager {
	
	/**
	 * 사용자 목록
	 */
	private List<User> userList;
	
	/**
	 * 게임에 접속된 사용자 
	 */
	private User player;
	
	/**
	 * 게임중인 캐릭터 
	 */
	private Character playCharacter;
	
	/**
	 * 맵 전체를 관리
	 */
	private MapManager mapManager;
	
	/**
	 * NPC 전체를 관리
	 */
	private NPCManager npcManager;
	
	/**
	 * 아이템 전체를 관리
	 */
	private ItemManager itemManager;
	
	/**
	 * 게임 상황별 안내 메시지
	 * ex) 캐릭터의 이동 방향, 몬스터의 출연등
	 */
	private LinkedList<String> systemMsg;
	
	Scanner sc;
	
	/**
	 * GameManager 객체가 생성되면 빈 유저 목록을 만들고 Scanner를 초기화 한다.
	 */
	public GameManager() {
		userList = new ArrayList<User>();
		sc = new Scanner(System.in);
		systemMsg = new LinkedList<String>();
		itemManager = new ItemManager();
		npcManager = new NPCManager(itemManager);
		// 시스템 안내 메시지 스레드 실행
		SystemMsgThread systemMsgThread = new SystemMsgThread(systemMsg);
		systemMsgThread.setDaemon(true);
		systemMsgThread.start();
	}
	
	/**
	 * 게임을 실행한다.
	 */
	public void start() {
		boolean isGameClose = false;		// 게임 종료 여부
		boolean isUserLogin = false;		// 사용자 로그인 여부
		boolean isSelected = false;			// 캐릭터 선택 여부
		
		/* 게임이 실행 되고 사용자의 입력을 받는다. */
		while(isGameClose == false) {
			/* 배경 음악 시작 */
			Music player = new Music("/Users/sim-younghyun/Downloads/HeroicDemise.mp3", true);
			player.start(); 
			
			/* 로그인 처리 과정 */
			if(isUserLogin == false) {
				startLoginProcess();
				isUserLogin = true;
			}
			
			/* 캐릭터 선택 처리 과정 */
			if(isSelected == false) {
				startCharacterSelectionProcess();
				isSelected = true;
			}
			
			/* 배경 음악 종료 */
			player.playStop();
			
			mapManager = new MapManager(playCharacter);
			mapManager.getCurrentMap().startBgm();
			
			while(isUserLogin && isSelected) {
				int result = play();
				if(result == 1) isSelected = false;
				if(result == 2) {isUserLogin = false; isSelected = false;}
				if(result == 3) {ending(); isGameClose = true; mapManager.getCurrentMap().stopBgm(); break;}
			}

			break;
		}
	}
	
	/**
	 * 엔딩 화면 출력
	 */
	public void ending() {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("                                                                              축하합니다.");
		System.out.println();
		System.out.println("                                                                       대악마 군주를 처치했습니다. "); 
		System.out.println();
		System.out.println();
		System.out.println("                                                              "+playCharacter.getJob()+"가문의 "+playCharacter.getCharacterId()+"님이 수니방스 제국의 왕이 되었습니다.");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	/**
	 * 게임이 실행 된다.
	 */
	public int play() {
		int sign = 0; // 0 : 진행, 1 : 캐릭터 선택창으로, 2: 로그아웃, 3: 엔딩
		
		// 미니맵 출력
		mapManager.printMiniMap();
		
		// 캐릭터, 배경 그래픽 출력
		mapManager.printGraphicMap();
		
		// 게임 메시지 출력
		printSystemMsg(systemMsg, mapManager.getCurrentMap().getMiniMapInfo().length+3);
		
		// 게임 입력창 구분선, 키 설명 출력
		System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println("a:왼쪽,s:아래,d:오른쪽,w:위쪽");
		
		// 캐릭터 조작 안내 메세지 출력 
		mapManager.printControlMsg();
		
		// 입력값 처리
		String input = sc.nextLine();
		switch (mapManager.getMiniMapDetail()) {
		case 0:
			if("1".equals(input) && mapManager.getCurrentMap().isCanRecovery()) {
				recovery();
			}
			break;
		case 2:
		case 7:
			if("1".equals(input)) {	//전투
				boolean isBossDie = battle();
				if(isBossDie) sign = 3;
			}else if(mapManager.getCurrentMap().isCanRecovery() && "2".equals(input)) {
				recovery();
			}
			break;
		case 3:
			if("1".equals(input)) {	//상점 이용
				trade();
			}else if(mapManager.getCurrentMap().isCanRecovery() && "2".equals(input)) {
				recovery();
			}
			break;
		case 4:
			if("1".equals(input)) {	//NPC 대화
				quest();
			}else if(mapManager.getCurrentMap().isCanRecovery() && "2".equals(input)) {
				recovery();
			}
			break;
		case 5:
			if("1".equals(input)) {	//이전맵으로 이동
				mapManager.prevMap();
			}else if(mapManager.getCurrentMap().isCanRecovery() && "2".equals(input)) {
				recovery();
			}
			break;
		case 6:
			if("1".equals(input)) {	//다음맵으로 이동
				mapManager.nextMap();
			}else if(mapManager.getCurrentMap().isCanRecovery() && "2".equals(input)) {
				recovery();
			}
			break;
		}
		
		/* 사용자 편의 단축키  */
		if("/i".equals(input)) { // 인벤토리
			openInventory();
		}else if("/e".equals(input)) {// 장비
			openEquip();
		}else if("/s".equals(input)) {// 상태창
			openStatus();
		}else if("/q".equals(input)) {// 퀘스트창
			openQuest();
		}else if("/select".equals(input)) {// 캐릭터 선택창으로 돌아가기
			sign = 1;
		}else if("/logout".equals(input)) {// 로그인창으로 돌아가기
			sign = 2;
		}else if("/test".equals(input)) {
			test();
		}
		
		
		// 캐릭터가 이동이 가능한지 판단
		if(!playCharacter.isMoveRange(input, mapManager.getCurrentMap().getMiniMapInfo())) {
			systemMsg.add("이동할 수 없습니다.");
			return 0;
		}
		
		// 캐릭터 위치로 맵 정보 셋팅
		mapManager.setCurrentMap();
		
		// 캐릭터 위치 변화
		playCharacter.changeCharacterLocation(input);
		
		// 이동 메세지 저장
		saveSystemMsgFromMove(input);
		
		// 몬스터 등장 메세지 저장
		saveSystemMsgFromMap();
		
		return sign;
	}
	
	
	public void test() {
		
		FindPathThread findPathThread = new FindPathThread(playCharacter.getLocation()
				, "forest3-1-1", mapManager, systemMsg);
		findPathThread.start();
	}
	
	/**
	 * NPC가 캐릭터에게 퀘스트를 준다.
	 */
	public void quest() {
		QuestNPC questNPC = npcManager.getQuestNPCByLocation(playCharacter.getLocation());
		LinkedList<String> questMsg = new LinkedList<String>();
		
		Quest quest = questNPC.getDetailData();
		
		boolean isClose = false;
		
		// 퀘스트 내용에 따른 사용자 입력값 분기 처리를 위한 변수
		// 0 : 제안, 1: 진행중, 2: 조건 만족, 3: 완료
		int type = 0;
		
		while(isClose == false) {
			
			if(!playCharacter.hasQuest(quest)) {
				
				/* 퀘스트 제안 */
				questNPC.printNPCQuest();
			}else if(quest.getType() == 0){
				
				/* 이미 진행중인 퀘스트 */
				questNPC.printNPCQuestAlready();
				type = 1;
			}else if(quest.getType() == 1) {
				
				/* 조건을 충족한 퀘스트 */
				questNPC.printNPCQuestComplete();
				quest.setType(2);
				questMsg.add("[퀘스트] "+quest.getQuestTitle()+"를 완료했습니다.");
				questMsg.add("[퀘스트] 완료 보상 경험치 100을 획득했습니다.");
				mapManager.setQuest(null);
				playCharacter.addExp(100, questMsg);
				type = 2;
			}else if(quest.getType() == 2) {
				questNPC.printNPCNormalContent();
				type = 3;
			}
			
			// 퀘스트 관련 메시지를 콘솔창에 출력한다.
			printSystemMsg(questMsg,-4);
			
			System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
			if(type == 0) { // 제안
				System.out.println("1.수락하기 2.거절하기");
				String input = sc.nextLine();
				
				if("1".equals(input)) { // 수락하기
					/* 캐릭터에 새로운 퀘스트 추가 */
					playCharacter.addQuest(quest);
					systemMsg.add(questNPC.getName()+"의 퀘스트를 수락하셨습니다.");
					break;
				}else if("2".equals(input)) { // 거절하기
					break;
				}
			}else if(type == 1 || type == 2 || type == 3) { // 진행중
				System.out.println("1.나가기");
				String input = sc.nextLine();
				
				if("1".equals(input)) { // 나가기
					break;
				}
			}
		}
		
	}
	
	/*
	 * 캐릭터의 10초 간격으로 체력, 마력을 일정량 회복한다.
	 */
	public void recovery() {
		mapManager.getCurrentMap().pauseBgm();
		RestThread restThread = new RestThread(playCharacter, this);
		restThread.setDaemon(true);
		restThread.start();
		Music recoveryMusic = new Music("/Users/sim-younghyun/Downloads/heal.mp3", true);
		recoveryMusic.start();
		sc.nextLine();
		restThread.isDone = true;
		recoveryMusic.playStop();
		mapManager.getCurrentMap().reStartBgm();
	}
	
	/**
	 * 캐릭터가 진행중인 퀘스트창을 연다.
	 */
	public void openQuest() {
		Quest quest =  playCharacter.getQuestInQuestList();
		if(quest == null) {
			systemMsg.add("진행중인 퀘스트가 없습니다.");
			return;
		}
		
		if(mapManager.getQuest() == null) {
			mapManager.setQuest(playCharacter.getQuestInProgress());
		}else {
			mapManager.setQuest(null);
		}
		
		
	}
	
	/**
	 * 캐릭터의 인벤토리창을 확인한다.
	 */
	public void openInventory() {
		// 메뉴 효과음
		Music inven = new Music("/Users/sim-younghyun/Downloads/menu.mp3", false);
		inven.start();
		
		boolean isClose = false;
		LinkedList<String> invenMsg = new LinkedList<String>();
		
		/* 인벤토리 메뉴, 사용자 입력 받아 처리하는 과정 */
		while(isClose == false) {
			
			// 인벤토리를 콘솔 창에 보여준다.
			playCharacter.getInventory().printInventory();
			
			// 인벤토리 관련 메시지를 콘솔창에 출력한다.
			printSystemMsg(invenMsg,9);
			
			System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
			System.out.println("1.장착/사용 2.나가기");
			String input = sc.nextLine();
			if("1".equals(input)) {	// 장착/사용
				System.out.println("장착또는 사용할 아이템 번호를 입력해주세요.");
				input = sc.nextLine();
				int inputNum = Integer.parseInt(input);
				invenMsg.add(playCharacter.getInventory().takeItem(inputNum-1));
			}else if("2".equals(input)) {	// 나가기
				isClose = true;
			}
		}
	}
	
	/**
	 * 캐릭터의 장비창을 확인한다.
	 */
	public void openEquip() {
		
		// 메뉴 효과음
		Music inven = new Music("/Users/sim-younghyun/Downloads/menu.mp3", false);
		inven.start();
				
		boolean isClose = false;
		LinkedList<String> equipMsg = new LinkedList<String>();
		
		/* 장비창 메뉴, 사용자 입력 받아 처리하는 과정 */
		while(isClose == false) {
			
			// 캐릭터의 장비창을 콘솔창에 출력
			playCharacter.getEquip().printEquip();
			
			// 장비창 관련 메세지 출력
			printSystemMsg(equipMsg,-9);
			
			System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
			System.out.println("1.장비 해제 2.나가기");
			String input = sc.nextLine();
			
			if("1".equals(input)) { // 무기
				
				System.out.println("1.무기 2.방어구");
				input = sc.nextLine();
				int inputNum = Integer.parseInt(input);
				equipMsg.add(playCharacter.getEquip().takeOffEquip(inputNum));
				
			}else if("2".equals(input)) { // 방어구 
				
				isClose = true;
				
			}
		}
	}
	
	/**
	 * 캐릭터의 상태창을 확인한다.
	 */
	public void openStatus() {
		
		// 메뉴 효과음
		Music inven = new Music("/Users/sim-younghyun/Downloads/menu.mp3", false);
		inven.start();
		
		boolean isClose = false;
		LinkedList<String> statusMsg = new LinkedList<String>();
		
		/* 상태창 메뉴, 사용자 입력 받아 처리하는 과정 */
		while(isClose == false) {
			
			// 캐릭터 상태창 콘솔창에 출력
			playCharacter.printStatus();
			
			// 상태창 관련 메세지 출력
			printSystemMsg(statusMsg,-3);
			
			System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
			System.out.println("1.나가기");
			String input = sc.nextLine();
			if("1".equals(input)) {
				isClose = true;
			}
		}
	}
	
	/**
	 * 몬스터 등장 메세지를 저장한다.
	 */
	public void saveSystemMsgFromMap() {
		String location = playCharacter.getLocation();
		Iterator<Monster> it = npcManager.getMonsterList().iterator();
		while(it.hasNext()) {
			NPC temp = it.next();
			if(temp.getLocation().equals(location) && "monster".equals(temp.getNpcType())) {
				Monster monster = (Monster)temp;
				systemMsg.add(monster.getSystemMsg()+"[Lv:"+monster.getLevel()+"]");
			}
		}
	}
	
	/**
	 * 캐릭터와 몬스터의 전투를 처리한다.
	 */
	public boolean battle() {
		boolean isBossDie = false;
		mapManager.getCurrentMap().pauseBgm();
		
		Music battleMusic = new Music("/Users/sim-younghyun/Downloads/battle.mp3", true);
		battleMusic.start();
		
		String location = playCharacter.getLocation();	// 플레이어 위치
		Monster monster = npcManager.getMonsterByLocation(location);	// 플레이어 위치와 일치하는 몬스터
		
		/* 전투 스레드 객체 생성 */
		Battle_ battle = new Battle_(playCharacter, monster, systemMsg, itemManager, mapManager);	// 전투를 다루는 객체 생성
		CharacterAttackThread characterAttackThread = new CharacterAttackThread(battle);
		MonsterAttackThread monsterAttackThread = new MonsterAttackThread(battle);
		SkillThread skillThread = new SkillThread(battle);
		
		characterAttackThread.start();
		monsterAttackThread.start();
		skillThread.start();
		
		boolean isClose = false;
		
		while(isClose == false) {
			String input = sc.nextLine();
			
			if("1".equals(input)) { // 스킬 공격
				battle.attackMonsterBySkill();
			}else if("2".equals(input)) { // 나가기
				battle.setStop(true);
				Quest quest = playCharacter.getQuestInQuestList(); // 진행중인 퀘스트
				/* 진행중인 퀘스트가 있다면 */
				if(quest != null) {
					mapManager.updateQuestInfo(); // 퀘스트 정보 업데이트
					
					/* 전투를 마치고 퀘스트가 완료됐는지 확인한다. */
					if(quest.isCompleteQuest()) {
						systemMsg.add("[퀘스트] "+quest.getQuestTitle()+" 조건을 충족했습니다." );
						quest.setType(1);
							String template = "";
							String blank = "                                                     ";
							template+=blank+"          ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
							template+=blank+"          ┃            퀘스트 완료 조건 충족          ┃\n";                     		
							template+=blank+"          ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
							template+=blank+"          ┃           퀘스트를 완료했습니다.          ┃\n";
							template+=blank+"          ┃                                           ┃\n";
							template+=blank+"          ┃          1.NPC 위치까지 바로가기          ┃\n"; 
							template+=blank+"          ┃                                           ┃\n"; 
							template+=blank+"          ┃          2.게임 화면으로 돌아가기         ┃\n"; 
							template+=blank+"          ┃                                           ┃\n"; 
							template+=blank+"          ┃                                           ┃\n"; 
							template+=blank+"          ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n";
						System.out.println(template);
						printSystemMsg(new LinkedList<String>(), -6);
						System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
						System.out.println("1. 자동 이동 2. 나가기");
						String questInput = sc.nextLine();
						if("1".equals(questInput)) {
							FindPathThread findPathThread = new FindPathThread(playCharacter.getLocation()
									, quest.getQuestNPCLocation(), mapManager, systemMsg);
							findPathThread.start();
							systemMsg.add("[시스템] 자동 길찾기를 시작합니다.");
							isClose = true;
						}else if("2".equals(questInput)) {
							isClose = true;
						}
					}
				}
				isClose = true;
			}
			
		}
		battleMusic.playStop();
		mapManager.getCurrentMap().reStartBgm();
		
		if(battle.getMonster().isDie() && battle.getMonster().getName().equals("안용")) {
			isBossDie = true;
		}
		return isBossDie;
	}
	
	/**
	 * 상인 NPC와 아이템을 거래한다.
	 */
	public void trade() {
		mapManager.getCurrentMap().pauseBgm();
		Music music = new Music("/Users/sim-younghyun/Downloads/store.mp3", true);
		music.start();
		LinkedList<String> tradeMsg = new LinkedList<String>();
		boolean isClose = false;
		Merchant merchant = npcManager.getMerchantByLocation(playCharacter.getLocation());
		List<Item> saleList = merchant.getSaleList() ;
		List<Item> inventoryList = playCharacter.getInventory().getItemList();
		
		/* 상인과 거래 메뉴, 사용자 입력 받아 처리하는 과정 */
		while(isClose == false) {
			
			// 상인의 판매 목록, 캐릭터의 인벤토리 현황을 출력한다.
			printStoreList(saleList, inventoryList, merchant.getName());
			
			// 상인과 거래 내용을 메시지로 출력한다.
			printSystemMsg(tradeMsg,9);
			
			System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
			System.out.println("1.구입 2.판매 3.나가기");
			String input = sc.nextLine();
			
			/* 상인과 거래 메뉴, 사용자의 입력을 받아 처리한다. */
			if("1".equals(input)) {	// 구입
				System.out.println("구입할 아이템 번호를 입력해 주세요");
				int buyInput = Integer.parseInt(sc.nextLine());
				
				// 판매목록의 크기 보다 입력 받은 값이 클 경우
				if(saleList.size() < buyInput) {
					tradeMsg.add("구입할 아이템이 존재하지 않습니다.");
					continue;
				}
				// 판매 목록의 아이템중 하나를 가져온다.
				Item item = saleList.get(buyInput-1);
				
				// 구매 개수 기본 값
				int buyCount = 1;
				
				// ID가 부여된 새로운 아이템을 하나 만든다.
				item = itemManager.createNewItem(item);
				
				// 대량 거래가 가능하다면 개수를 추가로 입력 받는다.
				if(item.isBulk()) {
					System.out.println("몇개를 구입 할까요?");
					buyCount = Integer.parseInt(sc.nextLine());
					item.setCount(buyCount);
				}
				
				// 아이템의 판매 가격과 개수를 곱해 가격을 구한다.
				int price = item.getPurchasePrice() * buyCount;
				
				// 캐릭터가 보유한 금액과 아이템의 가격을 비교한다.
				if(price > playCharacter.getInventory().getMoney()) {
					tradeMsg.add("돈이 부족해 구매할 수 없습니다.");
					item.setCount(1);
					continue;
				}
				
				// 캐릭터가 구매한 아이템이 들어갈 공간이 있는지 확인한다.
				if(!playCharacter.getInventory().isSave()) {
					tradeMsg.add("더이상 아이템이 들어갈 공간이 없습니다.");
					item.setCount(1);
					continue;
				}
				
				Music buy = new Music("/Users/sim-younghyun/Downloads/money.mp3", false);
				buy.start();
				
				// 거래 메시지 저장 및 캐릭터의 보유금 차감
				tradeMsg.add("-"+price+"원");
				tradeMsg.add("["+item.getName()+"] "+buyCount+"개를 구입했습니다.");
				playCharacter.getInventory().setMoney(playCharacter.getInventory().getMoney()-price); //지불
				playCharacter.getInventory().addItem(item);
			}else if("2".equals(input)) {	// 판매
				System.out.println("판매할 아이템 번호를 입력해 주세요");
				int saleInput = Integer.parseInt(sc.nextLine());
				
				// 인벤토리의 아이템 목록 크기와 입력값을 비교한다. 
				if(inventoryList.size() < saleInput) {
					tradeMsg.add("판매할 아이템이 존재하지 않습니다.");
					continue;
				}
				
				// 인벤토리에서 아이템을 하나 꺼낸다.
				Item item = inventoryList.get(saleInput-1);
				
				// 판매 개수 초기값
				int saleCount = 1;
				
				// 아이템의 개수가 1개보다 많을 경우 몇개를 팔지 입력받는다.
				if(item.getCount() > 1) {
					System.out.println("몇개를 판매할까요?");
					saleCount = Integer.parseInt(sc.nextLine());
					if(item.getCount() < saleCount) {
						tradeMsg.add("소유한 개수를 초과했습니다.");
						continue;
					}
				}
				
				// 거래 메시지 저장 및 캐릭터의 보유금 증가
				int money = item.getSalePrice()*saleCount;
				tradeMsg.add("+"+money+"원");
				tradeMsg.add("["+item.getName()+"] "+saleCount+"개를 판매했습니다.");
				playCharacter.getInventory().deleteItem(item, saleCount);
				playCharacter.getInventory().setMoney(playCharacter.getInventory().getMoney()+money);
				
				Music sale = new Music("/Users/sim-younghyun/Downloads/money.mp3", false);
				sale.start();
			}else if("3".equals(input)) { // 거래를 중지한다.
				isClose = true;
			}
		}
		music.playStop();
		mapManager.getCurrentMap().reStartBgm();
	}
	
	/**
	 * 상인 판매목록, 캐릭터 인벤토리 템플릿을 가져와 필요 정보를 넣고 콘솔창에 출력한다.
	 * @param saleList	: 상인의 판매 목록
	 * @param inventoryList : 인벤토리 아이템 목록
	 * @param storeName : 상인의 이름
	 */
	public void printStoreList(List<Item> saleList, List<Item> inventoryList, String storeName) {
		String template = storeTempl();
		
		template = template.replace("npcName"		, getStrPlusSpace(storeName,42));
		template = template.replace("characterName", getStrPlusSpace(playCharacter.getCharacterId()+"님의 인벤토리",42));
		
		int saleSize = saleList.size();
		for(int i=0; i<10; i++) {
			if(saleSize > i) {
				template = template.replace("buy"+i, getStrPlusSpace(saleList.get(i).getName(), 28));
				template = template.replace("buyPrice"+i, getStrPlusSpace(saleList.get(i).getPurchasePrice()+"", 8));
			}else {
				template = template.replace("buy"+i, getStrPlusSpace("", 28));
				template = template.replace("buyPrice"+i, getStrPlusSpace("", 8));
			}
		}
		
		int invenSize = inventoryList.size();
		for(int i=0; i<10; i++) {
			if(invenSize > i) {
				template = template.replace("sale"+i, getStrPlusSpace(inventoryList.get(i).getName()+" x"+inventoryList.get(i).getCount(), 28));
				template = template.replace("salePrice"+i, getStrPlusSpace(inventoryList.get(i).getSalePrice()*inventoryList.get(i).getCount()+"", 8));
			}else {
				template = template.replace("sale"+i, getStrPlusSpace("", 28));
				template = template.replace("salePrice"+i, getStrPlusSpace("", 8));
			}
		}
		template = template.replace("money", getStrPlusSpace(playCharacter.getInventory().getMoney()+"원", 76));
		
		System.out.println(template);
	}
	
	/**
	 * 상인 판매 목록, 캐릭터 인벤토리 문자열 템플릿을 반환한다.
	 * @return
	 */
	public String storeTempl() {
		String spaceBlank = "                                                               ";
		String str = "";
		  str+=spaceBlank+"┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
		  str+=spaceBlank+"┃npcName┃characterName┃\n";                     		
		  str+=spaceBlank+"┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		  str+=spaceBlank+"┃구매┃판매 목록                   ┃가격    ┃판매┃아이템 목록                 ┃가격    ┃\n";
		  str+=spaceBlank+"┣━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━╋━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━┫\n";
		  str+=spaceBlank+"┃  1 ┃buy0┃buyPrice0┃  1 ┃sale0┃salePrice0┃\n"; 
		  str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━╋━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━┫\n"; 
		  str+=spaceBlank+"┃  2 ┃buy1┃buyPrice1┃  2 ┃sale1┃salePrice1┃\n"; 
		  str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━╋━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━┫\n"; 
		  str+=spaceBlank+"┃  3 ┃buy2┃buyPrice2┃  3 ┃sale2┃salePrice2┃\n"; 
		  str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━╋━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━┫\n"; 
		  str+=spaceBlank+"┃  4 ┃buy3┃buyPrice3┃  4 ┃sale3┃salePrice3┃\n"; 
		  str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━╋━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━┫\n"; 
		  str+=spaceBlank+"┃  5 ┃buy4┃buyPrice4┃  5 ┃sale4┃salePrice4┃\n"; 
		  str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━╋━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━┫\n"; 
		  str+=spaceBlank+"┃  6 ┃buy5┃buyPrice5┃  6 ┃sale5┃salePrice5┃\n"; 
		  str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━╋━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━┫\n"; 
		  str+=spaceBlank+"┃  7 ┃buy6┃buyPrice6┃  7 ┃sale6┃salePrice6┃\n"; 
		  str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━╋━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━┫\n"; 
		  str+=spaceBlank+"┃  8 ┃buy7┃buyPrice7┃  8 ┃sale7┃salePrice7┃\n"; 
		  str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━╋━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━┫\n"; 
		  str+=spaceBlank+"┃  9 ┃buy8┃buyPrice8┃  9 ┃sale8┃salePrice8┃\n"; 
		  str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━╋━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━┫\n"; 
		  str+=spaceBlank+"┃ 10 ┃buy9┃buyPrice9┃ 10 ┃sale9┃salePrice9┃\n"; 
		  str+=spaceBlank+"┣━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━┻━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━┫\n"; 
		  str+=spaceBlank+"┃소지금 : money┃\n";
		  str+=spaceBlank+"┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n";
		  return str;
	}
	
	//받아온 문자를 공백 개수단위로 계산해서 총공백 개수보다 부족할 경우 뒤에 추가 하여 반환
	public String getStrPlusSpace(String str,int totalSpace) {
		String result = "";
		int strLength = getSpaceCountFromStr(str);
		for(int i = 0; i < totalSpace - strLength; i++) {
			result+=" ";
		}
		return str+result;
	}
	
	public int getSpaceCountFromStr(String str) {
		int result = 0;
		for(int i =0; i < str.length(); i++) {
			int index = str.charAt(i);
			if(index >= 48 && index <= 57) { //숫자
				result++;
            } else if(index >= 65 && index <= 122) {//영어
            	result++;
            }else if(index == 9472 || index == 9474 || index == 9484 || index == 9488 || index == 9496 
            		|| index == 9492 || index == 9500 ||index == 9516 ||index == 9508 || index == 9524 
            		||index == 9532 || index == 9473 || index == 9475 || index == 9487
                	|| index == 9491 || index == 9499 || index == 9495 || index == 9507
                	|| index == 9523 || index == 9515 || index == 9531 || index == 9547
                	|| index == 9632 || index == 9633
            		) { //─│┌┐┘└├┬┤┴┼ ━ ┃ ┏ ┓ ┛ ┗ ┣ ┳┫ ┻ ╋■□
            	result ++;
            }else if(index == 47 || index == 58 || index == 40 || index == 41) { // /:()
            	result ++;
            }else if(index == 32) {//공백
            	result ++;
            }else {//한글
                result +=2;
            }
		}
		return result;
	}
	
	/**
	 * 캐릭터 이동 값에 해당하는 문자열을 systemMsg에 저장한다.
	 * @param input : 이동키(a,s,w,d)중 하나의 값
	 */
	public void saveSystemMsgFromMove(String input) {
		switch (input) {
		case "a": systemMsg.add("서쪽으로 이동합니다.");
			break;
		case "s": systemMsg.add("남쪽으로 이동합니다.");
			break;
		case "d":systemMsg.add("동쪽으로 이동합니다.");
			break;
		case "w":systemMsg.add("북쪽으로 이동합니다.");
			break;
		}
	}
	
	/**
	 * 콘솔 창에 게임 진행에 필요한 메시지를 출력한다.
	 * 
	 * @param colCount : 출력되는 총 행의 수에서 colCount의 크기만큼 차감 후 출력된다.
	 */
	public void printSystemMsg(LinkedList<String> systemMsg,int colCount) {
		int msgLength = 33 - colCount;
		String msg[] = new String[msgLength];
		for(int i=0; i<msg.length; i++) {
			msg[i] = "";
		}
		Iterator<String> it = systemMsg.descendingIterator();
		int index = 0;
		while(index < msg.length && it.hasNext()) {
			msg[index] = it.next();
			index++;
		}
		
		for(int i = msg.length-1 ; i >=0; i--) {
			System.out.println(msg[i]);
		}
	}
	
	/**
	 * 로그인한 유저가 게임에 접속할 캐릭터를 선택한다.
	 */
	public void startCharacterSelectionProcess() {
		boolean isSelected = false;
		while(isSelected == false) {
			int characterCount = player.getCharacterCount();
			System.out.println("현재 캐릭터 수 : "+characterCount);
			
			/* 캐릭터 수에 따른 선택지 변화 */
			if(characterCount == 0) {
				printNoCharacter();
				System.out.println("1.캐릭터 생성");
				String input = sc.nextLine();
				if("1".equals(input)) {
					createCharacter();
				}
			}else {
				System.out.println("1.캐릭터 선택 2.캐릭터 생성");
				String input = sc.nextLine();
				if("1".equals(input)) {
					selectCharacter();
					isSelected = true;
				}else if("2".equals(input)) {
					createCharacter();
				}
			}
		}
	}
	
	/**
	 * 유저가 보유한 캐릭터 목록을 보여주고 하나를 선택한다.
	 */
	public void selectCharacter() {
		System.out.println(player.getCharacterListStr());
		boolean isSelected = false;
		
		/* 사용자의 캐릭터 선택 과정 */
		while(isSelected == false) {
			printCharacterList();
			System.out.println("접속할 캐릭터 번호를 입력해주세요.");
			try {
				int input = Integer.parseInt(sc.nextLine())-1;
				
				/* 목록에 없는 숫자를 입력한 경우 */
				if(player.getCharacterCount() < input || input < 0) {
					continue;
				}
				
				playCharacter = player.selectCharacter(input);
				isSelected = true;
			}catch (NumberFormatException e) {
				System.out.println("숫자를 입력해 주세요.");
				continue;
			}
		}
	}
	
	/**
	 * 유저의 새로운 캐릭터를 만든다.
	 */
	public void createCharacter() {
		System.out.println("[1].전사   : 워리어 가문이 가지는 직업으로 검을 사용한다.\n             HP가 30%미만일때 일정 확률로 공격력이 2배가 된다. 기초 체력이 높다.");
		System.out.println();
		System.out.println("[2].마법사 : 위자드 가문이 가지는 직업으로 마법을 사용한다.\n             기초 체력이 낮은 편이지만 세 가문중 사용할 수 있는 스킬의 수가 가장 많다.");
		System.out.println();
		System.out.println("[3].궁수   : 아처 가문이 가지는 직업으로 활과 화살을 사용한다.\n             뛰어난 민첩력으로 적의 공격을 회피할 수 있다. 초기 자금이 제일 많다.");
		boolean isComplete = false;
		while(isComplete == false) {
			System.out.println("생성할 캐릭터 직업 번호를 입력해주세요.");
			String input = sc.nextLine();
			if("1".equals(input)) {		// 전사
				System.out.println("전사를 선택하셨습니다.");
				System.out.println("사용할 캐릭터 이름을 입력해주세요.");
				String id = sc.nextLine();
				Character character = new Warrior(id);
				player.addCharacter(character);
				isComplete = true;
			}else if("2".equals(input)) {	// 마법사
				System.out.println("마법사를 선택하셨습니다.");
				System.out.println("사용할 캐릭터 이름을 입력해주세요.");
				String id = sc.nextLine();
				Character character = new Wizard(id);
				player.addCharacter(character);
				isComplete = true;
			}else if("3".equals(input)) {	// 궁수
				System.out.println("궁수를 선택하셨습니다.");
				System.out.println("사용할 캐릭터 이름을 입력해주세요.");
				String id = sc.nextLine();
				Character character = new Archer(id);
				player.addCharacter(character);
				isComplete = true;
			}
		}
	}
	
	/**
	 * 사용자가 선택할 수 있는 캐릭터가 없을때 콘솔창에 보여진다.
	 */
	public void printNoCharacter() {
		String result = "";
		result += "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                        캐릭터가 존재하지 않습니다.                                                       ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┃                                                                                                                                          ┃\n";
		result += "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n";
		System.out.println(result);
	}
	
	/**
	 * 접속한 사용자가 선택할 수 있는 캐릭터 목록을 콘솔창에 보여준다.
	 */
	public void printCharacterList() {
		int count = player.getCharacterCount();	// 보유 캐릭터 수
		String selectBox[] = new String[((count-1)/6+1)*5];	
		for(int i =0; i<selectBox.length; i++) {
			selectBox[i] = "";
		}
		List<Character> characterList = player.getCharacterList();
		int index = 0;
		
		/* 캐릭터 정보를 문자열에 담는다. */
		for(Character character : characterList) {
			int boxCol = index / 6 * 5;
			String head = "";
			if(index < 10) {
				head = "┏━━━━━━━["+(index+1)+"]━━━━━━━━┓";
			}else {
				head = "┏━━━━━━━["+(index+1)+"]━━━━━━━┓";
			}
			String name = getStrPlusSpace(character.getCharacterId(),12);
			String level = getStrPlusSpace(character.getLv()+"",12);
			String job = getStrPlusSpace(character.getJob()+"",12);
			selectBox[boxCol] += head+" ";
			selectBox[boxCol+1] += "┃이름: "+name+"┃ ";
			selectBox[boxCol+2] += "┃레벨: "+level+"┃ ";
			selectBox[boxCol+3] += "┃직업: "+job+"┃ ";
			selectBox[boxCol+4] += "┗──────────────────┛ ";
			index++;
		}
		
		/* 캐릭터 목록의 테두리를 만든다. */
		String result = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
		for(int i = 0; i <21; i++) {
			if(selectBox.length > i) {
				result+="┃"+getStrPlusSpace(selectBox[i],138)+"┃\n";
			}else {
				result+="┃"+getStrPlusSpace("",138)+"┃\n";
			}
		}
		result += "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n";
		System.out.println(result);
	}
	
	/**
	 * 게임의 배경 설명을 출력한다.
	 */
	public void intro() {
		System.out.println();
		System.out.println();
		System.out.println();
		String intro = "";
		intro += "                                                             수니방스 대제국에는 제국을 지탱하는 3개의 왕족 가문이 있다.\n";
		intro += "                                                    검을 사용하는 워리어가문, 마법을 사용하는 위자드가문, 활을 사용하는 아처가문\n";
		intro += "                                                 천년동안 왕위를 번갈아 가며 제국을 평화롭게 통치하던 세 가문 사이에 균열이 생겼다.\n";
		intro += "                                              제국 건설 당시부터 전해 내려오는 왕위 계승 방침에 적혀 있는 단 하나의 예외사항이 발견된 것이다.\n";
		intro += "                                         '천년에 한번씩 마계에서 현세계로 넘어오는 대악마 군주를 처치하는 자, 이 나라의 진정한 왕이 될 것이다.'\n";
		intro += "                                                         대악마 군주가 출연하고 온갖 악마들이 설치며 세계를 피로 물들던 때에\n";
		intro += "                                                              전설의 용사 영현이 나타나 악마를 봉인 한지 999년이 흘렀다. \n";
		intro += "                                                    대악마를 예고 하듯이 마을 주변에는 보이지 않던 각종 몬스터들이 나타나기 시작했고\n";
		intro += "                                                      워리어, 위자드, 아처가문에서는 가문의 영광을 세우기위해, 제국을 지키기 위해 \n";
		intro += "                                                                              젊은 용사들을 출전시킨다.\n";
		intro += "                                                                     '대악마를 처치하면 누구나 왕이 될 수 있다.'\n";
		System.out.println(intro);
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	/**
	 * 사용자 로그인 과정을 처리한다.
	 */
	public void startLoginProcess() {
		
		/* 게임 배경 설명 출력 */
		intro();
		
		String id = "";		// 사용자 입력 아이디
		String pw = "";		// 사용자 입력 비밀번호
		boolean isClose = false;	// 로그인 과정 종료 여부
		
		/* 사용자 로그인 처리 및 신규 아이디 생성 로직 */
		while(isClose == false) {
			System.out.println();
			System.out.println("로그인이 필요합니다.");
			System.out.println("1.로그인 2.아이디 생성");
			String input = sc.nextLine();
			if("1".equals(input)) {			// 로그인
				boolean isLogin = false;
				
				/* 사용자 아이디/비밀번호 확인 */
				while(isLogin == false) {
					System.out.println("아이디를 입력해주세요. (아이디 생성 화면으로 돌아가기 : 1 )");
					id = sc.nextLine().trim();
					
					/* 아이디 생성 화면으로 돌아가기 */
					if("1".equals(id)) {
						break;
					}
					
					/* 입력받은 아이디와 일치하는 유저가 있는지 확인 */
					if(!hasUser(id)) {
						System.out.println("일치하는 아이디가 없습니다.");
						id="";
						pw="";
						continue;
					}
					
					System.out.println("비밀번호를 입력해주세요.");
					pw = sc.nextLine().trim();
					/* 입력받은 아이디, 비밀번호와 일치하는 유저가 있는지 확인 */
					if(!hasUser(id,pw)) {
						System.out.println("비밀번호가 일치하지 않습니다.");
						id="";
						pw="";
						continue;
					}
					
					isClose = true; // 로그인 과정 종료
					isLogin = true; // 로그인 성공
					
					/* 로그인 성공한 유저를 플레이어로 등록 */
					User user = getUser(id,pw);
					player = user;
					
				}
				
			}else if("2".equals(input)) {	// 아이디 생성
				boolean isValid = false;	// 아이디 및 비밀번호 유효성 체크
				String newId = "";		// 새로운 아이디
				String newPw = "";		// 새로운 비밀번호
				
				/* 사용자 신규 아이디 유효성 검사 */
				while(isValid == false) {
					System.out.println("사용할 아이디를 영문이나 숫자로 입력해주세요.(길이 제한 : 4~12글자)");
					newId = sc.nextLine().trim();
					
					/* 이미 존재하는 아이디 인지 확인 */
					if(hasUser(newId)) {
						System.out.println("이미 존재하는 아이디입니다.");
						continue;
					}
					
					/* 아이디가 영문과 숫자로만 이루어졌는지 확인 */
					boolean hasHangle = false;
					for(int i=0; i<newId.length(); i++) {
						char ch = newId.charAt(i);
						if(ch >= 0x61 && ch <= 0x7A || ch >= 0x41 && ch <= 0x5A || ch >= 0x30 && ch <= 0x39) {
							hasHangle = false;
						}else {
							hasHangle = true;
							break;
						}
					}
					if(hasHangle) {
						System.out.println("아이디는 영문/숫자만 가능합니다.");
						continue;
					}
					
					if(newId.length() < 4 || newId.length() > 12) {
						System.out.println("아이디의 길이는 4 ~ 12글자 이내로 가능합니다.");
						newId = "";
						newPw = "";
						continue;
					}
					while(isValid == false) {
						System.out.println("비밀번호를 입력해주세요.(길이 제한 : 4~12글자)");
						newPw = sc.nextLine().trim();
						if(newPw.length() < 4 || newPw.length() > 12) {
							System.out.println("비밀번호의 길이는 4 ~ 12글자 이내로 가능합니다.");
							newId = "";
							newPw = "";
							continue;
						}
						isValid = true;
					}
				}
				
				/* 유저 등록 */
				createUser(newId, newPw);
				System.out.println("아이디가 등록되었습니다.");
			}
		}
	}
	
	/**
	 * 인자로 들어온 아이디가 유저 목록에 있는지 확인한다.
	 * 
	 * @param userId
	 * @return true : 유저 아이디 존재, false : 유저 아이디 없음  
	 */
	public boolean hasUser(String userId) {
		boolean result = false;
		for(User user : userList) {
			if(userId.equals(user.getUserId())) {
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 인자로 들어온 아이디,비밀번호와 일치하는 유저가 있는지 유저 목록에서 확인한다.
	 * 
	 * @param userId
	 * @param userPw
	 * @return true : 유저 아이디/비밀번호 모두 일치, false : 유저 아이디/비밀번호 불일치
	 */
	public boolean hasUser(String userId, String userPw) {
		boolean result = false;
		for(User user : userList) {
			if(userId.equals(user.getUserId()) && userPw.equals(user.getPassword())) {
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 유저 목록에서 아이디와 비밀번호가 일치하는 유저를 반환
	 * 
	 * @param userId
	 * @param userPw
	 * @return User
	 */
	public User getUser(String userId, String userPw) {
		User player = null;
		for(User user : userList) {
			if(userId.equals(user.getUserId()) && userPw.equals(user.getPassword())) {
				player = user;
			}
		}
		return player;
	}
	
	/**
	 * 유저 목록에 유저 등록
	 * 
	 * @param userId
	 * @param userPw
	 * @return true : 정상 등록, false : 이미 같은 아이디가 존재할 경우 
	 */
	public boolean createUser(String userId, String userPw) {
		if(hasUser(userId)) {
			return false;
		}
		User user = new User(userId, userPw);
		userList.add(user);
		return true;
	}

	public LinkedList<String> getSystemMsg() {
		return systemMsg;
	}

	public MapManager getMapManager() {
		return mapManager;
	}
	
	
	
}
