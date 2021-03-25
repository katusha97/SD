package cli.commands;

import cli.Arguments;
import cli.exceptions.WrongArgumentsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AbstractCommand implements Command {

    AbstractCommand(List<String> args, Commands name) throws WrongArgumentsException {
        this.name = name;
        this.args = new Arguments(args, getKeyValueNumber());
    }

    @Override
    public Commands getName() {
        return name;
    }

    @Override
    public Arguments getArguments() {
        return args;
    }

    @Override
    public Map<String, Integer> getKeyValueNumber() {
        return new HashMap<>();
    }

    protected final Arguments args;
    private final Commands name;
}
