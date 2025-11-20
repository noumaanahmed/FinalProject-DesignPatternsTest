package edu.neu.csye7374;

import java.util.ArrayList;
import java.util.List;

/**
 * Core domain entity representing a player or enemy.
 *
 * Design Pattern: Strategy
 *  - Holds a reference to AttackStrategy to delegate combat behavior.
 *
 * Design Pattern: Observer / Bridge
 *  - Notifies GameObserver instances instead of printing directly.
 *    ConsoleLogger and TextAreaObserverAdapter are different "views"
 *    bridged through the GameObserver abstraction.
 */
public class Character {

    private String name;
    private int health;
    private int maxHealth;
    private AttackStrategy strategy;
    private List<GameObserver> observers = new ArrayList<>();

    public Character(String name, int health) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
    }

    // --- Observer registration ---

    public void addObserver(GameObserver obs) {
        observers.add(obs);
    }

    public void notifyObservers(String msg) {
        for (GameObserver obs : observers) {
            obs.onEvent(msg);
        }
    }

    // --- Getters / Setters ---

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public AttackStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(AttackStrategy strategy) {
        this.strategy = strategy;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    // --- Combat helpers ---

    public void takeDamage(int dmg) {
        if (health <= 0) return; // dead can't take damage
        health -= dmg;
        if (health < 0) {
            health = 0;
        }
        notifyObservers(name + " took " + dmg + " damage! (HP: " + health + ")");
    }

    public void heal(int amount) {
        if (health <= 0) return; // dead can't heal
        health += amount;
        if (health > maxHealth) {
       	    health = maxHealth;
        }
        notifyObservers(name + " healed " + amount + " HP! (HP: " + health + ")");
    }

    public void attack(Character target) {
        if (health <= 0) {
            notifyObservers(name + " is already defeated and cannot attack!");
            return;
        }
        if (strategy == null) {
            notifyObservers(name + " has no attack strategy set!");
            return;
        }
        if (target == null || !target.isAlive()) {
            notifyObservers(name + " tried to attack, but the target is already defeated!");
            return;
        }
        strategy.execute(this, target);
    }

    public boolean isAlive() {
        return health > 0;
    }
}
