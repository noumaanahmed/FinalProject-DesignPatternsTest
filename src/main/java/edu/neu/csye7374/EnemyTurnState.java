package edu.neu.csye7374;

public class EnemyTurnState implements GameState {

    @Override
    public void playerAttack(GameFacade game) {
        // Ignore player input – it's enemy's turn
        game.log("It is not your turn.");
    }

    @Override
    public void playerHeal(GameFacade game) {
        // Ignore player input – it's enemy's turn
        game.log("It is not your turn.");
    }

    @Override
    public String getName() {
        return "Enemy Turn";
    }
}
