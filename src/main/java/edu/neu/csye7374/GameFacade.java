package edu.neu.csye7374;

public class GameFacade {

    private Character player;
    private Character enemy;

    private GameState state;
    private GameObserver observer;

    private final GameConfig config = GameConfig.getInstance();

    public GameFacade() {
        this.state = new GameOverState(); // until a game starts
    }

    public void setObserver(GameObserver observer) {
        this.observer = observer;
    }

    void log(String msg) {
        if (observer != null) {
            observer.onEvent(msg);
        }
    }

    public void startNewGame(String name, String type, int difficulty) {
        config.setDifficulty(difficulty);

        // Factory + Prototype
        player = CharacterFactory.createCharacter(type, name);

        // Builder for enemy
        int goblinHP;
        switch (difficulty) {
            case 1: goblinHP = 50; break;
            case 2: goblinHP = 80; break;
            case 3: goblinHP = 120; break;
            default: goblinHP = 80;
        }

        CharacterBuilder enemyBuilder = new CharacterBuilder()
                .setName("Goblin")
                .setHealth(goblinHP);
        enemy = enemyBuilder.build();

        // Observer wiring
        if (observer != null) {
            player.addObserver(observer);
            enemy.addObserver(observer);
        }

        // Strategies (Strategy + Decorator)
        AttackStrategy playerBase = new AggressiveAttack();
        player.setStrategy(new CriticalStrikeDecorator(playerBase)); // Decorated strategy
        enemy.setStrategy(new AggressiveAttack());

        state = new PlayerTurnState();

        log("New game started: " + player.getName() + " vs Goblin");
        log("Difficulty: " + difficulty + " | Goblin HP: " + goblinHP);
    }

    // Template-ish helper methods: they define the base "steps" of a turn
    void basicPlayerAttack() {
        if (player != null && enemy != null && player.isAlive() && enemy.isAlive()) {
            player.attack(enemy);
            afterAction();
        }
    }

    void basicPlayerHeal() {
        if (player != null && player.isAlive()) {
            player.heal(10);
            afterAction();
        }
    }

    void basicEnemyAttack() {
        if (enemy != null && player != null && enemy.isAlive() && player.isAlive()) {
            log("Enemy turn:");
            enemy.attack(player);
            afterAction();
        }
    }

    // called after any action
    private void afterAction() {
        if (isBattleOver()) {
            state = new GameOverState();
            if (!player.isAlive()) {
                log("You were defeated!");
            } else if (!enemy.isAlive()) {
                log("You defeated the Goblin!");
            }
        } else if (state instanceof EnemyTurnState) {
            // enemy automatically attacks and then back to player
            basicEnemyAttack();
            if (!isBattleOver()) {
                state = new PlayerTurnState();
            }
        }
    }

    public void playerAttack() {
        state.playerAttack(this);
    }

    public void playerHeal() {
        state.playerHeal(this);
    }

    public boolean isBattleOver() {
        return player == null || enemy == null || !player.isAlive() || !enemy.isAlive();
    }

    public Character getPlayer() {
        return player;
    }

    public Character getEnemy() {
        return enemy;
    }

    public GameState getState() {
        return state;
    }

    void setState(GameState state) {
        this.state = state;
    }
}
