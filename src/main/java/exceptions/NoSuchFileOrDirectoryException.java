package main.java.exceptions;

public class NoSuchFileOrDirectoryException  extends Exception{
    public NoSuchFileOrDirectoryException(final String message) {
        super(message);
    }
}
