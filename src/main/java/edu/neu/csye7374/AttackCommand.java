package edu.neu.csye7374;

public class AttackCommand implements Command {

	private final Character attacker;
	private final Character target;

	public AttackCommand(Character attacker, Character target) {
		this.attacker = attacker;
		this.target = target;
	}

	@Override
	public void execute() {
		attacker.attack(target);
	}
}
