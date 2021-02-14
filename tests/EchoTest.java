import commands.Echo;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


class EchoTest {

    static String getString(InputStream input) {
        return new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
    }

    @Test
    void call() throws Exception {
        String string = "aba\ncaba\teee";
        {
            Echo e = new Echo(new ArrayList<>());
            InputStream ans = e.call(InputStream.nullInputStream());
            String text = getString(ans);
            assert (text.equals(""));
        }

        {
            List<String> testList = new ArrayList<>();
            testList.add("3");
            testList.add("hello");
            Echo e = new Echo(testList);
            InputStream ans = e.call(new ByteArrayInputStream(string.getBytes()));
            String text = getString(ans);
            assert (text.equals("3 hello"));
        }
    }
}