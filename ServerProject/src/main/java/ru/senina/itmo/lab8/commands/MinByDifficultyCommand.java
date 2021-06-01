package ru.senina.itmo.lab8.commands;

import ru.senina.itmo.lab8.CollectionKeeper;
import ru.senina.itmo.lab8.CollectionParser;
import ru.senina.itmo.lab8.CommandResponse;
import ru.senina.itmo.lab8.Status;
import ru.senina.itmo.lab8.labwork.LabWork;
import ru.senina.itmo.lab8.parser.ParsingException;

import java.util.Objects;

/**
 * Command class to find the minimum difficult subject in the collection
 */
@CommandAnnotation(name = "min_by_difficulty", collectionKeeper = true, parser = true)
public class MinByDifficultyCommand extends CommandWithoutArgs {
    private CollectionKeeper collectionKeeper;
    private CollectionParser parser;

    @Override
    public void setParser(CollectionParser parser) {
        this.parser = parser;
    }

    public MinByDifficultyCommand() {
        super("min_by_difficulty", "remove any object from the collection with the minimum difficulty value");
    }

    public void setCollectionKeeper(CollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected CommandResponse doRun() {
        try {
            LabWork element = collectionKeeper.minByDifficulty();
            if(Objects.isNull(element)){
                return new CommandResponse(Status.PROBLEM_PROCESSED, getName(),"Collection is empty. There is no minimal element!");
            }else {
                return new CommandResponse(Status.OK, getName(), "The less difficult subject is: \n" + parser.fromElementToString(element));
            }

        } catch ( ParsingException e){
            return new CommandResponse(Status.PARSER_EXCEPTION, getName(), "Minimal element with such description was incorrect.");
        }
    }
}
