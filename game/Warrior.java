package game;

import game.assets.WarriorSkin;

/**
 * 클래스 명 : Warrior class
 * 설명 : 분노 수치를 가지고 있고 공격할때 일정 확률로 추가 데미지를 줄 수 있는 캐릭터이다. 
 * 
 * @author sim-younghyun
 *
 */
public class Warrior extends Character{
	
	/**
	 * 분노 수치
	 */
	int anger;
	
	public Warrior(String id) {
		super( id,  "warrior",  300,  100,  100000, WarriorSkin.LEFT.CODE,WarriorSkin.RIGHT.CODE,WarriorSkin.FRONT.CODE,WarriorSkin.BACK.CODE, WarriorSkin.BATTLE_GP.CODE);
		Stat stat = new Stat(2, 1, 1, 1, 1);
		super.setStat(stat);
		Skill skill = new Skill("참격", 20, "[스킬] 참격을 시전했습니다.", 20, 5);
		super.setSkill(skill);
		this.anger = 10;
	}
	
	/**
	 * 캐릭터의 능력치와 장착된 무기의 공격력등을 합산해 공격 수치를 반환한다.
	 * 
	 * ex) 전사의 hp가 30% 미만일 경우 공격력이 2배가 된다.
	 * 
	 * @return int : 공격 수치
	 */
	public int attack() {
		int hp = super.getHp();
		int maxHp = super.getMaxHp();
		int weaponAttack = 0;
		if(super.getEquip().getWeapon() != null) {
			Item weapon = super.getEquip().getWeapon();
			weaponAttack = ((Weapon)weapon).getAttackPower();
		}
		int attackPoint = super.getStat().getAttackPower()+super.getStat().getStrength()+weaponAttack;
		attackPoint = attackPoint - (attackPoint / 100 * ((int)(Math.random()*10)+1));
		if((hp / maxHp)*100 <= 30) {
			if((int)(Math.random()*100)+1 <= anger) {
				attackPoint = attackPoint * 2; 
			}
		}
		return attackPoint;
	}

	public int getAnger() {
		return anger;
	}

	public void setAnger(int anger) {
		this.anger = anger;
	}
	
	
}
