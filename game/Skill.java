package game;

/**
 * 캐릭터의 스킬을 나타낸다.
 * 
 * @author sim-younghyun
 *
 */
public class Skill {
	
	/**
	 * 스킬 이름
	 */
	private String name;
	
	/**
	 * 스킬 공격력
	 */
	private int attackPower;
	
	/**
	 * 스킬 사용시 필요 마력
	 */
	private int mp;
	
	/**
	 * 스킬 시전할때 출력 문자열
	 */
	private String systemMsg;
	
	/**
	 * 스킬 대기 시간
	 */
	private int coolTime;
	
	
	public Skill(String name, int attackPower, String systemMsg, int mp, int cooltime) {
		this.name = name;
		this.attackPower = attackPower;
		this.systemMsg = systemMsg;
		this.mp = mp;
		this.coolTime = cooltime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public String getSystemMsg() {
		return systemMsg;
	}

	public void setSystemMsg(String systemMsg) {
		this.systemMsg = systemMsg;
	}

	public int getCoolTime() {
		return coolTime;
	}

	public void setCoolTime(int coolTime) {
		this.coolTime = coolTime;
	}
	
	
}
