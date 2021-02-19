import main.java.Arguments;
import main.java.commands.Pwd;
import main.java.commands.Wc;
import main.java.exceptions.NoSuchFileOrDirectoryException;
import main.java.exceptions.WrongArgumentsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.basic.BasicScrollPaneUI;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class WcTest {

    static String getString(InputStream input) {
        return new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
    }

    @Test
    void call() throws Exception {
        String string = "Hello world!\n";
        {
            Wc w = new Wc(new Arguments(new ArrayList<>()));
            InputStream input = new ByteArrayInputStream(string.getBytes());
            InputStream ans = w.call(input, new HashMap<>());
            String text = getString(ans);
            assert (text.equals("1 2 13"));
        }
        string = "This test check wc from file.\nHave a nice day:)";
        {
            List<String> list = new ArrayList<>();
            File f = new File("test.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(string);
            writer.close();
            list.add(f.getName());
            Wc w = new Wc(new Arguments(list));
            InputStream ans = w.call(InputStream.nullInputStream(), new HashMap<>());
            f.delete();
            String text = getString(ans);
            assert (text.equals("2 10 48 test.txt"));
        }

        {
            List<String> list = new ArrayList<>();
            File f1 = new File("test1.txt");
            File f2 = new File("test2.txt");
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(f1));
            BufferedWriter writer2 = new BufferedWriter(new FileWriter(f2));
            writer1.write(string);
            writer1.close();
            writer2.write(string);
            writer2.close();
            list.add(f1.getName());
            list.add(f2.getName());
            Wc w = new Wc(new Arguments(list));
            InputStream ans = w.call(InputStream.nullInputStream(), new HashMap<>());
            f1.delete();
            f2.delete();
            String text = getString(ans);
            assert (text.equals("2 10 48 test1.txt\n2 10 48 test2.txt\n4 20 96 total"));
        }

        {
            List<String> testList = new ArrayList<>();
            testList.add("3");
            Wc c = new Wc(new Arguments(testList));
            Assertions.assertThrows(NoSuchFileOrDirectoryException.class,
                    () -> c.call(InputStream.nullInputStream(), new HashMap<>()));
        }
    }
}