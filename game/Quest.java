package game;

import java.util.ArrayList;
import java.util.List;

/**
 * 클래스 명 : Quest class
 * 
 * 설명 : 퀘스트의 상세 정보를 담고 있고 처치해야하는 몬스터 정보를 포함한다.
 * 
 * 
 * @author sim-younghyun
 *
 */
public class Quest {
	
	/**
	 * 처치 해야하는 몬스터 정보를 리스트로 관리한다.
	 */
	private List<QuestDetail> questDetailList = new ArrayList<QuestDetail>();
	
	/**
	 * 퀘스트 식별 번호
	 */
	private int questId;
	
	/**
	 * 퀘스트 진행 상황
	 * 0 : 진행중, 1 : 조건 충족, 2 : 완료 
	 */
	private int type;
	
	/**
	 * 퀘스트 현황 내용
	 */
	private String questContent;
	
	/**
	 * 퀘스트 제목
	 */
	private String questTitle;
	
	private String questNPCLocation;
	
	public Quest(String questDataStr, int questId, String questContent, String questTitle, String questNPCLocation) {
		String[] questData = questDataStr.split(",");
		for(String detailInfo : questData) {
			String monsterName = detailInfo.split(":")[0];
			int targetTotalCount = Integer.parseInt(detailInfo.split(":")[1]);
			QuestDetail questDetail = new QuestDetail(monsterName, targetTotalCount);
			questDetailList.add(questDetail);
			this.questContent = questContent;
			this.questNPCLocation = questNPCLocation;
		}
		
		this.questId = questId;
		this.type = 0;
		this.questTitle = questTitle; 
	}

	/**
	 * 퀘스트 완료 여부를 확인한다.
	 * 
	 * @return true : 퀘스트 완료, false : 퀘스트 미완료
	 */
	public boolean isCompleteQuest() {
		boolean result = true;
		for(QuestDetail temp : questDetailList) {
			if(!temp.isComplete()) {
				result = false;
				break;
			}
		}
		
		if(result) {
			type = 1;
		}
		return result;
	}
	
	public List<QuestDetail> getQuestDetailList() {
		return questDetailList;
	}

	public void setQuestDetailList(List<QuestDetail> questDetailList) {
		this.questDetailList = questDetailList;
	}

	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getQuestContent() {
		return questContent;
	}

	public void setQuestContent(String questContent) {
		this.questContent = questContent;
	}

	public String getQuestTitle() {
		return questTitle;
	}

	public void setQuestTitle(String questTitle) {
		this.questTitle = questTitle;
	}

	public String getQuestNPCLocation() {
		return questNPCLocation;
	}

	public void setQuestNPCLocation(String questNPCLocation) {
		this.questNPCLocation = questNPCLocation;
	}
	
	
}

/**
 * 클래스 명 : QuestDetail class
 * 
 * 설명 : 처치해야 하는 몬스터의 이름, 처치 해야하는 숫자등 퀘스트 상세 정보를 담은 클래스다.
 * 
 * @author sim-younghyun
 *
 */
class QuestDetail{
	
	/**
	 * 처치 해야하는 몬스터 이름
	 */
	private String monsterName;
	
	/*
	 * 처치 해야하는 수 
	 */
	private int targetTotalCount;
	
	/**
	 * 처치한 수
	 */
	private int targetAchievements;
	
	/**
	 * 몬스터 처치 완료 여부
	 */
	private boolean isComplete;
	
	public QuestDetail(String monsterName, int targetTotalCount) {
		this.monsterName = monsterName;
		this.targetTotalCount = targetTotalCount;
		this.targetAchievements = 0;
		this.isComplete = false;
	}
	
	/**
	 * 처치한 수 증가
	 */
	public void addTargetAchievements() {
		targetAchievements++;
		if(targetAchievements >= targetTotalCount) {
			isComplete = true;
		}
	}
	
	public String getMonsterName() {
		return monsterName;
	}

	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}

	public int getTargetTotalCount() {
		return targetTotalCount;
	}

	public void setTargetTotalCount(int targetTotalCount) {
		this.targetTotalCount = targetTotalCount;
	}

	public int getTargetAchievements() {
		return targetAchievements;
	}

	public void setTargetAchievements(int targetAchievements) {
		this.targetAchievements = targetAchievements;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
	
	
	
}