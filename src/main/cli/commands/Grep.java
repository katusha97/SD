package cli.commands;

import cli.exceptions.EmptyCommandException;
import cli.exceptions.NoSuchFileOrDirectoryException;
import cli.exceptions.WrongArgumentsException;

import java.io.*;
import java.util.*;

public class Grep extends AbstractCommand{

    Grep(List<String> args) throws WrongArgumentsException {
        super(args, "grep");
    }

    @Override
    public InputStream call(InputStream input, Map<String, String> dictVal) throws Exception {
        if (args.size() == 0) {
            throw new EmptyCommandException("empty command");
        }
        boolean i = false;
        boolean w = false;
        int A = -1;
        StringBuilder ans = new StringBuilder();
        if (args.get("-i", dictVal).isPresent()) {
            i = true;
        }
        if (args.get("-w", dictVal).isPresent()) {
            w = true;
        }
        if (args.get("-A", dictVal).isPresent()) {
            A = Integer.parseInt(args.get("-A", dictVal).get().get(0));
        }
        String regexp = args.get(0, dictVal);
        if (args.size() == 1) {
            try (InputStreamReader reader = new InputStreamReader(input)) {
                ans.append(find(reader, regexp, i, w, A));
            } catch (IOException exception) {
                throw new NoSuchFileOrDirectoryException(exception.getMessage());
            }
        } else {
            for (int j = 1; j < args.size(); j++) {
                try (FileReader fileReader = new FileReader(args.get(j, dictVal))) {
                    ans.append(find(fileReader, regexp, i, w, A));
                } catch (IOException exception) {
                    throw new NoSuchFileOrDirectoryException(exception.getMessage());
                }
            }
        }
        return new ByteArrayInputStream(ans.toString().getBytes());
    }

    private String find(Reader reader, String regexp, boolean i, boolean w, int A) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder ans = new StringBuilder();
            if (i) {
                regexp = regexp.toLowerCase();
            }
            if (w) {
                regexp = "\\b" + regexp + "\\b";
            }
            regexp = ".*" + regexp + ".*";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String copyLine = new String(line);
                if (i) {
                    copyLine = copyLine.toLowerCase();
                }
                if (copyLine.matches(regexp)) {
                    ans.append(line).append("\n");
                    if (A != -1) {
                        int j = 0;
                        while (j < A) {
                            line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            ans.append(line).append("\n");
                            j++;
                        }
                    }
                }
            }
            return ans.toString();

    }

    @Override
    public Map<String, Integer> getKeyValueNumber() {
        Map<String, Integer> map = new HashMap<>();
        map.put("-i", 0);
        map.put("-w", 0);
        map.put("-A", 1);
        return map;
    }
}
