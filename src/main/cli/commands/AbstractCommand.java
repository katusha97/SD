package cli.commands;

import cli.Arguments;
import cli.exceptions.WrongArgumentsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AbstractCommand implements Command {

    AbstractCommand(List<String> args, String name) throws WrongArgumentsException {
        this.name = name;
        this.args = new Arguments(args, getKeyValueNumber());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Arguments getArguments() {
        return args;
    }

    protected final Arguments args;
    private final String name;
}
