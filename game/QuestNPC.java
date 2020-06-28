package game;

/**
 * 클래스 명 : QuestNPC class
 * 설명 : 퀘스트를 제공하는 NPC 클래스이다.
 * 기능 : 퀘스트를 주고 퀘스트를 완료했는지 확인한다. 
 * @author sim-younghyun
 *
 */
public class QuestNPC extends NPC {
	
	/**
	 * 퀘스트 제목
	 */
	private String requestTitle;
	
	/**
	 * 퀘스트 내용
	 */
	private String requestContent;
	
	/**
	 * 퀘스트가 진행중일때 내용
	 */
	private String requestContentInProgress;
	
	/**
	 * 퀘스트가 완료될때 내용
	 */
	private String questCompleteContent;
	
	/**
	 * 퀘스트 완료에 필요한 몬스터 정보
	 */
	private Quest detailData;
	
	private String orkNormalContent;
	
	public QuestNPC(String name, String npcType, String location, String requestContent, String requestTitle
			, String monsterData, int questId, String requestContentInProgress, String questCompleteContent
			, String questContent, String orkNormalContent) {
		super(name, npcType, location);
		this.requestContent = requestContent;
		this.requestTitle = requestTitle;
		this.detailData = new Quest(monsterData, questId, questContent, requestTitle, location);
		this.requestContentInProgress = requestContentInProgress;
		this.questCompleteContent = questCompleteContent;
		this.orkNormalContent = orkNormalContent;
	}
	
	/**
	 * 퀘스트 제안 하는 문자열을 반환한다.
	 */
	public void printNPCQuest() {
		String template = questTempl();
		String questContent[] = getQuestStrArr(requestContent);
		for(int i=0; i<6; i++) {
			template = template.replace("quest"+i, getStrPlusSpace(questContent[i],50));
		}
		System.out.println(template);
	}
	
	/**
	 * 이미 진행중인 퀘스트일 경우 반환되문 문자열
	 */
	public void printNPCQuestAlready() {
		String template = alreadyQuestTempl();
		String questContent[] = getQuestStrArr(requestContentInProgress);
		for(int i=0; i<6; i++) {
			template = template.replace("quest"+i, getStrPlusSpace(questContent[i],50));
		}
		System.out.println(template);
	}

	/**
	 * 완료한 퀘스트일 경우 반환되는 문자열
	 */
	public void printNPCQuestComplete() {
		String template = alreadyQuestTempl();
		String questContent[] = getQuestStrArr(questCompleteContent);
		for(int i=0; i<6; i++) {
			template = template.replace("quest"+i, getStrPlusSpace(questContent[i],50));
		}
		System.out.println(template);
	}
	
	public void printNPCNormalContent() {
		String template = alreadyQuestTempl();
		String questContent[] = getQuestStrArr(orkNormalContent);
		for(int i=0; i<6; i++) {
			template = template.replace("quest"+i, getStrPlusSpace(questContent[i],50));
		}
		System.out.println(template);
	}
	
	
	
	/**
	 * 퀘스트 요청시 보여줄 문자열 템플릿을 반환한다.
	 */
	public String questTempl() {
		String spaceBlank = "                                                                      ";
		String str = "";
		
		str+=spaceBlank+"┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
		str+=spaceBlank+"┃                        퀘스트                    ┃\n";                     		
		str+=spaceBlank+"┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃quest0┃\n";
		str+=spaceBlank+"┃quest1┃\n";
		str+=spaceBlank+"┃quest2┃\n"; 
		str+=spaceBlank+"┃quest3┃\n"; 
		str+=spaceBlank+"┃quest4┃\n"; 
		str+=spaceBlank+"┃quest5┃\n"; 
		str+=spaceBlank+"┃                                                  ┃\n"; 
		str+=spaceBlank+"┃           1. 수락하기       2.거절하기           ┃\n"; 
		str+=spaceBlank+"┃                                                  ┃\n"; 
		str+=spaceBlank+"┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n";
		
		return str;
	}
	
	public String alreadyQuestTempl() {
		String spaceBlank = "                                                                      ";
		String str = "";
		
		str+=spaceBlank+"┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
		str+=spaceBlank+"┃                        퀘스트                    ┃\n";                     		
		str+=spaceBlank+"┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n"; 
		str+=spaceBlank+"┃quest0┃\n";
		str+=spaceBlank+"┃quest1┃\n";
		str+=spaceBlank+"┃quest2┃\n"; 
		str+=spaceBlank+"┃quest3┃\n"; 
		str+=spaceBlank+"┃quest4┃\n"; 
		str+=spaceBlank+"┃quest5┃\n"; 
		str+=spaceBlank+"┃                                                  ┃\n"; 
		str+=spaceBlank+"┃                                                  ┃\n"; 
		str+=spaceBlank+"┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n";
		
		return str;
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
	
	public String[] getQuestStrArr(String str) {
		String[] quest = {"","","","","",""};
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
			
			if(questSpaceCount <= 48) {
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
            }else if(index == 63 || index == 46 || index == 47 || index == 58 || index == 40 || index == 41) { // /:()
            	result ++;
            }else if(index == 32) {//공백
            	result ++;
            }else {//한글
                result +=2;
            }
		}
		return result;
	}

	public String getRequestTitle() {
		return requestTitle;
	}

	public void setRequestTitle(String requestTitle) {
		this.requestTitle = requestTitle;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	public Quest getDetailData() {
		return detailData;
	}

	public void setDetailData(Quest detailData) {
		this.detailData = detailData;
	}

	public String getRequestContentInProgress() {
		return requestContentInProgress;
	}

	public void setRequestContentInProgress(String requestContentInProgress) {
		this.requestContentInProgress = requestContentInProgress;
	}

	public String getQuestCompleteContent() {
		return questCompleteContent;
	}

	public void setQuestCompleteContent(String questCompleteContent) {
		this.questCompleteContent = questCompleteContent;
	}

	
	
	
}
