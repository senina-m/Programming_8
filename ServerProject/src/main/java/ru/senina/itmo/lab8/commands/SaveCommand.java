package ru.senina.itmo.lab8.commands;

import ru.senina.itmo.lab8.*;
import ru.senina.itmo.lab8.parser.LabWorkListParser;

/**
 * Command saves collection to file
 */
//todo: change it to update command
@CommandAnnotation(name = "save", collectionKeeper = true, parser = true, filename = true, isVisibleInHelp = false)
public class SaveCommand extends CommandWithoutArgs {
    private CollectionKeeper collectionKeeper;
    private LabWorkListParser parser;
    private final String filename;

    public SaveCommand(String filename) {
        super("save", "save collection to file");
        this.filename = filename;
    }

    public void setCollectionKeeper(CollectionKeeper collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    public void setParser(LabWorkListParser parser) {
        this.parser = parser;
    }

    @Override
    protected CommandResponse doRun() {
//        Parser.writeStringToFile(filename, );
        return new CommandResponse(Status.OK, getName(), parser.fromObjectToString(new LabWorkList(collectionKeeper.getList())));
    }
}
