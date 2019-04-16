package ua.com.foxminded.division;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.division.text.ClassicFormatter;
import ua.com.foxminded.division.text.Formatter;
import ua.com.foxminded.division.text.HtmlFormatter;
import ua.com.foxminded.division.text.JsonFormatter;
import ua.com.foxminded.division.text.XmlFormatter;

class ContextTest {

    private Context context;

    @BeforeEach
    void setup() {
        context = new Context();
    }

    @Test
    void shouldContextReturnSameInstance() {
        assertEquals(Context.getInstance(), Context.getInstance());
    }

    @Test
    void shouldContextReturnClassicFormatter() {
        Formatter formatter = context.getFormatter(Format.CLASSIC);
        assertEquals(ClassicFormatter.class, formatter.getClass());
    }

    @Test
    void shouldContextReturnHtmlFormatter() {
        Formatter formatter = context.getFormatter(Format.HTML);
        assertEquals(HtmlFormatter.class, formatter.getClass());
    }

    @Test
    void shouldContextReturnJsonFormatter() {
        Formatter formatter = context.getFormatter(Format.JSON);
        assertEquals(JsonFormatter.class, formatter.getClass());
    }

    @Test
    void shouldContextReturnXmlFormatter() {
        Formatter formatter = context.getFormatter(Format.XML);
        assertEquals(XmlFormatter.class, formatter.getClass());
    }
}
