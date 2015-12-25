package lms_rozier_convers.core.tests;

import lms_rozier_convers.CLIU.Actions;
import lms_rozier_convers.CLIU.UserInterface;
import lms_rozier_convers.core.FactoryMaker;
import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.items.*;
import lms_rozier_convers.core.library.*;
import lms_rozier_convers.core.member.Member;
import lms_rozier_convers.core.tidying.AnyFitStrategy;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by hx on 20/12/2015.
 */
public class ActionsTest {

    private Library library;
    /**
     * Creates a library that will be used by the following tests
     * @throws Exception
     */
    @Before public void setUp()
     {
         library = new Library(new AnyFitStrategy(),1,3,5,5,"Miterrand");
         UserInterface.addLibrary(library);
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

             // 3 books written by Albert Camus
             if (i<=2) {
                 ArrayList<String> author1 = new ArrayList<>();
                 author1.add("Albert Camus");
                 shelf.addItem(factory.createItem("Book", "Title" + i, author1, "Publisher" + i, 1900 + i, i, true, new Cuboid(i, i, i), String.valueOf(i)));
             }
             // 7 other books
             else if (i<=10){
                 ArrayList<String> author1 = new ArrayList<>();
                 author1.add("Author "+i);
                 shelf.addItem(factory.createItem("Book", "Title" + i, author1, "Publisher" + i, 1900 + i, i, true, new Cuboid(i, i, i), String.valueOf(i)));
             }
             // 2 CDs from the daft punk, not borrowable (i.e. consultation-only)
             else if (i<=12){
                 ArrayList<String> author1 = new ArrayList<>();
                 author1.add("Daft Punk");
                 shelf.addItem(factory.createItem("CD", "Discovery", author1, "Publisher" + i, 1900 + i, i, false, new Cuboid(i, i, i)));
             }
             // 3 other CDs, not borrowable
             else if (i<=15){
                 ArrayList<String> author1 = new ArrayList<>();
                 author1.add("Composer "+i);
                 shelf.addItem(factory.createItem("CD", "Title" + i, author1, "Publisher" + i, 1900 + i, i, false, new Cuboid(i, i, i)));
             }
             // 5 DVDs, borrowable
             else if (i <= 20) {
                 ArrayList<String> author1 = new ArrayList<>();
                 author1.add("Composer "+i);
                 shelf.addItem(factory.createItem("DVD", "Title" + i, author1, "Publisher" + i, 1900 + i, i, true, new Cuboid(i, i, i)));
             }
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

    /**
     * Tests if a member is successfully found, and if the two instances found match.
     * @throws Exception
     */
    @Test
    public void testFindMember() throws Exception {

        Member member1 = new Member();
        member1.setName("Alexandre Rozier");
        member1.setCurrentLibrary(UserInterface.getCurrentLibrary());
        CardFactory cardFactory = new CardFactory();
        Card card = cardFactory.create("Standard");
        member1.setMemberCard(card);
        Member member2 = Actions.findMember("Alexandre Rozier");
        assertTrue(member2.equals(member1));
    }

    /**
     * Checks whether the item with the title "Discovery" is found in the test Library.
     */
    @Test
    public  void testFindLibraryItem() throws Exception {
        LibraryItem item = Actions.findLibraryItem("Discovery");
        assertTrue(item!=null);
    }




}