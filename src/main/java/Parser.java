package main.java;

import exceptions.EmptyCommandException;
import main.java.commands.Command;

import java.util.ArrayList;
import java.util.List;

class Parser {

    Parser(Factory factory) {
        this.factory = factory;
    }

    List<Command> parse(String s) throws EmptyCommandException {
        List<Command> commandList = new ArrayList<>();
        String[] parsed = s.split(" \\| ");
        for (int i = 0; i < parsed.length; i++) {
            String value = parsed[i];
            String[] bySpace = value.split(" ");
            if (bySpace.length == 0) {
                throw new EmptyCommandException("empty command on " + i + "-th part of pipeline");
            }

            String name;
            List<String> args = new ArrayList<>();
            name = bySpace[0];
            for (int j = 1; j < bySpace.length; j++) {
                args.add(bySpace[j]);
            }
            commandList.add(factory.create(name, args));
        }
        return commandList;
    }

    private final Factory factory;
}
