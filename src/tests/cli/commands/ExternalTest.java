package cli.commands;

import cli.exceptions.ExternalErrorException;
import cli.exceptions.WrongArgumentsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static cli.Utils.getString;


import static org.junit.jupiter.api.Assertions.*;

class ExternalTest {

    @Test
    void call() throws Exception {
        {
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
            External e = new External(list);
            InputStream ans = e.call(InputStream.nullInputStream(), dict);
            f.delete();
            String text = getString(ans);
            assertEquals("Test for scripts! 7 2", text);
        }

        {
            List<String> list = new ArrayList<>();
            list.add("git");
            list.add("aaaaaa");
            External e = new External(list);
            Assertions.assertThrows(
                    ExternalErrorException.class,
                    () -> e.call(InputStream.nullInputStream(), new HashMap<>()));
        }

        {
            List<String> list = new ArrayList<>();
            list.add("ping");
            list.add("127.0.0.1");
            list.add("-c");
            list.add("1");
            External e = new External(list);
            InputStream ans = e.call(InputStream.nullInputStream(), new HashMap<>());
            String[] text = getString(ans).split("\n");
            assertEquals("PING 127.0.0.1 (127.0.0.1) 56(84) bytes of data.", text[0]);
        }
    }
}
