package ua.com.foxminded.division.text;

import java.io.FileNotFoundException;
import java.io.OutputStream;

import ua.com.foxminded.division.exception.DivisionFileNotSetException;
import ua.com.foxminded.division.exception.DivisionInnerProccessingException;
import ua.com.foxminded.division.math.Result;

public interface Formatter {
    public default void setFileName(String fileName) {
    };

    public default String getFileName() {
        return "";
    };

    public String format(Result result) throws DivisionFileNotSetException, DivisionInnerProccessingException;

    public OutputStream getOutputStream() throws FileNotFoundException;
}
