package cli.commands;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cli.Utils.getString;
import static org.junit.jupiter.api.Assertions.*;

class GrepTest {

    @Test
    void callI() throws Exception {
        String string = "Hello world!";
        List<String> l = new ArrayList<>();
        File f = new File("test.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write(string);
        writer.close();
        String forSearch = "hello";
        l.add("-i");
        l.add(forSearch);
        l.add(f.getName());
        Grep g = new Grep(l);
        InputStream ans = g.call(InputStream.nullInputStream(), new HashMap<>());
        f.delete();
        String text = getString(ans);
        String right = "Hello world!";
        assertEquals(right, text);
    }

    @Test
    void callA() throws Exception {
        List<String> l = new ArrayList<>();
        File f = new File("test.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        for (int i = 0; i < 10; i++) {
            writer.write(i + "\n");
        }
        writer.close();
        String forSearch = "[08]";
        l.add("-A");
        l.add("3");
        l.add(forSearch);
        l.add(f.getName());
        Grep g = new Grep(l);
        InputStream ans = g.call(InputStream.nullInputStream(), new HashMap<>());
        f.delete();
        String text = getString(ans);
        String right = "0\n1\n2\n3\n8\n9";
        assertEquals(right, text);
    }

    @Test
    void callAll() throws Exception {
        List<String> l = new ArrayList<>();
        String string = "ImalittlePig\nfirst\nsecond\nImalittlePiggy";
        InputStream input = new ByteArrayInputStream(string.getBytes());
        String forSearch = "imalittlepig";
        l.add("-A");
        l.add("1");
        l.add("-i");
        l.add("-w");
        l.add(forSearch);
        Grep g = new Grep(l);
        InputStream ans = g.call(input, new HashMap<>());
        String text = getString(ans);
        String right = "ImalittlePig\nfirst";
        assertEquals(right, text);
    }
}