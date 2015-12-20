package lms_rozier_convers.core.tests;

import lms_rozier_convers.core.FactoryMaker;
import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.items.DVD;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Library;
import lms_rozier_convers.core.member.Member;
import lms_rozier_convers.core.member.MemberObserver;
import lms_rozier_convers.core.tidying.AbstractTidyingStrategy;
import lms_rozier_convers.core.tidying.AnyFitStrategy;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 05/12/2015.
 * Test class for MemberObserver
 */
public class MemberObserverTest {

    /**
     * Checks whether the member's status is automatically changed by the observers when the number of borrowed books is enough / too little.
     * @throws Exception
     */
    @Test
    public void testCheckStatus() throws Exception {

        //Creates the library
        Library library = new Library(new AnyFitStrategy(),1,3,4,5);
        Member member = new Member();
        library.addMember(member);

        // The borrowed objects
        DVD dvd = new DVD(new Cuboid(1, 1, 1));
        DVD dvd2 = new DVD(new Cuboid(1, 1, 1));
        DVD dvd3 = new DVD(new Cuboid(1, 1, 1));

        HashMap<LibraryItem, Calendar[]> borrowedListHistory = new HashMap<>();

        // The borrowing / return dates of the dvd
        Calendar[] dates = new Calendar[2];
        dates[0] = new GregorianCalendar(2015, 11, 5);
        dates[1] = new GregorianCalendar(2015, 11, 6);
        borrowedListHistory.put(dvd, dates);

        // The borrowing / return dates of the dvd2
        Calendar[] dates2 = new Calendar[2];
        dates2[0] = new GregorianCalendar(2015, 11, 4);
        dates2[1] = new GregorianCalendar(2015, 11, 8);
        borrowedListHistory.put(dvd2, dates2);

        // The borrowing date of the dvd3 (still borrowed)
        HashMap<LibraryItem, Calendar> borrowedItems = new HashMap<>();
        borrowedItems.put(dvd3, new GregorianCalendar(2015, 10, 3));

        member.setBorrowedItemsHistory(borrowedListHistory);
        member.setBorrowedItems(borrowedItems);

        // Adds a standard card to member
        CardFactory cardFactory = (CardFactory) FactoryMaker.createFactory("cardFactory");
        Card standardCard = cardFactory.create("Standard");
        member.setMemberCard(standardCard);

        // Calls the update of the associated MemberObserver, and updates the member's status
        member.notifyObservers();
        assertTrue(member.getMemberCard().getName().equals("Frequent"));

    }
}