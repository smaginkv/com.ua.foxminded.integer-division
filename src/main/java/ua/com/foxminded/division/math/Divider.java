package ua.com.foxminded.division.math;

import ua.com.foxminded.division.exception.DivisionInputException;

public class Divider {
    private int cursor, offset;
    private int quotient, partialDividend, divisor;
    private int[] dividendDigits;
    private Result result;

    public Divider() {
        cursor = offset = 0;
        quotient = divisor = partialDividend = 0;
        dividendDigits = null;
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
        this.dividendDigits = new int[digitAsString.length()];
        for (int i = 0; i < digitAsString.length(); i++) {
            this.dividendDigits[i] = Integer.parseInt(digitAsString.substring(i, i + 1));
        }
        this.divisor = (divisor > 0 ? divisor : -divisor);
        this.result.clearStages();
        this.result.setupIfNegative(dividend < 0, divisor < 0);
    }

    private boolean hasPartialDividend() {
        offset = 0;
        while (hasNextDigit()) {
            partialDividend = multiply(partialDividend, 10);
            partialDividend += nextDigit();
            offset++;

            if (divisor <= partialDividend) {
                return true;
            } else {
                quotient = multiply(quotient, 10);
            }
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

    //package access for the testing 
    int nextDigit() {
        if (cursor == dividendDigits.length) {
            throw new IndexOutOfBoundsException();
        }
        int nextDigit = dividendDigits[cursor];
        cursor++;
        return nextDigit;
    }

    private boolean hasNextDigit() {
        return cursor != dividendDigits.length;
    }

    private int multiply(int multiplicand, int multiplier) {
        if (multiplier == 1) {
            return multiplicand;
        } else {
            return multiply(multiplicand, --multiplier) + multiplicand;
        }
    }

    private void checkInput(int dividend, int divisor) {
        if (divisor == 0) {
            throw new DivisionInputException("Division by zero!");
        }
    }
}
