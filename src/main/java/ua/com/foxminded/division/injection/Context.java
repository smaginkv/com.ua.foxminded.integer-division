package ua.com.foxminded.division.injection;
import ua.com.foxminded.division.text.*;

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
        if (format == Format.CLASSIC)
            if (classic == null)
                classic = new ClassicFormatter();
            else
                ;
        else if (format == Format.XML)
            if (xml == null)
                xml = new XmlFormatter();
            else
                ;
        else if (format == Format.HTML)
            if (html == null)
                html = new HtmlFormatter();
            else
                ;
        else if (format == Format.JSON)
            if (json == null)
                json = new JsonFormatter();

    }

    public Formatter getFormatter(Format format) {
        initFormatter(format);
        if (format == Format.CLASSIC)
            return classic;
        else if (format == Format.JSON)
            return json;
        else if (format == Format.HTML)
            return html;
        else if (format == Format.XML)
            return xml;
        return classic;
    }
}
