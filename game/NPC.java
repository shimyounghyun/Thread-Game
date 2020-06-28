package game;

/**
 * 클래스 명 : NPC class
 * 설명 : 상인, 퀘스트, 몬스터를 만들때 공통적으로 갖는 기능과 특징이 있는 클래스
 * 
 * @author sim-younghyun
 *
 */
public class NPC {
	/**
	 * NPC의 이름
	 */
	private String name;
	
	/**
	 * NPC의 위치
	 */
	private String location;
	
	/**
	 * NPC의 종류
	 */
	private String npcType;
	
	public NPC(String name, String npcType, String location) {
		this.name = name;
		this.npcType = npcType;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNpcType() {
		return npcType;
	}

	public void setNpcType(String npcType) {
		this.npcType = npcType;
	}
	
	
}
