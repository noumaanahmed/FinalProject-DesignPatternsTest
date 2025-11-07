package edu.neu.csye7374;

public class HealCommand implements Command {

	private final Character target;
	private final int amount;

	public HealCommand(Character target, int amount) {
		this.target = target;
		this.amount = amount;
	}

	@Override
	public void execute() {
		target.heal(amount);
	}
}
