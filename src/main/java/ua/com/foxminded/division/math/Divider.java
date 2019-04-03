package ua.com.foxminded.division.math;

import javax.management.RuntimeErrorException;

public class Divider {
    private int cursor, sizeDigitArray, offset;
    private int quotient, partialDividend, divisor;
    private int[] digitArray;
    private Result result;

    public Divider() {
        cursor = sizeDigitArray = offset = 0;
        quotient = divisor = partialDividend = 0;
        digitArray = null;
        result = new Result();
    }

    public Result divide(int dividend, int divisor) {
        if (dividend < divisor)
            throw new RuntimeException("Divident is smaller than divisor");

        countDigits(dividend, divisor);

        while (hasPartialDividend()){
            processPartialDividend();
        }     

        result.setDividend(dividend);
        result.setDivisor(divisor);
        result.setQuotient(quotient);
        result.setRemainder(partialDividend);
        return result;
    }

    private void countDigits(Integer dividend, int divisor) {
        String digitAsString = dividend.toString();
        this.digitArray = new int[digitAsString.length()];
        for (int i = 0; i < digitAsString.length(); i++) {
            this.digitArray[i] = Integer.parseInt(digitAsString.substring(i, i + 1));
        }

        this.sizeDigitArray = digitAsString.length();

        this.divisor = divisor;

        this.result.clearStages();
    }

    private boolean hasPartialDividend() {
        offset = 0;
        while (hasNextDigit()) {
            partialDividend = multiply(partialDividend, 10);
            partialDividend += NextDigit();
            offset++;

            if (divisor <= partialDividend)
                return true;
            else
                quotient = multiply(quotient, 10);
        }
        return false;
    }

    private void processPartialDividend() {
        int integralPartialDividend = partialDividend;
        quotient = multiply(quotient, 10);
        while (divisor <= partialDividend) {
            partialDividend = partialDividend - divisor;
            quotient++;
        }
        result.addStage(integralPartialDividend, integralPartialDividend - partialDividend, offset);
    }

    private int NextDigit() {
        if (cursor == sizeDigitArray)
            throw new RuntimeException("No such digit exception");
        return digitArray[cursor++];
    }

    private boolean hasNextDigit() {
        return cursor != sizeDigitArray;
    }

    private int multiply(int multiplicand, int multiplier) {
        if (multiplier == 1)
            return multiplicand;
        else
            return multiply(multiplicand, --multiplier) + multiplicand;
    }

}
