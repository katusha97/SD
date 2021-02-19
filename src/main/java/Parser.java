package main.java;

import main.java.exceptions.EmptyCommandException;
import main.java.commands.Command;

import java.util.*;

class Parser {

    Parser(final Factory fac) {
        this.factory = fac;
    }

    List<Command> parse(final String s) throws EmptyCommandException {
        List<Command> commandList = new ArrayList<>();
        String[] parsed = s.split("\\|");
        for (int i = 0; i < parsed.length; i++) {
            String token = parsed[i];
            if (token.matches(" *")) {
                throw new EmptyCommandException("empty command on " + i
                        + "-th part of pipeline");
            }
            token = token.strip();
            if (checkIfVariable(token)) {
                String[] splited = token.split("=");
                List<String> args = new ArrayList<>();
                args.add(splited[0].strip());
                args.add(splited[1].strip());
                commandList.add(factory.create("assignment", new Arguments(args)));
            }else {
                String name = "";
                int j = 0;
                while (j < token.length() && token.charAt(j) != ' '){
                    name += token.charAt(j);
                    j++;
                }
                String sub = "";
                if (j < token.length()) {
                    sub = token.substring(j + 1);
                }
                List<String> args = new ArrayList<>();
                if (checkIfSingleQuotes(sub)) {
                    args.add(sub.substring(1, sub.length() - 1));
                } else if (!sub.equals("")) {
                    String[] bySpace = sub.split(" ");
                    Collections.addAll(args, bySpace);
                }
                commandList.add(factory.create(name, new Arguments(args)));
            }
        }
        return commandList;
    }

    boolean checkIfVariable(String token) {
        return token.matches("[a-zA-Z0-9]+=[a-zA-Z0-9]+");
    }

    boolean checkIfDoubleQuotes(String token) {
        return token.matches("\"\\$.*\"");
    }

    boolean checkIfSingleQuotes(String token) {
        return token.matches("'.*'");
    }

    private final Factory factory;
}
