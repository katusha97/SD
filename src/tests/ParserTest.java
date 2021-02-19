import cli.Factory;
import cli.Parser;
import cli.exceptions.EmptyCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void parse() {
        {
            Factory factory = new Factory();
            Parser parser = new Parser(factory);
            String string = "x=5 |    | y=1a00";
            Assertions.assertThrows(EmptyCommandException.class,
                    () -> parser.parse(string));
        }
    }
}