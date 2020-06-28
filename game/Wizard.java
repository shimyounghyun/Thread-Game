package game;

import game.assets.WizardSkin;

/**
 * 클래스 명 : Wizard class
 * 설명 : 마법사를 만들때 사용한다. 마법사는 몬스터가 물리형 몬스터일 경우 추가데미지를 줄 수 있다.
 * @author sim-younghyun
 *
 */
public class Wizard extends Character{

	public Wizard(String id) {
		super( id,  "wizard",  100,  300,  100000, WizardSkin.LEFT.CODE,WizardSkin.RIGHT.CODE,WizardSkin.FRONT.CODE,WizardSkin.BACK.CODE, WizardSkin.BATTLE_GP.CODE);
		Stat stat = new Stat(2, 1, 1, 1, 1);
		super.setStat(stat);
		Skill skill = new Skill("썬더 볼트", 20, "[스킬] 썬더 볼트를 시전했습니다.", 20, 5);
		super.setSkill(skill);
	}
	
	/**
	 *  캐릭터의 능력치와 장착된 무기의 공격력등을 합산해 공격 수치를 반환한다.
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
		return attackPoint;
	}
}
