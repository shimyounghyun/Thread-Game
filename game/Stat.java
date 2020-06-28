package game;

/**
 * 캐릭터의 능력치를 나타낸다.
 * 
 * @author sim-younghyun
 *
 */
public class Stat {
	/**
	 * 근력
	 */
	private int strength;
	
	/**
	 * 지력
	 */
	private int intelligence;
	
	/**
	 * 민첩
	 */
	private int agility;
	
	/**
	 * 공격력
	 */
	private int attackPower;
	
	/**
	 * 방어력
	 */
	private int defensePower;
	
	/**
	 * 이전 레벨
	 */
	private int prevLv;
	
	public Stat(int strength, int intelligence, int agility, int attackPower, int defensePower) {
		this.strength = strength;
		this.intelligence = intelligence;
		this.agility = agility;
		this.attackPower = attackPower;
		this.defensePower = defensePower;
		this.prevLv = 1;
	}
	
	/**
	 * 캐릭터가 레벨업 할때, 이 메소드를 호출시 스탯을 레벨에 맞게 증가 시킨다.
	 * 
	 * @param lv : 캐릭터 현재 레벨 
	 */
	public void setLevelUpStat(int lv) {
		int upPoint = lv - prevLv;
		this.strength += 10 * upPoint;
		this.intelligence += 10 * upPoint;
		this.agility += 10 * upPoint;
		this.attackPower += 10 * upPoint;
		this.defensePower += 10 * upPoint;
		setPrevLv(lv);
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public int getDefensePower() {
		return defensePower;
	}

	public void setDefensePower(int defensePower) {
		this.defensePower = defensePower;
	}

	public int getPrevLv() {
		return prevLv;
	}

	public void setPrevLv(int prevLv) {
		this.prevLv = prevLv;
	}
	
	
	
}
