package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * 클래스 명 : SystemMsgThread class
 * 
 * 설명 : 게임 팁 메시지를 출력하는 스레드 클래스이다.
 * 
 * 변수 : ArrayList<String> msgList - 게임 팁 정보를 문자열 리스트로 저장한다.
 * 
 * 기능 : run():void - 30초 간격으로 msgList에 담긴 팁 정보를 게임 메시지로 출력한다.
 * @author sim-younghyun
 *
 */
public class SystemMsgThread extends Thread{
	LinkedList<String> systemMsg;
	ArrayList<String> msgList = new ArrayList<String>();
	
	public SystemMsgThread(LinkedList<String> systemMsg) {
		this.systemMsg = systemMsg;
		msgList.add("\u001b[38;2;223;167;26m[시스템] 상태창 단축키 : \\s, 장비창 단축키 : \\e, 인벤토리 단축키 : \\i\u001b[0m");
		msgList.add("\u001b[38;2;223;167;26m[시스템] 캐릭터 선택창 : \\select, 로그 아웃 : \\logout\u001b[0m");
		msgList.add("\u001b[38;2;223;167;26m[시스템] 전사는 전투중 체력이 30% 미만일 경우 일정 확률로 공격력이 2배 증가합니다.\u001b[0m");
		msgList.add("\u001b[38;2;223;167;26m[시스템] 마을에서 휴식을 취할 경우 체력과 마력을 회복할 수 있습니다.\u001b[0m");
	}
	public void run() {
		while(true) {
			int i = (int)(Math.random()*msgList.size());
			systemMsg.add(msgList.get(i));
			try {
				sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
