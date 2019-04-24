package ua.com.foxminded.division.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.division.exception.DivisionInnerProccessingException;
import ua.com.foxminded.division.math.Result;

class HtmlFormatterTest {

    private HtmlFormatter htmlFormatter;
    private Result inputResult;
    private String expectedResult;

    @BeforeEach
    void setup() {
        htmlFormatter = new HtmlFormatter();
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

            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Integer division</title>\r\n<style>\r\nbody {\r\n    border-collapse: collapse;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    font-size:24px;\r\n    white-space: pre;\r\n}\r\n.block {\r\n    display: flex;\r\n    border: 1px solid black;\r\n    flex-wrap: wrap;\r\n    width: 168px;\r\n    justify-content: space-between;\r\n    background: #786b59;\r\n    margin: 16px;\r\n}\r\n\r\n.block__element {\r\n    width: 24px;\r\n    text-align: center;\r\n}\r\n\r\n#target1:hover ~ .GREEN1{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .BLUE1{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .VIOLET1{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n#target2:hover ~ .GREEN2{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target2:hover ~ .BLUE2{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target2:hover ~ .VIOLET2{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n   \r\n</style>\r\n</head>\r\n<body>\r\n    <strong>British notation:</strong>\r\n    <div class=\"block\">\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\" id = \"target1\">6</div>\r\n                   <div class=\"block__element\" id = \"target2\">8</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\">2</div>\r\n                   <div class=\"block__element\">3</div>\r\n                   <div class=\"block__element\">|</div>\r\n                   <div class=\"block__element GREEN1\">1</div>\r\n                   <div class=\"block__element GREEN1\">5</div>\r\n                   <div class=\"block__element GREEN1\">6</div>\r\n                   <div class=\"block__element\">5</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element BLUE1\">1</div>\r\n                   <div class=\"block__element BLUE1\">3</div>\r\n                   <div class=\"block__element BLUE1\">8</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element VIOLET2\">1</div>\r\n                   <div class=\"block__element VIOLET2\">8</div>\r\n                   <div class=\"block__element GREEN2\">5</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element BLUE2\">1</div>\r\n                   <div class=\"block__element BLUE2\">8</div>\r\n                   <div class=\"block__element BLUE2\">4</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element VIOLET3\">1</div>\r\n    </div>\r\n</body>\r\n</html>";
            assertEquals(expectedResult, htmlFormatter.format(inputResult));
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
            
            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Integer division</title>\r\n<style>\r\nbody {\r\n    border-collapse: collapse;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    font-size:24px;\r\n    white-space: pre;\r\n}\r\n.block {\r\n    display: flex;\r\n    border: 1px solid black;\r\n    flex-wrap: wrap;\r\n    width: 216px;\r\n    justify-content: space-between;\r\n    background: #786b59;\r\n    margin: 16px;\r\n}\r\n\r\n.block__element {\r\n    width: 24px;\r\n    text-align: center;\r\n}\r\n\r\n#target1:hover ~ .GREEN1{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .BLUE1{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .VIOLET1{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n#target2:hover ~ .GREEN2{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target2:hover ~ .BLUE2{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target2:hover ~ .VIOLET2{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n   \r\n</style>\r\n</head>\r\n<body>\r\n    <strong>British notation:</strong>\r\n    <div class=\"block\">\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\">-</div>\r\n                   <div class=\"block__element\" id = \"target1\">4</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\">3</div>\r\n                   <div class=\"block__element\">1</div>\r\n                   <div class=\"block__element\">3</div>\r\n                   <div class=\"block__element\">|</div>\r\n                   <div class=\"block__element GREEN1\">-</div>\r\n                   <div class=\"block__element GREEN1\">1</div>\r\n                   <div class=\"block__element GREEN1\">5</div>\r\n                   <div class=\"block__element GREEN1\">6</div>\r\n                   <div class=\"block__element GREEN1\">4</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element BLUE1\">-</div>\r\n                   <div class=\"block__element BLUE1\">1</div>\r\n                   <div class=\"block__element BLUE1\">2</div>\r\n                   <div class=\"block__element BLUE1\">5</div>\r\n                   <div class=\"block__element BLUE1\">2</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element VIOLET2\">-</div>\r\n                   <div class=\"block__element VIOLET2\">3</div>\r\n                   <div class=\"block__element VIOLET2\">1</div>\r\n                   <div class=\"block__element VIOLET2\">2</div>\r\n    </div>\r\n</body>\r\n</html>";
            assertEquals(expectedResult, htmlFormatter.format(inputResult));
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

            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Integer division</title>\r\n<style>\r\nbody {\r\n    border-collapse: collapse;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    font-size:24px;\r\n    white-space: pre;\r\n}\r\n.block {\r\n    display: flex;\r\n    border: 1px solid black;\r\n    flex-wrap: wrap;\r\n    width: 168px;\r\n    justify-content: space-between;\r\n    background: #786b59;\r\n    margin: 16px;\r\n}\r\n\r\n.block__element {\r\n    width: 24px;\r\n    text-align: center;\r\n}\r\n\r\n#target1:hover ~ .GREEN1{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .BLUE1{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .VIOLET1{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n#target2:hover ~ .GREEN2{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target2:hover ~ .BLUE2{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target2:hover ~ .VIOLET2{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n#target3:hover ~ .GREEN3{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target3:hover ~ .BLUE3{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target3:hover ~ .VIOLET3{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n#target4:hover ~ .GREEN4{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target4:hover ~ .BLUE4{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target4:hover ~ .VIOLET4{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n   \r\n</style>\r\n</head>\r\n<body>\r\n    <strong>British notation:</strong>\r\n    <div class=\"block\">\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\">-</div>\r\n                   <div class=\"block__element\" id = \"target1\">5</div>\r\n                   <div class=\"block__element\" id = \"target2\">2</div>\r\n                   <div class=\"block__element\" id = \"target3\">1</div>\r\n                   <div class=\"block__element\">-</div>\r\n                   <div class=\"block__element\">3</div>\r\n                   <div class=\"block__element\">|</div>\r\n                   <div class=\"block__element GREEN1\">1</div>\r\n                   <div class=\"block__element GREEN1\">5</div>\r\n                   <div class=\"block__element\">6</div>\r\n                   <div class=\"block__element\">4</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element BLUE1\">1</div>\r\n                   <div class=\"block__element BLUE1\">5</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element GREEN2\">6</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element BLUE2\">6</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element GREEN3\">4</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element BLUE3\">3</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element VIOLET4\">1</div>\r\n    </div>\r\n</body>\r\n</html>";  
            assertEquals(expectedResult, htmlFormatter.format(inputResult));
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

            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Integer division</title>\r\n<style>\r\nbody {\r\n    border-collapse: collapse;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    font-size:24px;\r\n    white-space: pre;\r\n}\r\n.block {\r\n    display: flex;\r\n    border: 1px solid black;\r\n    flex-wrap: wrap;\r\n    width: 192px;\r\n    justify-content: space-between;\r\n    background: #786b59;\r\n    margin: 16px;\r\n}\r\n\r\n.block__element {\r\n    width: 24px;\r\n    text-align: center;\r\n}\r\n\r\n#target1:hover ~ .GREEN1{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .BLUE1{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .VIOLET1{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n#target2:hover ~ .GREEN2{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target2:hover ~ .BLUE2{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target2:hover ~ .VIOLET2{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n#target3:hover ~ .GREEN3{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target3:hover ~ .BLUE3{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target3:hover ~ .VIOLET3{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n#target4:hover ~ .GREEN4{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target4:hover ~ .BLUE4{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target4:hover ~ .VIOLET4{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n   \r\n</style>\r\n</head>\r\n<body>\r\n    <strong>British notation:</strong>\r\n    <div class=\"block\">\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\" id = \"target1\">1</div>\r\n                   <div class=\"block__element\" id = \"target2\">5</div>\r\n                   <div class=\"block__element\" id = \"target3\">6</div>\r\n                   <div class=\"block__element\" id = \"target4\">4</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\">-</div>\r\n                   <div class=\"block__element\">1</div>\r\n                   <div class=\"block__element\">|</div>\r\n                   <div class=\"block__element GREEN1\">-</div>\r\n                   <div class=\"block__element GREEN1\">1</div>\r\n                   <div class=\"block__element\">5</div>\r\n                   <div class=\"block__element\">6</div>\r\n                   <div class=\"block__element\">4</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element BLUE1\">-</div>\r\n                   <div class=\"block__element BLUE1\">1</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element GREEN2\">-</div>\r\n                   <div class=\"block__element GREEN2\">5</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element BLUE2\">-</div>\r\n                   <div class=\"block__element BLUE2\">5</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element GREEN3\">-</div>\r\n                   <div class=\"block__element GREEN3\">6</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element BLUE3\">-</div>\r\n                   <div class=\"block__element BLUE3\">6</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element GREEN4\">-</div>\r\n                   <div class=\"block__element GREEN4\">4</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element BLUE4\">-</div>\r\n                   <div class=\"block__element BLUE4\">4</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element VIOLET5\">0</div>\r\n    </div>\r\n</body>\r\n</html>";
            assertEquals(expectedResult, htmlFormatter.format(inputResult));
        }

        @Test
        void shouldCorrectToString() {
            assertEquals("HTML", htmlFormatter.toString());
        }
    }

    @Nested
    @DisplayName("Strategy: error path ")
    class ErrorPath {
        @Test
        void shouldThrowExceptionWhenIncorrectHtmlTemplate() {
            assertThrows(DivisionInnerProccessingException.class, ()->htmlFormatter.getMustacheTemplate("1.mustache"));
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
            
            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Integer division</title>\r\n<style>\r\nbody {\r\n    border-collapse: collapse;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    font-size:24px;\r\n    white-space: pre;\r\n}\r\n.block {\r\n    display: flex;\r\n    border: 1px solid black;\r\n    flex-wrap: wrap;\r\n    width: 216px;\r\n    justify-content: space-between;\r\n    background: #786b59;\r\n    margin: 16px;\r\n}\r\n\r\n.block__element {\r\n    width: 24px;\r\n    text-align: center;\r\n}\r\n\r\n#target1:hover ~ .GREEN1{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .BLUE1{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .VIOLET1{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n   \r\n</style>\r\n</head>\r\n<body>\r\n    <strong>British notation:</strong>\r\n    <div class=\"block\">\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\" id = \"target1\">1</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\">1</div>\r\n                   <div class=\"block__element\">3</div>\r\n                   <div class=\"block__element\">1</div>\r\n                   <div class=\"block__element\">3</div>\r\n                   <div class=\"block__element\">|</div>\r\n                   <div class=\"block__element GREEN1\">1</div>\r\n                   <div class=\"block__element GREEN1\">5</div>\r\n                   <div class=\"block__element GREEN1\">6</div>\r\n                   <div class=\"block__element GREEN1\">4</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element BLUE1\">1</div>\r\n                   <div class=\"block__element BLUE1\">3</div>\r\n                   <div class=\"block__element BLUE1\">1</div>\r\n                   <div class=\"block__element BLUE1\">3</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element VIOLET2\">2</div>\r\n                   <div class=\"block__element VIOLET2\">5</div>\r\n                   <div class=\"block__element VIOLET2\">1</div>\r\n    </div>\r\n</body>\r\n</html>";
            assertEquals(expectedResult, htmlFormatter.format(inputResult));
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
            
            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Integer division</title>\r\n<style>\r\nbody {\r\n    border-collapse: collapse;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    font-size:24px;\r\n    white-space: pre;\r\n}\r\n.block {\r\n    display: flex;\r\n    border: 1px solid black;\r\n    flex-wrap: wrap;\r\n    width: 216px;\r\n    justify-content: space-between;\r\n    background: #786b59;\r\n    margin: 16px;\r\n}\r\n\r\n.block__element {\r\n    width: 24px;\r\n    text-align: center;\r\n}\r\n\r\n#target1:hover ~ .GREEN1{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .BLUE1{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .VIOLET1{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n   \r\n</style>\r\n</head>\r\n<body>\r\n    <strong>British notation:</strong>\r\n    <div class=\"block\">\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\" id = \"target1\">0</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\">1</div>\r\n                   <div class=\"block__element\">5</div>\r\n                   <div class=\"block__element\">6</div>\r\n                   <div class=\"block__element\">5</div>\r\n                   <div class=\"block__element\">|</div>\r\n                   <div class=\"block__element GREEN1\">1</div>\r\n                   <div class=\"block__element GREEN1\">5</div>\r\n                   <div class=\"block__element GREEN1\">6</div>\r\n                   <div class=\"block__element GREEN1\">4</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element BLUE1\">1</div>\r\n                   <div class=\"block__element BLUE1\">5</div>\r\n                   <div class=\"block__element BLUE1\">6</div>\r\n                   <div class=\"block__element BLUE1\">4</div>\r\n    </div>\r\n</body>\r\n</html>";
            assertEquals(expectedResult, htmlFormatter.format(inputResult));

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
            
            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Integer division</title>\r\n<style>\r\nbody {\r\n    border-collapse: collapse;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    font-size:24px;\r\n    white-space: pre;\r\n}\r\n.block {\r\n    display: flex;\r\n    border: 1px solid black;\r\n    flex-wrap: wrap;\r\n    width: 144px;\r\n    justify-content: space-between;\r\n    background: #786b59;\r\n    margin: 16px;\r\n}\r\n\r\n.block__element {\r\n    width: 24px;\r\n    text-align: center;\r\n}\r\n\r\n#target1:hover ~ .GREEN1{\r\n    color: green;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .BLUE1{\r\n    color: blue;\r\n    font-weight: 600;\r\n}\r\n\r\n#target1:hover ~ .VIOLET1{\r\n    color: violet;\r\n    font-weight: 600;\r\n}\r\n   \r\n</style>\r\n</head>\r\n<body>\r\n    <strong>British notation:</strong>\r\n    <div class=\"block\">\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\" id = \"target1\">0</div>\r\n                   <div class=\"block__element\">1</div>\r\n                   <div class=\"block__element\">5</div>\r\n                   <div class=\"block__element\">6</div>\r\n                   <div class=\"block__element\">5</div>\r\n                   <div class=\"block__element\">|</div>\r\n                   <div class=\"block__element GREEN1\">0</div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element\"> </div>\r\n                   <div class=\"block__element BLUE1\">0</div>\r\n    </div>\r\n</body>\r\n</html>";
            assertEquals(expectedResult, htmlFormatter.format(inputResult));
        }
    }

}
