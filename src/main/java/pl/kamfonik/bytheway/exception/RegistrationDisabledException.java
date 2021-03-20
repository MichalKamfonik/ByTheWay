package pl.kamfonik.bytheway.exception;

public class RegistrationDisabledException extends Exception{
    public RegistrationDisabledException() {
    }

    public RegistrationDisabledException(String message) {
        super(message);
    }
}
