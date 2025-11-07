package edu.neu.csye7374;

import java.util.Random;

public class DefensiveAttack implements AttackStrategy {

    private final Random rand = new Random();

    @Override
    public void execute(Character self, Character target) {
        if (!self.isAlive()) return;
        int healAmt = rand.nextInt(7) + 8; // 8–14 inclusive, Java 8 compatible
        self.heal(healAmt);
        self.notifyObservers("\u001B[32m" + self.getName()
                + " focused defensively and healed " + healAmt + " HP!\u001B[0m");
    }

    @Override
    public String getName() {
        return "Defensive";
    }
}
