package banking.infrastructure.repository;

public class DBException extends RuntimeException {
    public DBException(String message) {
        super(message);
    }
}
