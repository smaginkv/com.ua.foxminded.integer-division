package ua.com.foxminded.division.text;

import java.io.FileNotFoundException;
import java.io.OutputStream;

import ua.com.foxminded.division.exception.DivisionFileNotSetException;
import ua.com.foxminded.division.exception.DivisionInnerProccessingException;
import ua.com.foxminded.division.math.Result;

public interface Formatter {
    default void setFileName(String fileName) {
    };

    default String getFileName() {
        return "";
    };

    String format(Result result) throws DivisionFileNotSetException, DivisionInnerProccessingException;

    OutputStream getOutputStream() throws FileNotFoundException;
}
