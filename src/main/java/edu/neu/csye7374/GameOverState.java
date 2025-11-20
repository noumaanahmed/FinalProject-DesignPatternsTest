package edu.neu.csye7374;

public class GameOverState implements GameState {

    @Override
    public void playerAttack(GameFacade game) {
        game.log("The battle is already over.");
    }

    @Override
    public void playerHeal(GameFacade game) {
        game.log("The battle is already over.");
    }

    @Override
    public String getName() {
        return "Game Over";
    }
}
