package model.cards.spells;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class SiphonSoul extends Spell implements LeechingSpell {
	public SiphonSoul() {
		super("Siphon Soul", 6, Rarity.RARE);
		setDescription(": Destroys a minion even if it has a divine shield and restores 3 health points to the hero");
	}

	@Override
	public int performAction(Minion m) {
		m.setCurrentHP(0);
		return 3;
	}

}
