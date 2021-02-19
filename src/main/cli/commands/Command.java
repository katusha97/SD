package cli.commands;

import cli.exceptions.WrongArgumentsException;

import java.io.InputStream;
import java.util.Map;

public interface Command {

    InputStream call(InputStream input, Map<String, String> dictVal) throws Exception,
            WrongArgumentsException;

    String getName();
}
