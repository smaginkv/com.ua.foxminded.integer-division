package ua.com.foxminded.division.text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheNotFoundException;

import lombok.Data;
import lombok.Setter;
import ua.com.foxminded.division.exception.DivisionFileNotSetException;
import ua.com.foxminded.division.exception.DivisionInnerProccessingException;
import ua.com.foxminded.division.math.Result;

public class HtmlFormatter implements Formatter {
    @Setter
    private String fileName;
    private int integralOffset;
    private int lengthDivisor;
    private final String TEMPLATE_HEAD_2_LINE = "%d|%d\n";
    private final String TEMPLATE_BODY_TAIL = "%%%dd\n";
    private final String MUSTACHE_TEMPLATE_NAME = "template.mustache";

    private void setup(Result result) throws DivisionFileNotSetException {

        if (fileName == "") {
            throw new DivisionFileNotSetException();
        }

        String stringDivisor = String.valueOf(result.getDivisor());
        this.lengthDivisor = stringDivisor.length() + 1;
        this.integralOffset = lengthDivisor;
    }

    public String format(Result result) throws DivisionFileNotSetException, DivisionInnerProccessingException {
        setup(result);
        Mustache mustacheTemplate = getMustacheTemplate();

        Map<String, TableLine[]> context = new HashMap<>();

        TableLine[] tableLines = new TableLine[result.getStagesNumber() + 1];
        for (int i = 0; i <= result.getStagesNumber(); i++) {
            tableLines[i] = new TableLine(getOutput(result, i));
        }
        context.put("tableLines", tableLines);

        StringWriter writer = new StringWriter();
        mustacheTemplate.execute(writer, context);
        return writer.toString();
    }

    private Mustache getMustacheTemplate() throws DivisionInnerProccessingException {
        try {
            return new DefaultMustacheFactory().compile(MUSTACHE_TEMPLATE_NAME);
        } catch (MustacheNotFoundException e) {
            throw new DivisionInnerProccessingException(
                    String.format("Template \"%s\" not found", MUSTACHE_TEMPLATE_NAME));
        }
    }

    @Data
    private class TableLine {
        private String value;

        TableLine(String value) {
            this.value = value;
        }
    }

    private String getOutput(Result result, int step) {
        String output = "";
        if (step == 0) {
            output += getOutputHead(result);
        }
        if (step != result.getStagesNumber()) {
            output += getOutputBody(result, step);
        }
        if (step == result.getStagesNumber()) {
            output += getOutputTail(result);
        }
        return output;
    }

    private String getOutputHead(Result result) {
        String output, formatBodyTail, stringQuotient;

        stringQuotient = String.valueOf(result.getQuotient());
        formatBodyTail = getFormatBodyTail(lengthDivisor + stringQuotient.length());
        output = String.format(formatBodyTail, result.getQuotient());
        output += String.format(TEMPLATE_HEAD_2_LINE, result.getDivisor(), result.getDividend());

        return output;
    }

    private String getOutputBody(Result result, int step) {
        String output = "", formatBodyTail;

        integralOffset += result.getStageOffset(step);

        formatBodyTail = getFormatBodyTail(integralOffset);
        if (step > 0) {
            output = String.format(formatBodyTail, result.getPartialDividend(step));
        }

        output += String.format(formatBodyTail, result.getPartialDividendWithoutRemainder(step));
        return output;
    }

    private String getOutputTail(Result result) {
        integralOffset += result.getRemaindOffset();
        return String.format(getFormatBodyTail(integralOffset), result.getRemainder());
    }

    String getFormatBodyTail(int offset) {
        return String.format(TEMPLATE_BODY_TAIL, offset);
    }

    @Override
    public String toString() {
        return "HTML";
    }

    public OutputStream getOutputStream() throws FileNotFoundException {
        return new FileOutputStream(fileName);
    }
}
