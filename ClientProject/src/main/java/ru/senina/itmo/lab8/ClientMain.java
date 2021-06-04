package ru.senina.itmo.lab8;

import java.util.Optional;


/**
 * @author Senina Mariya
 * Main class of programm to start app.
 */
public class ClientMain{
    public final static String HOST = "localhost";
    public static int PORT;
    public final static int ATTEMPTS_TO_CONNECT = 2;
    public final static int DELAY_TO_CONNECT = 2;
    public static String TOKEN;

    public static void main(String[] args) {
        try {
            String path = Optional.ofNullable(System.getenv("SENINA")).orElseThrow(
                    () -> new InvalidArgumentsException("\"SENINA\" variable is not set in the environment! \n Set file path to this variable! The program can't work without it!"));
            PORT = Integer.parseInt(args[0]);
            GraphicsMain.main();
//            ClientKeeper clientKeeper = new ClientKeeper(path);
//            clientKeeper.start(Integer.parseInt(args[0]));
        } catch (InvalidArgumentsException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("You have entered incorrect value of server port, it has to be integer! \n Try to write it again in arguments line!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No server port given to start!!! Set server port in arguments line!");
            System.exit(0);
        }
    }
}
