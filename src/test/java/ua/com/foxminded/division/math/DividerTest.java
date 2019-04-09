package ua.com.foxminded.division.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DividerTest {
    Divider divider;
    Result standart;

    @BeforeEach
    void setup() {
        divider = new Divider();
        standart = new Result();
    }

    @Test
    void shouldResultCorrectWhenTwoPositives() {
        standart.setDividend(1565);
        standart.setDivisor(23);
        standart.setQuotient(68);
        standart.setRemainder(1);
        standart.setDividendIsNegative(false);
        standart.setDivisorIsNegative(false);
        standart.setRemaindOffset(0);
        
        standart.clearStages();
        standart.addStage(156, 138, 3);
        standart.addStage(185, 184, 1);

        Result result = divider.divide(1565, 23);
        if (!result.equals(standart))
            fail();
    }

}
