package commands;

import exceptions.WrongArgumentsException;

import java.io.*;
import java.util.List;

public class Wc extends AbstractCommand{

    public Wc(List<String> s) {
        super(s, "wc");
    }

    @Override
    public InputStream call(InputStream input) throws WrongArgumentsException, IOException {
        if (args.size() == 0) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            int lineCount = 0;
            int wordCount = 0;
            while(reader.ready()) {
                lineCount += 1;
                String line = reader.readLine();
                String[] split = line.split(" ");
                wordCount += split.length;
            }
            byte[] allByte = input.readAllBytes();
            String ans = "";
            ans += wordCount + " ";
            ans += lineCount + " ";
            ans += String.valueOf(allByte.length);
            return new ByteArrayInputStream(ans.getBytes());
        }
        return null;

    }
}
