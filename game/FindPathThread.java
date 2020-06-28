package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 클래스 명 : FindPathThread class
 * 
 * 설명 : 퀘스트 완료 후 NPC의 위치까지 자동 이동을 시키기 위한 클래스이다.
 *  
 * 변수 : String startLocation - 시작 위치
 *        String endLocation - 종료 위치
 *        Mapmanager mapManager - 맵관리 객체
 *
 * 기능 : 스레드가 시작되면 시작 위치부터 종료 위치까지 캐릭터를 이동 시킨후 종료된다.
 *  
 * @author sim-younghyun
 *
 */
public class FindPathThread extends Thread {
	
	/**
	 * 길찾기 시작 위치
	 * 형태 : 맵ID-Y좌표-X좌표
	 */
	private String startLocation;
	
	/**
	 * 길찾기 종료 위치
	 * 형태 : 맵ID-Y좌표-X좌표
	 */
	private String endLocation;
	
	/**
	 * 맵관리 객체
	 */
	private MapManager mapManager;
	
	/**
	 * 시작 위치에 해당하는 맵리스트 인덱스 번호
	 */
	private int startMapListIndex;
	
	/**
	 * 종료 위치에 해당하는 맵리스트 인덱스 번호
	 */
	private int endMapListIndex;
	
	/**
	 * 게임 상황별 안내 메시지
	 * ex) 캐릭터의 이동 방향, 몬스터의 출연등
	 */
	private LinkedList<String> systemMsg;
	
	public FindPathThread(String startLocation, String endLocation, MapManager mapManager, LinkedList<String> systemMsg) {
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.mapManager = mapManager;
		this.systemMsg = systemMsg;
		this.startMapListIndex = mapManager.getMapListIndexByLocation(startLocation);
		this.endMapListIndex = mapManager.getMapListIndexByLocation(endLocation);
		
	}

	public void run() {
		Character character = mapManager.getPlayer();
		List<Map> distance = new ArrayList<Map>();
		String endMapId = endLocation.split("-")[0];
		FindePath findePath = new FindePath();
		boolean isIncrease = false;
		
		// 시작 위치 ~ 목표 위치에 해당하는 맵정보 담기
		if(startMapListIndex <= endMapListIndex) {
			for(int i=startMapListIndex; i<=endMapListIndex; i++) {
				distance.add(mapManager.getMapList().get(i));
			}
			isIncrease = true;
		}else{
			for(int i=startMapListIndex; i>=endMapListIndex; i--) {
				distance.add(mapManager.getMapList().get(i));
			}
		}
		
		for(Map temp : distance) {
			if(temp.getId().equals(endMapId)) { //최종 위치인 경우
				String location = character.getLocation();
				int characterY = Integer.parseInt(location.split("-")[1]);
				int characterX = Integer.parseInt(location.split("-")[2]);
				
				int endLocationY = Integer.parseInt(endLocation.split("-")[1]); 
				int endLocationX = Integer.parseInt(endLocation.split("-")[2]);
				
				findePath.init(temp.getMiniMapInfo());
				Node element =
						findePath.search(endLocationY, endLocationX, characterY, characterX);
				
				mapManager.setArrowTile(element);// 맵에 화살표 타일 추가
				
				while(element.getParent() != null) {
					int currentX = element.getX();
					int currentY = element.getY();
					element = element.getParent();
					int nextX = element.getX();
					int nextY = element.getY();
					
					int calculateX = currentX - nextX;
					int calculateY = currentY - nextY; 
					
					if(calculateX == 1 && calculateY == 0) { // 왼쪽 이동 a
						moveAutomatically("a", isIncrease);
					}else if(calculateX == 0 && calculateY == 1) { // 위쪽 이동 w
						moveAutomatically("w", isIncrease);
					}else if(calculateX == -1 && calculateY == 0) { // 오른쪽 이동 d
						moveAutomatically("d", isIncrease);
					}else if(calculateX == 0 && calculateY == -1) { // 아래쪽 이동 s
						moveAutomatically("s", isIncrease);
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}else { // 다른 맵에 있는 경우
				String location = character.getLocation();
				int characterY = Integer.parseInt(location.split("-")[1]);
				int characterX = Integer.parseInt(location.split("-")[2]);
				
				/* 맵의 인덱스가 증가하는가를 구분해 이전맵으로 이동할지, 
				 * 다음맵으로 이동할지를 결정한다. */
				if(isIncrease) {
					// 현재 맵 ~ 다음 맵 경로
					// 6 : 다음 맵 이동 포털
					Map currentMap = mapManager.getCurrentMap();
					int currentMapX = currentMap.getMiniMapDetailX(6);
					int currentMapY = currentMap.getMiniMapDetailY(6);
					findePath.init(temp.getMiniMapInfo());
					Node element =
					findePath.search(currentMapY, currentMapX, characterY, characterX);
					mapManager.setArrowTile(element);// 맵에 화살표 타일 추가
					while(element.getParent() != null) {
						int currentX = element.getX();
						int currentY = element.getY();
						element = element.getParent();
						int nextX = element.getX();
						int nextY = element.getY();
						
						int calculateX = currentX - nextX;
						int calculateY = currentY - nextY; 
						
						if(calculateX == 1 && calculateY == 0) { // 왼쪽 이동 a
							moveAutomatically("a", isIncrease);
						}else if(calculateX == 0 && calculateY == 1) { // 위쪽 이동 w
							moveAutomatically("w", isIncrease);
						}else if(calculateX == -1 && calculateY == 0) { // 오른쪽 이동 d
							moveAutomatically("d", isIncrease);
						}else if(calculateX == 0 && calculateY == -1) { // 아래쪽 이동 s
							moveAutomatically("s", isIncrease);
						}
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}else {
					// 현재 맵 ~ 이전 맵 경로
					// 5 : 이전 맵 이동 포털
					Map currentMap = mapManager.getCurrentMap();
					int currentMapX = currentMap.getMiniMapDetailX(5);
					int currentMapY = currentMap.getMiniMapDetailY(5);
					findePath.init(temp.getMiniMapInfo());
					Node element =
					findePath.search(currentMapY, currentMapX, characterY, characterX);
					mapManager.setArrowTile(element);// 맵에 화살표 타일 추가
					while(element.getParent() != null) {
						int currentX = element.getX();
						int currentY = element.getY();
						element = element.getParent();
						int nextX = element.getX();
						int nextY = element.getY();
						
						int calculateX = currentX - nextX;
						int calculateY = currentY - nextY; 
						
						if(calculateX == 1 && calculateY == 0) { // 왼쪽 이동 a
							moveAutomatically("a", isIncrease);
						}else if(calculateX == 0 && calculateY == 1) { // 위쪽 이동 w
							moveAutomatically("w", isIncrease);
						}else if(calculateX == -1 && calculateY == 0) { // 오른쪽 이동 d
							moveAutomatically("d", isIncrease);
						}else if(calculateX == 0 && calculateY == -1) { // 아래쪽 이동 s
							moveAutomatically("s", isIncrease);
						}
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		mapManager.setArrowMap(null);
			
	}
	
	
	public void moveAutomatically(String input, boolean isIncrease) {
		Character character = mapManager.getPlayer();
		
		// 캐릭터가 이동이 가능한지 판단
		if(!character.isMoveRange(input, mapManager.getCurrentMap().getMiniMapInfo())) {
			systemMsg.add("이동할 수 없습니다.");
			return;
		}
		
		// 캐릭터 위치로 맵 정보 셋팅
		mapManager.setCurrentMap();
		
		// 캐릭터 위치 변화
		character.changeCharacterLocation(input);
		
		// 이동 메세지 저장
		saveSystemMsgFromMove(input);
		
		// 미니맵 출력
		mapManager.printMiniMap();
		
		// 캐릭터, 배경 그래픽 출력
		mapManager.printGraphicMap();
		
		// 게임 메시지 출력
		printSystemMsg(systemMsg, mapManager.getCurrentMap().getMiniMapInfo().length+3);
		
		// 게임 입력창 구분선, 키 설명 출력
		System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println("아무키나 눌러 자동 길찾기 종료");
		
		switch (mapManager.getMiniMapDetail()) {
		case 0:
			break;
		case 2:
		case 7:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			if(!isIncrease) mapManager.prevMap();
			break;
		case 6:
			if(isIncrease) mapManager.nextMap();
			break;
		}
		
		
		// 몬스터 등장 메세지 저장
//		saveSystemMsgFromMap();
	}
	
	/**
	 * 콘솔 창에 게임 진행에 필요한 메시지를 출력한다.
	 * 
	 * @param colCount : 출력되는 총 행의 수에서 colCount의 크기만큼 차감 후 출력된다.
	 */
	public void printSystemMsg(LinkedList<String> systemMsg,int colCount) {
		int msgLength = 33 - colCount;
		String msg[] = new String[msgLength];
		for(int i=0; i<msg.length; i++) {
			msg[i] = "";
		}
		Iterator<String> it = systemMsg.descendingIterator();
		int index = 0;
		while(index < msg.length && it.hasNext()) {
			msg[index] = it.next();
			index++;
		}
		
		for(int i = msg.length-1 ; i >=0; i--) {
			System.out.println(msg[i]);
		}
	}
	
	/**
	 * 캐릭터 이동 값에 해당하는 문자열을 systemMsg에 저장한다.
	 * @param input : 이동키(a,s,w,d)중 하나의 값
	 */
	public void saveSystemMsgFromMove(String input) {
		switch (input) {
		case "a": systemMsg.add("서쪽으로 이동합니다.");
			break;
		case "s": systemMsg.add("남쪽으로 이동합니다.");
			break;
		case "d":systemMsg.add("동쪽으로 이동합니다.");
			break;
		case "w":systemMsg.add("북쪽으로 이동합니다.");
			break;
		}
	}

}
