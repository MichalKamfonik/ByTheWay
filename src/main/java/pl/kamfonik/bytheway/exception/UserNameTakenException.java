package pl.kamfonik.bytheway.exception;

public class UserNameTakenException extends Exception{
    public UserNameTakenException() {
    }

    public UserNameTakenException(String message) {
        super(message);
    }
}
