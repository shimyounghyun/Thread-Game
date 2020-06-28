package game;

/**
 * 클래스 명 : Potion class
 * 설명 : 캐릭터의 체력, 마력을 회복할 수 있는 아이템이다.
 * 
 * @author sim-younghyun
 *
 */
public class Potion extends Item{
	/**
	 * 회복량
	 */
	int recovery;
	
	/**
	 * 포션 종류
	 */
	String potionType;
	
	public Potion(String id, int recovery, String potionType, String name, int salePrice, int purchasePrice) {
		super(name, id, salePrice, purchasePrice,  "potion", 0, true, false);
		this.recovery = recovery;
		this.potionType = potionType;
	}

	public int getRecovery() {
		return recovery;
	}

	public void setRecovery(int recovery) {
		this.recovery = recovery;
	}

	public String getPotionType() {
		return potionType;
	}

	public void setPotionType(String potionType) {
		this.potionType = potionType;
	}
	
	
}
