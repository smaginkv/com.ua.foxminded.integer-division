package ua.com.foxminded.division.text;

import java.io.FileNotFoundException;
import java.io.OutputStream;

import com.fasterxml.jackson.core.JsonProcessingException;

import ua.com.foxminded.division.math.Result;
import ua.com.foxminded.exception.DivisionFileNotSetException;
import ua.com.foxminded.exception.DivisionInnerProccessingException;

public interface Formatter {
    public default void setFileName(String fileName) {
    };

    public default String getFileName() {
        return "";
    };

    public String format(Result result) throws DivisionFileNotSetException, DivisionInnerProccessingException;

    public OutputStream getOutputStream() throws FileNotFoundException;
}
