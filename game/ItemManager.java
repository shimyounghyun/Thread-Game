package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 클래스 명 : ItemManager class
 * 설명 : 모든 아이템을 생성해서 보관하고 관리한다.
 * 
 * @author sim-younghyun
 *
 */
public class ItemManager {
	
	/**
	 * 아이템의 고유 키로 사용될 숫자
	 */
	private int itemSeq;
	
	/**
	 * 상인이 판매할 아이템 목록
	 */
	private List<Item> storeItemList;
	
	/**
	 * 몬스터를 죽였을때 획득 가능한 아이템 목록
	 */
	private List<Item> dropItemList;
	
	public ItemManager() {
		this.itemSeq = 0;
		storeItemList = new ArrayList<Item>();
		dropItemList = new ArrayList<Item>();
		
		/* 포션류 아이템 생성 */
		Item hp100P 	= new Potion("hp100", 	100, 	"hp",	"체력 회복 물약(소)",	10,	30);
		Item hp300P 	= new Potion("hp300", 	300, 	"hp",	"체력 회복 물약(중)",	50,	100);
		Item hp1000P 	= new Potion("hp1000", 1000, 	"hp",	"체력 회복 물약(대)",	500,1000);
		Item mp100P 	= new Potion("mp100", 	100, 	"mp",	"마력 회복 물약(소)",	10,	30);
		Item mp300P 	= new Potion("mp300", 	300, 	"mp",	"마력 회복 물약(중)",	50,	100);
		Item mp1000P 	= new Potion("mp1000", 1000, 	"mp", 	"마력 회복 물약(대)",	500,1000);
		
		/* 장비류 아이템 생성 */
		Item oldArmor = new Armor("낡은 갑옷", "", 100, 300, 1, "", 10, 0, 10);
		Item armorGM = new Armor("운영자의 갑옷", "", 100, 100, 100, "", 10, 50, 10);
		Item WeaponGM = new Weapon("운영자의 무기", "", 100, 100, 100, "", 1, 50);
		
		/* 판매 목록에 아이템 넣기 */
		storeItemList.add(hp100P);
		storeItemList.add(hp300P);
		storeItemList.add(hp1000P);
		storeItemList.add(mp100P);
		storeItemList.add(mp300P);
		storeItemList.add(mp1000P);
		
		storeItemList.add(oldArmor);
		storeItemList.add(WeaponGM);
		storeItemList.add(armorGM);
		
		/* 몬스터 드랍 목록에 아이템 넣기 */
		dropItemList.add(oldArmor);
	}
	
	/**
	 * 상인 아이템 목록중 이름이 일치하는 것들만 목록을 만들어서 반환한다.
	 * @param args : 아이템 이름들
	 * @return List<Item> 아이템 목록 반환
	 */
	public List<Item> getStoreItemListByNames(String ...args){
		List<Item> result = new ArrayList<Item>();	// 결과로 반환될 아이템 목록
		
		/* 인자로 받은 이름들을 리스트에 담기 */
		LinkedList<String> itemNameList = new LinkedList<String>();
		for(String name : args) {
			itemNameList.add(name);
		}
		
		/* 아이템 목록과 itemNameList의 이름을 비교해서 같은 것들을 리스트에 담는다. */
		for(Item item : storeItemList) {
			for(String name : itemNameList) {
				if(item.getName().equals(name)) {
					result.add(item);
					itemNameList.remove(this);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 드랍 아이템 목록중 이름이 일치하는 것들만 목록을 만들어서 반환한다.
	 * @param args : 아이템 이름들
	 * @return List<Item> 아이템 목록 반환
	 */
	public List<Item> getDropItemListByNames(String ...args){
		List<Item> result = new ArrayList<Item>();	// 결과로 반환될 아이템 목록
		
		/* 인자로 받은 이름들을 리스트에 담기 */
		LinkedList<String> itemNameList = new LinkedList<String>();
		for(String name : args) {
			itemNameList.add(name);
		}
		
		/* 아이템 목록과 itemNameList의 이름을 비교해서 같은 것들을 리스트에 담는다. */
		for(Item item : dropItemList) {
			for(String name : itemNameList) {
				if(item.getName().equals(name)) {
					result.add(item);
					itemNameList.remove(this);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 드랍 아이템 목록에서 이름이 같은 아이템을 하나 찾아서 반환한다. 
	 * @param name : 아이템의 이름
	 * @return Item : dropItemList가 가진 목록중 하나의 아이템
	 */
	public Item getDropItemOneByName(String name) {
		Item result = null;
		for(Item item : dropItemList) {
			if(item.getName().equals(name)) {
				result = item;
			}
		}
		return result;
	}
	
	/**
	 * 새로운 아이템을 생성한다.
	 * @param item : ID값이 없는 껍데기 아이템 정보
	 * @return Item : 새로 생성된 아이템 반환
	 */
	public Item createNewItem(Item item) {
		Item newItem = null;
		String itemType = item.getType();
		String name = item.getName(); 
		int salePrice = item.getSalePrice();
		int purchasePrice = item.getPurchasePrice();
		int dropPercentage = item.getDropPercentage();
		if("weapon".equals(itemType)) {
			Weapon temp = (Weapon)item;
			int attackPower = temp.getAttackPower();
			int limitLevel = temp.getLimitLevel();
			String limitJob = temp.getLimitJob();
			String id = "weapon"+itemSeq;
			Weapon weapon = new Weapon(name, id, salePrice, purchasePrice, limitLevel, limitJob, dropPercentage, attackPower);
			newItem = weapon;
		}else if("armor".equals(itemType)) {
			Armor temp = (Armor)item;
			String id = "armor"+itemSeq;
			int limitLevel = temp.getLimitLevel();
			String limitJob = temp.getLimitJob();
			int physicalDefense = temp.getPhysicalDefense();
			int magicalDefense = temp.getMagicalDefense();
			Equipment equipment = new Armor(name, id, salePrice, purchasePrice, limitLevel, limitJob, physicalDefense, magicalDefense, dropPercentage);
			newItem = equipment;
		}else if("potion".equals(itemType)) {
			Potion temp = (Potion)item;
			String id = temp.getId();
			int recovery = temp.getRecovery();
			String potionType = temp.getPotionType();
			Potion potion = new Potion(id, recovery, potionType, name, salePrice, purchasePrice);
			newItem = potion;
		}
		itemSeq++;
		return newItem;
	}
	
	public int getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(int itemSeq) {
		this.itemSeq = itemSeq;
	}

	public List<Item> getStoreItemList() {
		return storeItemList;
	}

	public void setStoreItemList(List<Item> storeItemList) {
		this.storeItemList = storeItemList;
	}

	public List<Item> getDropItemList() {
		return dropItemList;
	}

	public void setDropItemList(List<Item> dropItemList) {
		this.dropItemList = dropItemList;
	}
	
}
