package edu.neu.csye7374;

public interface GameState {
    void playerAttack(GameFacade game);
    void playerHeal(GameFacade game);
    String getName();
}
