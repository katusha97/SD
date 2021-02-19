import main.java.Executor;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;


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
            String res = executor.execute(s, System.in);
            String test = System.getProperty("user.dir");
            int countLine = test.split("\n").length;
            int countWord = test.split(" ").length;
            int countByte = countLine + test.length();
            assert (res.equals(countLine + " " + countWord + " " + countByte));
        }

        {
            String s = "cat test.txt | wc";
            Executor executor = new Executor();
            String res = executor.execute(s, System.in);
            assert (res.equals("1 2 13"));
        }

        {
            String s = "cat | wc";
            Executor executor = new Executor();
            String res = executor.execute(s, new ByteArrayInputStream("hello".getBytes()));
            assert (res.equals("1 1 6"));
        }

        {
            String s = "echo testing echo";
            Executor executor = new Executor();
            String res = executor.execute(s, System.in);
            assert (res.equals("testing echo"));
        }

        {
            String s = "cat test.txt | echo 4";
            Executor executor = new Executor();
            String res = executor.execute(s, System.in);
            assert (res.equals("4"));
        }

        {
            String s = "echo | pwd";
            Executor executor = new Executor();
            String res = executor.execute(s,System.in);
            String test = System.getProperty("user.dir");
            assert (res.equals(test));
        }

        {
            String s = "cat test.txt | x=7 | echo $x";
            Executor executor = new Executor();
            String res = executor.execute(s, System.in);
            assert (res.equals("7"));
        }

//        {
//            String s = "echo '$(echo upg)'";
//            Executor executor = new Executor();
//            String res = executor.execute(s, System.in);
//            assert (res.equals("$(echo upg)"));
//        }
        f.delete();
    }
}