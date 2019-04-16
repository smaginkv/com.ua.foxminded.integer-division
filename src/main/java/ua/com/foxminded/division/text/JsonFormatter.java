package ua.com.foxminded.division.text;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.foxminded.division.exception.DivisionInnerProccessingException;
import ua.com.foxminded.division.math.Result;

public class JsonFormatter implements Formatter {

    @Override
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
}
