package lms_rozier_convers.core.library;

import java.util.ArrayList;
import java.util.List;

/**
 * 24/11/2015.
 * A bookcase, with shelves
 */
public class Bookcase {
    private Room room;
    private List<Shelf> shelves = new ArrayList<>();
    private String name = "";

    /**
     * CONSTRUCTOR
     * @param name
     */
    public Bookcase(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(List<Shelf> shelves) {
        this.shelves = shelves;
        for (Shelf shelf : shelves) {
            shelf.setBookcase(this);
        }
    }
}
