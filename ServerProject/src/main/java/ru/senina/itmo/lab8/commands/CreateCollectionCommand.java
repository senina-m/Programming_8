package ru.senina.itmo.lab8.commands;

import ru.senina.itmo.lab8.*;
import ru.senina.itmo.lab8.exceptions.InvalidArgumentsException;
import ru.senina.itmo.lab8.labwork.LabWork;
import ru.senina.itmo.lab8.parser.ParsingException;
import ru.senina.itmo.lab8.parser.LabWorkListParser;

import java.util.LinkedList;
import java.util.logging.Level;

@Deprecated //I don't use this command
@CommandAnnotation(name = "create_collection", collectionKeeper = true, parser = true, filename = true, isVisibleInHelp = false)
public class CreateCollectionCommand extends Command {
    private CollectionKeeper collectionKeeper;
    private LabWorkListParser parser;
    private String collectionString;

    public String getCollectionString() {
        return collectionString;
    }

    public void setCollectionString(String filename) {
        this.collectionString = filename;
    }

    public CreateCollectionCommand() {
        super("create_collection", "create collection from elements from given file");
    }

    @Override
    public void setCollectionKeeper(CollectionKeeper collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    public void setParser(LabWorkListParser parser) {
        this.parser = parser;
    }

    @Override
    protected CommandResponse doRun() throws InvalidArgumentsException {
        try {
            LinkedList<LabWork> list = (LinkedList<LabWork>) parser.fromStringToObject(collectionString).getLabWorkList();
            for (LabWork element : list) {
                collectionKeeper.add(element, getToken(), getResourceBundle());
            }
        } catch (ParsingException e) {
            ServerLog.log(Level.WARNING, getResourceBundle().getString("incorrectFileCantInitCollection"));
            return new CommandResponse(Status.PROBLEM_PROCESSED, getName(),
                    getResourceBundle().getString("fileWasIncorrectCollectionWillBeEmpty"));
        }
        if (collectionKeeper.getList() != null) {
            return new CommandResponse(Status.OK, getName(), getResourceBundle().getString("collectionWasSuccessfullyCreated"));
        } else {
            throw new InvalidArgumentsException(getResourceBundle().getString("file") + " " + collectionString + " "
                    + getResourceBundle().getString("wasInvalid") + ".");
        }

    }

    @Override
    public void validateArguments() {
        String[] args = this.getArgs();
        if (args.length == 2) {
            this.collectionString = args[1];
        } else {
            throw new InvalidArgumentsException("Create collection command has the only argument - contents of a collection file.");
        }
    }
}
