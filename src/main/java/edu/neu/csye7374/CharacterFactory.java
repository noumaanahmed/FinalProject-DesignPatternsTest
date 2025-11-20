package edu.neu.csye7374;

/**
 * Design Pattern: Factory
 * -----------------------
 * Centralized creation logic for different character types.
 */
public class CharacterFactory {

    public static Character createCharacter(String type, String name) {
        CharacterBuilder builder = new CharacterBuilder()
                .setName(name)
                .setHealth(100); // Player always starts at 100 HP

        // In this project, types are simple, but this factory is the
        // extension point for future character classes.
        switch (type.toLowerCase()) {
            case "warrior":
                // Future: adjust stats / equipment
                break;
            case "mage":
                // Future: adjust stats / mana, etc.
                break;
            default:
                // Fallback type if input is unknown
                break;
        }
        return builder.build();
    }
}
