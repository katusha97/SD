package cli;

import cli.commands.Command;
import cli.commands.Factory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс, который получает запрос в строке и выполняет его.
 */
public class Executor {

    public Executor() {
        this.varDict = new HashMap<>();
    }

    Executor(Map<String, String> varDict) {
        this.varDict = varDict;
    }

    private static String getString(final InputStream input) {
        return new BufferedReader(new InputStreamReader(input))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    /**
     * @param inputString Входной запрос.
     * @param inputStream Входной поток.
     * @return Результат выполнения запроса.
     * @throws Exception
     */
    public String execute(final String inputString, InputStream inputStream) throws Exception {
        Factory factory = new Factory();
        Parser parser = new Parser(factory);
        List<Command> commandList = parser.parse(inputString);
        for (Command command : commandList) {
            inputStream = command.call(inputStream, varDict);
        }
        return getString(inputStream);
    }

    private final Map<String, String> varDict;
}
