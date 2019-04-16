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
import ua.com.foxminded.division.math.Result;

class XmlFormatterTest {

    private XmlFormatter xmlFormatter;
    private Result inputResult;
    private String expectedResult;

    @BeforeEach
    void setup(){
        xmlFormatter = new XmlFormatter();
        xmlFormatter.setFileName("someFilePath");
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

            expectedResult = "<Result>\r\n  <dividend>1565</dividend>\r\n  <divisor>23</divisor>\r\n  <quotient>68</quotient>\r\n  <remainder>1</remainder>\r\n  <stages>\r\n    <stages>\r\n      <partialDividend>156</partialDividend>\r\n      <partialQuotient>138</partialQuotient>\r\n    </stages>\r\n    <stages>\r\n      <partialDividend>185</partialDividend>\r\n      <partialQuotient>184</partialQuotient>\r\n    </stages>\r\n  </stages>\r\n  <stagesLength>2</stagesLength>\r\n</Result>\r\n";   
            assertEquals(expectedResult, xmlFormatter.format(inputResult));
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

            expectedResult = "<Result>\r\n  <dividend>-1564</dividend>\r\n  <divisor>313</divisor>\r\n  <quotient>-4</quotient>\r\n  <remainder>-312</remainder>\r\n  <stages>\r\n    <stages>\r\n      <partialDividend>-1564</partialDividend>\r\n      <partialQuotient>-1252</partialQuotient>\r\n    </stages>\r\n  </stages>\r\n  <stagesLength>1</stagesLength>\r\n</Result>\r\n";   
            assertEquals(expectedResult, xmlFormatter.format(inputResult));
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

            expectedResult = "<Result>\r\n  <dividend>1564</dividend>\r\n  <divisor>-3</divisor>\r\n  <quotient>-521</quotient>\r\n  <remainder>1</remainder>\r\n  <stages>\r\n    <stages>\r\n      <partialDividend>15</partialDividend>\r\n      <partialQuotient>15</partialQuotient>\r\n    </stages>\r\n    <stages>\r\n      <partialDividend>6</partialDividend>\r\n      <partialQuotient>6</partialQuotient>\r\n    </stages>\r\n    <stages>\r\n      <partialDividend>4</partialDividend>\r\n      <partialQuotient>3</partialQuotient>\r\n    </stages>\r\n  </stages>\r\n  <stagesLength>3</stagesLength>\r\n</Result>\r\n";  
            assertEquals(expectedResult, xmlFormatter.format(inputResult));
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

            expectedResult = "<Result>\r\n  <dividend>-1564</dividend>\r\n  <divisor>-1</divisor>\r\n  <quotient>1564</quotient>\r\n  <remainder>0</remainder>\r\n  <stages>\r\n    <stages>\r\n      <partialDividend>-1</partialDividend>\r\n      <partialQuotient>-1</partialQuotient>\r\n    </stages>\r\n    <stages>\r\n      <partialDividend>-5</partialDividend>\r\n      <partialQuotient>-5</partialQuotient>\r\n    </stages>\r\n    <stages>\r\n      <partialDividend>-6</partialDividend>\r\n      <partialQuotient>-6</partialQuotient>\r\n    </stages>\r\n    <stages>\r\n      <partialDividend>-4</partialDividend>\r\n      <partialQuotient>-4</partialQuotient>\r\n    </stages>\r\n  </stages>\r\n  <stagesLength>4</stagesLength>\r\n</Result>\r\n";  
            assertEquals(expectedResult, xmlFormatter.format(inputResult));
        }

        @Test
        void shouldCorrectToString() {
            assertEquals("XML", xmlFormatter.toString());
        }

        @Test
        void shouldReturnFileOutputStreamWhenHtmlFormatter() throws IOException {
            try (OutputStream outputStream = xmlFormatter.getOutputStream()) {
                assertEquals(FileOutputStream.class, outputStream.getClass());
            }
            File file = new File(xmlFormatter.getFileName());
            if (file.isFile()) {
                file.delete();
            }
        }
    }
    
    @Nested
    @DisplayName("Strategy: error path ")
    class ErrorPath {
        @Test
        void shouldThrowExceptionWhenFileNameNotSet(){
            xmlFormatter.setFileName("");
            assertThrows(FileNotFoundException.class, ()->xmlFormatter.getOutputStream());
        }
        
        @Test
        void shouldFormatThrowExceptionWhenFileNameNotSet(){
            xmlFormatter.setFileName("");
            assertThrows(DivisionFileNotSetException.class, ()->xmlFormatter.format(inputResult));
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

            expectedResult = "<Result>\r\n  <dividend>1564</dividend>\r\n  <divisor>1313</divisor>\r\n  <quotient>1</quotient>\r\n  <remainder>251</remainder>\r\n  <stages>\r\n    <stages>\r\n      <partialDividend>1564</partialDividend>\r\n      <partialQuotient>1313</partialQuotient>\r\n    </stages>\r\n  </stages>\r\n  <stagesLength>1</stagesLength>\r\n</Result>\r\n";   
            assertEquals(expectedResult, xmlFormatter.format(inputResult));
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

            expectedResult = "<Result>\r\n  <dividend>1564</dividend>\r\n  <divisor>1565</divisor>\r\n  <quotient>0</quotient>\r\n  <remainder>1564</remainder>\r\n  <stages/>\r\n  <stagesLength>0</stagesLength>\r\n</Result>\r\n";   
            assertEquals(expectedResult, xmlFormatter.format(inputResult));

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

            expectedResult = "<Result>\r\n  <dividend>0</dividend>\r\n  <divisor>1565</divisor>\r\n  <quotient>0</quotient>\r\n  <remainder>0</remainder>\r\n  <stages/>\r\n  <stagesLength>0</stagesLength>\r\n</Result>\r\n"; 
            assertEquals(expectedResult, xmlFormatter.format(inputResult));
        }
    }

}
