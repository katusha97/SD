package cli.commands;

import cli.exceptions.WrongArgumentsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cli.Utils.getString;

class PwdTest {

    @Test
    void call() throws WrongArgumentsException {
        {
            Pwd p = new Pwd(new ArrayList<>());
            String test = System.getProperty("user.dir");
            InputStream ans = p.call(InputStream.nullInputStream(), new HashMap<>());
            String text = getString(ans);
            assert (text.equals(test));
        }

        {
            List<String> testList = new ArrayList<>();
            testList.add("3");
            Pwd p = new Pwd(testList);
            Assertions.assertThrows(
                    WrongArgumentsException.class,
                    () -> p.call(InputStream.nullInputStream(), new HashMap<>()));
        }
    }
}
