package edu.neu.csye7374;

import java.util.Random;

/**
 * Design Pattern: Strategy (Concrete Strategy)
 * -------------------------------------------
 * Defensive behavior: instead of attacking, the character heals itself.
 */
public class DefensiveAttack implements AttackStrategy {

    private final Random rand = new Random();

    @Override
    public void execute(Character self, Character target) {
        if (!self.isAlive()) return;

        int healAmt = rand.nextInt(7) + 8; // 8–14 inclusive
        self.heal(healAmt);
        self.notifyObservers(
                self.getName() + " focused defensively and healed "
                        + healAmt + " HP!");
    }

    @Override
    public String getName() {
        return "Defensive";
    }
}
