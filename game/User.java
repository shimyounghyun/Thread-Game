package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User class는 게임 사용자 정보를 나타낸다.
 *  
 * @author sim-younghyun
 */
public class User {
	
	/**
	 * 사용자의 게임 캐릭터들이 저장 된다.
	 * 
	 */
	private List<Character> characterList;
	
	/**
	 * 사용자 고유 식별 문자
	 * 
	 * 아이디는 영문자만 가능하고 길이를 4 ~ 12자 이내로 제한 한다.
	 */
	private String userId;
	
	/**
	 * 사용자 암호
	 * 
	 * 암호의 길이는 4 ~ 12자 이내로 제한 한다.  
	 */
	private String password;
	
	public User(String userId, String password) {
		this.userId = userId;
		this.password = password;
		this.characterList = new ArrayList<Character>();
	}
	
	/**
	 * 유저의 캐릭터 목록을 문자열로 변환하여 반환 한다.
	 * 
	 * @return 유저 목록이 담긴 문자열
	 */
	public String getCharacterListStr() {
		String result = "";
		int i = 1;
		for(Character c :characterList) {
			result += "["+(i++)+"] : "+c.getCharacterId() +"/"+ c.getJob() +"/"+ c.getLv()+"\n";
		}
		return result;
	}
	
	/**
	 * 캐릭터 목록에서 캐릭터를 하나 선택한다.
	 * 
	 * @param characterID
	 * @return Character
	 */
	public Character selectCharacter(String characterID) {
		Iterator<Character> iterator = characterList.iterator();
		Character result = null;
		while(iterator.hasNext()) {
			Character temp = iterator.next();
			if(characterID.equals(temp.getCharacterId())) {
				result = temp;
			}
		}
		return result;
	}
	
	/**
	 * 캐릭터 인덱스 번호를 받아 해당 캐릭터를 반환
	 * 
	 * @param 캐릭터 목록의 인덱스 번호
	 * @return 캐릭터
	 */
	public Character selectCharacter(int index) {
		return characterList.get(index);
	}
	
	/**
	 * 사용자의 캐릭터 수를 반환한다.
	 * 
	 * @return 캐릭터 리스트 크기
	 */
	public int getCharacterCount() {
		return characterList.size();
	}
	
	/**
	 * 캐릭터 목록에 캐릭터를 추가한다.
	 * 
	 * @param character
	 */
	public void addCharacter(Character character) {
		characterList.add(character);
	}
	
	public List<Character> getCharacterList() {
		return characterList;
	}

	public void setCharacterList(List<Character> characterList) {
		this.characterList = characterList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
