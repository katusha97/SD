package cli.commands;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cli.Utils.getString;

class EchoTest {

    @Test
    void call() throws Exception {
        String string = "aba\ncaba\teee";
        {
            Echo e = new Echo(new ArrayList<>());
            InputStream ans = e.call(InputStream.nullInputStream(), new HashMap<>());
            String text = getString(ans);
            assert (text.equals(""));
        }

        {
            List<String> testList = new ArrayList<>();
            testList.add("3");
            testList.add("hello");
            Echo e = new Echo(testList);
            InputStream ans = e.call(new ByteArrayInputStream(string.getBytes()), new HashMap<>());
            String text = getString(ans);
            assert (text.equals("3 hello"));
        }
    }
}
