package cli;

import cli.commands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class Factory {

    public Factory() {
        this.map = new HashMap<>();
        map.put("cat", Cat::new);
        map.put("echo", Echo::new);
        map.put("pwd", Pwd::new);
        map.put("wc", Wc::new);
        map.put("exit", Exit::new);
        map.put("assignment", Assignment::new);
    }

    public Command create(final String name, final Arguments args) {
        return map.get(name).apply(args);
    }

    private final Map<String, Function<Arguments, Command>> map;
}
