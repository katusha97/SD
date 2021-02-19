package cli.exceptions;

public class WrongArgumentsException extends Exception {
    public WrongArgumentsException(final String message){
        super(message);
    }
}
