package main.java.commands;

import exceptions.WrongArgumentsException;

import java.io.InputStream;
import java.util.List;

public class Exit extends AbstractCommand{

    public Exit(List<String> s) {
        super(s, "exit");
    }

    @Override
    public InputStream call(InputStream input) throws Exception, WrongArgumentsException {
        return null;
    }
}
