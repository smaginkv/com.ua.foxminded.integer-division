package ua.com.foxminded.division.text;

import ua.com.foxminded.division.math.Result;

public class ClassicFormatter implements Formatter {
    private int integralOffset;
    private int lengthDividended;
    private final String TEMPLATE_BODY_TAIL = "%%%dd\n";
    private final String TEMPLATE_HEAD = "%%-%ds|%%d\n";
    private final String TEMPLATE_HEAD_DIVIDEND = "%%%dd";

    @Override
    public String format(Result result) {
        String output = "";

        String stringDividended = String.valueOf(result.getDividend());
        lengthDividended = stringDividended.length();
        integralOffset = 0;

        for (int i = 0; i <= result.getStagesLength(); i++) {
            output += getOutput(result, i);
        }
        return output;
    }

    private String getOutput(Result result, int step) {

        String output = "";
        if (step == 0) {
            output += getOutputHead(result, step);
        } else {
            if (step != result.getStagesLength()) {
                output += getOutputBody(result, step);
            } else {
                output += getOutputTail(result);
            }
        }
        return output;
    }

    private String getOutputHead(Result result, int step) {
        String output, formatHead, formatHeadDividend, partialDividend;
        formatHead = getFormatHead(lengthDividended);
        output = String.format(formatHead, result.getDividend(), result.getDivisor());

        if (step == result.getStagesLength()) {
            integralOffset += result.getRemaindOffset();
            output += String.format(formatHead, result.getRemainder(), result.getQuotient());
        } else {
            integralOffset += result.getStageOffset(step);

            // Add same format as a body, but with quotients appendix on right side
            formatHeadDividend = getFormatHeadDividend(integralOffset);
            partialDividend = String.format(formatHeadDividend, result.getPartialQuotient(step));

            output += String.format(formatHead, partialDividend, result.getQuotient());
        }
        return output;
    }

    private String getOutputBody(Result result, int step) {
        String output = "", formatBodyTail;

        integralOffset += result.getStageOffset(step);

        formatBodyTail = getFormatBodyTail(integralOffset);
        output = String.format(formatBodyTail, result.getPartialDividend(step));
        output += String.format(formatBodyTail, result.getPartialQuotient(step));
        return output;
    }

    private String getOutputTail(Result result) {
        return String.format(getFormatBodyTail(lengthDividended), result.getRemainder());
    }

    private String getFormatBodyTail(int offset) {
        return String.format(TEMPLATE_BODY_TAIL, offset);
    }

    private String getFormatHeadDividend(int offset) {
        return String.format(TEMPLATE_HEAD_DIVIDEND, offset);
    }

    private String getFormatHead(int offset) {
        return String.format(TEMPLATE_HEAD, offset);
    }

    @Override
    public String toString() {
        return "Classic";
    }
}
