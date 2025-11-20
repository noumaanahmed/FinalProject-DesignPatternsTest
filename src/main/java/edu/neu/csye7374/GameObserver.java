package edu.neu.csye7374;

/**
 * Design Pattern: Observer / Bridge
 * ---------------------------------
 * Observer interface used by Character to publish events.
 * Different views (console, GUI, etc.) implement this.
 */
public interface GameObserver {
    void onEvent(String message);
}
