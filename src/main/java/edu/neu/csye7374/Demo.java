package edu.neu.csye7374;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Demo {

    public static void gameRun() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\u001B[1m\u001B[35m=== MINI RPG CONSOLE GAME ===\u001B[0m\n");

        // --- Singleton Pattern ---
        GameConfig config = GameConfig.getInstance();
        int diff = readIntInRange(sc, "Enter difficulty (1-Easy, 2-Normal, 3-Hard): ", 1, 3);
        config.setDifficulty(diff);

        // Goblin HP scaling
        int goblinHP;
        switch (diff) {
            case 1:
                goblinHP = 50;
                break;
            case 2:
                goblinHP = 80;
                break;
            case 3:
                goblinHP = 120;
                break;
            default:
                goblinHP = 80;
                break;
        }
        if (goblinHP < 1) goblinHP = 1;
        System.out.println("\nDifficulty set to: " + diff + " | Goblin HP = " + goblinHP + "\n");

        // --- Observer Pattern ---
        ConsoleLogger logger = new ConsoleLogger("GameLogger");

        // --- Factory + Builder Pattern ---
        System.out.println("Choose your class:");
        System.out.println("1. Warrior");
        System.out.println("2. Mage");
        int choice = readIntInRange(sc, "Enter choice: ", 1, 2);
  
        System.out.print("Enter your character name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) name = "Hero";

        Character player = (choice == 1)
                ? CharacterFactory.createCharacter("warrior", name)
                : CharacterFactory.createCharacter("mage", name);
        player.addObserver(logger);

        CharacterBuilder enemyBuilder = new CharacterBuilder()
                .setName("Goblin")
                .setHealth(goblinHP);
        Character enemy = enemyBuilder.build();
        enemy.addObserver(logger);

        // --- Strategy Pattern ---
        int strat = readIntInRange(sc, "\nSelect starting strategy:\n1. Aggressive (Attack)\n2. Defensive (Heal)\nEnter choice: ", 1, 2);
        if (strat == 1)
            player.setStrategy(new AggressiveAttack());
        else
            player.setStrategy(new DefensiveAttack());
        enemy.setStrategy(new AggressiveAttack());

        // --- Command Pattern ---
        CommandInvoker invoker = new CommandInvoker();

        boolean playing = true;
        while (playing) {
            System.out.println("\n\u001B[34m---------------------------------------\u001B[0m");
            System.out.println("\u001B[1m\u001B[34mBATTLE MENU\u001B[0m");
            System.out.println("---------------------------------------");
            System.out.println("Current Strategy: [" + playerStrategyName(player) + "]");
            System.out.println("Player HP: " + player.getHealth() + " | Goblin HP: " + enemy.getHealth() + "\n");

            String currentStrat = playerStrategyName(player).toLowerCase();
            if (currentStrat.equals("aggressive")) {
                System.out.println("1. Attack Enemy");
            } else {
                System.out.println("1. Heal Yourself");
            }
            System.out.println("2. Change Strategy");
            System.out.println("3. Quit Game");

            int action = readIntInRange(sc, "\nChoose an option: ", 1, 3);

            switch (action) {
                case 1:
                    if (currentStrat.equals("aggressive"))
                        invoker.addCommand(() -> player.attack(enemy));
                    else
                        invoker.addCommand(() -> player.heal(10));
                    break;

                case 2:
                    int s = readIntInRange(sc, "\nSelect new strategy:\n1. Aggressive (Attack)\n2. Defensive (Heal)\nEnter choice: ", 1, 2);
                    if (s == 1)
                        player.setStrategy(new AggressiveAttack());
                    else
                        player.setStrategy(new DefensiveAttack());
                    System.out.println("\u001B[36mStrategy changed successfully.\u001B[0m");
                    continue;

                case 3:
                    System.out.println("\u001B[33mExiting game...\u001B[0m");
                    playing = false;
                    continue;
            }

            invoker.executeAll();

            // Enemy turn
            if (playing && enemy.isAlive() && player.isAlive()) {
                System.out.println("\n\u001B[31m--- Enemy Turn ---\u001B[0m");
                enemy.attack(player);
            }

            // Check results
            if (!player.isAlive()) {
                System.out.println("\n\u001B[31mYou were defeated.\u001B[0m");
                playing = false;
            } else if (!enemy.isAlive()) {
                System.out.println("\n\u001B[32mYou defeated the Goblin!\u001B[0m");
                playing = false;
            }
        }

        System.out.println("\n\u001B[35m=== GAME OVER ===\u001B[0m\n");
        sc.close();
    }

    private static String playerStrategyName(Character player) {
        AttackStrategy s = player.getStrategy();
        return (s == null) ? "None" : s.getName();
    }

    // --- Helper methods for safe input ---
    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = sc.nextInt();
                sc.nextLine(); // consume newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println("\u001B[33mInvalid input. Please enter a number.\u001B[0m");
                sc.nextLine(); // clear invalid input
            }
        }
    }

    private static int readIntInRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            int value = readInt(sc, prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("\u001B[33mPlease enter a number between " + min + " and " + max + ".\u001B[0m");
        }
    }
}
