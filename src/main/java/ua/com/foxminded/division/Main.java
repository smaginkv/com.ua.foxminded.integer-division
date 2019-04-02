package ua.com.foxminded.division;

import ua.com.foxminded.division.text.*;
import ua.com.foxminded.division.math.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Application expects two integer arguments!" + '\n' +
                    "Set up arguments and try again");
            System.exit(1);
        }
        int dividend = Integer.parseInt(args[0]);
        int divisor = Integer.parseInt(args[1]);
        
        System.out.printf("Hello, welcome to application \"integer division\"!"+'\n'+
                "Yours dividend is %d, divisor is %d!"+'\n', dividend, divisor);
        
        Divider divider = new Divider();
        Result result = divider.divide(dividend, divisor);
        Formatter formatter = new ClassicFormatter();
        String output = formatter.format(result);
        System.out.printf(output);
    }

}
