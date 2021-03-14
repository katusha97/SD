package cli.exceptions;

public class ExternalErrorException extends Exception{
    public ExternalErrorException(final String message) {
        super(message);
    }
}
