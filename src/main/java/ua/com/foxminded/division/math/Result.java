package ua.com.foxminded.division.math;

public class Result {
    private int dividend;
    private int divisor;
    private int quotient;
    private stage[] stages;
    
    class stage{
        private int partialDividend;
        private int partialDividendWithoutRemainder;
    }

}
