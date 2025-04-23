package banking.core.application.context;

public class FailedLoginException extends Exception {
    public FailedLoginException() {
        super("Wrong card number or PIN!");
    }
}
