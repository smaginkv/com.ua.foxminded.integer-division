package ua.com.foxminded.division.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.division.exception.DivisionFileNotSetException;
import ua.com.foxminded.division.exception.DivisionInnerProccessingException;
import ua.com.foxminded.division.math.Result;

class HtmlFormatterTest {

    private HtmlFormatter htmlFormatter;
    private Result inputResult;
    private String expectedResult;

    @BeforeEach
    void setup() {
        htmlFormatter = new HtmlFormatter();
        htmlFormatter.setFileName("someFilePath");
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

            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Variant-1</title>\r\n<style>\r\n.division {\r\n    border: 1px dashed #634f36;\r\nbackground: #fffff5;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    padding: 7px;\r\n    white-space: pre;\r\n}\r\n\r\ntd:hover {\r\n    background: #786b59;\r\n    color: #ffe;\r\n}\r\n</style>\r\n</head>\r\n<body>\r\n    <p>British notation:</p>\r\n    <table class=\"division\">\r\n        <tr>\r\n            <td><span>   68&#10;23|1565&#10;   138&#10;</span></td>\r\n        </tr>\r\n        <tr>\r\n            <td><span>    185&#10;    184&#10;</span></td>\r\n        </tr>\r\n        <tr>\r\n            <td><span>      1&#10;</span></td>\r\n        </tr>\r\n    </table>\r\n</body>\r\n</html>";
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

            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Variant-1</title>\r\n<style>\r\n.division {\r\n    border: 1px dashed #634f36;\r\nbackground: #fffff5;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    padding: 7px;\r\n    white-space: pre;\r\n}\r\n\r\ntd:hover {\r\n    background: #786b59;\r\n    color: #ffe;\r\n}\r\n</style>\r\n</head>\r\n<body>\r\n    <p>British notation:</p>\r\n    <table class=\"division\">\r\n        <tr>\r\n            <td><span>    -4&#10;313|-1564&#10;    -1252&#10;</span></td>\r\n        </tr>\r\n        <tr>\r\n            <td><span>     -312&#10;</span></td>\r\n        </tr>\r\n    </table>\r\n</body>\r\n</html>";
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

            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Variant-1</title>\r\n<style>\r\n.division {\r\n    border: 1px dashed #634f36;\r\nbackground: #fffff5;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    padding: 7px;\r\n    white-space: pre;\r\n}\r\n\r\ntd:hover {\r\n    background: #786b59;\r\n    color: #ffe;\r\n}\r\n</style>\r\n</head>\r\n<body>\r\n    <p>British notation:</p>\r\n    <table class=\"division\">\r\n        <tr>\r\n            <td><span>   -521&#10;-3|1564&#10;   15&#10;</span></td>\r\n        </tr>\r\n        <tr>\r\n            <td><span>     6&#10;     6&#10;</span></td>\r\n        </tr>\r\n        <tr>\r\n            <td><span>      4&#10;      3&#10;</span></td>\r\n        </tr>\r\n        <tr>\r\n            <td><span>      1&#10;</span></td>\r\n        </tr>\r\n    </table>\r\n</body>\r\n</html>";
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

            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Variant-1</title>\r\n<style>\r\n.division {\r\n    border: 1px dashed #634f36;\r\nbackground: #fffff5;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    padding: 7px;\r\n    white-space: pre;\r\n}\r\n\r\ntd:hover {\r\n    background: #786b59;\r\n    color: #ffe;\r\n}\r\n</style>\r\n</head>\r\n<body>\r\n    <p>British notation:</p>\r\n    <table class=\"division\">\r\n        <tr>\r\n            <td><span>   1564&#10;-1|-1564&#10;   -1&#10;</span></td>\r\n        </tr>\r\n        <tr>\r\n            <td><span>    -5&#10;    -5&#10;</span></td>\r\n        </tr>\r\n        <tr>\r\n            <td><span>     -6&#10;     -6&#10;</span></td>\r\n        </tr>\r\n        <tr>\r\n            <td><span>      -4&#10;      -4&#10;</span></td>\r\n        </tr>\r\n        <tr>\r\n            <td><span>       0&#10;</span></td>\r\n        </tr>\r\n    </table>\r\n</body>\r\n</html>";
            assertEquals(expectedResult, htmlFormatter.format(inputResult));
        }

        @Test
        void shouldCorrectToString() {
            assertEquals("HTML", htmlFormatter.toString());
        }

        @Test
        void shouldReturnFileOutputStream() throws IOException {
            try (OutputStream outputStream = htmlFormatter.getOutputStream()) {
                assertEquals(FileOutputStream.class, outputStream.getClass());
            }
            File file = new File(htmlFormatter.getFileName());
            if (file.isFile()) {
                file.delete();
            }
        }
    }

    @Nested
    @DisplayName("Strategy: error path ")
    class ErrorPath {
        @Test
        void shouldThrowExceptionWhenIncorrectHtmlTemplate() {
            assertThrows(DivisionInnerProccessingException.class, ()->htmlFormatter.getMustacheTemplate("1.mustache"));
        }

        @Test
        void shouldThrowExceptionWhenFileNameNotSet() {
            htmlFormatter.setFileName("");
            assertThrows(FileNotFoundException.class, ()->htmlFormatter.getOutputStream());
        }

        @Test
        void shouldFormatThrowExceptionWhenFileNameNotSet() {
            htmlFormatter.setFileName("");
            assertThrows(DivisionFileNotSetException.class, ()->htmlFormatter.format(inputResult));
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

            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Variant-1</title>\r\n<style>\r\n.division {\r\n    border: 1px dashed #634f36;\r\nbackground: #fffff5;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    padding: 7px;\r\n    white-space: pre;\r\n}\r\n\r\ntd:hover {\r\n    background: #786b59;\r\n    color: #ffe;\r\n}\r\n</style>\r\n</head>\r\n<body>\r\n    <p>British notation:</p>\r\n    <table class=\"division\">\r\n        <tr>\r\n            <td><span>     1&#10;1313|1564&#10;     1313&#10;</span></td>\r\n        </tr>\r\n        <tr>\r\n            <td><span>      251&#10;</span></td>\r\n        </tr>\r\n    </table>\r\n</body>\r\n</html>";
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

            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Variant-1</title>\r\n<style>\r\n.division {\r\n    border: 1px dashed #634f36;\r\nbackground: #fffff5;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    padding: 7px;\r\n    white-space: pre;\r\n}\r\n\r\ntd:hover {\r\n    background: #786b59;\r\n    color: #ffe;\r\n}\r\n</style>\r\n</head>\r\n<body>\r\n    <p>British notation:</p>\r\n    <table class=\"division\">\r\n        <tr>\r\n            <td><span>     0&#10;1565|1564&#10;     1564&#10;</span></td>\r\n        </tr>\r\n    </table>\r\n</body>\r\n</html>";
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

            expectedResult = "<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta charset=\"utf-8\">\r\n<title>Variant-1</title>\r\n<style>\r\n.division {\r\n    border: 1px dashed #634f36;\r\nbackground: #fffff5;\r\n    font-family: \"Courier New\", Courier, monospace;\r\n    padding: 7px;\r\n    white-space: pre;\r\n}\r\n\r\ntd:hover {\r\n    background: #786b59;\r\n    color: #ffe;\r\n}\r\n</style>\r\n</head>\r\n<body>\r\n    <p>British notation:</p>\r\n    <table class=\"division\">\r\n        <tr>\r\n            <td><span>     0&#10;1565|0&#10;     0&#10;</span></td>\r\n        </tr>\r\n    </table>\r\n</body>\r\n</html>";
            assertEquals(expectedResult, htmlFormatter.format(inputResult));
        }
    }

}
