package ua.com.foxminded.division.math;

import ua.com.foxminded.division.exception.DivisionInputException;

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

    public Result divide(int dividend, int divisor) throws DivisionInputException {
        checkInput(dividend, divisor);

        setup(dividend, divisor);

        while (hasPartialDividend()) {
            processPartialDividend();
        }

        result.setDividend(dividend);
        result.setDivisor(divisor);
        result.setQuotient(quotient);
        result.setRemainder(partialDividend);
        result.setRemaindOffset(offset);
        return result;
    }

    private void setup(int dividend, int divisor) {
        int unsignedDividend = (dividend > 0 ? dividend : -dividend);
        
        String digitAsString = Integer.valueOf(unsignedDividend).toString();
        this.digitArray = new int[digitAsString.length()];
        for (int i = 0; i < digitAsString.length(); i++) {
            this.digitArray[i] = Integer.parseInt(digitAsString.substring(i, i + 1));
        }

        this.sizeDigitArray = digitAsString.length();

        this.divisor = (divisor > 0 ? divisor : -divisor);

        this.result.clearStages();

        this.result.setupIfNegative(dividend < 0, divisor < 0);
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
        offset = 0;
    }

    private int NextDigit() {
        if (cursor == sizeDigitArray)
            throw new IndexOutOfBoundsException();
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

    private void checkInput(int dividend, int divisor) {
        if (divisor == 0)
            throw new DivisionInputException("Division by zero!");

//        if (divisor < 0 || dividend < 0)
//            throw new DivisionInputException("Operation is possible if both numbers are positive!");

    }

}
