package main.java.commands;

import exceptions.WrongArgumentsException;

import java.io.InputStream;

public interface Command {

    InputStream call(InputStream input) throws Exception, WrongArgumentsException;

    String getName();
}
