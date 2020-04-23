package model.cards.spells;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class SealOfChampions extends Spell implements MinionTargetSpell {
	public SealOfChampions() {
		super("Seal of Champions", 3, Rarity.COMMON);
		setDescription("Increases the attack of a minion by 3 and gives it divine shield");
	}

	@Override
	public void performAction(Minion m) {
		m.setAttack(m.getAttack() + 3);
		m.setDivine(true);

	}

}
