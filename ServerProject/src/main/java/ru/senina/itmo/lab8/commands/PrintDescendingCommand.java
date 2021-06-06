package ru.senina.itmo.lab8.commands;

import ru.senina.itmo.lab8.CollectionKeeper;
import ru.senina.itmo.lab8.parser.LabWorkListParser;
import ru.senina.itmo.lab8.CommandResponse;
import ru.senina.itmo.lab8.Status;
import ru.senina.itmo.lab8.labwork.LabWork;
import ru.senina.itmo.lab8.parser.ParsingException;

import java.util.List;

/**
 * Command to print elements of collection in inverted sorted order
 */
@CommandAnnotation(name = "print_descending", collectionKeeper = true, parser = true)
public class PrintDescendingCommand extends CommandWithoutArgs {
    private CollectionKeeper collectionKeeper;
    private LabWorkListParser parser;

    public PrintDescendingCommand() {
        super("print_descending", "display the elements of the collection in descending order");
    }
    public void setCollectionKeeper(CollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    public void setParser( LabWorkListParser parser){
        this.parser = parser;
    }

    @Override
    protected CommandResponse doRun() {
        try {
            List<LabWork> list = collectionKeeper.getSortedList();
            if(list.size() != 0){
                StringBuilder result = new StringBuilder();
                result.append("You entered a command print_descending:\n");
                for(int i = list.size() - 1; i >= 0; i--){
                    result.append("Element ").append(i + 1).append(": \n").append(parser.fromElementToString(list.get(i))).append("\n");
                }
                return new CommandResponse(Status.OK, getName(), result.toString());
            }else{
                return new CommandResponse(Status.PROBLEM_PROCESSED, getName(), "There is now elements in collection now.");
            }
        }catch (ParsingException e){
            return new CommandResponse(Status.PARSER_EXCEPTION, getName(), "Parsing in print_descending was failed");
        }
    }
}
