package edu.neu.csye7374;

// Decorator base for AttackStrategy
public abstract class AttackDecorator implements AttackStrategy {

    protected final AttackStrategy inner;

    protected AttackDecorator(AttackStrategy inner) {
        this.inner = inner;
    }

    @Override
    public String getName() {
        return inner.getName();
    }
}
