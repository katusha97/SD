package cli.commands;

import cli.Arguments;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ScriptTest {

    static String getString(InputStream input) {
        return new BufferedReader(new InputStreamReader(input))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    @Test
    void call() throws Exception {
        List<String> list = new ArrayList<>();
        File f = new File("test.sh");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("#!/bin/sh\necho 'Test for scripts!' $x $1");
        writer.close();
        Runtime.getRuntime().exec("chmod +x " + f.getAbsolutePath()).waitFor();
        list.add(f.getAbsolutePath());
        list.add("2");
        Map<String, String> dict = new HashMap<>();
        dict.put("x", "7");
        Script s = new Script(new Arguments(list));
        InputStream ans = s.call(InputStream.nullInputStream(), dict);
        f.delete();
        String text = getString(ans);
        assertEquals("Test for scripts! 7 2", text);
    }
}
