package edu.neu.csye7374;

import java.util.ArrayList;
import java.util.List;

/**
 * Design Pattern: Command (Invoker)
 * ---------------------------------
 * Stores commands and executes them later as a batch.
 */
public class CommandInvoker {

    private List<Command> commands = new ArrayList<>();

    public void addCommand(Command cmd) {
        commands.add(cmd);
    }

    public void executeAll() {
        for (Command cmd : commands) {
            cmd.execute();
        }
        commands.clear();
    }
}
