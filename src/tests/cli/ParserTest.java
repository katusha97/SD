package cli;

import cli.commands.Factory;
import cli.commands.Command;
import cli.exceptions.EmptyCommandException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse() throws Exception {
        {
            Factory factory = new Factory();
            Parser parser = new Parser(factory);
            String string = "x=5 |    | y=1a00";
            assertThrows(EmptyCommandException.class, () -> parser.parse(string));
        }

        {
            Factory factory = new Factory();
            Parser parser = new Parser(factory);
            String string = "./test.sh 5 6 x";
            List<Command> listCommand = parser.parse(string);
            Command command = listCommand.get(0);
            assertEquals("script", command.getName());
            List<String> test = new ArrayList<>();
            test.add("./test.sh");
            test.add("5");
            test.add("6");
            test.add("x");
            Arguments args = command.getArguments();
            List<String> ans = new ArrayList<>();
            for (int i = 0; i < args.size(); i++) {
                ans.add(args.get(i, new HashMap<>()));
            }
            assertEquals(test, ans);
        }
    }
}
