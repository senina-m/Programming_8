package ru.senina.itmo.lab8;

import ru.senina.itmo.lab8.labwork.*;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class TerminalKeeper {
    private Scanner in = new Scanner(System.in);
    private final Map<String, String[]> commands;
    //    private final String filename;
    private final boolean debug = false;


    public TerminalKeeper(Map<String, String[]> commandsWithArgs) {
        this.commands = commandsWithArgs;
    }

    public CommandArgs readNextCommand() throws InvalidArgumentsException{
        while (in.hasNext()) {
            String[] line = cleanLine(in.nextLine().split("[ \t\f]+"));
            if (line.length > 0) {
                if (commands.containsKey(line[0])) {
                    CommandArgs newCommand = new CommandArgs(line[0], line);
                    String[] arguments = commands.get(line[0]);
                    for (String argument : arguments) {
                        if ("element".equals(argument)) {
                            LabWork element = Optional.ofNullable(readElement()).orElseThrow(() -> new InvalidArgumentsException("This command will be skipped."));
                            newCommand.setElement(element);
                        }
                    }
                    return newCommand;
                }
//                 else {
//                    throw new InvalidArgumentsException("There is no such command."); //fixme think if I want to write such log to user
//                }
            }
        }
        throw new InvalidArgumentsException("No commands was found");
    }

    private String[] cleanLine(String[] line) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : line) {
            if (!s.equals("")) {
                result.add(s);
            }
        }
        String[] resultStr = new String[result.size()];
        return result.toArray(resultStr);
    }

    /**
     * @return NULLABLE
     */
    private LabWork readElement() {
        if (debug) {
            return new LabWork("new lab", new Coordinates(60, 0), 2, "description", 1, Difficulty.NORMAL, new Discipline("Proga", 16, 32, 1000));
        } else {
            try {
                LabWork element = new LabWork();
                element.setName(in.nextLine());
                element.setCoordinates(new Coordinates(Integer.parseInt(in.nextLine()), Long.parseLong(in.nextLine())));
                element.setMinimalPoint(Float.parseFloat(in.nextLine()));
                element.setDescription(in.nextLine());
                element.setAveragePoint(Integer.parseInt(in.nextLine()));
                element.setDifficulty(in.nextLine());
                Discipline discipline = new Discipline();
                discipline.setName(in.nextLine());
                discipline.setLectureHours(Long.parseLong(in.nextLine()));
                discipline.setPracticeHours(Integer.parseInt(in.nextLine()));
                discipline.setSelfStudyHours(Integer.parseInt(in.nextLine()));
                element.setDiscipline(discipline);
                return element;
            } catch (InvalidArgumentsException | NumberFormatException e) {
                System.out.println("Script had incorrect command with element argument.");
                return null;
            }
        }
    }

    public LinkedList<CommandArgs> executeScript(String filename) throws FileAccessException {
        LinkedList<CommandArgs> commandsQueue = new LinkedList<>();
        try {
            in = new Scanner(new File(filename));
            while (in.hasNext()) {
                try {
                    commandsQueue.add(readNextCommand());
                }catch (InvalidArgumentsException ignored){} //is ignored because we has to look for new commands till the EOF
            }
        } catch (IOException e) {
            throw new FileAccessException("File for execution doesn't exist or doesn't have rights to be read.", filename);
        }
        in = new Scanner(System.in);
        return commandsQueue;
    }
}
