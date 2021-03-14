package cli.commands;

import cli.exceptions.WrongArgumentsException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

class Exit extends AbstractCommand {

    public Exit(final List<String> s) throws WrongArgumentsException {
        super(s, Commands.echo);
    }

    @Override
    public InputStream call(final InputStream input, final Map<String, String> dictVal)
            throws Exception, WrongArgumentsException {
        System.exit(0);
        return null;
    }
}
