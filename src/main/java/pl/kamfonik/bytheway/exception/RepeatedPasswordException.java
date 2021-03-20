package pl.kamfonik.bytheway.exception;

public class RepeatedPasswordException extends Exception{
    public RepeatedPasswordException() {
    }

    public RepeatedPasswordException(String message) {
        super(message);
    }
}
