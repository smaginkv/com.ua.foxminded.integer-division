package ua.com.foxminded.exception;
import org.omg.CORBA.UserException;

public class DivisionNoSuchOptionExeption extends DivisionException {
    public DivisionNoSuchOptionExeption(String s) {
        super(s);
    }
}
