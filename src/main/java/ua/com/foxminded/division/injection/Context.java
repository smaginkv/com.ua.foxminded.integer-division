package ua.com.foxminded.division.injection;

import ua.com.foxminded.division.text.*;

public class Context {
    private static Context instance;
    private static Formatter classic;
    private static Formatter jackson;

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
        else if (format == Format.JACSON)
            if (jackson == null)
                jackson = new JSON();

    }

    public Formatter getFormatter(Format format) {
        initFormatter(format);
        if(format == Format.CLASSIC)
            return classic;
        else if(format == Format.JACSON)
            return jackson; 
        return classic;
    }
}
