package lms_rozier_convers.core.library;

import java.util.ArrayList;
import java.util.List;

/**
 * 19/11/2015.
 * A room of the library, with bookcases
 */
public class Room {
    private String name = "";
    private List<Bookcase> bookcases = new ArrayList();
    private Library library;

    /**
     * CONSTRUCTOR
     * @param name
     */
    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public List<Bookcase> getBookcases() {
        return bookcases;

    }

    public void setBookcases(List<Bookcase> bookcases) {
        this.bookcases = bookcases;
        for (Bookcase bookcase : bookcases) {
            bookcase.setRoom(this);
        }
    }
}
