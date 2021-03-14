package cli;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static cli.Utils.getString;

class ExecutorTest {

    @Test
    void execute() throws Exception {
        String string = "Hello world!\n";
        File f = new File("test.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write(string);
        writer.close();

        {
            String s = "pwd | wc";
            Executor executor = new Executor();
            InputStream res = executor.execute(s, System.in);
            String test = System.getProperty("user.dir");
            int countLine = test.split("\n").length;
            int countWord = test.split(" ").length;
            int countByte = countLine + test.length();
            assertEquals(getString(res), countLine + " " + countWord + " " + countByte);
        }

        {
            String s = "cat test.txt | wc";
            Executor executor = new Executor();
            InputStream res = executor.execute(s, System.in);
            assertEquals(getString(res), "1 2 13");
        }

        {
            String s = "cat | wc";
            Executor executor = new Executor();
            InputStream res = executor.execute(s, new ByteArrayInputStream("hello".getBytes()));
            assertEquals(getString(res), "1 1 6");
        }

        {
            String s = "echo testing echo";
            Executor executor = new Executor();
            InputStream res = executor.execute(s, System.in);
            assertEquals(getString(res), "testing echo");
        }

        {
            String s = "cat test.txt | echo 4";
            Executor executor = new Executor();
            InputStream res = executor.execute(s, System.in);
            assertEquals(getString(res), "4");
        }

        {
            String s = "echo | pwd";
            Executor executor = new Executor();
            InputStream res = executor.execute(s, System.in);
            String test = System.getProperty("user.dir");
            assertEquals(getString(res), test);
        }

        {
            String s = "cat test.txt | x=7 | echo $x";
            Executor executor = new Executor();
            InputStream res = executor.execute(s, System.in);
            assertEquals(getString(res), "7");
        }

        {
            String s = "pwd | cat test.txt | wc | n=90 | echo $n";
            Executor executor = new Executor();
            InputStream res = executor.execute(s, System.in);
            assertEquals(getString(res), "90");
        }

        {
            String s = "echo 'echo 7'";
            Executor executor = new Executor();
            InputStream res = executor.execute(s, System.in);
            assertEquals(getString(res), "echo 7");
        }

        {
            String s = "echo \"echo 7\"";
            Executor executor = new Executor();
            InputStream res = executor.execute(s, System.in);
            assertEquals(getString(res), "echo 7");
        }

        {
            String s = "echo \"$echo 7\"";
            Executor executor = new Executor();
            InputStream res = executor.execute(s, System.in);
            assertEquals(" 7", getString(res));
        }

        {
            String s = "echo \"$(echo 7)\"";
            Executor executor = new Executor();
            InputStream res = executor.execute(s, System.in);
            assertEquals("7", getString(res));
        }

        f.delete();
    }

    @Test
    void bigTest() throws Exception {
        String s = "echo 'a b' n 'd f g'   'g'    k   k k";
        Executor executor = new Executor();
        InputStream res = executor.execute(s, System.in);
        assertEquals(getString(res), "a b n d f g g k k k");
    }
}
