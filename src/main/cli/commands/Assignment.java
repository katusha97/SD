package cli.commands;

import cli.exceptions.WrongArgumentsException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

class Assignment extends AbstractCommand {

    public Assignment(final List<String> args) throws WrongArgumentsException {
        super(args, Commands.assignment);
    }

    @Override
    public InputStream call(InputStream input, Map<String, String> dictVal)
            throws Exception, WrongArgumentsException {
        dictVal.put(args.get(0, dictVal), args.get(1, dictVal));
        return InputStream.nullInputStream();
    }
}
