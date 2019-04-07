package ua.com.foxminded.exception;

public class DivisionException extends RuntimeException {
    public DivisionException() {
        super();
    }
    public DivisionException(Throwable throwable) {
        super(throwable);
    }
    public DivisionException(String string) {
        super(string);
    }
}
