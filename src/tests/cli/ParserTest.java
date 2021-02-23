package cli;

import cli.Factory;
import cli.Parser;
import cli.commands.Command;
import cli.commands.Script;
import cli.exceptions.EmptyCommandException;
import cli.exceptions.WrongArgumentsException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
            assertThrows(EmptyCommandException.class,
                    () -> parser.parse(string));
        }

        {
            Factory factory = new Factory();
            Parser parser = new Parser(factory);
            String string = "./test.sh 5 6 x";
            List<Command> listCommand = parser.parse(string);
            Command command = listCommand.get(0);
            assert (command instanceof Script);
            Script s = (Script) command;
            List<String> test = new ArrayList<>();
            test.add("./test.sh");
            test.add("5");
            test.add("6");
            test.add("x");
            List<String> ans = new ArrayList<>();
            for (int i = 0; i < s.getArgs().size(); i++) {
                ans.add(s.getArgs().get(i, new HashMap<>()));
            }
            assertEquals(test, ans);
        }
    }
}