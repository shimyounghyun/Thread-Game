package game;

import java.util.Scanner;
/**
 * 클래스 명 : SkillThread class
 * 
 * 설명 : 스킬 사용 대기시간을 계산하는 스레드 클래스이다.
 * 
 * 변수 : Battle battle - 캐릭터와 몬스터의 전투를 다루는 객체
 * 
 * 기능 : run():void - 몬스터, 캐릭터가 죽기전까지 500밀리초 간격으로 반복된다.
 *                     Battle 객체의 boolean isSkillTurn의 값이 false일 경우 true로 바꿔준다. 
 * 
 * @author sim-younghyun
 *
 */
public class SkillThread extends Thread{
	
	Battle_ battle;
	
	public SkillThread(Battle_ battle) {
		this.battle = battle;
	}
	
	public void run() {
		Character character = battle.getCharacter();
		Monster monster = battle.getMonster();
		int coolTime = character.getSkill().getCoolTime() * 100;
		
		while(monster.isDie() == false && character.isDie() == false && battle.isStop() == false) {
			if(!battle.isSkillTurn()) {
				try {
					Thread.sleep(coolTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				battle.isSkillTurnOver();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
