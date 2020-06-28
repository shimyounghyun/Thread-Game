package game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 클래스 명 : Inventory class
 * 설명 : 캐릭터가 소유하는 아이템이 수납되는 장소이다. 돈, 아이템 목록, 저장 가능한 크기를 가진다.
 * 
 * @author sim-younghyun
 *
 */
public class Inventory {
	
	/**
	 * 캐릭터가 보유한 아이템 목록
	 */
	private List<Item> itemList;
	
	/**
	 *	캐릭터가 보유 금액
	 */
	private int money;
	
	/**
	 * 아이템 저장이 가능한 최대 숫자
	 */
	private int maxSpace;
	
	/**
	 * 캐릭터 장비창
	 */
	private CharacterEquip equip;
	
	/**
	 * 캐릭터
	 */
	private Character character;
	
	public Inventory(int money) {
		this.itemList = new LinkedList<Item>();
		this.maxSpace = 10;
		this.money = money;
	}
	
	/**
	 * 아이템이 인벤토리에 저장 가능한지 여부를 반환한다.
	 * 
	 * @return true : 저장 가능, false : 저장 불가능
	 */
	public boolean isSave() {
		return itemList.size() < maxSpace;
	
	}
	
	/**
	 * 보유한 아이템 목록중 착용 가능한 아이템의 번호를 받아 장비창에 장착한다.
	 * 
	 * @param index : 아이템 인덱스 번호
	 * @return 착용 성공/불가 메세지 반환
	 */
	public String takeItem(int index) {
		String result = "";
		if(index >= 0 && index < itemList.size()) {
			Item item = itemList.get(index);
			if(item.isTake()) { //장착 가능 아이템
				String type = item.getType();
				if("weapon".equals(type)) { //무기 장착
					Item temp = item;
					itemList.remove(item);
					equip.takeItem(temp, 1);
					result = "[무기] "+item.getName()+"를(을) 장착했습니다.";
				}else if("armor".equals(type)) { //장비 장착
					Item temp = item;
					itemList.remove(item);
					equip.takeItem(temp, 2);
					result = "[방어구] "+item.getName()+"를(을) 장착했습니다.";
				}
			}else {
				//장착 불가 아이템
				String type = item.getType();
				if("potion".equals(type)) { // 아이템이 포션 종류일 경우
					Potion potion = (Potion)item;
					String potionType = potion.getPotionType();
					if("mp".equals(potionType)) { // 포션의 종류가 mp일 경우
						int mp = character.getMp();
						int maxMp = character.getMaxMp();
						if(mp == maxMp) {
							result = "더이상 마력을 회복할 필요가 없습니다.";
						}else {
							int recovery = mp + potion.getRecovery() > maxMp ? maxMp - mp : potion.getRecovery();
							character.setMp(recovery+mp);
							result = "[포션] "+item.getName()+" 1개를 사용하여 마력 +"+recovery+"를 회복했습니다.";
							deleteItem(item,1);
						}
					}else if("hp".equals(potionType)) { // 포션의 종류가 hp일 경우
						int hp = character.getHp();
						int maxHp = character.getMaxHp();
						if(hp == maxHp) {
							result = "더이상 체력을 회복할 필요가 없습니다.";
						}else {
							int recovery = hp+potion.getRecovery() > maxHp ? maxHp - hp : potion.getRecovery();
							character.setHp(hp+recovery);
							result = "[포션] "+item.getName()+" 1개를 사용하여 체력 +"+recovery+"를 회복했습니다.";
							deleteItem(item,1);
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 인벤토리에 아이템을 추가한다.
	 * 
	 * @param item : 추가할 아이템
	 */
	public void addItem(Item item) {
		if(!isSave()) return;
		Iterator<Item> iterator = itemList.iterator();
		boolean hasItem = false;
		Item invenItem = null;
		while(iterator.hasNext()) {
			Item temp = iterator.next();
			if(item.getId().equals(temp.getId())) {
				hasItem = true;
				invenItem = temp;
			}
		}
		if(hasItem) {
			System.out.println("기존 개수 "+invenItem.getCount());
			System.out.println("새 아이템 개수 "+item.getCount());
			invenItem.setCount(invenItem.getCount()+item.getCount());
		}else {
			itemList.add(item);
		}
	}
	
	/**
	 * 콘솔창에 보여질 인벤토리 창의 모습을 문자열로 반환한다.
	 * 
	 * @return String : 만들어 놓은 템플릿을 반환
	 */
	public String inventoryTempl() {
		String spaceBlank = "                                                               ";
		String str = "";
		str+=spaceBlank+"┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
		str+=spaceBlank+"┃                                      인벤토리 창                                ┃\n";                     		
		str+=spaceBlank+"┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃번호┃보유 아이템 목록                                                            ┃\n";
		str+=spaceBlank+"┣━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n";
		str+=spaceBlank+"┃  1 ┃item0┃\n"; 
		str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃  2 ┃item1┃\n"; 
		str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃  3 ┃item2┃\n"; 
		str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃  4 ┃item3┃\n"; 
		str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃  5 ┃item4┃\n"; 
		str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃  6 ┃item5┃\n"; 
		str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃  7 ┃item6┃\n"; 
		str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃  8 ┃item7┃\n"; 
		str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃  9 ┃item8┃\n"; 
		str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃ 10 ┃item9┃\n"; 
		str+=spaceBlank+"┣━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃소지금 : money┃\n";
		str+=spaceBlank+"┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n";
		return str;
	}
	
	/**
	 * 템플릿에 보유한 아이템의 이름을 추가해 완성한 후 콘솔창에 출력한다.
	 */
	public void printInventory() {
		String template = inventoryTempl();
		for(int i = 0 ; i < 10; i++) {
			if(itemList.size() > i) {
				String replace = "";
				if(itemList.get(i).isTake()) {
					replace = itemList.get(i).getName();
				}else {
					replace = itemList.get(i).getName() + " x"+itemList.get(i).getCount();
				}
				template = template.replace("item"+i, getStrPlusSpace(replace,76));
			}else {
				template = template.replace("item"+i, getStrPlusSpace("",76));
			}
		}
		template = template.replace("money", getStrPlusSpace(money+"원",72));
		System.out.println(template);
	}
	public String getStrPlusSpace(String str,int totalSpace) {
		String result = "";
		int strLength = getSpaceCountFromStr(str);
		for(int i = 0; i < totalSpace - strLength; i++) {
			result+=" ";
		}
		return str+result;
	}
	
	/**
	 * 문자열의 길이와 같은 공백 문자열의 크기를 반환한다.
	 * @param str : 공백으로 변환 했을때 몇개의 공백이 나오는지 알고 싶은 문자열
	 * @return 공백 길이를 반환
	 */
	public int getSpaceCountFromStr(String str) {
		int result = 0;
		for(int i =0; i < str.length(); i++) {
			int index = str.charAt(i);
			if(index >= 48 && index <= 57) { //숫자
				result++;
            } else if(index >= 65 && index <= 122) {//영어
            	result++;
            }else if(index == 9472 || index == 9474 || index == 9484 || index == 9488 || index == 9496 
            		|| index == 9492 || index == 9500 ||index == 9516 ||index == 9508 || index == 9524 
            		||index == 9532 || index == 9473 || index == 9475 || index == 9487
                	|| index == 9491 || index == 9499 || index == 9495 || index == 9507
                	|| index == 9523 || index == 9515 || index == 9531 || index == 9547
                	|| index == 9632 || index == 9633
            		) { //─│┌┐┘└├┬┤┴┼ ━ ┃ ┏ ┓ ┛ ┗ ┣ ┳┫ ┻ ╋■□
            	result ++;
            }else if(index == 47 || index == 58 || index == 40 || index == 41) { // /:()
            	result ++;
            }else if(index == 32) {//공백
            	result ++;
            }else {//한글
                result +=2;
            }
		}
		return result;
	}
	
	/**
	 * 보유한 아이템 목록에서 아이템을 개수 만큼 차감한다. 개수가 0개 일경우 삭제한다.
	 * @param item : 삭제할 아이템 
	 * @param count : 차감할 개수 
	 */
	public void deleteItem(Item item, int count) {
		int index = itemList.indexOf(item);
		Item invenItem = itemList.get(index);
		if(invenItem.getCount()-count == 0) {
			itemList.remove(index);
		}else {
			invenItem.setCount(invenItem.getCount()-count);
		}
	}
	
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getMaxSpace() {
		return maxSpace;
	}
	public void setMaxSpace(int maxSpace) {
		this.maxSpace = maxSpace;
	}
	public void setEquip(CharacterEquip equip) {
		this.equip = equip;
	}
	public Character getCharacter() {
		return character;
	}
	public void setCharacter(Character character) {
		this.character = character;
	}
}
