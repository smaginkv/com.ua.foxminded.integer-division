package ua.com.foxminded.division;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import ua.com.foxminded.division.exception.DivisionInputException;
import ua.com.foxminded.division.exception.DivisionNoSuchOptionExeption;
import ua.com.foxminded.division.text.ClassicFormatter;
import ua.com.foxminded.division.text.HtmlFormatter;
import ua.com.foxminded.division.text.JsonFormatter;
import ua.com.foxminded.division.text.XmlFormatter;

class InputProcessingTest {

    @Nested
    @DisplayName("Strategy: happy path ")
    class HappyPath {

        @Test
        void shouldGetClassicFormatterWhenTwoDigits() {
            assertEquals(ClassicFormatter.class, InputProcessing.getFormatter("10", "2").getClass());
        }

        @Test
        void shouldGetJsonFormatterWhenJsonOption() {
            assertEquals(JsonFormatter.class, InputProcessing.getFormatter("10", "2", "-j").getClass());
        }

        @Test
        void shouldGetHtmlFormatterWhenHtmlOption() {
            assertEquals(HtmlFormatter.class,
                    InputProcessing.getFormatter("10", "2", "-h", ">", "index.html").getClass());
        }

        @Test
        void shouldGetXmlFormatterWhenXmlOption() {
            assertEquals(XmlFormatter.class,
                    InputProcessing.getFormatter("10", "2", "-x", ">", "example.xml").getClass());
        }
    }

    @Nested
    @DisplayName("Strategy: error path ")
    class ErrorPath {
        @Test
        void shouldThrowExceptionWhenInputLess2Digits() {
            assertThrows(DivisionInputException.class, new Executable() {
                @Override
                public void execute() throws Throwable {
                    InputProcessing.getFormatter("10");
                }
            });
        }

        @Test
        void shouldThrowExceptionWhenInputIncorrectOption() {
            assertThrows(DivisionNoSuchOptionExeption.class, new Executable() {
                @Override
                public void execute() throws Throwable {
                    InputProcessing.getFormatter("10", "2", "-m");
                }
            });
        }

        @Test
        void shouldThrowExceptionWhenInputIncorrectDigit() {
            assertThrows(DivisionInputException.class, new Executable() {
                @Override
                public void execute() throws Throwable {
                    InputProcessing.getFormatter("10", "2.0");
                }
            });
        }
    }
}
