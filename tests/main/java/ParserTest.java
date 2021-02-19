package main.java;

import main.java.exceptions.EmptyCommandException;
import main.java.exceptions.NoSuchFileOrDirectoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse() throws EmptyCommandException {
        {
            Factory factory = new Factory();
            Parser parser = new Parser(factory);
            String string = "x=5 | y=1a00";
            parser.parse(string);
            Map<String, String> varDict = parser.getVarDict();
            assert (varDict.get("x").equals("5"));
            assert (varDict.get("y").equals("1a00"));
        }

        {
            Factory factory = new Factory();
            Parser parser = new Parser(factory);
            String string = "x=5 |    | y=1a00";
            Assertions.assertThrows(EmptyCommandException.class,
                    () -> parser.parse(string));
        }

    }
}