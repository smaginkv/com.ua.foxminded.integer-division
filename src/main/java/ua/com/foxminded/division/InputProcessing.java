package ua.com.foxminded.division;

import lombok.Getter;
import ua.com.foxminded.division.exception.DivisionInputException;
import ua.com.foxminded.division.exception.DivisionNoSuchOptionExeption;
import ua.com.foxminded.division.text.Formatter;

public class InputProcessing {
    @Getter
    private static int dividend;
    @Getter
    private static int divisor;
    public static Formatter getFormatter(String... args) throws DivisionNoSuchOptionExeption, DivisionInputException {
        if (args.length < 2) {
            throw new DivisionInputException("Application expects two integer arguments!");
        }
        dividend = parseInt(args[0]);
        divisor = parseInt(args[1]);
        return checkInput(args);        
    }
    
    private static int parseInt(String stringArgs) throws DivisionInputException {
        try {
            return Integer.parseInt(stringArgs);
        } catch (NumberFormatException e) {
            throw new DivisionInputException("Application expects integer arguments!");
        }
    }

    private static Formatter checkInput(String... args) {
        Context context = Context.getInstance();
        Formatter formatter;

        if (args.length > 2) {
            formatter = chooseFormatter(args[2], context);
        } else {
            formatter = context.getFormatter(Format.CLASSIC);
        }

        if (args.length > 4) {
            formatter.setFileName(args[4]);
        } else {
            formatter.setFileName("");
        }
        return formatter;
    }

    private static Formatter chooseFormatter(String option, Context context) throws DivisionNoSuchOptionExeption {
        if (option.equals("-j")) {
            return context.getFormatter(Format.JSON);
        }

        else if (option.equals("-x")) {
            return context.getFormatter(Format.XML);
        }

        else if (option.equals("-h")) {
            return context.getFormatter(Format.HTML);
        } else {
            throw new DivisionNoSuchOptionExeption(option);
        }
    }
}
