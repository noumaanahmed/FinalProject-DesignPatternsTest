package edu.neu.csye7374;

/**
 * Design Pattern: Builder
 * -----------------------
 * Provides a fluent API to construct Character objects with
 * configurable fields (name, health).
 */
public class CharacterBuilder {
    private String name = "Unnamed";
    private int health = 100;

    public CharacterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CharacterBuilder setHealth(int health) {
        this.health = health;
        return this;
    }

    public Character build() {
        return new Character(name, health);
    }
}
