package lms_rozier_convers.core.library;

import lms_rozier_convers.core.StorageBox;

/**
 * 24/11/2015.
 * Location of an item in a given Library
 */
public class Location {
    private Room room;
    private Bookcase bookcase;
    private Shelf shelf;
    private StorageBox storageBox;


    /**
     * CONSTRUCTOR
     * @param room
     * @param bookcase
     * @param shelf
     */
    public Location(Room room, Bookcase bookcase, Shelf shelf) {
        this.room = room;
        this.bookcase = bookcase;
        this.shelf = shelf;
        this.storageBox = null;
    }

    /**
     * CONSTRUCTOR
     * @param storageBox
     */
    public Location(StorageBox storageBox)
    {
        this.storageBox = storageBox;
        this.room = null;
        this.bookcase = null;
        this.shelf = null;
    }

    @Override
    public String toString() {
        String descr = "The item is at the current Location :\n";
        if(storageBox==null) {
            descr += "Room : " + this.room.getName() + "\n";
            descr += "Bookcase : " + this.bookcase.getName() + "\n";
            descr += "Shelf : " + this.shelf.getName() + "\n";
        }
        else descr += "Storage Box : " + storageBox;

        return descr;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Bookcase getBookcase() {
        return bookcase;
    }

    public void setBookcase(Bookcase bookcase) {
        this.bookcase = bookcase;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public StorageBox getStorageBox() {
        return storageBox;
    }

    public void setStorageBox(StorageBox storageBox) {
        this.storageBox = storageBox;
    }
}
