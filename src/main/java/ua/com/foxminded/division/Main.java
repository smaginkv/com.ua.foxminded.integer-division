package ua.com.foxminded.division;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.printf( /* сообщение с объяснением что не так и примером правильного запуска */ );
            System.exit(1);
        }
        dividend = Integer.parseInt(args[0]);
        divisor = Integer.parseInt(args[1]);
        Divider divider = new Divider();
        Result result = divider.divide(dividend, divisor);
        Formatter formatter = new ClassicFormatter();
        String output = formatter.format(result);
        System.out.printf(output);
    }

}
