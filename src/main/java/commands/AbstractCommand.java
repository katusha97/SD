package main.java.commands;

import java.util.List;

public abstract class AbstractCommand implements Command{

    AbstractCommand(List<String> s, String name) {
        args = s;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    protected final List<String> args;
    private final String name;
}
