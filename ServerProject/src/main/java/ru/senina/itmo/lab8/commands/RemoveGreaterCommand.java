package ru.senina.itmo.lab8.commands;

import ru.senina.itmo.lab8.CollectionKeeper;
import ru.senina.itmo.lab8.CommandArgs;
import ru.senina.itmo.lab8.CommandResponse;
import ru.senina.itmo.lab8.Status;
import ru.senina.itmo.lab8.labwork.LabWork;

/**
 * Command removes all elements greater than given
 */
@CommandAnnotation(name = "remove_greater", element = true, collectionKeeper = true)
public class RemoveGreaterCommand extends CommandWithoutArgs{
    private CollectionKeeper collectionKeeper;
    private LabWork element;

    public RemoveGreaterCommand() {
        super("remove_greater {element}", "remove all items from the collection that are greater than the specified one");
    }

    public void setCollectionKeeper(CollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected CommandResponse doRun() {
        return new CommandResponse(Status.OK, getName(), collectionKeeper.removeGreater(element, getToken()));
    }

    @Override
    public void setArgs(CommandArgs args) {
        super.setArgs(args);
        this.element = args.getElement();
    }
}
