package game;

import game.assets.ArcherSkin;

/**
 * 클래스 명 : Archer class
 * 설명 : 궁수는 회피력을 가지고 있고 일정 확률로 몬스터의 공격을 받았을때 회피할 수 있다.
 * 
 * @author sim-younghyun
 *
 */
public class Archer extends Character{

	/**
	 * 회피 수치
	 */
	int miss;
	
	public Archer(String id) {
		super( id,  "archer",  200,  200,  200000, ArcherSkin.LEFT.CODE,ArcherSkin.RIGHT.CODE,ArcherSkin.FRONT.CODE,ArcherSkin.BACK.CODE, ArcherSkin.BATTLE_GP.CODE);
		Stat stat = new Stat(2, 1, 1, 1, 1);
		super.setStat(stat);
		Skill skill = new Skill("난사", 20, "[스킬] 난사를 시전했습니다.", 20, 5);
		super.setSkill(skill);
		this.miss = 10;
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
	
	/**
	 * 궁수가 몬스터에게 공격을 받는다. 
	 * 착용 장비의 방어력, 스탯을 계산해 최종 피해 수치를 반환한다.
	 * 회피 수치만큼의 확률로 적의 공격을 회피할 수 있다.
	 * 
	 * @param attack : 몬스터의 공격력
	 * @return int : 피해 입은 수치
	 */
	public int setDamage(int attack) {
		int deffense = 0;
		int armorDeffense = 0;
		if(super.getEquip().getArmor() != null) {
			armorDeffense = super.getEquip().getArmor().getPhysicalDefense();;
		}
		deffense = super.getStat().getDefensePower() + armorDeffense;
		int resultAttak = 0;
		if(attack < deffense) {
			resultAttak = 1;
		}else {
			if((int)(Math.random()*100)+1 <= miss) {
				resultAttak = 0;
			}else {
				resultAttak = attack - deffense;
			}
		}
		super.setHp(super.getHp() - resultAttak);
		if(super.getHp() <= 0) {
			super.setDie(true);
			setHp(0);
		}
		return resultAttak;
	}
}
