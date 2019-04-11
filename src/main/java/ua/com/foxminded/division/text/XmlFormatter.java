package ua.com.foxminded.division.text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ua.com.foxminded.division.exception.DivisionFileNotSetException;
import ua.com.foxminded.division.exception.DivisionInnerProccessingException;
import ua.com.foxminded.division.math.Result;

public class XmlFormatter implements Formatter {
    private String fileName;

    @Override
    public String getFileName() {
        return fileName;
    };

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    };

    public String format(Result result) throws DivisionFileNotSetException, DivisionInnerProccessingException {
        if (fileName == "") {
            throw new DivisionFileNotSetException();
        }

        XmlMapper xmlMapper = new XmlMapper();
        String output = "";
        try {
            output = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new DivisionInnerProccessingException(e);
        }
        return output;
    }

    @Override
    public String toString() {
        return "XML";
    }

    public OutputStream getOutputStream() throws FileNotFoundException {
        return new FileOutputStream(fileName);

    }
}
