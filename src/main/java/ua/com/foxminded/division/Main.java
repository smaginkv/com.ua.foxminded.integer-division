package ua.com.foxminded.division;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import ua.com.foxminded.division.exception.DivisionFileNotSetException;
import ua.com.foxminded.division.exception.DivisionInnerProccessingException;
import ua.com.foxminded.division.exception.DivisionInputException;
import ua.com.foxminded.division.exception.DivisionNoSuchOptionExeption;
import ua.com.foxminded.division.math.Divider;
import ua.com.foxminded.division.math.Result;
import ua.com.foxminded.division.text.Formatter;

public class Main {
    public static void main(String ... args) {
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

    //package for the testing access
    static void proccessDivision(String... args) throws DivisionFileNotSetException, FileNotFoundException,
            IOException, DivisionNoSuchOptionExeption, DivisionInnerProccessingException, DivisionInputException {
        
        Formatter formatter = InputProcessing.getFormatter(args);
        int dividend = InputProcessing.getDividend();
        int divisor = InputProcessing.getDivisor();

        System.out.printf("Output format is: %s. Yours dividend is %d, divisor is %d!\n", formatter, dividend, divisor);

        Divider divider = new Divider();
        Result result = divider.divide(dividend, divisor);
        String output = formatter.format(result);

        writeDivisionResult(formatter, output);
    }
    private static void writeDivisionResult(Formatter formatter, String output)
            throws FileNotFoundException, IOException {

        try (OutputStream outputStream = formatter.getOutputStream()) {
            outputStream.write(output.getBytes());
        }
        //Open created file
        String outputFileName = formatter.getFileName();
        File outputFile = new File(outputFileName);
        if( outputFile.exists() && outputFile.isFile()) {
            Desktop.getDesktop().open(outputFile); 
        }
    }
}