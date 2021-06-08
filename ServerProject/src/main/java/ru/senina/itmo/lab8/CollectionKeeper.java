package ru.senina.itmo.lab8;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.senina.itmo.lab8.labwork.LabWork;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class to keep collection's elements
 */
public class CollectionKeeper {
    @JsonCreator
    public CollectionKeeper() {
    }

    @JsonIgnore
    private final Date time = new Date();

    public List<LabWork> getList() {
        return DBManager.readAll();
    }


    @JsonIgnore
    public long getAmountOfElements() {
        return Optional.of(DBManager.countNumOfElements()).orElse((long) 0);
    }

    public String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        return dateFormat.format(time);
    }

    public String updateID(long id, LabWork element, String token, ResourceBundle rb) {
        try {
            DBManager.updateById(element, id, token);
            return rb.getString("elementWithId") + ": " + id + " " + rb.getString("wasSuccessfullyUpdated") + ".";
        } catch (IllegalArgumentException e) {
            return rb.getString("thereIsNoElementWithId") + ": " + id + " " + rb.getString("inCollection") + ".";
        }
    }

    public String add(LabWork element, String token, ResourceBundle rb) {
        DBManager.addElement(element, token);
        return rb.getString("elementWithId") + ": " + element.getId() + " " + rb.getString("wasSuccessfullyAdded") + ".";
    }

    public String removeById(long id, String token, ResourceBundle rb) {
        try {
            DBManager.removeById(id, token);
            return rb.getString("elementWithId") + ": " + id + " " + rb.getString("wasSuccessfullyRemoved") + ".";
        } catch (NoSuchElementException e) {
            return rb.getString("thereIsNoElementWithId") + ": " + id + " " + rb.getString("inCollection") + ".";
        } catch (UserPermissionsException e) {
            return rb.getString("currentUserIsNotPermittedToRemoveElementWithId") + ": " + id + " " + rb.getString("fromCollection") + ".";
        }
    }

    public String clear(String token, ResourceBundle resourceBundle) {
        DBManager.clear(token);
        return resourceBundle.getString("collectionWasSuccessfullyCleared");
    }

    public String removeAt(int index, String token, ResourceBundle rb) {
        try {
            DBManager.removeAtIndex(index, token);
            return rb.getString("elementWithIndex") + " " + index + " " + rb.getString("wasSuccessfullyRemoved") + ".";
        } catch (NoSuchElementException e) {
            return rb.getString("thereIsNoElementWithIndex") + ": " + index + " " + rb.getString("inCollection") + ".";
        } catch (UserPermissionsException e) {
            return rb.getString("currentUserIsNotPermittedToRemoveElementWithId") + ": " + index + " " + rb.getString("fromCollection") + ".";
        }
    }

    public String removeGreater(LabWork element, String token, ResourceBundle rb) {
        DBManager.removeGreater(element, token);
        return rb.getString("allElementsGreaterThenEnteredWereSuccessfullyRemoved") + ".";
    }

    //element could be null
    public LabWork minByDifficulty() throws IndexOutOfBoundsException {
        return DBManager.minByDifficulty();
    }

    public List<LabWork> filterByDescription(String description) {
        return DBManager.filterByDescription(description);
    }


    @JsonIgnore
    public List<LabWork> getSortedList() {
        return DBManager.getSortedList();
    }
}
