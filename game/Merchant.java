package game;

import java.util.List;

/**
 * 클래스 명 : Merchant class
 * 설명 : 캐릭터가 아이템을 팔고 살수 있는 상인 NPC 클래스이다.
 * 
 * @author sim-younghyun
 *
 */
public class Merchant extends NPC{
	
	/**
	 * 판매 목록
	 */
	private List<Item> saleList;
	
	public Merchant(List<Item> saleList, String name, String npcType, String location) {
		super(name, npcType, location);
		this.saleList = saleList;
	}
	
	/**
	 * 구매하려는 아이템 종류에 맞는 문자열 반환
	 * @param number : 사용자가 선택한 아이템 번호
	 * @return String : 구입 재확인 문자열
	 */
	public String selectNumber(int number) {
		String result = "";
		Item temp = saleList.get(number);
		String itemType = temp.getType();
		
		if("potion".equals(itemType)) {
			result = temp.getName()+ "를 몇개 구입 하시겠습니까?";
		}else if("equip".equals(itemType)) {
			result = temp.getName()+"를 구입하시겠습니까?";
		}else if("weapon".equals(itemType)) {
			result = temp.getName()+"를 구입하시겠습니까?";
		}
		return result;
	}

	public List<Item> getSaleList() {
		return saleList;
	}

	public void setSaleList(List<Item> saleList) {
		this.saleList = saleList;
	}
	
	
}
