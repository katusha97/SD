import main.java.Arguments;
import main.java.exceptions.WrongArgumentsException;
import main.java.commands.Pwd;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

class PwdTest {

    static String getString(InputStream input) {
        return new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
    }

    @Test
    void call() throws WrongArgumentsException {
        {
            Pwd p = new Pwd(new Arguments(new ArrayList<>()));
            String test = System.getProperty("user.dir");
            InputStream ans = p.call(InputStream.nullInputStream(), new HashMap<>());
            String text = getString(ans);
            assert(text.equals(test));
        }

        {
            List<String> testList = new ArrayList<>();
            testList.add("3");
            Pwd p = new Pwd(new Arguments(testList));
            Assertions.assertThrows(WrongArgumentsException.class, () -> p.call(InputStream.nullInputStream(), new HashMap<>()));
        }
    }
}