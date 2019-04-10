package ua.com.foxminded.division.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DividerTest {
    Divider divider;
    Result standart;

    @BeforeEach
    void setup() {
        divider = new Divider();
        standart = new Result();
    }

    @Nested
    @DisplayName("Strategy: happy path ")
    class HappyPath {
        @Test
        void shouldResultCorrectWhenTwoPositives() {
            standart.setDividend(1565);
            standart.setDivisor(23);
            standart.setQuotient(68);
            standart.setRemainder(1);
            standart.setDividendIsNegative(false);
            standart.setDivisorIsNegative(false);

            standart.clearStages();
            standart.addStage(156, 138, 3);
            standart.addStage(185, 184, 1);
            standart.setRemaindOffset(0);

            assertEquals(standart, divider.divide(1565, 23));
        }

        @Test
        void shouldResultCorrectWhenNegativeAndPositive() {
            standart.setDividend(-1564);
            standart.setDivisor(313);
            standart.setQuotient(-4);
            standart.setRemainder(-312);
            standart.setDividendIsNegative(true);
            standart.setDivisorIsNegative(false);
            
            standart.clearStages();
            standart.addStage(1564, 1252, 4);            
            standart.setRemaindOffset(0);

            assertEquals(standart, divider.divide(-1564, 313));
        }

        @Test
        void shouldResultCorrectWhenPositiveAndNegative() {
            standart.setDividend(1564);
            standart.setDivisor(-3);
            standart.setQuotient(-521);
            standart.setRemainder(1);
            standart.setDividendIsNegative(false);
            standart.setDivisorIsNegative(true);

            standart.clearStages();
            standart.addStage(15, 15, 2);
            standart.addStage(6, 6, 1);
            standart.addStage(4, 3, 1);
            standart.setRemaindOffset(0);

            assertEquals(standart, divider.divide(1564, -3));
        }
        
        @Test
        void shouldResultCorrectWhenTwoNegative() {
            standart.setDividend(-1564);
            standart.setDivisor(-1313);
            standart.setQuotient(1);
            standart.setRemainder(-251);
            standart.setDividendIsNegative(true);
            standart.setDivisorIsNegative(true);
            
            standart.clearStages();
            standart.addStage(1564, 1313, 4);            
            standart.setRemaindOffset(0);

            Result result = divider.divide(-1564, -1313);
            assertEquals(standart, result);
        }


    }
}
