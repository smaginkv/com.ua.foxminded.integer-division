package ua.com.foxminded.division.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.division.exception.DivisionInputException;

class DividerTest {
    private Divider divider;
    private Result expectedResult;

    @BeforeEach
    void setup() {
        divider = new Divider();
        expectedResult = new Result();
    }

    @Nested
    @DisplayName("Strategy: happy path ")
    class HappyPath {
        @Test
        void shouldResultCorrectWhenTwoPositives() {
            expectedResult.setDividend(1565);
            expectedResult.setDivisor(23);
            expectedResult.setQuotient(68);
            expectedResult.setRemainder(1);
            expectedResult.setDividendIsNegative(false);
            expectedResult.setDivisorIsNegative(false);

            expectedResult.clearStages();
            expectedResult.addStage(156, 138, 3);
            expectedResult.addStage(185, 184, 1);
            expectedResult.setRemaindOffset(0);

            assertEquals(expectedResult, divider.divide(1565, 23));
        }

        @Test
        void shouldResultCorrectWhenNegativeAndPositive() {
            expectedResult.setDividend(-1564);
            expectedResult.setDivisor(313);
            expectedResult.setQuotient(-4);
            expectedResult.setRemainder(-312);
            expectedResult.setDividendIsNegative(true);
            expectedResult.setDivisorIsNegative(false);

            expectedResult.clearStages();
            expectedResult.addStage(1564, 1252, 4);
            expectedResult.setRemaindOffset(0);

            assertEquals(expectedResult, divider.divide(-1564, 313));
        }

        @Test
        void shouldResultCorrectWhenPositiveAndNegative() {
            expectedResult.setDividend(1564);
            expectedResult.setDivisor(-3);
            expectedResult.setQuotient(-521);
            expectedResult.setRemainder(1);
            expectedResult.setDividendIsNegative(false);
            expectedResult.setDivisorIsNegative(true);

            expectedResult.clearStages();
            expectedResult.addStage(15, 15, 2);
            expectedResult.addStage(6, 6, 1);
            expectedResult.addStage(4, 3, 1);
            expectedResult.setRemaindOffset(0);

            assertEquals(expectedResult, divider.divide(1564, -3));
        }

        @Test
        void shouldResultCorrectWhenTwoNegative() {
            expectedResult.setDividend(-1564);
            expectedResult.setDivisor(-1);
            expectedResult.setQuotient(1564);
            expectedResult.setRemainder(0);
            expectedResult.setDividendIsNegative(true);
            expectedResult.setDivisorIsNegative(true);

            expectedResult.clearStages();
            expectedResult.addStage(1, 1, 1);
            expectedResult.addStage(5, 5, 1);
            expectedResult.addStage(6, 6, 1);
            expectedResult.addStage(4, 4, 1);
            expectedResult.setRemaindOffset(0);

            Result result = divider.divide(-1564, -1);
            assertEquals(expectedResult, result);
        }
    }

    @Nested
    @DisplayName("Strategy: error path ")
    class ErrorPath {
        // The lambda expressions is simpler, but we have not studied it.
        @Test
        void shouldThrowExceptionWhenDivisor0() {
            assertThrows(DivisionInputException.class, () -> divider.divide(1564, 0));
        }

        @Test
        void shouldThrowExceptionWhenNextDigit() {
            assertThrows(IndexOutOfBoundsException.class, () -> {
                divider.divide(10, 5);
                divider.nextDigit();
            });
        }
    }

    @Nested
    @DisplayName("Strategy: corner cases ")
    class CornerCases {
        @Test
        void shouldResultCorrectWhenEqualLength() {
            expectedResult.setDividend(1564);
            expectedResult.setDivisor(1313);
            expectedResult.setQuotient(1);
            expectedResult.setRemainder(251);
            expectedResult.setDividendIsNegative(false);
            expectedResult.setDivisorIsNegative(false);

            expectedResult.clearStages();
            expectedResult.addStage(1564, 1313, 4);
            expectedResult.setRemaindOffset(0);

            Result result = divider.divide(1564, 1313);
            assertEquals(expectedResult, result);
        }

        @Test
        void shouldResultCorrectWhenDividendLessDivisor() {
            expectedResult.setDividend(1564);
            expectedResult.setDivisor(1565);
            expectedResult.setQuotient(0);
            expectedResult.setRemainder(1564);
            expectedResult.setDividendIsNegative(false);
            expectedResult.setDivisorIsNegative(false);

            expectedResult.clearStages();
            expectedResult.setRemaindOffset(4);

            Result result = divider.divide(1564, 1565);
            assertEquals(expectedResult, result);
        }

        @Test
        void shouldResultCorrectWhenDividend0() {
            expectedResult.setDividend(0);
            expectedResult.setDivisor(1565);
            expectedResult.setQuotient(0);
            expectedResult.setRemainder(0);
            expectedResult.setDividendIsNegative(false);
            expectedResult.setDivisorIsNegative(false);

            expectedResult.clearStages();
            expectedResult.setRemaindOffset(1);

            Result result = divider.divide(0, 1565);
            assertEquals(expectedResult, result);
        }

        @Test
        void shouldResultCorrectWhenDividendMinus0() {
            expectedResult.setDividend(-0);
            expectedResult.setDivisor(1565);
            expectedResult.setQuotient(0);
            expectedResult.setRemainder(0);
            expectedResult.setDividendIsNegative(false);
            expectedResult.setDivisorIsNegative(false);

            expectedResult.clearStages();
            expectedResult.setRemaindOffset(1);

            Result result = divider.divide(-0, 1565);
            assertEquals(expectedResult, result);
        }
    }
}
