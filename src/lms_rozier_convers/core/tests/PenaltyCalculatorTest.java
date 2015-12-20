package lms_rozier_convers.core.tests;

import lms_rozier_convers.core.AbstractFactory;
import lms_rozier_convers.core.FactoryMaker;
import lms_rozier_convers.core.StorageBox;
import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.library.*;
import lms_rozier_convers.core.member.Member;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.items.Book;
import lms_rozier_convers.core.items.CD;
import lms_rozier_convers.core.member.MemberObserver;
import lms_rozier_convers.core.member.MemberStatus;
import lms_rozier_convers.core.penalty.PenaltyCalculator;
import lms_rozier_convers.core.tidying.BestBookcaseFitStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * IMPORTANT : Those tests were designed the 17/11/2015, and check the borrow durations compared to the current
 * date. Thus, if the test fails when you run it ( in one month ), you'll have to change the borrowing date in the
 * test to check if everything works.
 * 17/11/2015.
 */
@SuppressWarnings("Duplicates")
public class PenaltyCalculatorTest {


    /**
     * Checks if a member is suspended when returning an item after the correct duration
     * @throws Exception
     */
    @Test
    public void testSuspensionWithTwoItems() throws Exception
    {

        // Creates a member with a given MemberObserver instance
        Member member = new Member();

        // Creation of a new library
        Library library = new Library(new BestBookcaseFitStrategy());
        library.addMember(member);

        //Change these dates to adapt the test to the moment you run it ( the result depends of the computer date)

        Book book = new Book("Rimbaud","Dupuis", 1750,1,true,new Location(new StorageBox()),new Cuboid(1,1,1));
        CD cd = new CD("Rolling Stones","Whammy Records", 1995,1,true,new Location(new StorageBox()),new Cuboid(1,1,1));
        member.getBorrowedItems().put(book, new GregorianCalendar(2015,9,15)); // Suspended
        member.getBorrowedItems().put(cd, new GregorianCalendar(2015,10,5)); // Fined : put in the penalties queue of member

        // Adds a standard card to the member
        AbstractFactory cardFactory = FactoryMaker.createFactory("cardFactory");
        member.setMemberCard(((CardFactory)cardFactory).create("Standard"));

        member.getMemberCard().giveBack(book);
        member.getMemberCard().giveBack(cd);

        Assert.assertEquals(MemberStatus.SUSPENDED,member.getStatus());

    }

    /**
     * Tests if for the same borrowings, a golden member is not suspended
     * @throws Exception
     */
    @Test
    public void testWithGoldenMembers() throws Exception
    {

        // Creates a member with a given MemberObserver instance
        Member member = new Member();

        // Creation of a new library
        Library library = new Library(new BestBookcaseFitStrategy());
        library.addMember(member);

        //Change these dates to adapt the test to the moment you run it ( the result depends of the computer date)

        Book book = new Book("Rimbaud","Dupuis", 1750,1,true,new Location(new StorageBox()),new Cuboid(1,1,1));
        CD cd = new CD("Rolling Stones","Whammy Records", 1995,1,true,new Location(new StorageBox()),new Cuboid(1,1,1));
        member.getBorrowedItems().put(book, new GregorianCalendar(2015,9,15)); // Suspended
        member.getBorrowedItems().put(cd, new GregorianCalendar(2015,10,5)); // Fined : put in the penalties queue of member

        // Adds a standard card to the member
        AbstractFactory cardFactory = FactoryMaker.createFactory("cardFactory");
        member.setMemberCard(((CardFactory)cardFactory).create("Golden"));

        member.getMemberCard().giveBack(book);
        member.getMemberCard().giveBack(cd);

        Assert.assertEquals(MemberStatus.NORMAL,member.getStatus());

    }

    @Test
    public void testWarnedWithTwoItems() throws Exception
    {
        Member member = new Member();

        // Creation of a new library
        Library library = new Library(new BestBookcaseFitStrategy());
        library.addMember(member);


        //Change these dates to adapt the test to the moment you run it (the result depends of the computer date)
        Book book = new Book("Rimbaud","Dupuis", 1750,1,true,new Location(new StorageBox()),new Cuboid(1,1,1));
        CD cd = new CD("Rolling Stones","Whammy Records", 1995,1,true,new Location(new StorageBox()),new Cuboid(1,1,1));
        member.getBorrowedItems().put(book, new GregorianCalendar(2015,10,0)); // Warned
        member.getBorrowedItems().put(cd, new GregorianCalendar(2015,11,8)); // Normal

        AbstractFactory cardFactory = FactoryMaker.createFactory("cardFactory");
        member.setMemberCard(((CardFactory)cardFactory).create("Standard"));


        member.getMemberCard().giveBack(book);
        member.getMemberCard().giveBack(cd);

        member.notifyObservers();
        Assert.assertTrue(member.getMessage()!=null);
    }
}