package game;

/**
 * 클래스 명 : Armor class
 * 설명 : 모든 방어구 아이템이 가지는 공통된 특징, 기능을 가진 클래스 
 * 
 * @author sim-younghyun
 *
 */
public class Armor extends Equipment{
	
	/**
	 * 물리 방어력
	 */
	int physicalDefense;
	
	/**
	 * 마법 방어력
	 */
	int magicalDefense;
	
	public Armor(String name, String id, int salePrice, int purchasePrice, int limitLevel, String limitJob, int physicalDefense, int magicalDefense, int dropPercentage) {
		super(name, id, "armor", limitLevel, limitJob, salePrice, purchasePrice, dropPercentage, false);
		this.physicalDefense = physicalDefense;
		this.magicalDefense = magicalDefense;
	}

	public int getPhysicalDefense() {
		return physicalDefense;
	}

	public void setPhysicalDefense(int physicalDefense) {
		this.physicalDefense = physicalDefense;
	}

	public int getMagicalDefense() {
		return magicalDefense;
	}

	public void setMagicalDefense(int magicalDefense) {
		this.magicalDefense = magicalDefense;
	}
	
	
	
}
