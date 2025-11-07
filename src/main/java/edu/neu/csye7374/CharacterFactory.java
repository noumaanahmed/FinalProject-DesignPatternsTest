package edu.neu.csye7374;

public class CharacterFactory {

    public static Character createCharacter(String type, String name) {
        CharacterBuilder builder = new CharacterBuilder()
                .setName(name)
                .setHealth(100); // Always start player at 100 HP

        switch (type.toLowerCase()) {
            case "warrior":
                break; // future expansions could add extra attributes
            case "mage":
                break;
            default:
                break;
        }
        return builder.build();
    }
}
