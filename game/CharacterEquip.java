package game;

/**
 * 클래스 명 : CharacterEquip class
 * 설명 : 캐릭터가 착용한 아이템을 나타낸다. 장착 가능한 아이템은 무기와 방어구 두종류가 있다.
 * 
 * @author sim-younghyun
 *
 */
public class CharacterEquip {
	
	/**
	 * 무기
	 */
	private Weapon weapon;
	
	/**
	 * 방어구
	 */
	private Armor armor;
	
	/**
	 * 캐릭터의 인벤토리
	 */
	private Inventory inventory;
	
	/**
	 * 캐릭터가 착용한 아이템을 인벤토리로 이동한다.(장비를 벗는다.)
	 * @param 1 : 무기, 2 : 갑옷
	 * @return String : 결과 문자열 반환
	 */
	public String takeOffEquip(int index) {
		String result = "";
		
		/* 인벤토리 저장이 가능한지 여부 */
		if(inventory.isSave()) {
			if(index == 1 && weapon != null) { //무기
				result = "["+weapon.getName()+"]을(를) 해제 했습니다.";
				inventory.addItem(weapon);
				weapon = null;
			}else if(index ==2 && armor != null) { //갑옷
				result = "["+armor.getName()+"]을(를) 해제 했습니다.";
				inventory.addItem(armor);
				armor = null;
			}else {
				result = "해제할 장비가 없습니다.";
			}
		}else {
			result = "인벤토리가 가득차서 장비를 해제할수 없습니다.";
		}
		return result;
	}
	
	/**
	 * 아이템을 장비창에 착용시킨다.
	 * @param item : 착용할 아이템
	 * @param index : 1 - 무기 , 2 - 방어구
	 */
	public void takeItem(Item item, int index) {
		if(index == 1) {
			Weapon weapon = (Weapon)item;
			takeOffEquip(1);
			this.weapon = weapon;
		}else if(index == 2) {
			Armor armor = (Armor)item;
			takeOffEquip(2);
			this.armor = armor;
		}
	}
	
	/**
	 * 장비창 그래픽 형태의 문자열을 반환한다.
	 * @return String : 미리 만들어 놓은 장비창 형태의 문자열
	 */
	public String equipTempl() {
		String spaceBlank = "                                                                                     ";
		String str = "";
		str+=spaceBlank+"┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
		str+=spaceBlank+"┃               장비 창             ┃\n";                     		
		str+=spaceBlank+"┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃종류  ┃아이템 이름                 ┃\n";
		str+=spaceBlank+"┣━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n";
		str+=spaceBlank+"┃무기  ┃item0┃\n"; 
		str+=spaceBlank+"┣━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃방어구┃item1┃\n"; 
		str+=spaceBlank+"┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n";
		return str;
	}
	
	/**
	 * 템플릿에 무기, 방어구의 이름을 집어넣어 완성하고 콘솔창에 보여준다. 
	 */
	public void printEquip() {
		String template = equipTempl();
		String weaponName = weapon == null ? "" : weapon.getName();
		String armorName = armor == null ? "" : armor.getName();
		template = template.replace("item0", getStrPlusSpace(weaponName,28));
		template = template.replace("item1", getStrPlusSpace(armorName,28));
		System.out.println(template);
	}
	
	/**
	 * 문자열을 공백 길이로 변환 했을때, totalSpace의 크기 만큼 공백을 더해서 반환한다.
	 *   
	 * @param str
	 * @param totalSpace : 띄어쓰기 총 합
	 * @return
	 */
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
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Armor getArmor() {
		return armor;
	}

	public void setArmor(Armor armor) {
		this.armor = armor;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	
	
}
