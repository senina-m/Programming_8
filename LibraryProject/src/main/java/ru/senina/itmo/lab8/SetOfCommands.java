package ru.senina.itmo.lab8;

import java.util.Map;

public class SetOfCommands extends ru.senina.itmo.lab8.CommandResponse {
    private Map<String, String[]> commandsWithArgs;

    public SetOfCommands(Map<String, String[]> commandsWithArgs) {
        this.commandsWithArgs = commandsWithArgs;
    }

    public SetOfCommands() {
    }

    public Map<String, String[]> getCommandsWithArgs() {
        return commandsWithArgs;
    }

    public void setCommandsWithArgs(Map<String, String[]> commandsWithArgs) {
        this.commandsWithArgs = commandsWithArgs;
    }
}
