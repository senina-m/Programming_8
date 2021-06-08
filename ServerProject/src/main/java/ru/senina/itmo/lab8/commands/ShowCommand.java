package ru.senina.itmo.lab8.commands;

import ru.senina.itmo.lab8.*;
import ru.senina.itmo.lab8.parser.ParsingException;
import ru.senina.itmo.lab8.parser.LabWorkListParser;

/**
 * Command shows all collection elements
 */
@CommandAnnotation(name = "show", collectionKeeper = true, parser = true)
public class ShowCommand extends CommandWithoutArgs{

    private CollectionKeeper collectionKeeper;
    private LabWorkListParser parser;

    public ShowCommand() {
        super("show", "print to standard output all elements of the collection in string representation");
    }

    public void setCollectionKeeper(CollectionKeeper collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    public void setParser(LabWorkListParser parser) {
        this.parser = parser;
    }

    @Override
    protected CommandResponse doRun(){
        try {
            if(collectionKeeper.getAmountOfElements()!= 0) {
                return new CommandResponse(Status.OK, getName(), parser.fromCollectionToStringElements(new LabWorkList(collectionKeeper.getList())));
            } else {
                return new CommandResponse(Status.PROBLEM_PROCESSED, getName(), getResourceBundle().getString("noElementsInCollection") + ".");
            }
        }
        catch (ParsingException e){
            return new CommandResponse(Status.PARSER_EXCEPTION, getName(), getResourceBundle().getString("parsingWasFailed") + e.getMessage());
        }
    }
}
