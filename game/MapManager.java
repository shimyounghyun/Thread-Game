package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import game.assets.Tile;

/**
 * 클래스 명 : MapManager class
 * 설명 : 맵 목록을 관리하고 캐릭터의 현재 위치에 맞는 미니맵을 출력한다. 
 * 주요기능 : 미니맵 출력
 * 
 * @author sim-younghyun
 *
 */
public class MapManager {
	/**
	 * 맵 목록
	 */
	private List<Map> mapList;
	
	/**
	 * 플레이중인 캐릭터
	 */
	private Character player;
	
	/**
	 * 플레이중인 캐릭터가 위치한 현재 맵
	 */
	private Map currentMap;
	
	/**
	 * 진행중 퀘스트 
	 */
	private String[] quest;
	
	private HashMap<Integer, String[]> arrowMap;
	
	public MapManager(Character player) {
		/* 변수 초기화 */
		this.player = player;
		mapList = new ArrayList<Map>();
		
		/*
		 * 맵 정보 등록시 숫자별 의미
		 * 
		 * 0 : 빈 공간 				- □
		 * 1 : 벽					- ■
		 * 2 : 몬스터				- □
		 * 3 : 상점					- ST
		 * 4 : 퀘스트 NPC			- Q 
		 * 5 : 이전 맵 이동 포털	- <<
		 * 6 : 다음 맵 이동 포털	- >>
		 * 7 : 보스 몬스터			- BO
		 */
		int[][] baseVilageMiniMap = {
				{0,0,0,0,3,0,0,0}
				,{0,0,0,0,0,0,0,6}
				};
		
		int[][] forest1MiniMap = {
				{5,2,0,0,0,0,0,0}
				,{0,2,0,4,0,1,1,1}
				,{0,0,0,1,0,0,0,0}
				,{0,0,1,1,0,0,0,0}
				,{0,0,0,0,0,7,0,0}
				,{0,0,0,0,0,0,0,0}
				,{0,0,1,0,0,0,0,0}
				,{0,0,1,0,0,0,0,6}
				};
		
		int[][] forest2MiniMap = {
				{5,2,0,0,0,0,0,0}
				,{0,2,0,1,0,1,1,1}
				,{0,0,0,1,0,0,0,0}
				,{0,0,0,1,0,0,0,0}
				,{2,0,0,0,0,7,0,0}
				,{0,0,0,0,0,0,0,0}
				,{0,1,1,0,0,0,0,0}
				,{0,0,1,0,0,0,0,6}
				};
		
		int[][] forest3MiniMap = {
				{5,2,1,1,1,0,0,0}
				,{0,2,0,0,0,0,0,0}
				,{2,0,0,0,0,0,0,0}
				,{0,0,0,0,0,0,0,0}
				,{0,0,0,0,0,7,0,0}
				,{0,0,0,0,0,0,0,0}
				,{0,1,1,0,0,0,0,0}
				,{0,0,1,0,0,0,0,6}
		};
		
		int[][] forest4MiniMap = {
				{5,1,1,2,0,0,0,0}
				,{0,1,1,0,0,0,0,0}
				,{2,0,0,0,0,0,0,0}
				,{0,0,0,0,0,0,0,0}
				,{2,0,0,0,0,7,0,0}
				,{0,0,0,0,0,0,0,0}
				,{0,1,1,0,0,0,0,0}
				,{0,0,1,0,0,0,0,0}
		};
		Tile[][] baseVilageTile = {
				{Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.NPC,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
		};
		
		Tile[][] forest1Tile = {
				{Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.WEED,Tile.MUD_CENTER,Tile.WEED,Tile.GRASS_NPC,Tile.WEED,Tile.ROCK,Tile.ROCK,Tile.ROCK}
				,{Tile.WEED,Tile.MUD_CENTER,Tile.WEED,Tile.ROCK,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.WEED,Tile.MUD_CENTER,Tile.ROCK,Tile.ROCK,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.WEED,Tile.MUD_CENTER,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.WEED,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER}
				,{Tile.WEED,Tile.WEED,Tile.ROCK,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.MUD_CENTER}
				,{Tile.WEED,Tile.WEED,Tile.ROCK,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.MUD_CENTER}
		};
		
		Tile[][] forest2Tile = {
				{Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER}
				,{Tile.WEED,Tile.MUD_CENTER,Tile.WEED,Tile.ROCK,Tile.WEED,Tile.ROCK,Tile.ROCK,Tile.ROCK}
				,{Tile.WEED,Tile.MUD_CENTER,Tile.WEED,Tile.ROCK,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.WEED,Tile.MUD_CENTER,Tile.ROCK,Tile.ROCK,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.WEED,Tile.MUD_CENTER,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.WEED,Tile.MUD_CENTER,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.WEED,Tile.ROCK,Tile.ROCK,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.WEED,Tile.WEED,Tile.ROCK,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
		};
		
		Tile[][] forest3Tile = {
				{Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.ROCK,Tile.ROCK,Tile.ROCK,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER}
				,{Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.WEED,Tile.ROCK,Tile.WEED,Tile.ROCK,Tile.ROCK,Tile.ROCK}
				,{Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.ROCK,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.ROCK,Tile.MUD_CENTER,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.WEED,Tile.WEED,Tile.MUD_CENTER,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.WEED,Tile.WEED,Tile.WEED,Tile.MUD_CENTER,Tile.WEED,Tile.WEED}
				,{Tile.MUD_CENTER,Tile.ROCK,Tile.ROCK,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.MUD_CENTER}
				,{Tile.MUD_CENTER,Tile.WEED,Tile.ROCK,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.MUD_CENTER}
		};
		
		Tile[][] forest4Tile = {
				{Tile.MUD_CENTER,Tile.SEA_CENTER,Tile.SEA_CENTER,Tile.MUD_CENTER,Tile.ROCK,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER}
				,{Tile.MUD_CENTER,Tile.SEA_CENTER,Tile.SEA_CENTER,Tile.MUD_CENTER,Tile.WEED,Tile.ROCK,Tile.ROCK,Tile.ROCK}
				,{Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.WEED,Tile.WEED,Tile.MUD_CENTER,Tile.WEED,Tile.WEED,Tile.WEED}
				,{Tile.MUD_CENTER,Tile.MUD_CENTER,Tile.WEED,Tile.WEED,Tile.WEED,Tile.MUD_CENTER,Tile.WEED,Tile.WEED}
				,{Tile.MUD_CENTER,Tile.ROCK,Tile.ROCK,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.MUD_CENTER}
				,{Tile.MUD_CENTER,Tile.WEED,Tile.ROCK,Tile.WEED,Tile.WEED,Tile.WEED,Tile.WEED,Tile.MUD_CENTER}
		};
		
		mapList.add(new Map(baseVilageMiniMap, 	"base1", 	"시작 마을", 		baseVilageTile	,"/Users/sim-younghyun/Downloads/map1.mp3",	true));
		mapList.add(new Map(forest1MiniMap, 	"forest1", 	"어두운 숲속", 		forest1Tile		,"/Users/sim-younghyun/Downloads/map2.mp3", false));
		mapList.add(new Map(forest2MiniMap, 	"forest2", 	"더 어두운 숲속", 	forest2Tile		,"/Users/sim-younghyun/Downloads/map2.mp3", false));
		mapList.add(new Map(forest3MiniMap, 	"forest3", 	"깊은 숲속", 		forest3Tile		,"/Users/sim-younghyun/Downloads/map2.mp3",	false));
		mapList.add(new Map(forest4MiniMap, 	"forest4", 	"마지막 숲속", 		forest4Tile		,"/Users/sim-younghyun/Downloads/map2.mp3",	false));
		
		currentMap = getCurrentMap(); // 캐릭터의 현재 위치를 기반으로 현재맵 변수에 담는다.
	}
	
	/**
	 *	캐릭터의 위치를 기반으로 현재 맵 정보 셋팅하기
	 */
	public void setCurrentMap() {
		String location = player.getLocation();
		String mapId = location.split("-")[0];
		Iterator<Map> it = mapList.iterator();
		while(it.hasNext()) {
			Map temp = it.next();
			if(temp.getId().equals(mapId)) currentMap = temp;
		}
	}
	
	/**
	 * 캐릭터의 현재 위치와 맵 정보를 텍스트 형식으로 출력한다.
	 */
	public void printMiniMap() {
		int[][] miniMapInfo = currentMap.getMiniMapInfo();	// 캐릭터가 위치한 맵의 정보
		String location = player.getLocation();	// 캐릭터 위치
		
		/* 
		 * 변수별 용도
		 * 
		 * head :	미니맵의 상단 테두리
		 * info :	맵이 갖고 있는 정보를 문자열로 변환
		 * foot :	미니맵의 하단 테두리
		 * helpMsg : 맵에서 사용한 기호별 의미를 설명하는 글
		 */
		
		String head = "";
		String info = "";
		String foot = "";
		String centerBlank = "                                                                                                   ";
		String helpMsg = "                                                                        옷: 캐릭터 위치, ㅁ:일반 길, //:막힌 길, ST:상점, ΒO:보스, >>:다음맵, <<:이전맵\n";
		int y = Integer.parseInt(location.split("-")[1]);	// 캐릭터 y 좌표
		int x = Integer.parseInt(location.split("-")[2]);	// 캐릭터 x 좌표
		
		/* 맵의 크기만큼 반복하여 테두리의 크기, 맵 정보를 문자열로 변환하는 작업 */
		for(int i=0; i<miniMapInfo.length; i++) {
			info += centerBlank+"┃";
			if(i == 0) {
				head += centerBlank+"┏";
				foot += centerBlank+"┗";
			}
			for(int j=0; j<miniMapInfo[i].length; j++) {
				if(i==0) {
					head += "━━";
					foot += "━━";
				}
				if(i == y && j ==x) {
					//info += "☆";
					info += "옷";
				}else {
					switch(miniMapInfo[i][j]) {
					case 0 : info += "ㅁ";//info += "□";
					break;
					case 1 : info += "//";//info += "■";
					break;
					case 2 : info += "ㅁ";//info += "□";
					break;
					case 3 : info += "ST";//info += "S";
					break;
					case 4 : info += " Q";//info += "N";
					break;
					case 5 : info += "<<";//info += "<";
					break;
					case 6 : info += ">>";//info += ">";
					break;
					case 7 : info += "BO";//info += "B";
					break;
					}
				}
			}
			info += "┃\n";
			if(i == 0) {
				head += "┓\n";
				foot += "┛\n";
			}
		}
		System.out.println(head+info+foot+helpMsg);
	}
	
	/**
	 * 캐릭터의 현재 위치를 인자로 받아 맵 목록을 확인해 해당 하는 맵을 반환한다.
	 * 
	 * @param location : 캐릭터의 현재 위치
	 * @return Map : 맵정보 객체
	 */
	public Map getCurrentMap() {
		String location = player.getLocation().split("-")[0];
		Map result = null;
		for(Map map : mapList) {
			if(map.getId().equals(location)) {
				result = map;
			}
		}
		return result;
	}

	/**
	 * 캐릭터의 위치를 기반으로 맵을 그래픽 형태로 콘솔에 출력한다.
	 */
	public void printGraphicMap() {
		Tile[][] map = currentMap.getTileMapInfo();	// 맵의 그래픽 문자열
		String location = player.getLocation();
		int USER_Y = Integer.parseInt(location.split("-")[1]);	// 캐릭터 x좌표
		int USER_X = Integer.parseInt(location.split("-")[2]);	// 캐릭터 y좌표
		
		final int PIXEL_WIDTH = 16;	// 16 x 16 픽셀 이미지의 가로
		final int PIXEL_HEIGHT = 8;	// 16 x 16 픽셀 이미지의 세로
		final int CAMERA_WIDTH = 5;	// 콘솔 창에 나타날 화면의 가로 크기  
		final int CAMERA_HEIGHT = 2; // 콘솔 창에 나타날 화면의 세로 크기
		
		int MAP_FRAME = 0;	// 게임 그래픽 분할 번호
		int CAMERA_X = USER_X / CAMERA_WIDTH * CAMERA_WIDTH; // 게임 화면을 MAP_FRAME으로 분할 했을때 X좌표
		int CAMERA_Y = USER_Y / CAMERA_HEIGHT * CAMERA_HEIGHT; // 게임 화면을 MAP_FRAM으로 분할 했을때 Y좌표 
		
		// 최종 화면이 담길 화면의 가로 길이
		int FINAL_CAMERA_WIDTH = CAMERA_X+CAMERA_WIDTH > currentMap.getMiniMapInfo()[0].length ? 
				CAMERA_X+currentMap.getMiniMapInfo()[0].length % CAMERA_WIDTH :CAMERA_X+CAMERA_WIDTH; 
		// 최종 화면이 담길 화면의 세로 길이
		int FINAL_CAMERA_HEIGHT = CAMERA_Y+CAMERA_HEIGHT > currentMap.getMiniMapInfo().length ?
				CAMERA_HEIGHT + currentMap.getMiniMapInfo().length % CAMERA_HEIGHT : CAMERA_Y+CAMERA_HEIGHT; 
		
		
		final int CAMERA_ROW = PIXEL_WIDTH * PIXEL_HEIGHT * (FINAL_CAMERA_WIDTH - CAMERA_X);
		final int CAMERA_EXTRA_ROW = PIXEL_WIDTH * (FINAL_CAMERA_WIDTH - CAMERA_X);
		
		// 최종 화면이 담기는 배열
		String[] finalTile = new String[(FINAL_CAMERA_WIDTH - CAMERA_X) * 16 * (FINAL_CAMERA_HEIGHT - CAMERA_Y) * 8];
		
		for(int i=CAMERA_Y; i< FINAL_CAMERA_HEIGHT; i++) {
			for(int j= CAMERA_X; j< FINAL_CAMERA_WIDTH; j++) {
				MAP_FRAME++;
				for(int k =0; k < map[i][j].CODE.length; k++) {
					int xScale = (MAP_FRAME-1) % (FINAL_CAMERA_WIDTH - CAMERA_X) ;
					int yScale = (MAP_FRAME-1) / (FINAL_CAMERA_WIDTH - CAMERA_X);
					int y = k / PIXEL_WIDTH;
					int x = k % PIXEL_WIDTH;
					
					if(changeXYtoFrameNum(j,i) == changeXYtoFrameNum(USER_X, USER_Y)) {
						// 캐릭터가 그려진다.
						finalTile[(yScale * CAMERA_ROW) + (y * CAMERA_EXTRA_ROW) + (PIXEL_WIDTH * xScale) +x] 
								= renderUser(map[i][j].CODE[k],player.getMotion()[k]);
					}else if(arrowMap != null && arrowMap.get((i * currentMap.getMiniMapInfo()[0].length + j)+1) != null) { 
						// 화살표가 그려진다.
						finalTile[(yScale * CAMERA_ROW) + (y * CAMERA_EXTRA_ROW) + (PIXEL_WIDTH * xScale) +x]
								= renderUser(map[i][j].CODE[k], arrowMap.get((i * currentMap.getMiniMapInfo()[0].length + j)+1)[k] );
					}else {
						// 일반 맵이 그려진다.
						finalTile[(yScale * CAMERA_ROW) + (y * CAMERA_EXTRA_ROW) + (PIXEL_WIDTH * xScale) +x] 
								=  map[i][j].CODE[k];
					}
					
				}
			}
		}
	
		StringBuffer printStr = new StringBuffer();	// 콘솔에 출력될 문자열이 담길 변수
		for(int i=0, j=0; i< finalTile.length; i++) {
			if(i == 0) {
				if(quest == null) {
					printStr.append(getStrPlusSpace("",72));
				}else {
					printStr.append(getStrPlusSpace(quest[i],72));
					j++;
				}
			}
			if(i !=0 && i % ((FINAL_CAMERA_WIDTH - CAMERA_X) * 16) == 0) {
				if(quest == null) {
					printStr.append("\u001b[0m\n"+getStrPlusSpace("",72));
				}else {
					printStr.append("\u001b[0m\n"+getStrPlusSpace(quest[j],72));
					j++;
				}
			}
			printStr.append(finalTile[i]);
		}
		printStr.append("\u001b[0m\n");
		System.out.println(printStr);
	}
	
	public void setArrowTile(Node element) {
		arrowMap = new HashMap<Integer, String[]>();
		while(element.getParent() != null) {
			int currentX = element.getX();
			int currentY = element.getY();
			element = element.getParent();
			int nextX = element.getX();
			int nextY = element.getY();
			
			int calculateX = currentX - nextX;
			int calculateY = currentY - nextY; 
			
			int data = element.getData();
			
			if(calculateX == 1 && calculateY == 0) { // 왼쪽 이동 a
				arrowMap.put(element.getData(), Tile.ARROW_LEFT.CODE);
			}else if(calculateX == 0 && calculateY == 1) { // 위쪽 이동 w
				arrowMap.put(element.getData(), Tile.ARROW_TOP.CODE);
			}else if(calculateX == -1 && calculateY == 0) { // 오른쪽 이동 d
				arrowMap.put(element.getData(), Tile.ARROW_RIGHT.CODE);
			}else if(calculateX == 0 && calculateY == -1) { // 아래쪽 이동 s
				arrowMap.put(element.getData(), Tile.ARROW_BOTTOM.CODE);
 			}
		}
	}
	
	public int changeXYtoFrameNum(int x, int y) {
		int width = 8; //전체 가로 프레임 수
		return (width * y) + x+1;
	}
	
	/**
	 * 위치값으로 맵리스의 인덱스 번호 반환
	 * @param location : 위치, 형식 : MapId-Y좌표-X좌표
	 * @return int : 인덱스 번호
	 */
	public int getMapListIndexByLocation(String location) {
		int result = -1;
		String mapId = location.split("-")[0];
		for(int i=0; i<mapList.size(); i++) {
			if(mapId.equals(mapList.get(i).getId())) {
				result = i;
			}
		}
		return result;
	}
	
	/**
	 * 캐릭터의 퀘스트 정보를 업데이트한다.
	 */
	public void updateQuestInfo() {
		if(getQuest() != null) {
			setQuest(player.getQuestInProgress());
		}
	}
	
	/**
	 * 캐릭터와 맵 배경이 겹친 부분에서 맵 배경을 제거한다.
	 * 
	 * @param mapTile 맵 이미지를 이루는 문자열
	 * @param character 캐릭터 이미지를 이루는 문자열
	 * @return String
	 */
	public String renderUser(String mapTile, String character) {
		String result = "";
		boolean isBg = character.indexOf("\u001b[48;2;0;0;0m") < 0 ? false : true;
		boolean isFg = character.indexOf("\u001b[38;2;0;0;0m") < 0 ? false : true;
		if(isBg || isFg) {
			if(isBg) 
				result = character.replaceAll("\\u001b\\[48;2;0;0;0m", findRegex("\\u001b\\[48;2;[0-9]{1,3};[0-9]{1,3};[0-9]{1,3}m",mapTile));
			if(isFg)
				result = character.replaceAll("\\u001b\\[38;2;0;0;0m", findRegex("\\u001b\\[38;2;[0-9]{1,3};[0-9]{1,3};[0-9]{1,3}m",mapTile));
		}else {
			result = character;
		}
		
		return result;
	}
	
	/**
	 * 정규식을 이용해 문자열을 검사 후 반환한다. 
	 * 
	 * @param regex : 정규식 패턴으로 사용될 문자열
	 * @param statement : 검사할 문자열
	 * @return 정규식 조건에 맞는 문자열만 반환
	 */
	public String findRegex(String regex, String statement) {
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(statement);
		StringBuilder result = new StringBuilder();
		while(m.find()) {
			result.append(m.group());
		}
		return result.toString();
	}
	
	/**
	 * 캐릭터 위치에 조작 가능한 키 정보 출력
	 */
	public void printControlMsg() {
		int miniMapDetail = currentMap.getMiniMapDetail(player.getLocation());
		String controlMsg = "";
		switch (miniMapDetail) {
			case 0:
				if(currentMap.isCanRecovery()) {
					controlMsg = "1.회복";
				}
				break;
			case 2:
			case 7:
				if(currentMap.isCanRecovery()) {
					controlMsg = "1.전투 2.회복";
				}else {
					controlMsg = "1.전투";
				}
				break;
			case 3:
				if(currentMap.isCanRecovery()) {
					controlMsg = "1.상점 이용하기 2.회복";
				}else {
					controlMsg = "1.상점 이용하기";
				}
				break;
			case 4:
				if(currentMap.isCanRecovery()) {
					controlMsg = "1.대화하기 2.회복";
				}else {
					controlMsg = "1.대화하기";
				}
				break;
			case 5:
				if(currentMap.isCanRecovery()) {
					controlMsg = "1.이전맵으로 이동 2.회복";
				}else {
					controlMsg = "1.이전맵으로 이동";
				}
				break;
			case 6:
				if(currentMap.isCanRecovery()) {
					controlMsg = "1.다음맵으로 이동 2.회복";
				}else {
					controlMsg = "1.다음맵으로 이동";
				}
				break;
		}
		System.out.println(controlMsg);
	}
	
	/**
	 * 캐릭터가 이전 맵으로 이동한다.
	 */
	public void prevMap() {
		int index = mapList.indexOf(currentMap);
		currentMap.stopBgm();
		currentMap = mapList.get(index-1);
		currentMap.startBgm();
		int map[][] = currentMap.getMiniMapInfo();
		String nextMapPortal = "";
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[i].length; j++) {
				if(map[i][j] == 6) nextMapPortal = i+"-"+j;
			}
		}
		player.setLocation(currentMap.getId()+"-"+nextMapPortal);
	}
	
	/**
	 * 캐릭터가 다음 맵으로 이동한다. 
	 */
	public void nextMap() {
		int index = mapList.indexOf(currentMap);
		currentMap.stopBgm();
		currentMap = mapList.get(index+1);
		currentMap.startBgm();
		int map[][] = currentMap.getMiniMapInfo();
		String prevMapPortal = "";
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[i].length; j++) {
				if(map[i][j] == 5) prevMapPortal = i+"-"+j;
			}
		}
		player.setLocation(currentMap.getId()+"-"+prevMapPortal);
	}
	
	/**
	 * 캐릭터의 현재 위치에대한 맵 상세 정보를 반환 한다.
	 */
	public int getMiniMapDetail() {
		return currentMap.getMiniMapDetail(player.getLocation());
	}
	
	public List<Map> getMapList() {
		return mapList;
	}

	public void setMapList(List<Map> mapList) {
		this.mapList = mapList;
	}

	public Character getPlayer() {
		return player;
	}

	public void setPlayer(Character player) {
		this.player = player;
	}

	public void setCurrentMap(Map currentMap) {
		this.currentMap = currentMap;
	}
	
	
	
	
	public String[] getQuest() {
		return quest;
	}

	public void setQuest(String[] quest) {
		this.quest = quest;
	}

	//받아온 문자를 공백 개수단위로 계산해서 총공백 개수보다 부족할 경우 뒤에 추가 하여 반환
	public String getStrPlusSpace(String str,int totalSpace) {
		String result = "";
		int strLength = getSpaceCountFromStr(str);
		for(int i = 0; i < totalSpace - strLength; i++) {
			result+=" ";
		}
		return str+result;
	}

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
            }else if(index == 46 || index == 47 || index == 58 || index == 40 || index == 41) { // ./:()
            	result ++;
            }else if(index == 32) {//공백
            	result ++;
            }else {//한글
                result +=2;
            }
		}
		return result;
	}

	public HashMap<Integer, String[]> getArrowMap() {
		return arrowMap;
	}

	public void setArrowMap(HashMap<Integer, String[]> arrowMap) {
		this.arrowMap = arrowMap;
	}
	
	
}
