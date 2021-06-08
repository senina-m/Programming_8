package ru.senina.itmo.lab8.commands;

import ru.senina.itmo.lab8.CollectionKeeper;
import ru.senina.itmo.lab8.parser.LabWorkListParser;
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
    private LabWorkListParser parser;

    @Override
    public void setParser(LabWorkListParser parser) {
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
                return new CommandResponse(Status.PROBLEM_PROCESSED, getName(),getResourceBundle().getString("collectionEmptyNoMinimalElement"));
            }else {
                return new CommandResponse(Status.OK, getName(), getResourceBundle().getString("lessDifficult")+": \n" + parser.fromElementToString(element));
            }

        } catch ( ParsingException e){
            return new CommandResponse(Status.PARSER_EXCEPTION, getName(), getResourceBundle().getString("elementIncorrect"));
        }
    }
}
