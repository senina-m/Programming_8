//package ru.senina.itmo.lab8;
//
//import org.apache.commons.codec.digest.DigestUtils;
//import ru.senina.itmo.lab8.labwork.*;
//import ru.senina.itmo.lab8.parser.*;
//
//import java.io.Console;
//import java.io.File;
//import java.io.IOException;
//import java.util.*;
//
//
//public class TerminalKeeper {
//    private boolean systemInIsClosed = false;
//    private Scanner in = new Scanner(System.in);
//    private boolean script = false;
//    private Map<String, String[]> commands;
//    private final String filename;
//    private final boolean debug = false;
//
//
//    public TerminalKeeper(String filename) {
//        this.filename = filename;
//    }
//
//    public CommandArgs readNextCommand() {
//        while (!systemInIsClosed) {
//            try {
//                System.out.print("> ");
//                String[] line = cleanLine(in.nextLine().split("[ \t\f]+"));
//                if (line.length > 0) {
//                    if (commands.containsKey(line[0])) {
//                        CommandArgs newCommand = new CommandArgs(line[0], line);
//                        String[] arguments = commands.get(line[0]);
//                        for (String argument : arguments) {
//                            if ("element".equals(argument)) {
//                                LabWork element = Optional.ofNullable(readElement()).orElseThrow(() -> new InvalidArgumentsException("This command will be skipped."));
//                                newCommand.setElement(element);
//                            }
//                        }
//                        return newCommand;
//                    } else {
//                        System.out.println("There is no such command.");
//                    }
//                }
//
//            } catch (InvalidArgumentsException e) {
//                System.out.println(e.getMessage());
//            } catch (NullPointerException e) {
//                System.out.println("You have entered the end of file symbol. Program will be terminate and collection will be saved.");
//                systemInIsClosed = true;
//                return new CommandArgs("save", new String[]{"save"});
//            }
//        }
//        return new CommandArgs("exit", new String[]{"exit"});
//    }
//
//    private String[] cleanLine(String[] line) {
//        ArrayList<String> result = new ArrayList<>();
//        for (String s : line) {
//            if (!s.equals("")) {
//                result.add(s);
//            }
//        }
//        String[] resultStr = new String[result.size()];
//        return result.toArray(resultStr);
//    }
//
//    /**
//     * @return NULLABLE
//     */
//    private LabWork readElement() {
//        if (debug) {
//            return new LabWork("new lab", new Coordinates(60, 0), 2, "description", 1, Difficulty.NORMAL, new Discipline("Proga", 16, 32, 1000));
//        } else {
//            while (true) {
//                try {
//                    LabWork element = new LabWork();
//                    if (!script) {
//                        System.out.println("You run a command, which needs LabWork element to be entered.");
//                    }
//
//                    if (!script) {
//                        System.out.println("Enter element's name.");
//                    }
//                    element.setName(in.nextLine());
//
//                    if (!script) {
//                        System.out.println("Enter coordinates. In first line x <= 74. In second y >= -47.");
//                    }
//                    element.setCoordinates(new Coordinates(Integer.parseInt(in.nextLine()), Long.parseLong(in.nextLine())));
//
//                    if (!script) {
//                        System.out.println("Enter minimal point.");
//                    }
//                    element.setMinimalPoint(Float.parseFloat(in.nextLine()));
//
//                    if (!script) {
//                        System.out.println("Enter element description.");
//                    }
//                    element.setDescription(in.nextLine());
//
//                    if (!script) {
//                        System.out.println("Enter average point.");
//                    }
//                    element.setAveragePoint(Integer.parseInt(in.nextLine()));
//
//                    if (!script) {
//                        System.out.println("Enter one difficulty of following list:");
//                    }
//                    Difficulty[] difficulties = Difficulty.values();
//                    for (Difficulty difficulty : difficulties) {
//                        System.out.print(difficulty.toString() + "; ");
//                    }
//
//                    element.setDifficulty(in.nextLine());
//                    if (!script) {
//                        System.out.println("Enter discipline parametrs:");
//                    }
//                    Discipline discipline = new Discipline();
//                    if (!script) {
//                        System.out.println("Enter discipline name.");
//                    }
//                    discipline.setName(in.nextLine());
//                    if (!script) {
//                        System.out.println("Enter discipline lectureHours.");
//                    }
//                    discipline.setLectureHours(Long.parseLong(in.nextLine()));
//                    if (!script) {
//                        System.out.println("Enter discipline practiceHours.");
//                    }
//                    discipline.setPracticeHours(Integer.parseInt(in.nextLine()));
//                    if (!script) {
//                        System.out.println("Enter discipline selfStudyHours.");
//                    }
//                    discipline.setSelfStudyHours(Integer.parseInt(in.nextLine()));
//                    element.setDiscipline(discipline);
//                    return element;
//                } catch (InvalidArgumentsException | NumberFormatException e) {
//                    if (!script) {
//                        System.out.println("You have entered invalidate value." + e.getMessage() + "\nDo you want to exit from command? (yes/no)");
//                        if (in.nextLine().equals("yes")) {
//                            return null;
//                        } else {
//                            System.out.println("Try again.");
//                        }
//                    } else {
//                        System.out.println("Script had incorrect command with element argument.");
//                        return null;
//                    }
//                }
//            }
//        }
//    }
//
//    public LinkedList<CommandArgs> executeScript(String filename) throws FileAccessException {
//        LinkedList<CommandArgs> commandsQueue = new LinkedList<>();
//        try {
//            in = new Scanner(new File(filename));
//            script = true;
//            while (in.hasNext()) {
//                commandsQueue.add(readNextCommand());
//            }
//        } catch (IOException e) {
//            throw new FileAccessException("File for execution doesn't exist or doesn't hav rights to be read.", filename);
//        }
//        in = new Scanner(System.in);
//        script = false;
//        return commandsQueue;
//    }
//
//    public void printResponse(CommandResponse response) {
//        if (response.getCommandName().equals("exit")) {
//            Parser.writeStringToFile(filename, response.getResponse());
//            System.out.println("Collection was saved to file. Program will exit!");
//        } else {
//            System.out.println(response.getResponse());
//        }
//    }
//
//    public void setCommands(Map<String, String[]> commandsWithArgs) {
//        this.commands = commandsWithArgs;
//    }
//}
