package lms_rozier_convers.core.library;

import lms_rozier_convers.core.StorageBox;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.member.Member;
import lms_rozier_convers.core.member.MemberObserver;
import lms_rozier_convers.core.tidying.AbstractTidyingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * 24/11/2015.
 * This class represents a library, with its own numbers N, M, and Mprime described in the subject
 */
public class Library {

    private String name = "";
    private AbstractTidyingStrategy tidyingStrategy;
    private List<Member> members =new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private StorageBox storageBox = new StorageBox();
    private int numberToBeFrequent = 3; // The number N
    private int numberOfMonthsToBeFrequent = 3; // The number M
    private int numberOfMonthsToBeStandard = 4; // The number M'
    private int numberOfMaxSimultaneousBorrows = 5;
    private List<LibraryItem> itemsLinkedToTheLibrary = new ArrayList<>(); // Items linked, borrowed or not, lost or not
    //TODO : vérifier qu'un item est toujours rajouté à cette liste quand on en ajoute un

    /**
     * CONSTRUCTOR
     * @param tidyingStrategy
     */
    public Library(AbstractTidyingStrategy tidyingStrategy) {
        this.tidyingStrategy = tidyingStrategy;
        tidyingStrategy.setLibrary(this);
    }

    /**
     * CONSTRUCTOR
     * @param tidyingStrategy
     * @param numberToBeFrequent
     * @param numberOfMonthsToBeFrequent
     * @param numberOfMonthsToBeStandard
     * @param numberOfMaximumBorrows
     */
    public Library(AbstractTidyingStrategy tidyingStrategy, int numberToBeFrequent, int numberOfMonthsToBeFrequent, int numberOfMonthsToBeStandard, int numberOfMaximumBorrows) {

        this.tidyingStrategy = tidyingStrategy;
        this.numberToBeFrequent = numberToBeFrequent;
        this.numberOfMonthsToBeFrequent = numberOfMonthsToBeFrequent;
        this.numberOfMonthsToBeStandard = numberOfMonthsToBeStandard;
        this.numberOfMaxSimultaneousBorrows = numberOfMaximumBorrows;
    }

    /**
     * CONSTRUCTOR
     * @param tidyingStrategy
     * @param numberToBeFrequent
     * @param numberOfMonthsToBeFrequent
     * @param numberOfMonthsToBeStandard
     * @param numberOfMaximumBorrows
     * @param name
     */
    public Library(AbstractTidyingStrategy tidyingStrategy, int numberToBeFrequent, int numberOfMonthsToBeFrequent, int numberOfMonthsToBeStandard, int numberOfMaximumBorrows, String name) {

        this.tidyingStrategy = tidyingStrategy;
        this.numberToBeFrequent = numberToBeFrequent;
        this.numberOfMonthsToBeFrequent = numberOfMonthsToBeFrequent;
        this.numberOfMonthsToBeStandard = numberOfMonthsToBeStandard;
        this.numberOfMaxSimultaneousBorrows = numberOfMaximumBorrows;
        this.name = name;
    }



    /**
     * We also use this method to add the corresponding library to the room.
     * @param rooms
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
        for (Room room : rooms) {
            room.setLibrary(this);
        }
    }

    /**
     * This setter will add the current library to the libraryList of member
     * @param members
     */
    public void setMembers(List<Member> members) {
        this.members = members;
        for (Member m : members) {
            m.setCurrentLibrary(this);
        }
    }

    /**
     * Adds a member to the list of members of the library ; associate the current Library to the library of the member.
     * @param m
     */
    public void addMember(Member m) {
        this.members.add(m);
        m.setCurrentLibrary(this);
    }


    /**
     * Checks if a room is in the library.
     * @param room
     * @return boolean
     */
    public boolean isContained(Room room) {
        for (Room room1 : rooms) {
            if (room1.equals(room)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks a bookcase is in the library
     * @param bookcase
     * @return boolean
     */
    public boolean isContained(Bookcase bookcase) {
        for (Room room : rooms) {

            for (Bookcase bookcase1 : room.getBookcases()) {
                if (bookcase1.equals(bookcase)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Checks if a shelf is in the library
     * @param shelf
     * @return
     */
    public boolean isContained(Shelf shelf) {
        for (Room room : rooms) {
            for (Bookcase bookcase : room.getBookcases()) {
                for (Shelf shelf1 : bookcase.getShelves()) {
                    if (shelf1.equals(shelf)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the item is in the library.
     * @param item
     * @return
     */
    public boolean isContained(LibraryItem item) {
        //Looks first in the storage Box
        for (LibraryItem libraryItem : storageBox.getItems()) {
            if (libraryItem.equals(item)) return true;
        }
        //Looks in the rooms, then in bookcases, then in shelves
        for (Room room : rooms) {
            for (Bookcase bookcase : room.getBookcases()) {
                for (Shelf shelf1 : bookcase.getShelves()) {
                    for (LibraryItem libraryItem : shelf1.getItemsContained()) {
                        if (item.equals(libraryItem)) return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Allows to find a Bookcase according to its name
     * @param name
     * @return
     */
    public Bookcase findBookcaseByName(String name) {
        for (Room room : rooms) {
            for (Bookcase bookcase : room.getBookcases()) {
                if (bookcase.getName().equals(name)) {
                    return bookcase;
                }
            }
        }
        return null;
    }

    /**
     * Allows to find a Room according to its name
     * @param name
     * @return
     */
    public Room findRoomByName(String name) {
        for (Room room : rooms) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Allows to find a Shelf according to its name
     * @param name
     * @return
     */
    public Shelf findShelfByName(String name) {
        for (Room room : rooms) {
            for (Bookcase bookcase : room.getBookcases()) {
                for (Shelf shelf : bookcase.getShelves()) {
                    if (shelf.getName().equals(name)) {
                        return shelf;
                    }
                }
            }
        }
        return null;
    }

    public void addRoom(Room room) {
        rooms.add(room);
        room.setLibrary(this);
    }

    // After this line, getters and setters unmodified.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public int getNumberOfMaxSimultaneousBorrows() {
        return numberOfMaxSimultaneousBorrows;
    }

    public void setNumberOfMaxSimultaneousBorrows(int numberOfMaxSimultaneousBorrows) {
        this.numberOfMaxSimultaneousBorrows = numberOfMaxSimultaneousBorrows;
    }

    public StorageBox getStorageBox() {
        return storageBox;
    }

    public void setStorageBox(StorageBox storageBox) {
        this.storageBox = storageBox;
    }

    public AbstractTidyingStrategy getTidyingStrategy() {
        return tidyingStrategy;
    }

    public void setTidyingStrategy(AbstractTidyingStrategy tidyingStrategy) {
        this.tidyingStrategy = tidyingStrategy;
    }

    public List<Member> getMembers() {
        return members;
    }


    public int getNumberOfMonthsToBeStandard() {
        return numberOfMonthsToBeStandard;
    }

    public void setNumberOfMonthsToBeStandard(int numberOfMonthsToBeStandard) {
        this.numberOfMonthsToBeStandard = numberOfMonthsToBeStandard;
    }

    public int getNumberToBeFrequent() {
        return numberToBeFrequent;
    }

    public void setNumberToBeFrequent(int numberToBeFrequent) {
        this.numberToBeFrequent = numberToBeFrequent;
    }

    public int getNumberOfMonthsToBeFrequent() {
        return numberOfMonthsToBeFrequent;
    }

    public void setNumberOfMonthsToBeFrequent(int numberOfMonthsToBeFrequent) {
        this.numberOfMonthsToBeFrequent = numberOfMonthsToBeFrequent;
    }

    public List<LibraryItem> getItemsLinkedToTheLibrary() {
        return itemsLinkedToTheLibrary;
    }

    public void setItemsLinkedToTheLibrary(List<LibraryItem> itemsLinkedToTheLibrary) {
        this.itemsLinkedToTheLibrary = itemsLinkedToTheLibrary;
    }
}
