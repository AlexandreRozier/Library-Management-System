package lms_rozier_convers.core.tests;


import lms_rozier_convers.core.exceptions.ObjectNotFoundException;
import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.items.Book;
import lms_rozier_convers.core.library.Bookcase;
import lms_rozier_convers.core.library.Library;
import lms_rozier_convers.core.library.Room;
import lms_rozier_convers.core.library.Shelf;
import lms_rozier_convers.core.tidying.BestBookcaseFitStrategy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * 26/11/2015.
 * Tests the BestBookcaseFitStrategy
 */
public class BestBookcaseFitStrategyTest {

    /**
     * Creates a library and sort an item according to the BestBookcaseFitStrategy
     * @throws Exception
     */
    @Test(expected = ObjectNotFoundException.class)
    public void testTidyExpectsException() throws Exception {

        Library library = new Library(new BestBookcaseFitStrategy());

        //Set up the rooms
        List<Room> rooms = new ArrayList<>();

        Room room1 = new Room("room1");
        Room room2 = new Room("room2");

        rooms.add(room1);
        rooms.add(room2);

        library.setRooms(rooms);

        //Set up the Bookcases
        List<Bookcase> bookCases1 = new ArrayList<>();
        List<Bookcase> bookCases2 = new ArrayList<>();
        Bookcase bookcase1 = new Bookcase("bookCase1");
        Bookcase bookcase2 = new Bookcase("bookCase2");
        Bookcase bookcase3 = new Bookcase("bookCase3");
        Bookcase bookcase4 = new Bookcase("bookcase4");
        bookCases1.add(bookcase1);
        bookCases1.add(bookcase2);
        bookCases2.add(bookcase3);
        bookCases2.add(bookcase4);

        room1.setBookcases(bookCases1);
        room2.setBookcases(bookCases2);

        //Set up the shelves
        List<Shelf> shelves1 = new ArrayList<>();
        List<Shelf> shelves2 = new ArrayList<>();
        List<Shelf> shelves3 = new ArrayList<>();
        List<Shelf> shelves4 = new ArrayList<>();

        Shelf shelf1 = new Shelf(new Cuboid(10, 10, 10),"shelf1");
        Shelf shelf2 = new Shelf(new Cuboid(11, 11, 11),"shelf2");
        Shelf shelf3 = new Shelf(new Cuboid(7, 7, 7),"shelf3");
        Shelf shelf4 = new Shelf(new Cuboid(4, 6, 3),"shelf4");
        Shelf shelf5 = new Shelf(new Cuboid(7, 9, 2),"shelf5");
        Shelf shelf6 = new Shelf(new Cuboid(1, 2, 7),"shelf6");
        Shelf shelf7 = new Shelf(new Cuboid(5, 11, 13),"shelf7");
        Shelf shelf8 = new Shelf(new Cuboid(11, 5, 10),"shelf8");


        //Creates the structure
        shelves1.add(shelf1);
        shelves1.add(shelf2);
        shelves2.add(shelf3);
        shelves2.add(shelf4);
        shelves3.add(shelf5);
        shelves3.add(shelf6);
        shelves4.add(shelf7);
        shelves4.add(shelf8);

        bookcase1.setShelves(shelves1);
        bookcase2.setShelves(shelves2);
        bookcase3.setShelves(shelves3);
        bookcase4.setShelves(shelves4);

    //Creates the items

        Bookcase bookcase5 = new Bookcase("bookcase5"); //Not added to the Library

        Book book = new Book(new Cuboid(1,2,7));
        library.getTidyingStrategy().tidy(book,bookcase5);




    }

    @SuppressWarnings("Duplicates")
    @Test
    public void testTidy() throws Exception {

        Library library = new Library(new BestBookcaseFitStrategy());

        //Set up the rooms
        List<Room> rooms = new ArrayList<>();

        Room room1 = new Room("room1");
        Room room2 = new Room("room2");

        rooms.add(room1);
        rooms.add(room2);

        library.setRooms(rooms);

        //Set up the Bookcase
        List<Bookcase> bookCases1 = new ArrayList<>();

        Bookcase bookcase1 = new Bookcase("bookCase1");

        bookCases1.add(bookcase1);


        room1.setBookcases(bookCases1);

        //Set up the shelves
        List<Shelf> shelves1 = new ArrayList<>();

        Shelf shelf1 = new Shelf(new Cuboid(10, 10, 10),"shelf1");
        Shelf shelf2 = new Shelf(new Cuboid(11, 11, 11),"shelf2"); //Shelf with the biggest free space


        //Creates the structure
        shelves1.add(shelf1);
        shelves1.add(shelf2);

        bookcase1.setShelves(shelves1);


        //Creates the test items
        Book book = new Book(new Cuboid(5, 2, 7));
        library.getTidyingStrategy().tidy(book, bookcase1);
        assertTrue(book.getLocation().getShelf().getName().equals("shelf2")); // Shelf 2 has the biggest free space




    }
}