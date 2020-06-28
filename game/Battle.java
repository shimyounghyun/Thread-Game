package game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 캐릭터와 몬스터를 변수로 갖고 있고 전투를 실행할 수 있다.
 * 
 * @author sim-younghyun
 *
 */
public class Battle {
	/**
	 * 캐릭터
	 */
	Character character;
	
	/**
	 * 몬스터
	 */
	Monster monster;
	
	/**
	 * 시스템 메시지 목록 
	 */
	LinkedList<String> systemMsg;
	
	/**
	 * 전투 메시지 목록
	 */
	LinkedList<String> battleMsg;
	
	/**
	 * 아이템을 관리
	 */
	ItemManager itemManager;
	
	Scanner sc;
	
	public Battle(Character character, Monster monster, LinkedList<String> systemMsg, ItemManager itemManager) {
		this.character = character;
		this.monster = monster;
		this.systemMsg = systemMsg;
		this.battleMsg = new LinkedList<String>();
		this.sc = new Scanner(System.in);
		this.itemManager = itemManager;
	}
	
	/**
	 * 전투를 시작한다.
	 */
	public void start() {
		boolean isComplete = false;
		String job = character.getJob();
		while(isComplete == false) {
			
			// 캐릭터와 몬스터의 체력, 마력을 문자열로 출력한다.
			battleDisplay();
			
			// 캐릭터와 몬스터의 이미지 문자열을 출력한다.
			battleGraphic();
			
			// 전투 메시지를 출력한다.
			printSystemMsg(battleMsg, 10);
			
			System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
			System.out.println("1.공격하기 2.스킬 공격하기 3.도망가기");
			
			String input = sc.nextLine();
			if("1".equals(input)) {	// 공격하기
				int attack = 0;
				if("warrior".equals(job)) {
					Warrior temp = (Warrior)character;
					attack = temp.attack();
				}else if("wizard".equals(job)) {
					Wizard temp = (Wizard)character;
					attack = temp.attack();
				}else if("archer".equals(job)) {
					Archer temp = (Archer)character;
					attack = temp.attack();
				}
				
				// 캐릭터가 몬스터를 공격, 공격 메세지 저장
				monster.setDamage(attack); 
				battleMsg.add(character.getAttackMsg(monster, attack)); //공격 메세지 저장
				int monsterAttack = monster.attack();
				int resultDamage = character.setDamage(monsterAttack);
				battleMsg.add(character.getDamageMsg(monster, resultDamage)); //피해 메시지 저장
				
				// 캐릭터가 죽었을 경우
				if(character.isDie()) {
					character.resurrection();				
					systemMsg.add("죽은 후 부활했습니다.");
					break;
				}
				
				
			}else if ("2".equals(input)) { // 스킬 공격하기
				
				// 캐릭터의 마력이 스킬에 필요한 마력보다 적을 경우 사용하지 못한다.
				if(character.getMp() < character.getSkill().getMp()) {
					battleMsg.add("마력이 부족합니다.");
					continue;
				}
				
				// 캐릭터의 마력을 차감
				character.setMp(character.getMp() - character.getSkill().getMp());
				
				// 스킬 공격력을 구한다. 스킬 공격 메세지를 저장한다.
				int attackPoint = character.getSkill().getAttackPower() + character.getStat().getIntelligence();
				monster.setDamage(attackPoint);
				battleMsg.add(character.getSkill().getSystemMsg()+" "+attackPoint+"의 공격을 했습니다.");
				
				// 몬스터 공격력을 구한다.
				int monsterAttack = monster.attack();
				int resultDamage = character.setDamage(monsterAttack);
				battleMsg.add(character.getDamageMsg(monster, resultDamage)); //피해 메시지 저장
				
				// 캐릭터가 죽었을 경우
				if(character.isDie()) {
					character.resurrection();	// 캐릭터를 부활 시킨다.
					systemMsg.add("죽은 후 부활했습니다.");
					break;
				}
			}else if("3".equals(input)) { // 도망가기
				isComplete = true;
			}
			
			/* 몬스터가 죽었을 경우 처리한다. */
			if(monster.isDie()) {
				
				// 경험치 및 돈을 획득 한다.
				int exp = monster.getExp();
				int money = monster.getEarnMoney();
				character.getInventory().setMoney(money+character.getInventory().getMoney());
				systemMsg.add(monster.getName()+"을 처치하여 "+money+"원과 경험치 "+exp+"을 획득했습니다.");
				battleMsg.add(monster.getName()+"을 처치하여 "+money+"원과 경험치 "+exp+"을 획득했습니다.");
				character.addExp(exp,systemMsg);
				
				// 몬스터에게 아이템을 획득 했을 경우 
				if(monster.isDrop()) {
					Item item = monster.getItem();
					Item newItem = itemManager.createNewItem(item);
					
					// 캐릭터의 인벤토리에 아이템을 저장 가능한지를 판단한다.
					if(character.getInventory().isSave()) {	
						character.getInventory().addItem(newItem);
						systemMsg.add(newItem.getName()+"(을)를 획득했습니다.");
						battleMsg.add(newItem.getName()+"(을)를 획득했습니다.");
					}else {
						systemMsg.add("인벤토리 저장공간이 부족하여 더이상 아이템을 획득할 수 없습니다.");
						battleMsg.add("인벤토리 저장공간이 부족하여 더이상 아이템을 획득할 수 없습니다.");
					}
				}
				
				// 진행중인 퀘스트가 있을 경우
				Quest quest = character.getQuestInQuestList();
				if(quest != null) {
					List<QuestDetail> questList = quest.getQuestDetailList();
					int index = 0;
					for(QuestDetail temp : questList) {
						if(temp.getMonsterName().equals(monster.getName())) {
							int count = temp.getTargetAchievements();
							temp.setTargetAchievements(count++);
							questList.set(index, temp);
						}
						index++;
					}
				}
				
				break;
			}
		}
	}
	
	/**
	 * 콘솔 창에 게임 진행에 필요한 메시지를 출력한다.
	 * 
	 * @param colCount : 출력되는 총 행의 수에서 colCount의 크기만큼 차감 후 출력된다.
	 */
	public void printSystemMsg(LinkedList<String> systemMsg, int colCount) {
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
	 * 전투 모습을 그래픽 형태로 콘솔에 출력한다.
	 * 
	 * @param monster : 캐릭터와 전투할 몬스터
	 */
	public void battleGraphic() {
		// 최종 결과가 담길 문자열
		String result = "";
		
		// 캐릭터 전투 이미지 문자열
		String characterGraphic = "";
		
		// 몬스터 전투 이미지 문자열
		String monsterGraphic = "";
		
		// 캐릭터와 몬스터 사이의 여백 공간
		String space = "                                                                       ";
		
		// VS 문자 사이의 여백 공간
		String vs = "                                     ";
		
		/* 캐릭터와 문자 이미지 문자열을 합해서 result에 담는다. */
		for(int i=0; i< monster.getBattleFront().length; i++) {
			if(i % 2 == 0) characterGraphic += character.getBATTLE_FRONT()[i/2];
			monsterGraphic += monster.getBattleFront()[i];
			if((i+1) % 32 == 0) {
				result += space + characterGraphic + vs + monsterGraphic + "\u001b[0m\n";
				characterGraphic = "";
				monsterGraphic = "";
			}
		}
		System.out.println(result);
	}
	
	/**
	 * 캐릭터와 몬스터의 hp, mp 상태창을 문자열 형태로 출력한다.
	 * @param monster
	 */
	public void battleDisplay() {
		
		// 캐릭터, 몬스터의 정보를 담는다.
		String chId = character.getCharacterId();
		int chMaxHp = character.getMaxHp();
		int chMaxMp = character.getMaxMp();
		int chHp = character.getHp();
		int chMp = character.getMp();
		int chLv = character.getLv();
		String mId = monster.getName();
		int mLv = monster.getLevel();
		int mMaxHp = monster.getMaxHp();
		int mMaxMp = monster.getMaxmp();
		int mHp = monster.getHp();
		int mMp = monster.getMp();
		
		// 캐릭터의 이름과 레벨을 문자열로 만든다.
		String chName = "";
		if(getSpaceCountFromStr(chId) % 2 == 1) {
			chName+=" ";
		}
		if(chLv < 10) {
			chName += chId+"Lv0"+chLv;
		}else {
			chName += chId+"Lv"+chLv;
		}
		
		// 몬스터의 이름과 레벨을 문자열로 만든다.
		String mName = "";
		if(getSpaceCountFromStr(mId) % 2 == 1) {
			mName+=" ";
		}
		if(mLv < 10) {
			mName += mId+"Lv0"+mLv;
		}else {
			mName += mId+"Lv"+mLv;
		}
		
		// 캐릭터, 몬스터 이름을 공백길이로 변환한다.
		int characterNameLength = getSpaceCountFromStr(chName);
		int monsterNameLength = getSpaceCountFromStr(mName);
		
		// 캐릭터와 몬스터의 체력,마력을 문자열로 만든다.
		String characterHpBar = chHp +"/"+ chMaxHp;
		String characterMpBar = chMp +"/"+ chMaxMp;
		String monsterHpBar = mHp +"/"+ mMaxHp;
		String monsterMpBar = mMp +"/"+ mMaxMp;
		
		// 캐릭터의 체력 게이지, 마력 게이지를 문자열 공백 수를 구한다.
		int characterHpBarLength = getSpaceCountFromStr(characterHpBar);
		int characterMpBarLength = getSpaceCountFromStr(characterMpBar);
		if(characterHpBarLength % 2 == 0) characterHpBar += " ";
		if(characterMpBarLength % 2 == 0) characterMpBar += " ";
		
		int monsterHpBarLength = getSpaceCountFromStr(monsterHpBar);
		int monsterMpBarLength = getSpaceCountFromStr(monsterMpBar);
		if(monsterHpBarLength % 2 == 0) monsterHpBar += " ";
		if(monsterMpBarLength % 2 == 0) monsterMpBar += " ";
		
		characterHpBarLength = getSpaceCountFromStr(characterHpBar);
		characterMpBarLength = getSpaceCountFromStr(characterMpBar);
		monsterHpBarLength = getSpaceCountFromStr(monsterHpBar);
		monsterMpBarLength = getSpaceCountFromStr(monsterMpBar);
		
		// 캐릭터의 현재 hp, 최대 hp를 계산해 게이지바 문자열로 만든다.
		String characterHpBarStr = "";
		for(int i=0; i<20; i++) {
			if(chHp == 0 || chMaxHp == 0) {
				characterHpBarStr += "□";
			}else {
				int hp = chHp / (chMaxHp / 20);
				if(hp >= i) {
					characterHpBarStr += "■";
				}else {
					characterHpBarStr += "□";
				}
			}
		}
		
		// 캐릭터의 현재 mp, 최대 mp를 계산해 게이지바 문자열로 만든다.
		String characterMpBarStr = "";
		for(int i=0; i<20; i++) {
			if(chMp == 0 || chMaxMp == 0) {
				characterMpBarStr += "□";
			}else {
				int mp = chMp / (chMaxMp / 20);
				if(mp >= i) {
					characterMpBarStr += "■";
				}else {
					characterMpBarStr += "□";
				}
			}
		}
		
		// 몬스터의 현재 hp, 최대 hp를 계산해 게이지바 문자열로 만든다.
		String monsterHpBarStr = "";
		for(int i=0; i<20; i++) {
			if(mHp == 0 || mMaxHp == 0) {
				monsterHpBarStr += "□";
			}else {
				int hp = mHp / (mMaxHp / 20);
				if(hp >= i) {
					monsterHpBarStr += "■";
				}else {
					monsterHpBarStr += "□";
				}
			}
		}
		
		// 몬스터의 현재 mp, 최대 mp를 계산해 게이지바 문자열로 만든다.
		String monsterMpBarStr = "";
		for(int i=0; i<20; i++) {
			if(mMp == 0 || mMaxMp == 0) {
				monsterMpBarStr += "□";
			}else {
				int mp = mMp / (mMaxMp / 20);
				if(mp >= i) {
					monsterMpBarStr += "■";
				}else {
					monsterMpBarStr += "□";
				}
			}
		}
		
		// 캐릭터의 hp, mp중 길이가 긴 것을 기준으로 뒤에 공백을 붙인다.
		if(characterHpBarLength > characterMpBarLength) {
			for(int i=0; i < characterHpBarLength - characterMpBarLength; i++) {
				characterMpBar += " ";
			}
		}
		if(characterHpBarLength < characterMpBarLength) {
			for(int i=0; i < characterMpBarLength - characterHpBarLength; i++ ) {
				characterHpBar += " ";
			}
		}
		
		// 몬스터의 hp, mp중 길이가 긴 것을 기준으로 뒤에 공백을 붙인다.
		if(monsterHpBarLength > monsterMpBarLength) {
			for(int i=0; i < monsterHpBarLength - monsterMpBarLength; i++) {
				monsterMpBar += " ";
			}
		}
		if(monsterHpBarLength < monsterMpBarLength) {
			for(int i=0; i < monsterMpBarLength - monsterHpBarLength; i++ ) {
				monsterHpBar += " ";
			}
		}
		
		// 캐릭터, 몬스터의 상하단 꼭지점 문자열을 담는다.
		String cBarTopLine = "┏";
		String cBarMidLine = "┗";
		String mBarTopLine = "┏";
		String mBarMidLine = "┗";
		
		// 캐릭터, 몬스터의 이름 길이 만큼 테두리를 만든다.
		for(int i=0; i<characterNameLength/2; i++) {
			cBarTopLine += "━━";
			cBarMidLine += "━━";
		}
		for(int i=0; i<monsterNameLength/2; i++) {
			mBarTopLine += "━━";
			mBarMidLine += "━━";
		}
		
		// 캐릭터의 상단, 중단, 하단 테두리의 문자열을 담는다. 
		cBarTopLine += "┳━━━━━━━━━━━━━━━━━━━━━━━━";
		cBarMidLine += "╋━━━━━━━━━━━━━━━━━━━━━━━━";
		String cBarBotLine = "┗━━━━━━━━━━━━━━━━━━━━━━━━";
		
		// 몬스터의 상단, 중단, 하단 테두리의 문자열을 담는다.
		mBarTopLine += "┳━━━━━━━━━━━━━━━━━━━━━━━━";
		mBarMidLine += "╋━━━━━━━━━━━━━━━━━━━━━━━━";
		String mBarBotLine = "┗━━━━━━━━━━━━━━━━━━━━━━━━";
		
		// 캐릭터의 체력 길이에 맞는 테두리를 만든다.
		int characterFinalHpBar = getSpaceCountFromStr(characterHpBar);
		for(int i=0; i < (characterFinalHpBar+1)/2; i++) {
			cBarTopLine += "━━";
			cBarMidLine += "━━";
			cBarBotLine += "━━";
		}
		
		// 몬스터의 체력 길이에 맞는 테두리를 만든다.
		int monsterFinalHpBarLength = getSpaceCountFromStr(monsterHpBar);
		for(int i=0; i < (monsterFinalHpBarLength+1)/2; i++) {
			mBarTopLine += "━━";
			mBarMidLine += "━━";
			mBarBotLine += "━━";
		}
		
		// 캐릭터의 상단, 중단, 하단 마지막 꼭지점
		cBarTopLine += "┓";
		cBarMidLine += "┫";
		cBarBotLine += "┛";
		String cBarBlank = "";
		
		// 몬스터의 상단, 중단, 하단 마지막 꼭지점
		mBarTopLine += "┓";
		mBarMidLine += "┫";
		mBarBotLine += "┛";
		String mBarBlank = "";
		
		// 캐릭터와 몬스터의 이름뒤에 공백을 붙인다.
		for(int i = 0; i <characterNameLength+1;i++) {
			cBarBlank += " ";
		}
		for(int i = 0; i <monsterNameLength+1;i++) {
			mBarBlank += " ";
		}
		
		// 캐릭터의 hp, mp 테두리를 만든다.
		cBarBotLine = cBarBlank+cBarBotLine;
		String cBarInfo1 = "┃"+chName+"┃"+"HP:"+characterHpBarStr+" "+characterHpBar+" ┃";
		String cBarInfo2 = cBarBlank+"┃"+"MP:"+characterMpBarStr+" "+characterMpBar+" ┃";
		
		// 몬스터의 hp, mp 테두리를 만든다.
		mBarBotLine = mBarBlank+mBarBotLine;
		String mBarInfo1 = "┃"+mName+"┃"+"HP:"+monsterHpBarStr+" "+monsterHpBar+" ┃";
		String mBarInfo2 = mBarBlank+"┃"+"MP:"+monsterMpBarStr+" "+monsterMpBar+" ┃";
		
		String result 	= "";	// 최종 문자열이 담길 변수
		String vsSpace 	= "      ";	// vs 문자 사이의 공백
		String vs 		= "  VS  ";
		
		// 중간 위치를 맞추기 위한 공백
		String centerSpace = "                                                                      ";
		
		// 모든 조합을 더해서 최종 문자열을 만든다.
		result += centerSpace + cBarTopLine +vsSpace+ mBarTopLine +"\n";
		result += centerSpace + cBarInfo1 +vsSpace+mBarInfo1 +"\n";
		result += centerSpace + cBarMidLine + vs +mBarMidLine +"\n";
		result += centerSpace + cBarInfo2 +vsSpace +mBarInfo2 +"\n";
		result += centerSpace + cBarBotLine+vsSpace + mBarBotLine +"\n";
		System.out.println(result);
	}
	
	
	/**
	 * 유틸 기능, 문자열을 공백의 크기로 반환한다.
	 * @param str : 숫자로 변환할 문자열
	 * @return int
	 */
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
}
