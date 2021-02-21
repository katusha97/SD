package cli;

import cli.commands.Command;
import cli.exceptions.EmptyCommandException;
import cli.exceptions.WrongArgumentsException;

import java.util.List;
import java.util.Map;

public class Arguments {

    public Arguments(List<String> args) {
        this.args = args;
    }

    public int size() {
        return args.size();
    }

    private String execute(String arg, Map<String, String> varDict) throws Exception {
        Executor executor = new Executor(varDict);
        return executor.execute(arg, System.in);
    }

    private boolean isRightForName(char ch) {
        return Character.isLetterOrDigit(ch) || ch == '_';
    }

    private String parseDoubleQuote(String arg, Map<String, String> varDict) throws Exception {
        StringBuilder ans = new StringBuilder();
        int i = 0;
        while (i < arg.length()) {
            if (arg.charAt(i) == '$') {
                i++;
                if (i >= arg.length()) {
                    ans.append('$');
                    break;
                }

                if (arg.charAt(i) == '(') {
                    int right_bound = arg.indexOf(')', i + 1);
                    if (right_bound == -1) {
                        throw new WrongArgumentsException(") is missing");
                    }
                    ans.append(execute(arg.substring(i + 1, right_bound), varDict));
                    i = right_bound + 1;
                    continue;
                }

                StringBuilder nameBuilder = new StringBuilder();
                while (i < arg.length() && isRightForName(arg.charAt(i))) {
                    nameBuilder.append(arg.charAt(i));
                    i++;
                }
                if (nameBuilder.length() == 0) {
                    ans.append('$');
                    continue;
                }

                String name = nameBuilder.toString();
                if (varDict.containsKey(name)) {
                    ans.append(varDict.get(name));
                }
            }
            else {
                ans.append(arg.charAt(i));
                i++;
            }
        }
        return ans.toString();
    }

    public String get(int i, Map<String, String> varDict) throws Exception {
        String curr = args.get(i);
        if (curr.charAt(0) == '\'') {
            return curr.substring(1, curr.length() - 1);
        }
        if (curr.charAt(0) == '$') {
            if (curr.length() == 1) {
                return curr;
            }
            String sub = curr.substring(1);
            if (!varDict.containsKey(sub)) {
                return "";
            }
            return varDict.get(sub);
        }
        if (curr.charAt(0) == '"') {
            return parseDoubleQuote(curr.substring(1, curr.length() - 1), varDict);
        }
        return curr;
    }

    private final List<String> args;
}
