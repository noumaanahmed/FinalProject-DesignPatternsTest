package edu.neu.csye7374;

public interface AttackStrategy {
    void execute(Character self, Character target);
    String getName();
}
