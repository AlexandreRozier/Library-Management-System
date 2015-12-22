package lms_rozier_convers.core.tests;

import lms_rozier_convers.CLIU.Actions;
import lms_rozier_convers.CLIU.UserInterface;
import lms_rozier_convers.core.FactoryMaker;
import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.items.Book;
import lms_rozier_convers.core.items.CD;
import lms_rozier_convers.core.items.DVD;
import lms_rozier_convers.core.items.ItemFactory;
import lms_rozier_convers.core.library.*;
import lms_rozier_convers.core.member.Member;
import lms_rozier_convers.core.tidying.AnyFitStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by hx on 20/12/2015.
 */
public class ActionsTest {

    private Library library;
    /**
     * Creates a library that will be used for the test
     * @throws Exception
     */
    @Before public void setUp()
     {
        library = new Library(new AnyFitStrategy(),1,3,5,5,"Miterrand");
        UserInterface.setCurrentLibrary(library);
        //+++++++++++++++++++++++++++
        //Creates the Library
        int roomNumber = 3;
        int bookcaseNumber = 4;
        int shelfNumber = 8;

        for (int i = 0; i < roomNumber; i++) {
            library.addRoom(new Room("room"+i));
        }

        for (int i = 0; i < roomNumber*bookcaseNumber; i++) {
            Room room = UserInterface.getCurrentLibrary().findRoomByName("room" + i % roomNumber); // An easy way to set up the correct number of bookcases per room
            room.addBookcase(new Bookcase("bookcase"+i));

        }

        for (int i = 0; i < roomNumber*bookcaseNumber*shelfNumber; i++) {
            Bookcase bookcase = UserInterface.getCurrentLibrary().findBookcaseByName("bookcase" + i % (bookcaseNumber*roomNumber));
            Shelf shelf = new Shelf(new Cuboid(200, 200, 200),"shelf"+i);
            bookcase.addShelf(shelf);

            // Item creation

            ItemFactory factory = (ItemFactory) FactoryMaker.createFactory("itemFactory");
            ArrayList<String> authors = new ArrayList<>();
            authors.add("Author" + i);
            authors.add("Author" + (i + 1));
            shelf.addItem(factory.createItem("CD", "Title" + i, authors, "Publisher" + i, 1900 + i, i, true, new Cuboid(i, i, i)));
            shelf.addItem(factory.createItem("DVD", "Title" + i, authors, "Publisher" + i, 1900 + i, i, true, new Cuboid(i, i, i)));
            shelf.addItem(factory.createItem("Book", "Title" + i, authors, "Publisher" + i, 1900 + i, i, true, new Cuboid(i, i, i), String.valueOf(i)));

        }


        //+++++++++++++++++++++++++++
        // Creates the members

        Member member = new Member();
        Member member2 = new Member();
        Member member3 = new Member();

        library.addMember(member);
        library.addMember(member2);
        library.addMember(member3);

        // Set the member's cards
        CardFactory factoryCard = new CardFactory();
        Card standardCard = factoryCard.create("Standard");
        Card frequentCard = factoryCard.create("Frequent");
        Card goldenCard = factoryCard.create("Golden");
        member.setMemberCard(standardCard);
        member2.setMemberCard(goldenCard);
        member3.setMemberCard(frequentCard);

        //+++++++++++++++++++++++++++

    }

    /**
     * Tests the list_items method of Actions
     * @see Actions
     * @throws Exception
     */
    @Test
    public void testListItem() throws Exception{
        assert (!Actions.list_items(library.getName()).equals(""));
    }

    /**
     * Tests whether one can switch between two libraries registered in the UserInterface's libraries
     * @throws Exception
     */
    @Test
    public void testUseLibrary() throws Exception {
        Library library2 = new Library(new AnyFitStrategy(),1,3,5,3,"Library2");
        UserInterface.getLibraries().add(library2);
        Actions.use_library(library2.getName());
        assertTrue(UserInterface.getCurrentLibrary().getName().equals(library2.getName()));
    }

    @Test
    public void testFindItems() throws Exception {
        Actions.use_library("Miterrand");
        String result = Actions.find_items("Albert Camus");
    }
}