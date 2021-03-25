package cli.exceptions;

public class EmptyCommandException extends Exception {
    public EmptyCommandException(final String message) {
        super(message);
    }
}
