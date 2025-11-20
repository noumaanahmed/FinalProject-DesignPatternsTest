package edu.neu.csye7374;

public class PlayerTurnState implements GameState {

    @Override
    public void playerAttack(GameFacade game) {
        game.basicPlayerAttack();
        if (game.isBattleOver()) {
            game.setState(new GameOverState());
        } else {
            game.setState(new EnemyTurnState());
        }
    }

    @Override
    public void playerHeal(GameFacade game) {
        game.basicPlayerHeal();
        if (game.isBattleOver()) {
            game.setState(new GameOverState());
        } else {
            game.setState(new EnemyTurnState());
        }
    }

    @Override
    public String getName() {
        return "Player Turn";
    }
}
