package ua.com.foxminded.division.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.division.math.Result;

class JsonFormatterTest {

    private JsonFormatter jsonFormatter;
    private Result inputResult;
    private String expectedResult;

    @BeforeEach
    void setup() {
        jsonFormatter = new JsonFormatter();
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

            expectedResult = "{\r\n  \"dividend\" : 1565,\r\n  \"divisor\" : 23,\r\n  \"quotient\" : 68,\r\n  \"remainder\" : 1,\r\n  \"stages\" : [ {\r\n    \"partialDividend\" : 156,\r\n    \"partialDividendWithoutRemainder\" : 138\r\n  }, {\r\n    \"partialDividend\" : 185,\r\n    \"partialDividendWithoutRemainder\" : 184\r\n  } ],\r\n  \"stagesLength\" : 2\r\n}";   
            assertEquals(expectedResult, jsonFormatter.format(inputResult));
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

            expectedResult = "{\r\n  \"dividend\" : -1564,\r\n  \"divisor\" : 313,\r\n  \"quotient\" : -4,\r\n  \"remainder\" : -312,\r\n  \"stages\" : [ {\r\n    \"partialDividend\" : -1564,\r\n    \"partialDividendWithoutRemainder\" : -1252\r\n  } ],\r\n  \"stagesLength\" : 1\r\n}";   
            assertEquals(expectedResult, jsonFormatter.format(inputResult));
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

            expectedResult = "{\r\n  \"dividend\" : 1564,\r\n  \"divisor\" : -3,\r\n  \"quotient\" : -521,\r\n  \"remainder\" : 1,\r\n  \"stages\" : [ {\r\n    \"partialDividend\" : 15,\r\n    \"partialDividendWithoutRemainder\" : 15\r\n  }, {\r\n    \"partialDividend\" : 6,\r\n    \"partialDividendWithoutRemainder\" : 6\r\n  }, {\r\n    \"partialDividend\" : 4,\r\n    \"partialDividendWithoutRemainder\" : 3\r\n  } ],\r\n  \"stagesLength\" : 3\r\n}";  
            assertEquals(expectedResult, jsonFormatter.format(inputResult));
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

            expectedResult = "{\r\n  \"dividend\" : -1564,\r\n  \"divisor\" : -1,\r\n  \"quotient\" : 1564,\r\n  \"remainder\" : 0,\r\n  \"stages\" : [ {\r\n    \"partialDividend\" : -1,\r\n    \"partialDividendWithoutRemainder\" : -1\r\n  }, {\r\n    \"partialDividend\" : -5,\r\n    \"partialDividendWithoutRemainder\" : -5\r\n  }, {\r\n    \"partialDividend\" : -6,\r\n    \"partialDividendWithoutRemainder\" : -6\r\n  }, {\r\n    \"partialDividend\" : -4,\r\n    \"partialDividendWithoutRemainder\" : -4\r\n  } ],\r\n  \"stagesLength\" : 4\r\n}";  
            assertEquals(expectedResult, jsonFormatter.format(inputResult));
        }

        @Test
        void shouldCorrectToString() {
            assertEquals("JSON", jsonFormatter.toString());
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

            expectedResult = "{\r\n  \"dividend\" : 1564,\r\n  \"divisor\" : 1313,\r\n  \"quotient\" : 1,\r\n  \"remainder\" : 251,\r\n  \"stages\" : [ {\r\n    \"partialDividend\" : 1564,\r\n    \"partialDividendWithoutRemainder\" : 1313\r\n  } ],\r\n  \"stagesLength\" : 1\r\n}";   
            assertEquals(expectedResult, jsonFormatter.format(inputResult));
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

            expectedResult = "{\r\n  \"dividend\" : 1564,\r\n  \"divisor\" : 1565,\r\n  \"quotient\" : 0,\r\n  \"remainder\" : 1564,\r\n  \"stages\" : [ ],\r\n  \"stagesLength\" : 0\r\n}";    
            assertEquals(expectedResult, jsonFormatter.format(inputResult));

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

            expectedResult = "{\r\n  \"dividend\" : 0,\r\n  \"divisor\" : 1565,\r\n  \"quotient\" : 0,\r\n  \"remainder\" : 0,\r\n  \"stages\" : [ ],\r\n  \"stagesLength\" : 0\r\n}";  
            assertEquals(expectedResult, jsonFormatter.format(inputResult));
        }
    }

}
