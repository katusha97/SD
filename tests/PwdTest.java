import exceptions.WrongArgumentsException;
import commands.Pwd;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class PwdTest {

    static String getString(InputStream input) {
        return new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
    }

    @Test
    void call() throws WrongArgumentsException {
        {
            Pwd p = new Pwd(new ArrayList<>());
            String test = System.getProperty("user.dir");
            InputStream ans = p.call(InputStream.nullInputStream());
            String text = getString(ans);
            assert(text.equals(test));
        }

        {
            List<String> testList = new ArrayList<>();
            testList.add("3");
            Pwd p = new Pwd(testList);
            Assertions.assertThrows(WrongArgumentsException.class, () -> p.call(InputStream.nullInputStream()));
        }
    }
}