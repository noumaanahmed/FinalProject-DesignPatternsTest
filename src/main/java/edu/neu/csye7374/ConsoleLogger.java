package edu.neu.csye7374;

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
