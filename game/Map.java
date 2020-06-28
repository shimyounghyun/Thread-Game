package game;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import game.assets.Tile;

/**
 * 클래스 명 : Map class
 * 설명 : 맵 정보를 나타낸다.  
 * 
 * @author sim-younghyun
 *
 */
public class Map {
	/**
	 * 미니맵 정보
	 */
	private int miniMapInfo[][];
	
	/**
	 * 미니맵 이미지 문자열
	 */
	private Tile tileMapInfo[][];
	
	/**
	 * 맵 식별 문자
	 */
	private String id;
	
	/**
	 * 맵 이름
	 */
	private String name;
	
	/**
	 * 맵의 배경음악을 관리하는 객체
	 */
	private Music music;
	
	/**
	 * 배경 음악 파일 경로
	 */
	private String bgmPath;
	
	/**
	 * 맵에서 회복이 가능한지 여부
	 */
	private boolean canRecovery;
	
	public Map(int[][] miniMapInfo, String id, String name, Tile tile[][], String bgm, boolean canRecovery) {
		this.miniMapInfo = miniMapInfo;
		this.id = id;
		this.name = name;
		this.tileMapInfo = tile;
		this.bgmPath = bgm;
		this.music = new Music(bgm, true);
		this.canRecovery = canRecovery;
	}
	
	/**
	 * 미니맵 상세정보에서 해당 하는 숫자를 찾아 X좌표를 반환한다.
	 * @param detailNumber 상세 숫자 
	 * @return int : X좌표
	 */
	public int getMiniMapDetailX(int detailNumber) {
		for(int i=0; i<miniMapInfo[0].length; i++) {
			for(int j=0; j<miniMapInfo[i].length; j++) {
				if(detailNumber == miniMapInfo[i][j]) {
					return j;
				}
			}
		}
		return -1;
	}
	
	/**
	 * 미니맵 상세정보에서 해당 하는 숫자를 찾아 Y좌표를 반환한다.
	 * @param detailNumber 상세 숫자 
	 * @return int : Y좌표
	 */
	public int getMiniMapDetailY(int detailNumber) {
		for(int i=0; i<miniMapInfo[0].length; i++) {
			for(int j=0; j<miniMapInfo[i].length; j++) {
				if(detailNumber == miniMapInfo[i][j]) {
					return i;
				}
			}
		}
		return -1;
	}
	
	/**
	 * 미니맵 정보에 담긴 숫자를 반환한다.
	 * @param location : 캐릭터 위치 정보
	 * @return int : miniMapInfo[y][x]의 값
	 */
	public int getMiniMapDetail(String location) {
		int y = Integer.parseInt(location.split("-")[1]);
		int x = Integer.parseInt(location.split("-")[2]);
		return miniMapInfo[y][x];
	}
	
	//맵 배경음악 시작
	public void startBgm() {
		music.start();
	}
	//맵 배경음악 종료
	public void stopBgm() {
		music.playStop();
		music = new Music(bgmPath, true);
	}
	//맵 배경음악 일시정지
	public void pauseBgm() {
		music.playPause();
	}
	//맵 배경음악 재시작
	public void reStartBgm() {
		music.playBack();
	}
	
	public int[][] getMiniMapInfo() {
		return miniMapInfo;
	}

	public void setMiniMapInfo(int[][] miniMapInfo) {
		this.miniMapInfo = miniMapInfo;
	}

	public Tile[][] getTileMapInfo() {
		return tileMapInfo;
	}

	public void setTileMapInfo(Tile[][] tileMapInfo) {
		this.tileMapInfo = tileMapInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public boolean isCanRecovery() {
		return canRecovery;
	}

	public void setCanRecovery(boolean canRecovery) {
		this.canRecovery = canRecovery;
	}
	
	
}
