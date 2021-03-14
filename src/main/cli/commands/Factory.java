package cli.commands;

import cli.exceptions.WrongArgumentsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Фабрика для создания и добавления команд.
 */


public class Factory {

    private interface ThrowingCtor {
        Command create(List<String> l) throws WrongArgumentsException;
    }

    public Factory() {
        this.map = new HashMap<>();
        map.put(Commands.cat, Cat::new);
        map.put(Commands.echo, Echo::new);
        map.put(Commands.pwd, Pwd::new);
        map.put(Commands.wc, Wc::new);
        map.put(Commands.exit, Exit::new);
        map.put(Commands.assignment, Assignment::new);
        map.put(Commands.external, External::new);
    }

    /**
     * @param name Имя команды
     * @param args Агрументы команды
     * @return Созданную команду
     */
    public Command create(final Commands name, final List<String> args) throws WrongArgumentsException {
        return map.get(name).create(args);
    }

    private final Map<Commands, ThrowingCtor> map;
}
