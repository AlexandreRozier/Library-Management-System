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
import org.junit.Test;

import java.awt.*;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by hx on 20/12/2015.
 */
public class Test1 {

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {


        Library library = new Library(new AnyFitStrategy(),1,3,5,5,"Miterrand");
        UserInterface.addLibrary(library);
        UserInterface.setCurrentLibrary(library);
        Timer timer = new Timer();
        timer.schedule(new LibraryUpdater(library), 24 * 3600 * 1000);
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
                shelf.addItem(factory.createItem("CD", "Title" + i, author1, "Publisher" + i, 1900 + i, i, false, new Cuboid(i, i, i)));
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
        UserInterface.launch();
    }
}