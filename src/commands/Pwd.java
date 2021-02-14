package commands;

import exceptions.WrongArgumentsException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

public class Pwd extends AbstractCommand {

    public Pwd(List<String> s) {
        super(s, "pwd");
    }

    @Override
    public InputStream call(InputStream input) throws WrongArgumentsException {
        if (args.size() > 0) {
            throw new WrongArgumentsException("too many arguments");
        }
        return new ByteArrayInputStream(System.getProperty("user.dir").getBytes());
    }
}
