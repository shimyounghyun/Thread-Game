package game;

/**
 * 클래스 명 : Equipment
 * 설명 : 캐릭터가 장착 가능한 아이템을 만들때, 공통적으로 필요한 특징, 기능을 가진 클래스
 * 
 * @author sim-younghyun
 *
 */
public class Equipment extends Item{
	
	/**
	 * 사용 제한 레벨
	 */
	private int limitLevel;
	
	/**
	 * 사용 제한 직업
	 */
	private String limitJob;
	
	/**
	 * 강화 횟수
	 */
	private int enhancement;
	
	public Equipment(String name, String id, String type, int limitLevel, String limitJob, int salePrice, int purchasePrice, int dropPercentage, boolean isBulk) {
		super(name, id, salePrice, purchasePrice,  type, dropPercentage, isBulk, true);
		this.limitLevel = limitLevel;
		this.limitJob = limitJob;
		this.enhancement = 0;
	}
	
	/*
	 * 강화 횟수를 증가 시킨다.
	 */
	public void powerUp() {
		enhancement++;
	}
	
	public int getLimitLevel() {
		return limitLevel;
	}

	public void setLimitLevel(int limitLevel) {
		this.limitLevel = limitLevel;
	}

	public String getLimitJob() {
		return limitJob;
	}

	public void setLimitJob(String limitJob) {
		this.limitJob = limitJob;
	}

	public int getEnhancement() {
		return enhancement;
	}

	public void setEnhancement(int enhancement) {
		this.enhancement = enhancement;
	}
	
	
}
