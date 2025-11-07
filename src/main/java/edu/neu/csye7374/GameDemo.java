package edu.neu.csye7374;

public class GameDemo {

    public static void run() {
        System.out.println("=== Mini RPG Battle Demo ===");

        // Singleton for global config
        GameConfig config = GameConfig.getInstance();
        config.setDifficulty(2);
        System.out.println("Game difficulty: " + config.getDifficulty());

        // Observer (Logger)
        ConsoleLogger logger = new ConsoleLogger("GameLogger");

        // Factory creates characters
        Character hero = CharacterFactory.createCharacter("warrior", "Arthas");
        hero.addObserver(logger);

        Character mage = CharacterFactory.createCharacter("mage", "Jaina");
        mage.addObserver(logger);

        // Strategy pattern — different attack types
        hero.setStrategy(new AggressiveAttack());
        mage.setStrategy(new DefensiveAttack());

        // Command pattern — actions
        CommandInvoker invoker = new CommandInvoker();
        invoker.addCommand(new AttackCommand(hero, mage));
        invoker.addCommand(new AttackCommand(mage, hero));
        invoker.addCommand(new HealCommand(mage, 10));

        invoker.executeAll();

        System.out.println("=== Demo Complete ===");
    }
}
