package ua.com.foxminded.division.text;

import ua.com.foxminded.division.math.Result;

public class ClassicFormatter implements Formatter {
    private int integralOffset;
    private int lengthDividended;

    private void setup(Result result) {
        this.integralOffset = 0;

        String stringDividended = String.valueOf(result.getDividend());
        this.lengthDividended = stringDividended.length();
    }

    public String format(Result result) {
        setup(result);
        String output = "";

        for (int i = 0; i <= result.length(); i++) {
            output += getOutput(result, i);
        }
        return output;
    }

    private String getOutput(Result result, int step) {

        if (step == result.length())
            return getOutputTail(result);
        else {
            integralOffset += result.getOffset(step);
            if (step == 0)
                return getOutputHead(result, step);

            else
                return getOutputBody(result, step);
        }
    }

    private String getOutputHead(Result result, int step) {
        String output, formatHead2Line;

        output = String.format("%d|%d\n", result.getDividend(), result.getDivisor());

        formatHead2Line = getFormatHead2Line(lengthDividended);
        output += String.format(formatHead2Line, result.getPartialDividendWithoutRemainder(step), result.getQuotient());

        return output;
    }

    private String getOutputBody(Result result, int step) {
        String output, formatBodyTail;

        formatBodyTail = getFormatBodyTail(integralOffset);
        output = String.format(formatBodyTail, result.getPartialDividend(step));
        output += String.format(formatBodyTail, result.getPartialDividendWithoutRemainder(step));
        return output;
    }

    private String getOutputTail(Result result) {
        return String.format(getFormatBodyTail(lengthDividended), result.getRemainder());
    }

    String getFormatBodyTail(int offset) {
        return String.format("%%%dd\n", offset);
    }

    String getFormatHead2Line(int offset) {
        return String.format("%%-%dd|%%d\n", offset);
    }

    @Override
    public String toString() {
        return "Classic";
    }

}
