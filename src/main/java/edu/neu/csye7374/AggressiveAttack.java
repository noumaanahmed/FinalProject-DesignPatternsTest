package edu.neu.csye7374;

import java.util.Random;

/**
 * Design Pattern: Strategy (Concrete Strategy)
 * -------------------------------------------
 * Aggressive attack behavior: deals 10–20 damage to the target.
 */
public class AggressiveAttack implements AttackStrategy {

    private final Random rand = new Random();

    @Override
    public void execute(Character self, Character target) {
        if (target == null) return;

        int damage = rand.nextInt(11) + 10; // 10–20 inclusive
        target.takeDamage(damage);
        self.notifyObservers(
                self.getName() + " attacked " + target.getName()
                        + " for " + damage + " damage!");
    }

    @Override
    public String getName() {
        return "Aggressive";
    }
}
