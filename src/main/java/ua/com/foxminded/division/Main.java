package ua.com.foxminded.division;

import ua.com.foxminded.division.text.*;
import ua.com.foxminded.division.math.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import ua.com.foxminded.division.injection.*;

//не забыть завигачить исключения
//не забыть зафигачить тесты
//read me с картинками
//батник и uber file
public class Main {
    public static void main(String[] args) {
        Formatter formatter = proccessArgs(args);        
            
        int dividend = Integer.parseInt(args[0]);
        int divisor = Integer.parseInt(args[1]);        

        System.out.printf(
                "Hello, welcome to application \"integer division\"!\n"
                        + "Output format is: %s. Yours dividend is %d, divisor is %d!\n",
                formatter, dividend, divisor);
        
        Divider divider = new Divider();
        Result result = divider.divide(dividend, divisor);       
        
        String output = formatter.format(result);
        writeDivisionResult(formatter, output);
    }
    
    private static Formatter proccessArgs(String[] args) {
        Context context = Context.getInstance();
        Formatter formatter;
        if (args.length < 2) {
            System.out.println("Application expects two integer arguments!\n" + "Set up arguments and try again");
            System.exit(1);
        }
        if (args.length > 2) {
            formatter = chooseFormatter(args[2], context);
        } else
            formatter = context.getFormatter(Format.CLASSIC);
        if(args.length > 4)
            formatter.setFileName(args[4]);
        
        return formatter;

    }

    private static Formatter chooseFormatter(String option, Context context) {
        if (option.equals("-j"))
            return context.getFormatter(Format.JSON);

        else if (option.equals("-x"))

            return context.getFormatter(Format.XML);
        else if (option.equals("-h"))

            return context.getFormatter(Format.HTML);
        else
            throw new RuntimeException("unidentified option: " + option);    
    }
    
    private static void writeDivisionResult(Formatter formatter, String output) {
        try {            
            OutputStream outputStream = formatter.getOutputStream();
            outputStream.write(output.getBytes());
        } catch (FileNotFoundException e) {
            System.err.println("File not found "+formatter.getFileName());
        }catch (IOException e) {
            System.err.println("I/O exception has occurred");
        }
        
        
    }
}
