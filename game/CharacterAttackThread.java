package game;

/**
 * 클래스 명 : CharacterAttackThread
 * 설명 : 캐릭터가 몬스터를 공격하는 스레드이다.
 * 
 * @author sim-younghyun
 *
 */
public class CharacterAttackThread extends Thread{
//	배틀테스트 battle;
	Battle_ battle;
	BattleDisplay battleDisplay;
	Character character;
	Monster monster;
	
	public CharacterAttackThread(Battle_ battle) {
		this.battle = battle;
		this.character = battle.getCharacter();
		this.monster = battle.getMonster();
		this.battleDisplay = new BattleDisplay(character, monster);
	}
	
	public void run() {
		while(monster.isDie() == false && character.isDie() == false && battle.isStop() == false) {
			if(battle.isCharacterTurn() && battle.isStop() == false) {
				battle.attackMonster();
				battleDisplay.battleDisplay();
				battleDisplay.battleGraphic();
				battleDisplay.printSystemMsg(battle.getBattleMsg(), 4);
				System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
				System.out.println("1.스킬 공격 2.나가기");
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
}
