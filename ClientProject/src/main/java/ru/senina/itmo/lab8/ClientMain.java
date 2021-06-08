package ru.senina.itmo.lab8;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Senina Mariya
 * Main class of programm to start app.
 */
public class ClientMain{
    private static Locale LOCALE = Locale.getDefault();
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("text", LOCALE);
    public final static String HOST = "localhost";
    public static int PORT;
    public final static int ATTEMPTS_TO_CONNECT = 2;
    public final static int DELAY_TO_CONNECT = 2;
    public static String TOKEN;
    public static final int RECURSION_LEVEL = 10;

    public static void main(String[] args) {
        try {
            PORT = Integer.parseInt(args[0]);
            GraphicsMain.main();
        } catch (NumberFormatException e) {
            System.out.println("You have entered incorrect value of server port, it has to be integer! \n Try to write it again in arguments line!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No server port given to start!!! Set server port in arguments line!");
            System.exit(0);
        }
    }

    public static Locale getLOCALE() {
        return LOCALE;
    }

    public static void setLOCALE(Locale LOCALE) {
        ClientMain.LOCALE = LOCALE;
        ClientMain.resourceBundle = ResourceBundle.getBundle("text", LOCALE);
    }

    public static ResourceBundle getRB() {
        return resourceBundle;
    }
}
