package game;

/**
 * 클래스 명 : Monster class
 * 설명 : 몬스터는 체력과 공격력을 가지며 캐릭터를 공격할 수 있다. 몬스터가 죽으면 보상을 제공한다.
 * 
 * @author sim-younghyun
 *
 */
public class Monster extends NPC {
	
	/**
	 * 몬스터의 공격력 
	 */
	private int offensePower;
	
	/**
	 * 몬스터의 체력
	 */
	private int hp;
	
	/**
	 * 몬스터의 마력
	 */
	private int mp;
	
	/**
	 * 몬스터의 최대 체력
	 */
	private int maxHp;
	
	/**
	 * 몬스터의 최대 마력
	 */
	private int maxmp; 
	
	/**
	 * 몬스터의 레벨
	 */
	private int level;
	
	/**
	 * 몬스터가 죽었을때 습득 가능한 돈
	 */
	private int earnMoney;
	
	/**
	 * 몬스터가 죽었을때 습득 가능한 아이템
	 */
	private Item item;
	
	/**
	 * 몬스터가 죽었을때 캐릭터가 얻게 되는 경험치 
	 */
	private int exp;
	
	/**
	 * 몬스터의 종류
	 */
	private String monsterType;
	
	/**
	 * 몬스터의 등장 메세지
	 */
	private String systemMsg;
	
	/**
	 * 몬스터가 죽었는지 여부
	 */
	private boolean isDie;
	
	/**
	 * 전투에서 사용되는 앞 모습 이미지
	 */
	private String[] battleFront;
	
	public Monster(int level, int hp, int mp, int earnMoney, int offensePower, String monsterType,String name,int exp,String location,String msg, String[] battleFront) {
		super(name, "monster",location);
		this.hp = hp;
		this.mp = mp;
		this.maxHp = hp;
		this.maxmp = mp;
		this.level = level;
		this.earnMoney = earnMoney;
		this.offensePower = offensePower;
		this.monsterType = monsterType;
		this.exp = exp;
		this.systemMsg = msg;
		this.isDie = false;
		this.battleFront = battleFront;
	}

	/**
	 * 공격한다. 
	 * @return int : 공격력
	 */
	public int attack() {
		return offensePower;
	}
	
	/**
	 * 몬스터가 죽은 후 아이템이 습득 가능한지 여부 
	 * @return true : 습득 가능, false : 습득 불가능
	 */
	public boolean isDrop() {
		boolean result = false;
		if(item != null) {
			int p = item.getDropPercentage();
			int r = (int)(Math.random()*100)+1;
			if(r <= p) result = true;
		}
		return result;
	}
	
	/**
	 * 몬스터가 공격을 당해 hp를 차감한다.
	 * @param attack : 공격력
	 */
	public void setDamage(int attack) {
		hp -= attack;
		if(hp <= 0) {
			isDie = true;
			setHp(0);
		}
	}
	
	public int getOffensePower() {
		return offensePower;
	}

	public void setOffensePower(int offensePower) {
		this.offensePower = offensePower;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getMaxmp() {
		return maxmp;
	}

	public void setMaxmp(int maxmp) {
		this.maxmp = maxmp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getEarnMoney() {
		return earnMoney;
	}

	public void setEarnMoney(int earnMoney) {
		this.earnMoney = earnMoney;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public String getMonsterType() {
		return monsterType;
	}

	public void setMonsterType(String monsterType) {
		this.monsterType = monsterType;
	}

	public String getSystemMsg() {
		return systemMsg;
	}

	public void setSystemMsg(String systemMsg) {
		this.systemMsg = systemMsg;
	}

	public boolean isDie() {
		return isDie;
	}

	public void setDie(boolean isDie) {
		this.isDie = isDie;
	}

	public String[] getBattleFront() {
		return battleFront;
	}

	public void setBattleFront(String[] battleFront) {
		this.battleFront = battleFront;
	}
	
	
}
