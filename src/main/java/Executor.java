package main.java;

import main.java.commands.Command;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class Executor {

    static String getString(final InputStream input) {
        return new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
    }

    public String execute(final String inputString, InputStream inputStream) throws Exception {
        Factory factory = new Factory();
        Parser parser = new Parser(factory);
        List<Command> commandList = parser.parse(inputString);
        for (Command command : commandList) {
            inputStream = command.call(inputStream, parser.getVarDict());
        }
        return getString(inputStream);
    }
}
