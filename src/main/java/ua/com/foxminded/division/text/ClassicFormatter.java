package ua.com.foxminded.division.text;

import java.io.OutputStream;
import ua.com.foxminded.division.math.Result;

public class ClassicFormatter implements Formatter {
    private int integralOffset;
    private int lengthDividended;
    private final String TEMPLATE_BODY_TAIL = "%%%dd\n";
    private final String TEMPLATE_HEAD = "%%-%ds|%%d\n";
    private final String TEMPLATE_HEAD_DIVIDEND = "%%%dd";

    private void setup(Result result) {
        this.integralOffset = 0;

        String stringDividended = String.valueOf(result.getDividend());
        this.lengthDividended = stringDividended.length();
    }

    public String format(Result result) {
        setup(result);
        String output = "";

        for (int i = 0; i <= result.getStagesNumber(); i++) {
            output += getOutput(result, i);
        }
        return output;
    }

    private String getOutput(Result result, int step) {

        String output = "";
        if (step == 0)
            output += getOutputHead(result, step);
        else {
            if (step != result.getStagesNumber())
                output += getOutputBody(result, step);
            else
                output += getOutputTail(result);
        }
        return output;

    }

    private String getOutputHead(Result result, int step) {
        String output, formatHead, formatHeadDividend, partialDividend;
        formatHead = getFormatHead(lengthDividended);
        output = String.format(formatHead, result.getDividend(), result.getDivisor());

        if (step == result.getStagesNumber()) {
            integralOffset += result.getRemaindOffset();
            output += String.format(formatHead, result.getRemainder(), result.getQuotient());
        } else {
            integralOffset += result.getStageOffset(step);

            //Add same format as a body, but with quotients appendix on right side
            formatHeadDividend = getFormatHeadDividend(integralOffset);
            partialDividend = String.format(formatHeadDividend, result.getPartialDividendWithoutRemainder(step));
            
            output += String.format(formatHead, partialDividend, result.getQuotient());
        }
        return output;
    }

    private String getOutputBody(Result result, int step) {
        String output = "", formatBodyTail;

        integralOffset += result.getStageOffset(step);

        formatBodyTail = getFormatBodyTail(integralOffset);
        output = String.format(formatBodyTail, result.getPartialDividend(step));
        output += String.format(formatBodyTail, result.getPartialDividendWithoutRemainder(step));
        return output;
    }

    private String getOutputTail(Result result) {
        return String.format(getFormatBodyTail(lengthDividended), result.getRemainder());
    }

    String getFormatBodyTail(int offset) {
        return String.format(TEMPLATE_BODY_TAIL, offset);
    }

    String getFormatHeadDividend(int offset) {
        return String.format(TEMPLATE_HEAD_DIVIDEND, offset);
    }

    String getFormatHead(int offset) {
        return String.format(TEMPLATE_HEAD, offset);
    }

    @Override
    public String toString() {
        return "Classic";
    }

    public OutputStream getOutputStream() {
        return System.out;
    }

}
