package cli;

import cli.commands.Command;
import cli.commands.Factory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * @param inputString Входной запрос.
     * @param inputStream Входной поток.
     * @return Результат выполнения запроса.
     * @throws Exception
     */
    public InputStream execute(final String inputString, InputStream inputStream) throws Exception {
        Factory factory = new Factory();
        Parser parser = new Parser(factory);
        List<Command> commandList = parser.parse(inputString);
        for (Command command : commandList) {
            inputStream = command.call(inputStream, varDict);
        }
        return inputStream;
    }

    private final Map<String, String> varDict;
}
