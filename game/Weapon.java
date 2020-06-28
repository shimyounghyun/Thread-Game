package game;

/**
 * 클래스 명 : Weapon class
 * 설명 : 모든 무기 아이템이 가지는 공통된 특징, 기능을 가진 클래스  
 * 
 * @author sim-younghyun
 *
 */
public class Weapon extends Equipment{
	
	/**
	 * 무기의 공격력
	 */
	private int attackPower;
	
	public Weapon(String name, String id, int salePrice, int purchasePrice, int limitLevel, String limitJob, int dropPercentage, int attackPower) {
		super(name, id, "weapon", limitLevel, limitJob, salePrice, purchasePrice, dropPercentage, false);
		this.attackPower = attackPower;
	}
	
	/**
	 * 강화 횟수에 따른 공격력 증가
	 */
	public void setAttackPowerByEnhancement() {
		this.attackPower = this.attackPower + this.attackPower * super.getEnhancement() / 10;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}
	
}
