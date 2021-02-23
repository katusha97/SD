package cli.commands;

import cli.Arguments;
import cli.exceptions.WrongArgumentsException;

import java.io.InputStream;
import java.util.Map;

class Exit extends AbstractCommand {

    public Exit(final Arguments s) {
        super(s, "exit");
    }

    @Override
    public InputStream call(final InputStream input, final Map<String, String> dictVal)
            throws Exception, WrongArgumentsException {
        System.exit(0);
        return null;
    }
}
