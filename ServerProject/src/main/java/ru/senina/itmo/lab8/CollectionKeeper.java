package ru.senina.itmo.lab8;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.senina.itmo.lab8.labwork.LabWork;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public String updateID(long id, LabWork element, String token) {
        try {
            DBManager.updateById(element, id, token);
            return "Element with id: " + id + " was successfully updated.";
        } catch (IllegalArgumentException e) {
            return "There is no element with id: " + id + " in collection.";
        }
    }

    public String add(LabWork element, String token) {
        DBManager.addElement(element, token);
        return "Element with id: " + element.getId() + " was successfully added.";
    }

    public String removeById(long id, String token) {
        try {
            DBManager.removeById(id, token);
            return "Element with id: " + id + " was successfully removed.";
        } catch (NoSuchElementException e) {
            return "There is no element with id: " + id + " in collection.";
        } catch (UserPermissionsException e) {
            return "Current user is not permitted to remove the element with id: " + id + " from collection.";
        }
    }

    public String clear(String token) {
        DBManager.clear(token);
        return "The collection was successfully cleared.";
    }

    public String removeAt(int index, String token) {
        try {
            DBManager.removeAtIndex(index, token);
            return "Element with index " + index + " was successfully removed.";
        } catch (NoSuchElementException e) {
            return "There is no element with index: " + index + " in collection.";
        } catch (UserPermissionsException e) {
            return "Current user is not permitted to remove the element with id: " + index + " from collection.";
        }
    }

    public String removeGreater(LabWork element, String token) {
        DBManager.removeGreater(element, token);
        return "All elements greater then entered were successfully removed.";
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
