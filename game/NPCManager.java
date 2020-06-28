package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.assets.Tile;

/**
 * 클래스 명 : NPCManager class
 * 설명 : 모든 NPC를 생성하고 목록으로 관리한다.
 * @author sim-younghyun
 *
 */
public class NPCManager {
	/**
	 * 몬스터 NPC 목록
	 */
	private List<Monster> monsterList;
	
	/**
	 * 상인 NPC 목록
	 */
	private List<Merchant> merchantList;
	
	/**
	 * 퀘스트 NPC 목록
	 */
	private List<QuestNPC> questNPCList;
	
	/**
	 * 상인, 몬스터가 가질 아이템목록을 만드는데 사용
	 */
	ItemManager itemManager;
	
	public NPCManager(ItemManager itemManager) {
		this.itemManager = itemManager;
		monsterList = new ArrayList<Monster>();
		merchantList = new ArrayList<Merchant>();
		questNPCList = new ArrayList<QuestNPC>();
		
		/* 몬스터 생성 */
		Monster orkWarrior 	= new Monster(1,	100, 	0, 		10, 10, "physic",	"오크 전사"		,15000	,"forest1-0-1", "\u001b[38;2;88;88;250m거대한 몽둥이를 휘두르는 오크 전사가 나타났습니다.\u001b[0m", Tile.ORK.CODE);
		Monster orkShaman 	= new Monster(3,	100, 	100,	30, 30, "magic", 	"오크 주술사"	,20	,"forest1-1-1", "\u001b[38;2;88;88;250m'%$#!@#' 오크 주술사가 주문을 외우고 있습니다.\u001b[0m", Tile.ORK.CODE);
		Monster orkElite 	= new Monster(5,	300, 	0, 		50, 30, "physic", 	"오크 정예병"	,30	,"forest1-4-5", "\u001b[38;2;88;88;250m무언가 예사롭지 않은 오크가 보입니다.\u001b[0m", Tile.ORK.CODE);
		
		Monster spiderBig 		= new Monster(10,	300, 	0, 		100, 100, "physic", 	"거대 거미"		,500	,"forest2-0-1", "\u001b[38;2;88;88;250m맹독을 가진 거대 거미가 나타났습니다.\u001b[0m", Tile.SPIDER.CODE);
		Monster spiderShaman 	= new Monster(15,	400, 	0, 		100, 100, "physic", 	"주술 거미"		,600	,"forest2-1-1", "\u001b[38;2;88;88;250m주술을 부리는 거미가 나타났습니다.\u001b[0m", Tile.SPIDER.CODE);
		Monster spiderCute 		= new Monster(20,	500, 	0, 		100, 100, "physic", 	"귀욤 거미"		,700	,"forest2-4-1", "\u001b[38;2;88;88;250m그나마 귀여운 거대 거미가 나타났습니다.\u001b[0m", Tile.SPIDER.CODE);
		Monster spiderDevil 	= new Monster(25,	600, 	0, 		100, 100, "physic", 	"대마왕 거미"	,800	,"forest2-4-5", "\u001b[38;2;88;88;250m대마왕 거미가 나타났습니다.\u001b[0m", Tile.SPIDER.CODE);

		Monster cowBig 		= new Monster(30,	600, 	0, 		100, 100, "physic", 	"거대 들소"		,800	,"forest3-2-0", "\u001b[38;2;88;88;250m거대들소가 나타났습니다.\u001b[0m", Tile.COW.CODE);
		Monster cowCute 	= new Monster(35,	600, 	0, 		200, 200, "physic", 	"귀여운 들소"	,800	,"forest3-0-1", "\u001b[38;2;88;88;250m귀여운 들소가 무서운척 합니다.\u001b[0m", Tile.COW.CODE);
		Monster cowFail 	= new Monster(40,	600, 	0, 		300, 300, "physic", 	"내가 젖소"		,800	,"forest3-1-1", "\u001b[38;2;88;88;250m패배를 모르는 내가 젖소가 나타났습니다.\u001b[0m", Tile.COW.CODE);
		Monster cowNormal 	= new Monster(45,	6000, 	0, 		300, 300, "physic", 	"나는그냥소"	,800	,"forest3-4-5", "\u001b[38;2;88;88;250m무척 강해보이는 그냥 소가 나타났습니다.\u001b[0m", Tile.COW.CODE);
		
		Monster dragon 			= new Monster(50,	4000, 	0, 		400, 400, "physic", 	"용"		,800	,"forest4-0-3", "\u001b[38;2;88;88;250m엄청난 힘이 느껴지는 용이 나타났습니다.!!\u001b[0m", Tile.DRAGON.CODE);
		Monster dragonBig 		= new Monster(55,	4000, 	0, 		400, 400, "physic", 	"커용"		,800	,"forest4-2-0", "\u001b[38;2;88;88;250m거대한 산이 움직입니다. 커용이 나타났습니다.\u001b[0m", Tile.DRAGON.CODE);
		Monster dragonSmall 	= new Monster(60,	4000, 	0, 		400, 400, "physic", 	"작아용"	,800	,"forest4-4-0", "\u001b[38;2;88;88;250m커용의 동생 작아용이 나타났습니다.\u001b[0m", Tile.DRAGON.CODE);
		Monster dragonHi 		= new Monster(70,	4000, 	0, 		400, 400, "physic", 	"안용"		,800	,"forest4-4-5", "\u001b[38;2;88;88;250m용들의 왕, 안용이 나타났습니다.! 안뇽!\u001b[0m", Tile.DRAGON.CODE);
		
		/* 몬스터 드랍 아이템 넣기 */
		Item orkWarriorDropItem = itemManager.getDropItemOneByName("낡은 갑옷");
		orkWarrior.setItem(orkWarriorDropItem);
		
		monsterList.add(orkWarrior);
		monsterList.add(orkShaman);
		monsterList.add(orkElite);
		
		monsterList.add(spiderBig);
		monsterList.add(spiderShaman);
		monsterList.add(spiderCute);
		monsterList.add(spiderDevil);
		
		monsterList.add(cowBig);
		monsterList.add(cowCute);
		monsterList.add(cowFail);
		monsterList.add(cowNormal);
		
		monsterList.add(dragon);
		monsterList.add(dragonBig);
		monsterList.add(dragonSmall);
		monsterList.add(dragonHi);
		
		/* 상인이 파는 아이템 목록 가져오기, 상인 생성 하기 */
		List<Item> baseMerchantItemList 
			=  itemManager.getStoreItemListByNames("체력 회복 물약(소)", "체력 회복 물약(중)", "체력 회복 물약(대)"
					,"마력 회복 물약(소)", "마력 회복 물약(중)", "마력 회복 물약(대)", "낡은 갑옷", "운영자의 갑옷", "운영자의 무기");
		
		Merchant baseVilageMerchant = new Merchant(baseMerchantItemList, "황씨가게 아저씨", "merchant", "base1-0-4");
		
		/* 상인 목록에 NPC 넣기 */
		merchantList.add(baseVilageMerchant);
		
		/* 퀘스트 NPC 생성 */
		int questId = 0; // 퀘스트를 식별할때 사용 하는 번호, 겹치지 않는다.
		String orkQuestContent = "요즘 마을앞에 몬스터들이 자주 출몰하고 있습니다. 괘씸한 오크족들이 나타나 주민들을 괴롭히고 농작물을 파괴하고 있습니다. "
				+ "우리들끼리 힘을 합쳐 맞서보았지만 오크족은 너무 강했습니다. 부디 저희를 도와주세요.";
		String orkQuestTitle = "오크 처치하기";
		String orkQuestMonster = "오크 전사:1,오크 주술사:1,오크 정예병:1";
		String orkQuestProgress = "제가 부탁한일을 잊으신건 아니죠? 숲속에서 오크 전사를 본것 같아요. 빨리 처리해주세요.";
		String orkQuestComplete = "와~ 정말 대단하세요! 해내실줄 알았습니다. 저희 마을에 평화가 찾아왔네요. 정말 감사합니다.";
		String orkNormalContent = "오늘 날씨가 참 좋죠?";
		String orkQuestDetailContent = "요즘 마을앞에 몬스터들이 자주 출몰하고 있습니다. 마을 앞 숲속에 숨어 있는 오크족들을 모두 몰아내면 합당한 보상이 주어집니다.";
		QuestNPC orkQuest = new QuestNPC("평범한 마을 주민", "quest", "forest1-1-3"
				, orkQuestContent, orkQuestTitle, orkQuestMonster, questId, orkQuestProgress, orkQuestComplete
				, orkQuestDetailContent, orkNormalContent);
		
		questNPCList.add(orkQuest);
	}

	/**
	 * 퀘스트 NPC의 위치와 일치하는 QuestNPC 반환 
	 * @param location : QuestNPC의 위치
	 * @return Merchant
	 */
	public QuestNPC getQuestNPCByLocation(String location) {
		Iterator<QuestNPC> it = questNPCList.iterator();
		QuestNPC questNPC = null;
		while(it.hasNext()) {
			QuestNPC temp = it.next();
			if(temp.getNpcType().equals("quest") && temp.getLocation().equals(location)) {
				questNPC = (QuestNPC)temp;
			}
		}
		return questNPC;
	}
	
	/**
	 * Merchant의 위치와 일치하는 Merchant 반환 
	 * @param location : Merchant의 위치
	 * @return Merchant
	 */
	public Merchant getMerchantByLocation(String location) {
		Iterator<Merchant> it = merchantList.iterator();
		Merchant merchant = null;
		while(it.hasNext()) {
			Merchant temp = it.next();
			if(temp.getNpcType().equals("merchant") && temp.getLocation().equals(location)) {
				merchant = (Merchant)temp;
			}
		}
		return merchant;
	}
	
	/**
	 * 몬스터의 위치를 인자로 받아 해당 위치에 있는 몬스터 객체를 반환한다.
	 * 
	 * @return Monster
	 */
	public Monster getMonsterByLocation(String location) {
		Monster monster = null;
		Iterator<Monster> it = monsterList.iterator();
		while(it.hasNext()) {
			NPC temp = it.next();
			if("monster".equals(temp.getNpcType()) && location.equals(temp.getLocation())) {
				Monster monTemp = (Monster)temp;
				int level = monTemp.getLevel();
				int hp = monTemp.getHp();
				int mp = monTemp.getMp();
				int earnMoney = monTemp.getEarnMoney();
				int offensePower = monTemp.getOffensePower();
				String monsterType = monTemp.getMonsterType();
				String name = monTemp.getName();
				int exp = monTemp.getExp();
				String monsterXY = monTemp.getLocation();
				String msg = monTemp.getSystemMsg();
				String[] monsterGraphic = monTemp.getBattleFront();
				monster = new Monster(level, hp, mp, earnMoney, offensePower, monsterType, name, exp, monsterXY, msg, monsterGraphic);
				monster.setItem(monTemp.getItem());
			}
		}
		return monster;
				
	}
	
	public List<Monster> getMonsterList() {
		return monsterList;
	}

	public void setMonsterList(List<Monster> monsterList) {
		this.monsterList = monsterList;
	}

	public List<Merchant> getMerchantList() {
		return merchantList;
	}

	public void setMerchantList(List<Merchant> merchantList) {
		this.merchantList = merchantList;
	}
	
	
}
