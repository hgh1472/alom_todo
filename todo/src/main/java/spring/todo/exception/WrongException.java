package spring.todo.exception;

public class WrongException extends RuntimeException {
    public WrongException() {
        super();
    }

    public WrongException(String message) {
        super(message);
    }

    public WrongException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongException(Throwable cause) {
        super(cause);
    }

    protected WrongException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
