package ua.com.foxminded.division;

import ua.com.foxminded.division.text.*;

enum Format {
    CLASSIC, JSON, XML, HTML, NON_INITIALIZED
}

public class Context {
    private static Context instance;
    private static Formatter classic;
    private static Formatter json;
    private static Formatter xml;
    private static Formatter html;

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    private static void initFormatter(Format format) {
        if (format == Format.XML) {
            if (xml == null) {
                xml = new XmlFormatter();
            }
        } else if (format == Format.HTML) {
            if (html == null) {
                html = new HtmlFormatter();
            }
        } else if (format == Format.JSON) {
            if (json == null) {
                json = new JsonFormatter();
            }
        } else {
            if (classic == null) {
                classic = new ClassicFormatter();
            }
        }
    }

    public Formatter getFormatter(Format format) {
        initFormatter(format);
        if (format == Format.CLASSIC) {
            return classic;
        } else if (format == Format.JSON) {
            return json;
        } else if (format == Format.HTML) {
            return html;
        } else if (format == Format.XML) {
            return xml;
        }
        return classic;
    }
}
