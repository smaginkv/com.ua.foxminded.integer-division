package ua.com.foxminded.division.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ResultTest {

    private Result result;

    @BeforeEach
    void setup() {
        result = new Result();
        result.addStage(3, 2, 1);
    }

    @Nested
    @DisplayName("Strategy: happy path ")
    class HappyPath {
        @Test
        void shouldClearStagesWhenNonEmpty() {
            result.clearStages();
            assertEquals(0, result.getStagesLength());
        }

        @Test
        void shouldAddStageWhenEmpty() {
            assertEquals(1, result.getStagesLength());
        }

        @Test
        void shouldAddTwoStageWhenEmpty() {
            result.addStage(3, 2, 1);
            assertEquals(2, result.getStagesLength());
        }

        @Test
        void shouldGetPartialDividendWhenNonEmpty() {
            assertEquals(3, result.getPartialDividend(0));
        }

        @Test
        void shoulGetPartialDividendWithoutRemainderWhenNonEmpty() {
            assertEquals(2, result.getPartialDividendWithoutRemainder(0));
        }

        @Test
        void shoulGetStageOffsetWhenNonEmpty() {
            assertEquals(1, result.getStageOffset(0));
        }

        @Test
        void shoulGetCorrectToStringWhenNonEmpty() {
            assertEquals(
                    "Result(dividend=0, divisor=0, quotient=0, remainder=0, dividendIsNegative=false, divisorIsNegative=false, remaindOffset=0, stages=[Result.Stage(partialDividend=3, partialDividendWithoutRemainder=2, offset=1)])",
                    result.toString());
        }

        @Test
        void shouldGetHashCodeWhenNonEmpty() {
            assertNotEquals(0, result.hashCode());
        }

        @Test
        void shouldEqualsWhenDifferentReference() {
            Result sameResult = new Result();
            sameResult.addStage(3, 2, 1);

            assertEquals(true, sameResult.equals(result));
        }
    }

    @Nested
    @DisplayName("Strategy: error path ")
    class ErrorPath {

        // The lambda expressions is simpler, but we have not studied it.
        @Test
        void shouldThrowExceptionWhenPartialDividendEmpty() {
            result.clearStages();
            assertThrows(IndexOutOfBoundsException.class, () -> result.getPartialDividend(0));
        }

        @Test
        void shouldThrowExceptionWhenPartialDividendWithoutRemainderEmpty() {
            result.clearStages();
            assertThrows(IndexOutOfBoundsException.class, () -> result.getPartialDividendWithoutRemainder(0));
        }

        @Test
        void shouldThrowExceptionWhenStageOffsetEmpty() {
            result.clearStages();
            assertThrows(IndexOutOfBoundsException.class, () -> result.getStageOffset(0));
        }
    }
}
