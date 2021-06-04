//package ru.senina.itmo.lab8;
//
//import org.apache.commons.codec.digest.DigestUtils;
//
//import java.io.Console;
//
//public class LogInKeeper {
//    public CommandArgs authorizeUser() {
//        if (debug) {
//            CommandArgs testCommandRegistration = new CommandArgs();
//            testCommandRegistration.setArgs(new String[]{"authorize", "nan", encrypt("nan")});
//            testCommandRegistration.setCommandName("authorize");
//            return testCommandRegistration;
//        } else {
//            System.out.print("Do you have an account or you to register? Type \"sign up\" or \"log in\".\n >");
//            while (true) {
//                switch (in.nextLine().trim()) {
//                    case ("sign up"):
//                        try {
//                            return signUp();
//                        }catch (ChangeModException e){
//                            System.out.print("Entered passwords aren't equal! Try to authorize again...\nDo you have an account or you to register? Type \"sign up\" or \"log in\".\n >");
//                            continue;
//                        }
//                    case ("log in"):
//                        return lonIn();
//                    default:
//                        System.out.print("Incorrect input! Try to type again \"sign up\" or \"log in\".\n >");
//                }
//            }
//        }
//    }
//
//    /**
//     * I have to put login first and then password
//     */
//    public CommandArgs signUp() throws ChangeModException {
//        CommandArgs signUpCommand = new CommandArgs();
//        signUpCommand.setCommandName("register");
//
//        //Get login
//        System.out.print("Please enter your login\n>");
//        String login = in.nextLine().trim();
//        while (login.equals("")) {
//            System.out.println("You entered empty login! Try again\n>");
//            login = in.nextLine().trim();
//        }
//        signUpCommand.setLogin(login);
//
//        //Get password
//        String password;
//
//        if (debug) {
//            password = getPasswordDebug();
//        } else {
//            Console console = System.console();
//            password = new String(console.readPassword("Please enter your password. It's length has to be more than 8 symbols\n>")).trim();
//            while (password.length() < 9) {
//                password = new String(console.readPassword("You entered too short password! Please try again\n>")).trim();
//            }
//            if(!new String(console.readPassword("Please repeat your password\n>")).trim().equals(password)){
//                throw new ChangeModException();
//            }
//        }
//
//        signUpCommand.setArgs(new String[]{signUpCommand.getCommandName(), signUpCommand.getLogin(), encrypt(password)});
//        return signUpCommand;
//    }
//
//    public CommandArgs lonIn() {
//        CommandArgs logInCommand = new CommandArgs();
//        logInCommand.setCommandName("authorize");
//
//        //get login
//        System.out.print("Please enter your login\n>");
//        String login = in.nextLine().trim();
//        logInCommand.setLogin(login);
//
//        //get password
//        Console console = System.console();
//        String password = new String(console.readPassword("Please enter your password\n>")).trim();
//
//        logInCommand.setArgs(new String[]{logInCommand.getCommandName(), logInCommand.getLogin(), encrypt(password)});
//        return logInCommand;
//    }
//
//    private String getPasswordDebug() {
//        System.out.print("Please enter your password\n>");
//        String password = in.nextLine().trim();
//        while (password.equals("")) {
//            System.out.print("You entered empty password! Please try again\n>");
//            password = in.nextLine().trim();
//        }
//        return password;
//    }
//
//    private String encrypt(String password) {
//        String salt = "klj;kjgsdkj";
//        return DigestUtils.md5Hex(salt + password);
//    }
//
//}
