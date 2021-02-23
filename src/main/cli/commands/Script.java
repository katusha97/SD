package cli.commands;

import cli.Arguments;
import cli.exceptions.WrongArgumentsException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Script extends AbstractCommand{

    public Script(Arguments args) {
        super(args, "script");
    }

    @Override
    public InputStream call(InputStream input, Map<String, String> dictVal) throws Exception, WrongArgumentsException {
        List<String> cmd = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            cmd.add(args.get(i, dictVal));
        }
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.environment().putAll(dictVal);
        Process p = pb.start();
        p.waitFor();
        return p.getInputStream();
    }
}
