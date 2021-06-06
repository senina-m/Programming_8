package ru.senina.itmo.lab8.commands;

import ru.senina.itmo.lab8.CollectionKeeper;
import ru.senina.itmo.lab8.CommandResponse;
import ru.senina.itmo.lab8.LabWorkList;
import ru.senina.itmo.lab8.Status;
import ru.senina.itmo.lab8.parser.LabWorkListParser;

@CommandAnnotation(name = "update_collection", isVisibleInHelp = false, parser = true, collectionKeeper = true)
public class UpdateCollectionCommand extends CommandWithoutArgs {
    private CollectionKeeper collectionKeeper;
    private LabWorkListParser parser;

    public UpdateCollectionCommand() {
        super("update_collection", "updatedClientLocalCollection");
    }

    public void setCollectionKeeper(CollectionKeeper collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    public void setParser(LabWorkListParser parser) {
        this.parser = parser;
    }

    @Override
    protected CommandResponse doRun() {
        return new CommandResponse(Status.OK, getName(), parser.fromObjectToString(new LabWorkList(collectionKeeper.getList())));
    }
}
