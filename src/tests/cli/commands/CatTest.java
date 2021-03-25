package cli.commands;

import org.junit.jupiter.api.Test;
import static cli.Utils.getString;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CatTest {

    @Test
    void call() throws Exception {
        String string = "aba\ncaba\teee";
        {
            Cat c = new Cat(new ArrayList<>());
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
            Cat c = new Cat(l);
            InputStream ans = c.call(InputStream.nullInputStream(), new HashMap<>());
            f.delete();
            String text = getString(ans);
            assertEquals(text, string);
        }
    }
}
