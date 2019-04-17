package ua.com.foxminded.division.exception;

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
