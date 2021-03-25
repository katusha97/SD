package cli.commands;

import cli.exceptions.NoSuchFileOrDirectoryException;
import cli.exceptions.WrongArgumentsException;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Wc extends AbstractCommand {

    public Wc(final List<String> s) throws WrongArgumentsException {
        super(s, Commands.wc);
    }

    private String getAns(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        int lineCount = 0;
        int wordCount = 0;
        int byteCount = 0;
        while (reader.ready()) {
            lineCount += 1;
            String line = reader.readLine();
            byteCount += line.length() + 1;
            String[] split = line.split(" ");
            wordCount += split.length;
        }
        String ans = "";
        ans += lineCount + " ";
        ans += wordCount + " ";
        ans += byteCount;
        return ans;
    }

    private InputStream inputStreamFromFile(final String name) throws NoSuchFileOrDirectoryException {
        Path file = FileSystems.getDefault().getPath(name);
        try (InputStream in = Files.newInputStream(file)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder line = new StringBuilder();
            while (reader.ready()) {
                line.append(reader.readLine() + "\n");
            }
            return new ByteArrayInputStream(line.toString().getBytes());
        } catch (IOException e) {
            throw new NoSuchFileOrDirectoryException("no such file or directory");
        }
    }

    @Override
    public InputStream call(final InputStream input, final Map<String, String> dictVal)
            throws Exception {
        if (args.size() == 0) {
            return new ByteArrayInputStream(getAns(input).getBytes());
        }
        int totalLine = 0;
        int totalWord = 0;
        int totalByte = 0;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < args.size(); i++) {
            InputStream currFile = inputStreamFromFile(args.get(i, new HashMap<>()));
            String currAns = getAns(currFile);
            currAns += " " + args.get(i, dictVal) + "\n";
            res.append(currAns);
            String[] splitCurr = currAns.split(" ");
            totalLine += Integer.parseInt(splitCurr[0]);
            totalWord += Integer.parseInt(splitCurr[1]);
            totalByte += Integer.parseInt(splitCurr[2]);
        }
        if (args.size() > 1) {
            res.append(totalLine)
                    .append(" ")
                    .append(totalWord)
                    .append(" ")
                    .append(totalByte)
                    .append(" ")
                    .append("total");
        }
        return new ByteArrayInputStream(res.toString().getBytes());
    }
}
