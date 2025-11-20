package edu.neu.csye7374;

/**
 * Design Pattern: Observer (Concrete Observer)
 * Design Pattern: Bridge (Console implementor)
 * --------------------------------------------
 * Prints game events to the standard console.
 *
 * NOTE: We will also log pattern explanations using System.out
 * directly, so they appear only in the console (not GUI).
 */
public class ConsoleLogger implements GameObserver {

    private final String name;

    public ConsoleLogger(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(String message) {
        System.out.println("[" + name + "] " + message);
    }
}
