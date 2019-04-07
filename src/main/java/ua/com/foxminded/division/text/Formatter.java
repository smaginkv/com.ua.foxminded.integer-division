package ua.com.foxminded.division.text;

import java.io.FileNotFoundException;
import java.io.OutputStream;

import lombok.Data;
import ua.com.foxminded.division.math.Result;

public interface Formatter {
    public default void setFileName(String fileName) {        
    };
    public default String getFileName(){ 
        return "";
    };
    public String format(Result result);
    public OutputStream getOutputStream() throws FileNotFoundException;
}
