package ua.com.foxminded.division;

import ua.com.foxminded.division.text.*;
import ua.com.foxminded.division.math.*;
import ua.com.foxminded.division.injection.*;

//не забыть завигачить исключения
//не забыть зафигачить тесты
//read me с картинками
//батник и uber file
public class Main {
    public static void main(String[] args) {
        
        if (args.length < 2) {
            System.out.println("Application expects two integer arguments!\n" + "Set up arguments and try again");
            System.exit(1);
        } 
        
        Formatter formatter;
        Context context = Context.getInstance();
       
        if (args.length > 2) {
            formatter = chooseFormatter(args[2], context);
        } else
            formatter = context.getFormatter(Format.CLASSIC);            
            
        int dividend = Integer.parseInt(args[0]);
        int divisor = Integer.parseInt(args[1]);        

        System.out.printf(
                "Hello, welcome to application \"integer division\"!\n"
                        + "Output format is: %s. Yours dividend is %d, divisor is %d!\n",
                formatter, dividend, divisor);
        
        Divider divider = new Divider();
        Result result = divider.divide(dividend, divisor);       
        
        String output = formatter.format(result);
        System.out.printf(output);
    }
    private static Formatter chooseFormatter(String option, Context context) {
        if(option.equals("-j"))
            return context.getFormatter(Format.JACSON);
        else
            throw new RuntimeException("unidentified option: "+option);        
    }
}
