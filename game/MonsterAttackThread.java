package game;

/**
 * 클래스 명 : MonsterAttackThread
 *
 * 설명 : 몬스터가 캐릭터를 공격하는 스레드이다.
 *
 * 변수 : Battle battle - 캐릭터와 몬스터의 전투를 담당하는 객체
 *
 * 기능 : run():void - 캐릭터 또는 몬스터가 죽기전까지 battle 객체의 attackCharacter()를 호출한다.
 *                              반복되는 시간 간격은 500밀리초이다. 
 */
public class MonsterAttackThread extends Thread{
//	배틀테스트 battle;
	Battle_ battle;
	BattleDisplay battleDisplay;
	Character character;
	Monster monster;
	
	public MonsterAttackThread(Battle_ battle) {
		this.battle = battle;
		this.character = battle.getCharacter();
		this.monster = battle.getMonster();
		this.battleDisplay = new BattleDisplay(character, monster);
	}
	
	public void run() {
		while(monster.isDie() == false && character.isDie() == false && battle.isStop() == false) {
			if(battle.isMonsterTurn() && battle.isStop() == false) {
				battle.attackCharacter();
				battleDisplay.battleDisplay();
				battleDisplay.battleGraphic();
				battleDisplay.printSystemMsg(battle.getBattleMsg() , 4);
				System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
				System.out.println("1.스킬 공격 2.나가기");
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			if(character.isDie()) { // 캐릭터가 죽었다면
				battle.getMapManager().getCurrentMap().stopBgm();
				battle.getCharacter().resurrection(); // 부활
				battle.getMapManager().getCurrentMap().startBgm();
				break;
			}
		}
	}
	
}
