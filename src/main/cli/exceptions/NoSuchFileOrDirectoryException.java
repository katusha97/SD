package cli.exceptions;

public class NoSuchFileOrDirectoryException extends Exception {
    public NoSuchFileOrDirectoryException(final String message) {
        super(message);
    }
}
