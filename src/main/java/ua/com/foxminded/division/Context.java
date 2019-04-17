package ua.com.foxminded.division;

import java.util.HashMap;

import ua.com.foxminded.division.text.ClassicFormatter;
import ua.com.foxminded.division.text.Formatter;
import ua.com.foxminded.division.text.HtmlFormatter;
import ua.com.foxminded.division.text.JsonFormatter;
import ua.com.foxminded.division.text.XmlFormatter;

enum Format {
    CLASSIC {
        public Formatter getInstanceFormatter() {
            return new ClassicFormatter();
        }
    },
    JSON {
        public Formatter getInstanceFormatter() {
            return new JsonFormatter();
        }
    },
    XML {
        public Formatter getInstanceFormatter() {
            return new XmlFormatter();
        }
    },
    HTML {
        public Formatter getInstanceFormatter() {
            return new HtmlFormatter();
        }
    };
    public abstract Formatter getInstanceFormatter();
}

public class Context {
    private static Context instance;
    private static HashMap<Format, Formatter> formatters = new HashMap<>();

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    public Formatter getFormatter(Format format) {
        Formatter formatter = formatters.get(format);
        if (formatter == null) {
            formatter = format.getInstanceFormatter();
            formatters.put(format, formatter);
        }
        return formatter;
    }
}
