package ua.com.foxminded.division.text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

import lombok.Data;
import lombok.Setter;
import ua.com.foxminded.division.math.Result;

public class HtmlFormatter implements Formatter {
    @Setter
    private String fileName;
    private int integralOffset;
    private int lengthDivisor;
    private final String TEMPLATE_HEAD_2_LINE = "%d|%d\n";
    private final String TEMPLATE_BODY_TAIL = "%%%dd\n";

    private void setup(Result result) {
        String stringDivisor = String.valueOf(result.getDivisor());
        this.lengthDivisor = stringDivisor.length() + 1;
        this.integralOffset = lengthDivisor;
    }

    public String format(Result result) {
        setup(result);
        Mustache m = new DefaultMustacheFactory().compile("template.mustache");

        Map<String, TableLine[]> context = new HashMap<>();

        TableLine[] tableLines = new TableLine[result.getStagesNumber() + 1];
        for (int i = 0; i <= result.getStagesNumber(); i++) {
            tableLines[i] = new TableLine(getOutput(result, i));
        }
        context.put("tableLines", tableLines);

        StringWriter writer = new StringWriter();
        m.execute(writer, context);
        return writer.toString();
    }

    @Data
    private class TableLine {
        private String value;

        TableLine(String value) {
            this.value = value;
        }
    }

    private String getOutput(Result result, int step) {
        if (step == 0) {
            return getOutputHead(result) + getOutputBody(result, step);

        } else if (step < result.getStagesNumber()) {
            return getOutputBody(result, step);

        } else
            return getOutputTail(result);
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
        if (step > 0)
            output = String.format(formatBodyTail, result.getPartialDividend(step));

        output += String.format(formatBodyTail, result.getPartialDividendWithoutRemainder(step));
        return output;
    }

    private String getOutputTail(Result result) {
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
