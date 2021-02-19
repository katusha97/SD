import cli.Arguments;
import cli.commands.Cat;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CatTest {

    static String getString(InputStream input) {
        return new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
    }

    @Test
    void call() throws Exception {
        String string = "aba\ncaba\teee";
        {

            Cat c = new Cat(new Arguments(new ArrayList<>()));
            InputStream ans = c.call(new ByteArrayInputStream(string.getBytes()), new HashMap<>());
            String text = getString(ans);
            assertEquals(text, string);
        }

        {
            List<String> l = new ArrayList<>();
            File f = new File("test1.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(string);
            writer.close();
            l.add(f.getName());
            Cat c = new Cat(new Arguments(l));
            InputStream ans = c.call(InputStream.nullInputStream(), new HashMap<>());
            f.delete();
            String text = getString(ans);
            assertEquals(text, string);
        }
    }
}