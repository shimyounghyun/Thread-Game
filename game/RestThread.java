package game;

/**
 * 클래스 명 : RestThread
 * 
 * 설명 : 10초 간격으로 마력, 체력을 일정량 회복하는 스레드 클래스이다.
 * 
 * 변수 : boolean isClose - 스레드의 종료 여부
 * 
 * 기능 : run():void - 10초 간격으로 캐릭터의 체력, 마력을 최대치까지 일정량 회복 시킨다.
 *                     isClose의 값이 true일 경우 스레드는 종료된다.
 * @author sim-younghyun
 *
 */
public class RestThread extends Thread{
	Character character;
	GameManager display;
	int x;
	int y;
	boolean isDone;
	public RestThread(Character character, GameManager display) {
		this.character = character;
		this.x = Integer.parseInt(character.getLocation().split("-")[2]);
		this.y = Integer.parseInt(character.getLocation().split("-")[1]);
		this.display = display;
		this.isDone = false;
	}
	public void run() {
		int cx = x;
		int cy = y;
		while(cx == x && cy == y && isDone == false) {
			try {
				sleep(10000);
				cx = Integer.parseInt(character.getLocation().split("-")[2]);
				cy = Integer.parseInt(character.getLocation().split("-")[1]);
				if(cx == x && cy == y && isDone == false) {
					int hp = character.getHp();
					int mp = character.getMp();
					int maxHp = character.getMaxHp();
					int maxMp = character.getMaxMp();
					int recoverHp = 10;
					int recoverMp = 10;
					if(recoverHp+hp > maxHp) recoverHp = recoverHp+10 - maxHp;
					if(recoverMp+mp > maxHp) recoverMp = recoverMp+10 - maxMp;
					character.setHp(character.getHp()+recoverHp);
					character.setMp(character.getMp()+recoverMp);
					if(hp == maxHp) {
						display.getSystemMsg().add("체력이 모두 회복되었습니다.");
					}else {
						display.getSystemMsg().add("휴식으로 체력+"+recoverHp+" 회복되었습니다.");
					}
					if(mp == maxMp) {
						display.getSystemMsg().add("마력이 모두 회복되었습니다.");
					}else {
						display.getSystemMsg().add("휴식으로 마력+"+recoverHp+" 회복되었습니다.");
					}
					display.getMapManager().printMiniMap();
					display.getMapManager().printGraphicMap();
					display.printSystemMsg(display.getSystemMsg(), display.getMapManager().getCurrentMap().getMiniMapInfo().length+8);
					System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
					System.out.println("아무키나 눌러 휴식을 해제합니다.");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
