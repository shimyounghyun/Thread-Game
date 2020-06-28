package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 클래스 명 : Character class
 * 설명 : 전사, 마법사, 궁수를 만들때 공통적으로 가진 기능과 특징을 포함한 최상위 클래스
 * 
 * @author sim-younghyun
 */
public class Character {
	
	/**
	 * 캐릭터 옆 모습
	 */
	private final String[] LEFT;
	
	/**
	 * 캐릭터의 오른쪽 모습
	 */
	private final String[] RIGHT;
	
	/**
	 * 캐릭터의 정면 모습
	 */
	private final String[] FRONT;
	
	/**
	 * 캐릭터의 뒷 모습
	 */
	private final String[] BACK;
	
	/**
	 * 캐릭터 전투시 앞 모습
	 */
	private final String[] BATTLE_FRONT;
	
	/**
	 * 캐릭터의 현재 모습
	 */
	private String[] motion;
	
	/**
	 * 캐릭터 이름
	 */
	private String characterId;
	
	/**
	 * 캐릭터 레벨
	 */
	private int lv;
	
	/**
	 * 캐릭터 경험치
	 */
	private int exp;
	
	/**
	 * 캐릭터 직업
	 */
	private String job;
	
	/**
	 * 캐릭터 현재 체력
	 */
	private int hp;
	
	/**
	 * 캐릭터 현재 마력
	 */
	private int mp;
	
	/**
	 * 캐릭터 최대 체력
	 */
	private int maxHp;
	
	/**
	 * 캐릭터 최대 마력
	 */
	private int maxMp;
	
	/**
	 * 캐릭터 현재 위치
	 * 형태 : 맵ID-X좌표-Y좌표 
	 */
	private String location;
	
	/**
	 * 캐릭터 인벤토리
	 */
	private Inventory inventory;
	
	/**
	 * 캐릭터 장비창
	 */
	private CharacterEquip equip;
	
	/**
	 * 캐릭터 능력치 
	 */
	private Stat stat;
	
	/**
	 * 캐릭터가 죽었는지 여부
	 */
	private boolean isDie;
	
	/**
	 * 캐릭터가 보유한 스킬
	 */
	private Skill skill;
	
	/**
	 * 진행중인 퀘스트
	 */
	private List<Quest> questList;
	
	public Character(String id, String job, int hp, int mp, int money, String[] left, String[] right, String[] front, String[] back, String[] battleFront) {
		this.characterId = id;
		this.job = job;
		this.hp = hp;
		this.maxHp = hp; 
		this.mp = mp;
		this.maxMp = mp;
		this.lv = 1;
		this.exp = 0;
		this.location = "base1-0-0";
		this.motion = front;
		this.LEFT = left;
		this.RIGHT = right;
		this.FRONT = front;
		this.BACK = back;
		this.equip = new CharacterEquip();
		this.inventory = new Inventory(money);
		this.equip.setInventory(inventory);
		this.inventory.setEquip(equip);
		this.inventory.setCharacter(this);
		this.BATTLE_FRONT = battleFront;
		this.questList = new ArrayList<Quest>();
	}
	
	/**
	 * a,w,s,d 입력값을 받았을때 캐릭터의 위치를 이동 시킨다.
	 * @param input : 이동키(a,w,s,d)중 하나의 입력값
	 */
	public void changeCharacterLocation(String input) {
		String location = this.location;
		String mapId = location.split("-")[0];
		int y = Integer.parseInt(location.split("-")[1]);
		int x = Integer.parseInt(location.split("-")[2]);
		switch (input) {
			case "a": x--;
				break;
			case "s": y++;
				break;
			case "d":x++;
				break;
			case "w":y--;
				break;
		}
		this.location = mapId+"-"+y+"-"+x;
	}
	
	/**
	 * 맵 정보와 입력값을 받아 캐릭터가 움직일수 있는지 여부를 판단한다.
	 * @param input : 이동키(a,w,s,d)중 하나의 값
	 * @param map : 맵 정보
	 * @return true : 이동 가능, false : 이동 불가
	 */
	public boolean isMoveRange(String input, int[][] map) {
		/* x좌표, y좌표 구하기 */
		int y = Integer.parseInt(location.split("-")[1]);
		int x = Integer.parseInt(location.split("-")[2]);
		
		/* 입력값을 받아 캐릭터 모션 등록 및 x,y좌표 계산 */
		switch (input) {
			case "a": x--; setMotion(getLEFT());
				break;
			case "s": y++; setMotion(getFRONT());
				break;
			case "d":x++; setMotion(getRIGHT());
				break;
			case "w":y--; setMotion(getBACK());
				break;
		}
		
		/* 이동이 가능한지 판단 */
		if(x < 0 || x >= map[0].length || y < 0 || y >= map.length) {
			return false;
		}
		if(map[y][x] == 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * 캐릭터의 공격 메시지를 문자열로 반환 한다. 
	 * 
	 * @param monster : 공격 대상 몬스터
	 * @param attack : 공격 수치 
	 * @return
	 */
	public String getAttackMsg(Monster monster, int attack) {
		String weapon = "맨손";
		if(getEquip().getWeapon() != null) {
			weapon = getEquip().getWeapon().getName(); 
		}
		String attackMsg = getCharacterId()+"님이"+monster.getName()+"에게"+weapon+"(을)를 사용해 "+attack+"의 공격을 했습니다.";
		return attackMsg;
	}
	
	/**
	 * 캐릭터의 피해 메시지를 문자열로 반환한다.
	 * 
	 * @param monster : 캐릭터가 공격받은 몬스터
	 * @param attack : 몬스터의 공격 수치
	 * @return
	 */
	public String getDamageMsg(Monster monster, int attack) {
		return "\u001b[38;2;254;46;46m"+monster.getName()+"이(가) "+getCharacterId()+"님 에게 "+attack+"의 피해를 입혔습니다.\u001b[0m";
	}
	
	/**
	 * 캐릭터가 인자로 들어온 경험치를 받았을때 레벨업이 가능한지를 판단한다.
	 * 
	 * @param exp : 경험치
	 * @return true : 레벨업 가능하다, false : 레벨업 불가능하다.
	 */
	public boolean isLevelUp(int exp) {
		boolean result = false;
		if(lv * 100 <= this.exp * 100) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 캐릭터의 경험치를 더하고 레벨이 올랐을 경우 알림 메시지를 저장한다. 
	 * @param exp
	 * @param systemMsg
	 */
	public void addExp(int exp, LinkedList<String> systemMsg) {
		this.exp += exp;
		while(lv*100 <= this.exp) {
			setExp(getExp()-lv*100);
			setLv(lv+1);
			systemMsg.add("캐릭터의 레벨이 올랐습니다.");
		}
		setLevelHpAndMp(lv);
		stat.setLevelUpStat(lv);
	}
	
	/**
	 * 퀘스트 목록에서 진행중인 퀘스트를 반환한다. 
	 * @return
	 */
	public Quest getQuestInQuestList() {
		Quest quest = null;
		for(Quest temp : questList) {
			if(temp.getType() == 0 || temp.getType() == 1) {
				quest = temp;
				break;
			}
		}
		return quest;
	}

	
	/**
	 * 진행중인 퀘스트 현황을 문자 배열로 반환한다.
	 * @return
	 */
	public String[] getQuestInProgress() {
		String template[] = questInProgressTempl();
		Quest quest = getQuestInQuestList();
		String questArr[] = getQuestStrArr(quest.getQuestContent());
		
		for(int i=0; i<4; i++) {
			template[3+i] = template[3+i].replace("quest"+i, getStrPlusSpace(questArr[i],43));
		}
		
		for(int i=0; i<3; i++) {
			QuestDetail questDetail = quest.getQuestDetailList().get(i);
			template[11+i] = template[11+i].replace("monster"+i, getStrPlusSpace(" "+questDetail.getMonsterName()
			+" "+questDetail.getTargetAchievements()+" / "+questDetail.getTargetTotalCount(),43));
		}
		
		return template;
	}
	
	public String[] questInProgressTempl() {
		String[] template = new String[16];
		template[0]= "          ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓";
		template[1]= "          ┃               진행중인 퀘스트             ┃";                     		
		template[2]= "          ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫"; 
		template[3]= "          ┃quest0┃";
		template[4]= "          ┃quest1┃";
		template[5]= "          ┃quest2┃"; 
		template[6]= "          ┃quest3┃"; 
		template[7]= "          ┃                                           ┃"; 
		template[8]= "          ┃                                           ┃"; 
		template[9]= "          ┃                                           ┃"; 
		template[10]="          ┃                                           ┃"; 
		template[11]="          ┃monster0┃"; 
		template[12]="          ┃monster1┃"; 
		template[13]="          ┃monster2┃"; 
		template[14]="          ┃                                           ┃"; 
		template[15]="          ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛";
		return template;
	}
	
	/**
	 * 레벨에 맞는 체력,마력을 재조정한다.
	 * 
	 * @param lv : 캐릭터 레벨
	 */
	public void setLevelHpAndMp(int lv) {
		int upPoint = lv - stat.getPrevLv();
		maxHp += upPoint * 50;
		maxMp += upPoint * 50;
		hp = maxHp;
		mp = maxMp;
	}
	
	/**
	 * 캐릭터가 몬스터에게 공격을 받는다. 
	 * 착용 장비의 방어력, 스탯을 계산해 최종 피해 수치를 반환한다.
	 * 
	 * @param attack : 몬스터의 공격력
	 * @return int : 피해 입은 수치
	 */
	public int setDamage(int attack) {
		int deffense = 0;
		int armorDeffense = 0;
		if(equip.getArmor() != null) {
			armorDeffense = equip.getArmor().getPhysicalDefense();;
		}
		deffense = stat.getDefensePower() + armorDeffense;
		int resultAttak = 0;
		if(attack < deffense) {
			resultAttak = 1;
		}else {
			resultAttak = attack - deffense;
		}
		hp -= resultAttak;
		if(hp <= 0) {
			isDie = true;
			setHp(0);
		}
		return resultAttak;
	}
	
	/**
	 * 캐릭터가 죽었을때 부활 시킵니다. 
	 * 초기 마을로 강제이동, 체력 30% 회복
	 */
	public void resurrection() {
		setLocation("base1-0-0");
		setHp(maxHp / 100 * 30);
		isDie = false;
	}
	
	/**
	 * 동일한 퀘스트를 진행중인지 확인한다.
	 * 
	 * @param quest : 확인할 퀘스트 정보
	 * @return true : 동일 퀘스트 진행중, false : 해당 퀘스트 없음
	 */
	public boolean hasQuest(Quest quest) {
		boolean result = false;
		for(Quest temp : questList) {
			if(temp.getQuestId() == quest.getQuestId())
				result = true;
		}
		return result;
	}
	
	/**
	 * 퀘스트 목록에 새로운 퀘스트를 추가한다.
	 * 
	 * @param quest : 새로운 퀘스트
	 */
	public void addQuest(Quest quest) {
		if(hasQuest(quest)) {
			return;
		}
		questList.add(quest);
	}
	
	/**
	 * 캐릭터의 상태창을 보여줄 문자열 템플릿을 반환한다.
	 * 
	 * @return String
	 */
	public String statusTempl() {
		String spaceBlank = "                                                                                     ";
		String str = "";
		str+=spaceBlank+"┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
		str+=spaceBlank+"┃                   상태 창                 ┃\n";                     		
		str+=spaceBlank+"┣━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃이름   ┃name┃\n";
		str+=spaceBlank+"┣━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n";
		str+=spaceBlank+"┃직업   ┃job┃\n";
		str+=spaceBlank+"┣━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n";
		str+=spaceBlank+"┃레벨   ┃level┃\n"; 
		str+=spaceBlank+"┣━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃체력   ┃hp┃\n"; 
		str+=spaceBlank+"┣━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃마력   ┃mp┃\n"; 
		str+=spaceBlank+"┣━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃경험치 ┃exp┃\n"; 
		str+=spaceBlank+"┗━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n";
		return str;
	}
	
	/**
	 * 캐릭터의 상태창을 보여준다. 템플릿에 필요한 값을 넣어 콘솔창에 나타낸다.
	 */
	public void printStatus() {
		String template = statusTempl();
		String characterHpBarStr = "";
		int chHp = getHp();
		int chMaxHp = getMaxHp();
		for(int i=0; i<20; i++) {
			if(chHp == 0 || chMaxHp == 0) {
				characterHpBarStr += "□";
			}else {
				int hp = chHp / (chMaxHp / 20);
				if(hp >= i) {
					characterHpBarStr += "■";
				}else {
					characterHpBarStr += "□";
				}
			}
		}
		String characterMpBarStr = "";
		int chMp = getMp();
		int chMaxMp = getMaxMp();
		for(int i=0; i<20; i++) {
			if(chMp == 0 || chMaxMp == 0) {
				characterMpBarStr += "□";
			}else {
				int mp = chMp / (chMaxMp / 20);
				if(mp >= i) {
					characterMpBarStr += "■";
				}else {
					characterMpBarStr += "□";
				}
			}
		}
		String characterExpBarStr = "";
		int chExp = getExp();
		int chMaxExp = getLv() * 100;
		for(int i=0; i<20; i++) {
			if(chExp == 0) {
				characterExpBarStr += "□";
			}else {
				int exp = chExp / (chMaxExp / 20);
				if(exp >= i) {
					characterExpBarStr += "■";
				}else {
					characterExpBarStr += "□";
				}
			}
		}
		template = template.replace("name", " "+getStrPlusSpace(getCharacterId(),34));
		template = template.replace("job", " "+getStrPlusSpace(getJob(),34));
		template = template.replace("level", " "+getStrPlusSpace(getLv()+"",34));
		template = template.replace("hp", " "+getStrPlusSpace(characterHpBarStr+" "+chHp+"/"+chMaxHp,34));
		template = template.replace("mp", " "+getStrPlusSpace(characterMpBarStr+" "+chMp+"/"+chMaxMp,34));
		template = template.replace("exp", " "+getStrPlusSpace(characterExpBarStr+" "+chExp+"/"+chMaxExp,34));
		System.out.println(template);
	}
	
	/**
	 * 문자열에 필요한 공백을 붙여서 반환한다.
	 * 
	 * @param str 뒤에 공백을 붙일 문자열
	 * @param totalSpace 필요한 공백 크기
	 * @return String
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
	 * 문자열을 받아 공백의 개수로 변환했을때 몇칸이 나오는지 알려준다.
	 * 
	 * @param str : 공백 개수를 알고 싶은 문자열
	 * @return int : 공백의 개수
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
            }else if(index == 46 || index == 47 || index == 58 || index == 40 || index == 41) { // /:()
            	result ++;
            }else if(index == 32) {//공백
            	result ++;
            }else {//한글
                result +=2;
            }
		}
		return result;
	}
	
	public String[] getQuestStrArr(String str) {
		String[] quest = {"","","",""};
		int questSpaceCount = 0;
		int questIndex = 0;

		for(int i =0; i < str.length(); i++) {
			int result = 0;
			int index = str.charAt(i);
			if(index >= 0 && index <= 127) { //숫자
				result++;
				questSpaceCount++;
            }else if(index == 9472 || index == 9474 || index == 9484 || index == 9488 || index == 9496 
            		|| index == 9492 || index == 9500 ||index == 9516 ||index == 9508 || index == 9524 
            		||index == 9532 || index == 9473 || index == 9475 || index == 9487
                	|| index == 9491 || index == 9499 || index == 9495 || index == 9507
                	|| index == 9523 || index == 9515 || index == 9531 || index == 9547
                	|| index == 9632 || index == 9633
            		) { //─│┌┐┘└├┬┤┴┼ ━ ┃ ┏ ┓ ┛ ┗ ┣ ┳┫ ┻ ╋■□
            	result ++;
            	questSpaceCount++;
            }else {//한글
                result +=2;
                questSpaceCount+=2;
            }
			
			if(questSpaceCount <= 42) {
				quest[questIndex] += str.charAt(i)+"";
			}else {
				questSpaceCount = result;
				questIndex++;
				quest[questIndex] += str.charAt(i)+"";
			}
		}
		
		for(int i=0; i<quest.length; i++) {
			if(!"".equals(quest[i])) {
				quest[i] = " "+quest[i];
			}
		}
		return quest;
	}
	
	public String getCharacterId() {
		return characterId;
	}

	public void setCharacterId(String characterId) {
		this.characterId = characterId;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
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

	public int getMaxMp() {
		return maxMp;
	}

	public void setMaxMp(int maxMp) {
		this.maxMp = maxMp;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String[] getMotion() {
		return motion;
	}

	public void setMotion(String[] motion) {
		this.motion = motion;
	}

	public String[] getLEFT() {
		return LEFT;
	}

	public String[] getRIGHT() {
		return RIGHT;
	}

	public String[] getFRONT() {
		return FRONT;
	}

	public String[] getBACK() {
		return BACK;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public CharacterEquip getEquip() {
		return equip;
	}

	public void setEquip(CharacterEquip equip) {
		this.equip = equip;
	}

	public String[] getBATTLE_FRONT() {
		return BATTLE_FRONT;
	}

	public Stat getStat() {
		return stat;
	}

	public void setStat(Stat stat) {
		this.stat = stat;
	}

	public boolean isDie() {
		return isDie;
	}

	public void setDie(boolean isDie) {
		this.isDie = isDie;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public List<Quest> getQuestList() {
		return questList;
	}

	public void setQuestList(List<Quest> questList) {
		this.questList = questList;
	}

	
	
}
