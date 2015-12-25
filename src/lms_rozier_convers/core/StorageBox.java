package lms_rozier_convers.core;

import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Library;
import lms_rozier_convers.core.library.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * 24/11/2015.
 * The storage Box of each library, of unlimited capacity
 */
public class StorageBox {

    List<LibraryItem> items = new ArrayList<>();
    private Library library;

    public StorageBox(Library library) {
        this.library = library;
    }


    public List<LibraryItem> getItems() {
        return items;
    }

    public void setItems(List<LibraryItem> items) {
        this.items = items;
    }

    public void addItem(LibraryItem item){
        this.items.add(item);
        item.setLocation(new Location(this));
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
