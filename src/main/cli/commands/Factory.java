package cli.commands;

import cli.Arguments;
import cli.exceptions.WrongArgumentsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Фабрика для создания и добавления команд.
 */


public class Factory {

    private interface ThrowingCtor {
        Command create(List<String> l) throws WrongArgumentsException;
    }

    public Factory() {
        this.map = new HashMap<>();
        map.put("cat", Cat::new);
        map.put("echo", Echo::new);
        map.put("pwd", Pwd::new);
        map.put("wc", Wc::new);
        map.put("exit", Exit::new);
        map.put("assignment", Assignment::new);
        map.put("script", Script::new);
    }

    /**
     * @param name Имя команды
     * @param args Агрументы команды
     * @return Созданную команду
     */
    public Command create(final String name, final List<String> args) throws WrongArgumentsException {
        return map.get(name).create(args);
    }

    private final Map<String, ThrowingCtor> map;
}
