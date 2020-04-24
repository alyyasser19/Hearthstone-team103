package model.cards.spells;

import model.cards.Rarity;
import model.cards.minions.Minion;

import java.util.ArrayList;

public class LevelUp extends Spell implements FieldSpell {
	public LevelUp() {
		super("Level Up!", 6, Rarity.EPIC);
		setDescription("Increase the attack, current, and max HP of all silver hand recruits by 1");
	}

	@Override
	public void performAction(ArrayList<Minion> field) {
		for (int i = 0; i < field.size(); i++) {
			Minion m = field.get(i);
			if (m.getName().equals("Silver Hand Recruit")) {
				m.setAttack(m.getAttack() + 1);
				m.setMaxHP(m.getMaxHP() + 1);
				m.setCurrentHP(m.getCurrentHP() + 1);
			}
		}

	}

}
