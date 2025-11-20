package edu.neu.csye7374;

import java.util.Random;

public class CriticalStrikeDecorator extends AttackDecorator {

    private final Random rand = new Random();

    public CriticalStrikeDecorator(AttackStrategy inner) {
        super(inner);
    }

    @Override
    public void execute(Character self, Character target) {
        inner.execute(self, target);
        if (target != null && target.isAlive()) {
            // 30% chance for extra 5 damage
            if (rand.nextInt(100) < 30) {
                int extra = 5;
                target.takeDamage(extra);
                self.notifyObservers("\u001B[35mCritical strike! "
                        + self.getName() + " dealt extra " + extra + " damage!\u001B[0m");
            }
        }
    }

    @Override
    public String getName() {
        return inner.getName() + "+Crit";
    }
}
