package lms_rozier_convers.core.tests;

import lms_rozier_convers.core.AbstractFactory;
import lms_rozier_convers.core.FactoryMaker;
import lms_rozier_convers.core.StorageBox;
import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.items.Book;
import lms_rozier_convers.core.items.CD;
import lms_rozier_convers.core.items.DVD;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Library;
import lms_rozier_convers.core.library.LibraryUpdater;
import lms_rozier_convers.core.library.Location;
import lms_rozier_convers.core.member.Member;
import lms_rozier_convers.core.member.MemberStatus;
import lms_rozier_convers.core.tidying.AnyFitStrategy;
import lms_rozier_convers.core.tidying.BestBookcaseFitStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * 11/12/2015.
 * Tests whether the library updater works correctly : it is supposed to remove a day to the current member penalty at
 * each call, then notify the observers linked to the member.
 * @see lms_rozier_convers.core.member.MemberObserver
 */
public class LibraryUpdaterTest {

    @Test
    public void testRun() throws Exception {
        Member member = new Member();

        // Creation of a new library
        Library library = new Library(new BestBookcaseFitStrategy());
        library.addMember(member);

        //Change these dates to adapt the test to the moment you run it (the result depends of the computer date)
        Book book = new Book("Rimbaud","Dupuis", 1750,1,true,new Location(new StorageBox()),new Cuboid(1,1,1));
        CD cd = new CD("Rolling Stones","Whammy Records", 1995,1,true,new Location(new StorageBox()),new Cuboid(1,1,1));
        member.getBorrowedItems().put(book, new GregorianCalendar(2015,9,15)); // Suspended
        member.getBorrowedItems().put(cd, new GregorianCalendar(2015,11,8)); // Normal

        AbstractFactory cardFactory = FactoryMaker.createFactory("cardFactory");
        member.setMemberCard(((CardFactory)cardFactory).create("Standard"));


        member.getMemberCard().giveBack(book); // Adds a suspension to the member
        member.getMemberCard().giveBack(cd);

        member.notifyObservers();
        assertEquals(MemberStatus.SUSPENDED, member.getStatus());

        // Makes the days pass until the member's penalty is reset to zero
        LibraryUpdater dailyUpdater = new LibraryUpdater(library);
        while (!member.getStatus().equals(MemberStatus.NORMAL)) {
            dailyUpdater.run();
        }

        // Checks whether the member's status is back to NORMAL
        assertEquals(MemberStatus.NORMAL,member.getStatus());

    }
}