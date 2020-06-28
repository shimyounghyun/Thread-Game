package game;

/**
 * Main class는 게임을 실행 시킨다.
 * 
 * 이하 실행 되는 모든 class의 JDK 버전은 동일하다.
 * _
 * @version 1.00 19/08/01
 * @author	sim-younghyun
 * @since	JDK 11
 *
 */
public class Main {
	public static void main(String[] args) {
		GameManager gameManager = new GameManager();
		gameManager.start();
	}
}

