package edu.neu.csye7374;

import java.util.Random;

public class AggressiveAttack implements AttackStrategy {

    private final Random rand = new Random();

    @Override
    public void execute(Character self, Character target) {
        if (target == null) return;
        int damage = rand.nextInt(11) + 10; // 10–20 inclusive, Java 8 compatible
        target.takeDamage(damage);
        self.notifyObservers("\u001B[31m" + self.getName() + " attacked " + target.getName()
                + " for " + damage + " damage!\u001B[0m");
    }

    @Override
    public String getName() {
        return "Aggressive";
    }
}
