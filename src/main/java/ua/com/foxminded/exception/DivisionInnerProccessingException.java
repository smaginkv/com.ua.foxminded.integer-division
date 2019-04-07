package ua.com.foxminded.exception;

import org.omg.CORBA.UserException;

public class DivisionInnerProccessingException extends DivisionException {
    public DivisionInnerProccessingException(Throwable cause) {
        super(cause);
    }
    public DivisionInnerProccessingException(String cause) {
        super(cause);
    }
}
