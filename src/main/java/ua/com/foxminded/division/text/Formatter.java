package ua.com.foxminded.division.text;

import ua.com.foxminded.division.exception.DivisionInnerProccessingException;
import ua.com.foxminded.division.math.Result;

public interface Formatter {
    String format(Result result) throws DivisionInnerProccessingException;
}
