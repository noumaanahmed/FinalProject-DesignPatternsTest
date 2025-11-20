package edu.neu.csye7374;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Demo entry point for the console version.
 *
 * Design Pattern: Template Method (overall game loop)
 * ---------------------------------------------------
 * The battle loop follows a fixed algorithm with pluggable details:
 *  1. Show menu and stats
 *  2. Read player choice
 *  3. Queue commands in CommandInvoker
 *  4. Execute commands
 *  5. Enemy turn
 *  6. Check win/lose and repeat
 */
public class Demo {

    public static void gameRun() {
        Scanner sc = new Scanner(System.in);

        System.out.println();
        System.out.println("===========================================");
        System.out.println("           MINI RPG CONSOLE GAME           ");
        System.out.println("===========================================\n");

        // --- Singleton Pattern in use ---
        System.out.println("[Pattern] Using Singleton (GameConfig) for difficulty.");
        GameConfig config = GameConfig.getInstance();

        int diff = readIntInRange(sc,
                "Enter difficulty (1-Easy, 2-Normal, 3-Hard): ",
                1, 3);
        config.setDifficulty(diff);

        // Goblin HP scaling based on difficulty
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

        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println("Difficulty set to: " + diff + "  |  Goblin HP = " + goblinHP);
        System.out.println("-------------------------------------------\n");

        // --- Observer / Bridge Pattern ---
        System.out.println("[Pattern] Using Observer/Bridge for logging (ConsoleLogger, GUI adapter).");
        ConsoleLogger logger = new ConsoleLogger("GameLogger");

        // --- Factory + Builder Pattern for Player & Enemy ---
        System.out.println("[Pattern] Using Factory (CharacterFactory) + Builder (CharacterBuilder) for characters.\n");

        System.out.println("Choose your class:");
        System.out.println("  1. Warrior");
        System.out.println("  2. Mage");
        int choice = readIntInRange(sc, "Enter choice: ", 1, 2);

        System.out.print("\nEnter your character name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            name = "Hero";
        }

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
        System.out.println();
        System.out.println("[Pattern] Using Strategy for attack behavior (Aggressive / Defensive).");
        int strat = readIntInRange(sc,
                "\nSelect starting strategy:\n"
                        + "  1. Aggressive (Attack)\n"
                        + "  2. Defensive (Heal)\n"
                        + "Enter choice: ",
                1, 2);

        if (strat == 1) {
            player.setStrategy(new AggressiveAttack());
        } else {
            player.setStrategy(new DefensiveAttack());
        }
        enemy.setStrategy(new AggressiveAttack());

        // --- Command Pattern ---
        System.out.println("[Pattern] Using Command + CommandInvoker to queue actions.\n");
        CommandInvoker invoker = new CommandInvoker();

        boolean playing = true;
        while (playing) {
            // ===== Menu & Status =====
            System.out.println();
            System.out.println("===========================================");
            System.out.println("                 BATTLE MENU               ");
            System.out.println("===========================================");
            System.out.println("Current Strategy : [" + playerStrategyName(player) + "]");
            System.out.println("Player HP        : " + player.getHealth());
            System.out.println("Goblin HP        : " + enemy.getHealth());
            System.out.println("-------------------------------------------");

            String currentStrat = playerStrategyName(player).toLowerCase();
            if (currentStrat.equals("aggressive")) {
                System.out.println("  1. Attack Enemy");
            } else {
                System.out.println("  1. Heal Yourself");
            }
            System.out.println("  2. Change Strategy");
            System.out.println("  3. Quit Game");

            int action = readIntInRange(sc, "\nChoose an option: ", 1, 3);
            System.out.println();

            switch (action) {
                case 1:
                    if (currentStrat.equals("aggressive")) {
                        invoker.addCommand(new AttackCommand(player, enemy));
                    } else {
                        invoker.addCommand(new HealCommand(player, 10));
                    }
                    break;

                case 2:
                    int s = readIntInRange(sc,
                            "\nSelect new strategy:\n"
                                    + "  1. Aggressive (Attack)\n"
                                    + "  2. Defensive (Heal)\n"
                                    + "Enter choice: ",
                            1, 2);
                    if (s == 1) {
                        player.setStrategy(new AggressiveAttack());
                    } else {
                        player.setStrategy(new DefensiveAttack());
                    }
                    System.out.println("\n[Info] Strategy changed successfully.\n");
                    continue;

                case 3:
                    System.out.println("[Info] Exiting game...\n");
                    playing = false;
                    continue;
            }

            // Execute all queued commands this turn
            invoker.executeAll();

            // Enemy turn
            if (playing && enemy.isAlive() && player.isAlive()) {
                System.out.println();
                System.out.println("--------------- Enemy Turn ---------------");
                enemy.attack(player);
            }

            // Check results
            if (!player.isAlive()) {
                System.out.println();
                System.out.println("===========================================");
                System.out.println("               YOU WERE DEFEATED           ");
                System.out.println("===========================================\n");
                playing = false;
            } else if (!enemy.isAlive()) {
                System.out.println();
                System.out.println("===========================================");
                System.out.println("             YOU DEFEATED GOBLIN           ");
                System.out.println("===========================================\n");
                playing = false;
            }
        }

        System.out.println("=== GAME OVER ===\n");
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
                System.out.println("[Warning] Invalid input. Please enter a number.");
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
            System.out.println("[Warning] Please enter a number between "
                    + min + " and " + max + ".");
        }
    }
}
