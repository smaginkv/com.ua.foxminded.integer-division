package ua.com.foxminded.division.text;

import java.io.OutputStream;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.com.foxminded.division.math.Result;
import ua.com.foxminded.exception.DivisionInnerProccessingException;

public class JsonFormatter implements Formatter {

    public String format(Result result) throws DivisionInnerProccessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String output = "";

        try {
            output = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new DivisionInnerProccessingException(e);
        }

        return output;
    }

    @Override
    public String toString() {
        return "JSON";
    }

    public OutputStream getOutputStream() {
        return System.out;
    }
}
