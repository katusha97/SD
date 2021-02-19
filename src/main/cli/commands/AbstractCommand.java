package cli.commands;

import cli.Arguments;

public abstract class AbstractCommand implements Command {

    AbstractCommand(Arguments args, String name) {
        this.args = args;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    protected final Arguments args;
    private final String name;
}
