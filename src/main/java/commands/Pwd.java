package main.java.commands;

import main.java.Arguments;
import main.java.exceptions.WrongArgumentsException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Pwd extends AbstractCommand {

    public Pwd(final Arguments s) {
        super(s, "pwd");
    }

    @Override
    public InputStream call(final InputStream input, final Map<String, String> varDict)
            throws WrongArgumentsException {
        if (args.size() > 0) {
            throw new WrongArgumentsException("too many arguments");
        }
        return new ByteArrayInputStream(System.getProperty("user.dir").getBytes());
    }
}
