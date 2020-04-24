package model.cards.spells;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class DivineSpirit extends Spell implements MinionTargetSpell {
	String description;
	public DivineSpirit() {
		super("Divine Spirit", 3, Rarity.BASIC);
		description="Doubles the current and max HP of a minion";
	}

	@Override
	public void performAction(Minion m) {
		m.setMaxHP(m.getMaxHP() * 2);
		m.setCurrentHP(m.getCurrentHP() * 2);

	}

}
