package lms_rozier_convers.core.tests;

import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.items.DVD;
import lms_rozier_convers.core.items.ItemFactory;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.*;
import lms_rozier_convers.core.member.Member;
import lms_rozier_convers.core.tidying.AnyFitStrategy;
import lms_rozier_convers.core.tidying.BestBookcaseFitStrategy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 10/12/2015.
 * Tests the card class, and its main methods borrow() and giveBack()
 */
public class CardTest {

    /**
     * Tests if the borrowing of an item is correctly handled, i.e. if two members can't borrow the same item.
     * Checks if the second member is notified when the first member gives back the desired item.
     * @throws Exception
     */
    @Test
    public void testBorrow() throws Exception {
        Library library = new Library(new AnyFitStrategy());
        LibraryUpdater dailyUpdater = new LibraryUpdater(library);

        //Set up the rooms
        List<Room> rooms = new ArrayList<>();
        Room room1 = new Room("room1");
        Room room2 = new Room("room2");
        rooms.add(room1);
        rooms.add(room2);
        library.setRooms(rooms);

        //Set up the Bookcases
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

        //Creates an item
        ItemFactory itemFactory = new ItemFactory();
        Location location1 = new Location(room1,bookcase1,shelf1);
        DVD dvd = (DVD) itemFactory.createItem("DVD","HarryPotter","JKRowling",2001,1,true,location1,new Cuboid(3,3,3));

        // Sorts the item
        library.getTidyingStrategy().tidy(dvd);

        Member member = new Member();
        Member member2 = new Member();

        library.addMember(member);
        library.addMember(member2);

        // Set the member's cards
        CardFactory factoryCard = new CardFactory();
        Card standardCard1 = factoryCard.create("Standard");
        Card standardCard2 = factoryCard.create("Standard");
        member.setMemberCard(standardCard1);
        member2.setMemberCard(standardCard2);

        // Member 1 borrows successfully the dvd
        member.getMemberCard().borrow(dvd);

        assert (!library.isContained(dvd));
        assert (member.getBorrowedItems().containsKey(dvd));

        // Member 2 can't borrow the dvd because member 1 has it : he is added to the reservation list
        member2.getMemberCard().borrow(dvd);
        assert (!member2.getBorrowedItems().containsKey(dvd));
        assert(!dvd.getReservationList().isEmpty());

        // Member 1 gives back the dvd : it is automatically sorted in the library and warns Member 2 that the item is available.
        member.getMemberCard().giveBack(dvd);
        assert (library.isContained(dvd));
        assert (!member.getBorrowedItems().containsKey(dvd));
        assert (member2.getMessage() != null);



    }
}