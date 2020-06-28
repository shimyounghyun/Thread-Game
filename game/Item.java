package game;
/**
 * 클래스 명 : Item class
 * 설명 :  게임 내 모든 아이템이 만들어질 때, 공통으로 갖는 기능과 특징을 포함하는 클래스
 * 주요 기능 : 아이템 정보 확인을 한다.
 * 
 * @author sim-younghyun
 *
 */
public class Item {
	
	/**
	 * 아이템 이름
	 */
	private String name;
	
	/**
	 * 아이템 고유 식별 문자
	 */
	private String id;
	
	/**
	 * 아이템 종류
	 */
	private String type;
	
	/**
	 * 대량 거래 가능 여부
	 */
	private boolean isBulk;
	
	/**
	 * 판매 가격
	 */
	private int salePrice;
	
	/**
	 * 구입 가격
	 */
	private int purchasePrice;
	
	/**
	 * 드랍 확률
	 */
	private int dropPercentage;
	
	/**
	 * 개수
	 */
	private int count;
	
	/**
	 * 장착 가능 여부
	 */
	private boolean isTake;
	
	public Item(String name, String id, int salePrice, int purchasePrice,  String type, int dropPercentage, boolean isBulk, boolean isTake) {
		this.name = name;
		this.id = id;
		this.salePrice = salePrice;
		this.purchasePrice = purchasePrice;
		this.type = type;
		this.dropPercentage = dropPercentage;
		this.count = 1;
		this.isBulk = isBulk;
		this.isTake = isTake;
	}
	
	/**
	 * 아이템 정보를 알려준다.
	 * 
	 * @return String
	 */
	public String getItemInfo() {
		String result = "";
		result += "아이템 이름 : "+name+"\n";
		result += "아이템 종류 : "+type;
		return result;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isBulk() {
		return isBulk;
	}

	public void setBulk(boolean isBulk) {
		this.isBulk = isBulk;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public int getDropPercentage() {
		return dropPercentage;
	}

	public void setDropPercentage(int dropPercentage) {
		this.dropPercentage = dropPercentage;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isTake() {
		return isTake;
	}

	public void setTake(boolean isTake) {
		this.isTake = isTake;
	}
	
	
}
