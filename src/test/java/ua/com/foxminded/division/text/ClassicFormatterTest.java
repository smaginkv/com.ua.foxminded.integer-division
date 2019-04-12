package ua.com.foxminded.division.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.division.math.Result;

class ClassicFormatterTest {

    private ClassicFormatter classicFormatter;
    private Result inputResult;

    @BeforeEach
    void setup() {
        classicFormatter = new ClassicFormatter();
        inputResult = new Result();
    }

    @Nested
    @DisplayName("Strategy: happy path ")
    class HappyPath {
        @Test
        void shouldResultCorrectWhenTwoPositives() {
            inputResult.setDividend(1565);
            inputResult.setDivisor(23);
            inputResult.setQuotient(68);
            inputResult.setRemainder(1);
            inputResult.setDividendIsNegative(false);
            inputResult.setDivisorIsNegative(false);

            inputResult.clearStages();
            inputResult.addStage(156, 138, 3);
            inputResult.addStage(185, 184, 1);
            inputResult.setRemaindOffset(0);
            
            assertEquals("1565|23\n138 |68\n 185\n 184\n   1\n", classicFormatter.format(inputResult));
        }

        @Test
        void shouldResultCorrectWhenNegativeAndPositive() {
            inputResult.setDividend(-1564);
            inputResult.setDivisor(313);
            inputResult.setQuotient(-4);
            inputResult.setRemainder(-312);
            inputResult.setDividendIsNegative(true);
            inputResult.setDivisorIsNegative(false);

            inputResult.clearStages();
            inputResult.addStage(1564, 1252, 4);
            inputResult.setRemaindOffset(0);

            assertEquals("-1564|313\n-1252|-4\n -312\n", classicFormatter.format(inputResult));
        }

        @Test
        void shouldResultCorrectWhenPositiveAndNegative() {
            inputResult.setDividend(1564);
            inputResult.setDivisor(-3);
            inputResult.setQuotient(-521);
            inputResult.setRemainder(1);
            inputResult.setDividendIsNegative(false);
            inputResult.setDivisorIsNegative(true);

            inputResult.clearStages();
            inputResult.addStage(15, 15, 2);
            inputResult.addStage(6, 6, 1);
            inputResult.addStage(4, 3, 1);
            inputResult.setRemaindOffset(0);
            
            assertEquals("1564|-3\n15  |-521\n  6\n  6\n   4\n   3\n   1\n", classicFormatter.format(inputResult));
        }
        @Test
        void shouldResultCorrectWhenTwoNegative() {
            inputResult.setDividend(-1564);
            inputResult.setDivisor(-1);
            inputResult.setQuotient(1564);
            inputResult.setRemainder(0);
            inputResult.setDividendIsNegative(true);
            inputResult.setDivisorIsNegative(true);

            inputResult.clearStages();
            inputResult.addStage(1, 1, 1);
            inputResult.addStage(5, 5, 1);
            inputResult.addStage(6, 6, 1);
            inputResult.addStage(4, 4, 1);
            inputResult.setRemaindOffset(0);
            
            assertEquals("-1564|-1\n-1   |1564\n -5\n -5\n  -6\n  -6\n   -4\n   -4\n    0\n", classicFormatter.format(inputResult));
        }
    }
    
    @Nested
    @DisplayName("Strategy: corner cases ")
    class CornerCases {
        @Test
        void shouldResultCorrectWhenEqualLength() {
            inputResult.setDividend(1564);
            inputResult.setDivisor(1313);
            inputResult.setQuotient(1);
            inputResult.setRemainder(251);
            inputResult.setDividendIsNegative(false);
            inputResult.setDivisorIsNegative(false);

            inputResult.clearStages();
            inputResult.addStage(1564, 1313, 4);
            inputResult.setRemaindOffset(0);

            assertEquals("1564|1313\n1313|1\n 251\n", classicFormatter.format(inputResult));
        }
        
        @Test
        void shouldResultCorrectWhenDividendLessDivisor() { 
            inputResult.setDividend(1564);
            inputResult.setDivisor(1565);
            inputResult.setQuotient(0);
            inputResult.setRemainder(1564);
            inputResult.setDividendIsNegative(false);
            inputResult.setDivisorIsNegative(false);

            inputResult.clearStages();
            inputResult.setRemaindOffset(4);
            
            assertEquals("1564|1565\n1564|0\n", classicFormatter.format(inputResult));
        }
        
        @Test
        void shouldResultCorrectWhenDividend0() {
            inputResult.setDividend(0);
            inputResult.setDivisor(1565);
            inputResult.setQuotient(0);
            inputResult.setRemainder(0);
            inputResult.setDividendIsNegative(false);
            inputResult.setDivisorIsNegative(false);

            inputResult.clearStages();
            inputResult.setRemaindOffset(1);
            
            assertEquals("0|1565\n0|0\n", classicFormatter.format(inputResult));
        }
    }
}
