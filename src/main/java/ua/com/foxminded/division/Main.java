package ua.com.foxminded.division;

import ua.com.foxminded.division.text.*;
import ua.com.foxminded.division.math.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import ua.com.foxminded.division.exception.*;
import ua.com.foxminded.division.injection.*;

//не забыть зафигачить тесты
//read me с картинками
public class Main {
    private static int dividend;
    private static int divisor;
    public static void main(String[] args) {
        System.out.println("Hello, welcome to application \"integer division\"!");

        try {
            proccessDivision(args);

        } catch (DivisionFileNotSetException e) {
            System.err.printf("The file associated with the output is not specified");
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.err.println("I/O exception has occurred");
        } catch (DivisionNoSuchOptionExeption e) {
            System.err.printf("Option \"" + e.getMessage() + "\" is unavailable");
        } catch (DivisionInnerProccessingException e) {
            System.err.println(e.getMessage());
        } catch (DivisionInputException e) {
            System.err.println(e.getMessage());
            System.err.println("Set up arguments and try again");
        }
    }

    private static void proccessDivision(String[] args) throws DivisionFileNotSetException, FileNotFoundException,
            IOException, DivisionNoSuchOptionExeption, DivisionInnerProccessingException, DivisionInputException {
        Formatter formatter = proccessArgs(args);

        System.out.printf("Output format is: %s. Yours dividend is %d, divisor is %d!\n", formatter, dividend, divisor);

        Divider divider = new Divider();
        Result result = divider.divide(dividend, divisor);
        String output = formatter.format(result);

        writeDivisionResult(formatter, output);
    }

    private static Formatter proccessArgs(String[] args)
            throws DivisionNoSuchOptionExeption, DivisionInputException {     
        
        if (args.length < 2) 
            throw new DivisionInputException("Application expects two integer arguments!");
        
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
    
    private static Formatter checkInput(String[] args) {
        Context context = Context.getInstance();
        Formatter formatter;
        
        if (args.length > 2) 
            formatter = chooseFormatter(args[2], context);
        else
            formatter = context.getFormatter(Format.CLASSIC);

        if (args.length > 4)
            formatter.setFileName(args[4]);
        else
            formatter.setFileName("");       
        return formatter;
    }

    private static Formatter chooseFormatter(String option, Context context) throws DivisionNoSuchOptionExeption {
        if (option.equals("-j"))
            return context.getFormatter(Format.JSON);

        else if (option.equals("-x"))

            return context.getFormatter(Format.XML);
        else if (option.equals("-h"))

            return context.getFormatter(Format.HTML);
        else
            throw new DivisionNoSuchOptionExeption(option);
    }

    private static void writeDivisionResult(Formatter formatter, String output)
            throws FileNotFoundException, IOException {
        OutputStream outputStream = formatter.getOutputStream();
        outputStream.write(output.getBytes());

    }
}
