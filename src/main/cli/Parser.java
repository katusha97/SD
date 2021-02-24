package cli;

import cli.commands.Factory;
import cli.exceptions.EmptyCommandException;
import cli.commands.Command;
import cli.exceptions.WrongArgumentsException;

import java.util.*;

/**
 * Основной класс для парсинга входной строки.
 */
public class Parser {

    public Parser(final Factory fac) {
        this.factory = fac;
    }

    /**
     * @param s Входная строка.
     * @return Лист команд. Команды разделены пайплайнами. Функция парсит имя команды и аргументы.
     * Потом для каждой команды вызывается функция create из класса Factory, которая создает
     * экземпляр класса данной команды.
     * @throws EmptyCommandException
     * @throws WrongArgumentsException
     */
    public List<Command> parse(final String s) throws EmptyCommandException, WrongArgumentsException {
        List<Command> commandList = new ArrayList<>();
        String[] parsed = s.split("\\|");
        for (int i = 0; i < parsed.length; i++) {
            String token = parsed[i];
            if (token.matches(" *")) {
                throw new EmptyCommandException("empty command on " + i + "-th part of pipeline");
            }
            token = token.strip();
            if (checkIfVariable(token)) {
                String[] splited = token.split("=");
                List<String> args = new ArrayList<>();
                args.add(splited[0].strip());
                args.add(splited[1].strip());
                commandList.add(factory.create("assignment", args));
            } else if (token.length() > 2
                    && ((token.charAt(0) == '.' && token.charAt(1) == '/') || token.charAt(0) == '/')) {
                NameAndArgs nameAndArgs = splitNameAndArgs(token);
                String name = nameAndArgs.name;
                List<String> args = new ArrayList<>();
                args.add(name);
                List<String> parsedArgs = parseArgs(nameAndArgs.args);
                args.addAll(parsedArgs);
                commandList.add(factory.create("script", args));
            } else {
                NameAndArgs nameAndArgs = splitNameAndArgs(token);
                String name = nameAndArgs.name;
                String sub = nameAndArgs.args;
                List<String> args = parseArgs(sub);
                commandList.add(factory.create(name, args));
            }
        }
        return commandList;
    }

    private boolean checkIfVariable(String token) {
        return token.matches("[a-zA-Z0-9]+=[a-zA-Z0-9]+");
    }

    private static class NameAndArgs {
        String name = "";
        String args = "";
    }

    private NameAndArgs splitNameAndArgs(String token) {
        NameAndArgs nameAndArgs = new NameAndArgs();
        int j = 0;
        while (j < token.length() && token.charAt(j) != ' ') {
            nameAndArgs.name += token.charAt(j);
            j++;
        }
        if (j >= token.length()) {
            nameAndArgs.args = "";
        } else {
            nameAndArgs.args = token.substring(j + 1);
        }
        return nameAndArgs;
    }

    private List<String> parseArgs(String string) throws WrongArgumentsException {
        List<String> ans = new ArrayList<>();
        int i = 0;
        while (i < string.length()) {
            char stop = ' ';
            String curr = "";
            if (isBracket(string.charAt(i))) {
                stop = string.charAt(i);
                curr += stop;
                i++;
            }
            int nextI = string.indexOf(stop, i);
            if (nextI == -1) {
                nextI = string.length();
            }
            curr += string.substring(i, nextI);
            i = nextI;
            if (i == string.length() && !isBracket(string.charAt(i - 1)) && isBracket(stop)) {
                throw new WrongArgumentsException("missing bracket");
            }
            if (isBracket(stop)) {
                curr += stop;
            }
            if (!curr.equals("")) {
                ans.add(curr);
            }
            i++;
        }
        return ans;
    }

    private boolean isBracket(char ch) {
        return ch == '\'' || ch == '"';
    }

    private final Factory factory;
}
