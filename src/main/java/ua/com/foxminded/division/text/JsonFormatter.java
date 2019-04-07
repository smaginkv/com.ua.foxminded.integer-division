package ua.com.foxminded.division.text;

import java.io.OutputStream;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.com.foxminded.division.math.Result;

public class JsonFormatter implements Formatter {

    public String format(Result result) {
        ObjectMapper objectMapper = new ObjectMapper();
        String output = "";

        try {
            output = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error json creating");
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
