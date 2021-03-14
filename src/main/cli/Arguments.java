package cli;

import cli.exceptions.WrongArgumentsException;

import java.io.InputStream;
import java.util.*;

import static cli.Utils.getString;

/**
 * В это классе обобщена вся работа с аргументами.
 * Класс, который хранит информацию об аргументах команды. Главная функция - get. Она обрабатывает
 * ситуации с переменными, одинарными и двойными кавычками.
 * Также при создании объекта этого класса происходит подстановка переменных.
 */
public class Arguments {

    private InputStream execute(String arg, Map<String, String> varDict) throws Exception {
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
                    int rightBound = arg.indexOf(')', i + 1);
                    if (rightBound == -1) {
                        throw new WrongArgumentsException(") is missing");
                    }
                    ans.append(getString(execute(arg.substring(i + 1, rightBound), varDict)));
                    i = rightBound + 1;
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
            } else {
                ans.append(arg.charAt(i));
                i++;
            }
        }
        return ans.toString();
    }

    public Arguments(List<String> args, Map<String, Integer> keyValueNumber) throws WrongArgumentsException {
        List<String> argsUsually = new ArrayList<>();
        Map<String, List<String>> keyValue = new HashMap<>();
        int i = 0;
        while (i < args.size()){
            if (!args.get(i).matches("-[a-zA-Z]")) {
                argsUsually.add(args.get(i));
                i++;
                continue;
            }
            String currKey = args.get(i);
            if (!keyValueNumber.containsKey(currKey)) {
                argsUsually.add(args.get(i));
                i++;
                continue;
            }
            List<String> argsForCurrKey = new ArrayList<>();
            int count = keyValueNumber.get(currKey);
            int j = i + 1;
            int k = 0;
            while (k < count && j < args.size()) {
                argsForCurrKey.add(args.get(j));
                k++;
                j++;
            }
            if (argsForCurrKey.size() != count) {
                throw new WrongArgumentsException("missing arguments");
            }
            keyValue.put(currKey, argsForCurrKey);
            i = j;
        }
        this.args = argsUsually;
        this.keyValue = keyValue;
    }

    /**
     * @return Вспомогательная функция, которая возвращает количество аргуметнов команды.
     */
    public int size() {
        return args.size();
    }

    private String substitute(String s, Map<String, String> varDict) throws Exception {
        if (s.charAt(0) == '\'') {
            return s.substring(1, s.length() - 1);
        }
        if (s.charAt(0) == '$') {
            if (s.length() == 1) {
                return s;
            }
            String sub = s.substring(1);
            if (!varDict.containsKey(sub)) {
                return "";
            }
            return varDict.get(sub);
        }
        if (s.charAt(0) == '"') {
            return parseDoubleQuote(s.substring(1, s.length() - 1), varDict);
        }
        return s;
    }

    /**
     * @param i       индекс аргумента, который мы хотим получить
     * @param varDict - словарь, в котором по названию переменной хранится его значение на текущий
     *                момент.
     * @return строку - аргумент на i-м месте
     * @throws Exception
     */
    public String get(int i, Map<String, String> varDict) throws Exception {
        return substitute(args.get(i), varDict);
    }

    /**
     * Этот метод возвращает аргументы, которые нужны для данного ключа.
     * @param key Имя ключа.
     * @param varDict - словарь, в котором по названию переменной хранится его значение на текущий
     *      *                момент.
     * @return Аргументы по ключу.
     * @throws Exception
     */
    public Optional<List<String>> get(String key, Map<String, String> varDict) throws Exception {
        List<String> curr = keyValue.get(key);
        if (curr == null) {
            return Optional.empty();
        }
        List<String> ans = new ArrayList<>();
        for (String s : curr) {
            ans.add(substitute(s, varDict));
        }
        return Optional.of(ans);
    }

    private final List<String> args;
    private final Map<String, List<String>> keyValue;
}
