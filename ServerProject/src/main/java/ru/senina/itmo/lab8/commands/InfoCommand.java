package ru.senina.itmo.lab8.commands;

import ru.senina.itmo.lab8.CollectionKeeper;
import ru.senina.itmo.lab8.CommandResponse;
import ru.senina.itmo.lab8.Status;

/**
 * Command prints information about the collection (type, initialization date, number of elements, etc.) to the standard output stream
 */
@CommandAnnotation(name = "info", collectionKeeper = true)
public class InfoCommand extends CommandWithoutArgs {
    CollectionKeeper collectionKeeper;

    public InfoCommand() {
        super("info", "print information about the collection (type, initialization date, number of elements, etc.) to the standard output stream");
    }

    public void setCollectionKeeper(CollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected CommandResponse doRun() {
        return new CommandResponse(Status.OK, getName(), "This collection was created: " + collectionKeeper.getTime() + "\n"
                + "Amount of collection's elements: " + collectionKeeper.getAmountOfElements());
    }
}
