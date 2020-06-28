package game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Battle_ {
	/**
	 * 캐릭터
	 */
	private Character character;
	
	/**
	 * 몬스터
	 */
	private Monster monster;
	
	/**
	 * 시스템 메시지 목록 
	 */
	private LinkedList<String> systemMsg;
	
	/**
	 * 전투 메시지 목록
	 */
	private LinkedList<String> battleMsg;
	
	/**
	 * 아이템을 관리
	 */
	private ItemManager itemManager;
	
	/**
	 * 캐릭터 공격 차례 여부
	 */
	private boolean isCharacterTurn;
	
	/**
	 * 몬스터 공격 차례 여부
	 */
	private boolean isMonsterTurn;
	
	/**
	 * 스킬 공격 가능 여부
	 */
	private boolean isSkillTurn;
	
	/**
	 * 강제 종료
	 */
	private boolean isStop;
	
	/**
	 * MonsterAttackThread, CharacterAttackThread가 통신하는데 사용되는 잠금 객체
	 */
	private Object autoHunt = new Object();
	
	private MapManager mapManager;
	
	Scanner sc;
	
	
	
	public Battle_(Character character, Monster monster, LinkedList<String> systemMsg, ItemManager itemManager, MapManager mapManager ) {
		this.character = character;
		this.monster = monster;
		this.systemMsg = systemMsg;
		this.battleMsg = new LinkedList<String>();
		this.sc = new Scanner(System.in);
		this.itemManager = itemManager;
		this.isCharacterTurn = false;
		this.isMonsterTurn = true;
		this.isSkillTurn = true;
		this.isStop = false;
		this.mapManager = mapManager;
	}
	
	/**
	 * 캐릭터가 몬스터를 공격한다.
	 */
	public void attackMonster() {
		if(!isCharacterTurn) return;
		
		Music battleMusic = new Music("/Users/sim-younghyun/Downloads/attack.mp3", false);
		battleMusic.start();
		
		String job = character.getJob();
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
		
		// 캐릭터가 몬스터를 공격
		monster.setDamage(attack); 
		battleMsg.add(character.getAttackMsg(monster, attack)); //공격 메세지 저장
		
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
			battleMsg.add("전투가 종료되었습니다. 나가기를 눌러주세요.");
			isStop = true; // 전투 종료
			
			// 진행중인 퀘스트가 있을 경우
			Quest quest = character.getQuestInQuestList();
			if(quest != null) {
				for(QuestDetail temp : quest.getQuestDetailList()) {
					if(temp.getMonsterName().equals(monster.getName())) {
						temp.addTargetAchievements();
					}
				}
			}
		}
		
		isCharacterTurn = false;
		isCharacterTurnOver();
	}
	
	/**
	 * 몬스터가 캐릭터를 공격한다.
	 */
	public void attackCharacter() {
		if(!isMonsterTurn) return;
		
		// 몬스터 공격력을 구한다.
		int monsterAttack = monster.attack();
		int resultDamage = character.setDamage(monsterAttack);
		battleMsg.add(character.getDamageMsg(monster, resultDamage)); //피해 메시지 저장
		
		// 캐릭터가 죽었을 경우
		if(character.isDie()) {
//			character.resurrection();	// 캐릭터를 부활 시킨다.
			systemMsg.add("죽은 후 부활했습니다.");
			battleMsg.add("캐릭터가 죽었습니다.");
			battleMsg.add("전투가 종료되었습니다. 나가기를 눌러주세요.");
			isStop = true; // 전투 종료
		}
		
		isMonsterTurn = false;
		isMonsterTurnOver();
	}
	
	/**
	 * 스킬을 사용해 몬스터를 공격한다.
	 */
	public void attackMonsterBySkill() {
		if(monster.isDie() || character.isDie()) {
			return;
		}
		
		if(!isSkillTurn) {
			battleMsg.add("[스킬] 사용 대기 시간입니다.");
			return;
		}
		
		// 캐릭터의 마력이 스킬에 필요한 마력보다 적을 경우 사용하지 못한다.
		if(character.getMp() < character.getSkill().getMp()) {
			battleMsg.add("마력이 부족합니다.");
			return;
		}
		
		Music battleMusic = new Music("/Users/sim-younghyun/Downloads/skill.mp3", false);
		battleMusic.start();
		
		// 캐릭터의 마력을 차감
		character.setMp(character.getMp() - character.getSkill().getMp());
		
		// 스킬 공격력을 구한다. 스킬 공격 메세지를 저장한다.
		int attackPoint = character.getSkill().getAttackPower() + character.getStat().getIntelligence();
		monster.setDamage(attackPoint);
		battleMsg.add(character.getSkill().getSystemMsg()+" "+attackPoint+"의 공격을 했습니다.");
		
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
			battleMsg.add("전투가 종료되었습니다. 나가기를 눌러주세요.");
			isStop = true; // 전투 종료
			
			// 진행중인 퀘스트가 있을 경우
			Quest quest = character.getQuestInQuestList();
			if(quest != null) {
				for(QuestDetail temp : quest.getQuestDetailList()) {
					if(temp.getMonsterName().equals(monster.getName())) {
						temp.addTargetAchievements();
					}
				}
			}
		}
		
		isSkillTurn = false;
	}

	/**
	 * 캐릭터의 일반 공격이 끝난 후 처리한다.
	 */
	public void isCharacterTurnOver() {
		synchronized (autoHunt) {
			if(!isMonsterTurn) {
				autoHunt.notify();
				try {
					autoHunt.wait(600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isMonsterTurn = true;
			}
		}
	}
	
	/**
	 * 몬스터의 일반 공격이 끝난 후 처리한다.
	 */
	public void isMonsterTurnOver() {
		synchronized (autoHunt) {
			if(!isCharacterTurn) {
				autoHunt.notify();
				try {
					autoHunt.wait(600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isCharacterTurn = true;
			}
		}
	}
	
	/**
	 * 스킬 공격이 끝난 후 처리한다.
	 */
	public void isSkillTurnOver() {
		if(!isSkillTurn) {
			isSkillTurn = true;
		}
	}
	
	
	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

	public LinkedList<String> getSystemMsg() {
		return systemMsg;
	}

	public void setSystemMsg(LinkedList<String> systemMsg) {
		this.systemMsg = systemMsg;
	}

	public LinkedList<String> getBattleMsg() {
		return battleMsg;
	}

	public void setBattleMsg(LinkedList<String> battleMsg) {
		this.battleMsg = battleMsg;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public boolean isHasCharacterTurn() {
		return isCharacterTurn;
	}

	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public boolean isCharacterTurn() {
		return isCharacterTurn;
	}

	public void setCharacterTurn(boolean isCharacterTurn) {
		this.isCharacterTurn = isCharacterTurn;
	}

	public boolean isMonsterTurn() {
		return isMonsterTurn;
	}

	public void setMonsterTurn(boolean isMonsterTurn) {
		this.isMonsterTurn = isMonsterTurn;
	}

	public boolean isSkillTurn() {
		return isSkillTurn;
	}

	public void setSkillTurn(boolean isSkillTurn) {
		this.isSkillTurn = isSkillTurn;
	}

	public MapManager getMapManager() {
		return mapManager;
	}

	public void setMapManager(MapManager mapManager) {
		this.mapManager = mapManager;
	}
	
	
	
}
