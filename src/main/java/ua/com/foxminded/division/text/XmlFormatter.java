package ua.com.foxminded.division.text;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ua.com.foxminded.division.exception.DivisionInnerProccessingException;
import ua.com.foxminded.division.math.Result;

public class XmlFormatter implements Formatter {

    @Override
    public String format(Result result) throws DivisionInnerProccessingException {

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
}
