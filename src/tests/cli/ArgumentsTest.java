package cli;

import cli.exceptions.WrongArgumentsException;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentsTest {

    @Test
    void getSimpleArg() throws Exception {
        Arguments args = new Arguments(Arrays.asList("a", "b"), new HashMap<>());
        assertEquals("a", args.get(0, new HashMap<>()));
        assertEquals("b", args.get(1, new HashMap<>()));
    }

    @Test
    void getOutOfBounds() throws WrongArgumentsException {
        Arguments args = new Arguments(new ArrayList<>(), new HashMap<>());
        assertThrows(IndexOutOfBoundsException.class, () -> args.get(1, new HashMap<>()));
    }

    @Test
    void getVariable() throws Exception {
        Arguments args = new Arguments(Arrays.asList("$x", "$y", "$a", "$"), new HashMap<>());
        Map<String, String> vars = new HashMap<>();
        vars.put("x", "ffff");
        vars.put("y", "aaa");
        assertEquals("ffff", args.get(0, vars));
        assertEquals("aaa", args.get(1, vars));
        assertEquals("", args.get(2, vars));
        assertEquals("$", args.get(3, vars));
    }

    @Test
    void getSingleQuotes() throws Exception {
        String arg = "'hello $x    $(cat 5)'";
        Arguments args = new Arguments(Collections.singletonList(arg), new HashMap<>());
        Map<String, String> vars = new HashMap<>();
        assertEquals(arg.substring(1, arg.length() - 1), args.get(0, vars));
    }

    @Test
    void getDoubleQuotesVariables() throws Exception {
        String arg = "\"hello $x$y-$z$\"";
        Arguments args = new Arguments(Collections.singletonList(arg), new HashMap<>());
        Map<String, String> vars = new HashMap<>();
        vars.put("x", "7");
        vars.put("y", "13");
        assertEquals("hello 713-$", args.get(0, vars));
    }

    @Test
    void getDoubleQuotesExecution() throws Exception {
        String arg = "\"hello $(pwd)$(echo $x)\"";
        Arguments args = new Arguments(Collections.singletonList(arg), new HashMap<>());
        Map<String, String> vars = new HashMap<>();
        vars.put("x", "7");
        assertEquals("hello " + System.getProperty("user.dir") + "7", args.get(0, vars));
    }

    @Test
    void getDoubleQuotesUnclosedParenthesis() throws Exception {
        String arg = "\"hello $(pwd\"";
        Arguments args = new Arguments(Collections.singletonList(arg), new HashMap<>());
        Map<String, String> vars = new HashMap<>();
        assertThrows(WrongArgumentsException.class, () -> args.get(0, vars));
    }
}
