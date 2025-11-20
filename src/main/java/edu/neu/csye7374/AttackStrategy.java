package edu.neu.csye7374;

/**
 * Design Pattern: Strategy
 * ------------------------
 * Encapsulates different attack behaviors for a Character.
 * Concrete strategies: AggressiveAttack, DefensiveAttack, CriticalStrikeDecorator.
 */
public interface AttackStrategy {
    void execute(Character self, Character target);
    String getName();
}
