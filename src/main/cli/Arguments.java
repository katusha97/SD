package cli;

import java.util.List;
import java.util.Map;

public class Arguments {

    public Arguments(List<String> args) {
        this.args = args;
    }

    public int size() {
        return args.size();
    }

    public String get(int i , Map<String, String> varDict){
        String curr = args.get(i);
        if (curr.charAt(0) == '$') {
            String sub = curr.substring(1);
            return varDict.get(sub);
        }
        return curr;
    }

    private final List<String> args;
}
