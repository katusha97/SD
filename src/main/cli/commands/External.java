package cli.commands;

import cli.exceptions.ExternalErrorException;
import cli.exceptions.WrongArgumentsException;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cli.Utils.getString;

class External extends AbstractCommand {

    public External(final List<String> args) throws WrongArgumentsException {
        super(args, Commands.external);
    }

    @Override
    public InputStream call(InputStream input, Map<String, String> dictVal)
            throws Exception, WrongArgumentsException {
        List<String> cmd = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            cmd.add(args.get(i, dictVal));
        }
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.environment().putAll(dictVal);
        try {
            Process p = pb.start();
            p.waitFor();
            if (p.exitValue() != 0) {
                throw new ExternalErrorException("exitValue: " + p.exitValue() + "\n" + getString(p.getErrorStream()));
            }
            return new SequenceInputStream(p.getInputStream(), p.getErrorStream());
        } catch (IOException e) {
            throw new ExternalErrorException(e.getMessage());
        }
    }
}
