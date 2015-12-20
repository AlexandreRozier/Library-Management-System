package lms_rozier_convers.core.member;

import lms_rozier_convers.core.FactoryMaker;
import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.penalty.PenaltyCalculator;

import java.util.*;

/**
 * 17/11/2015.
 * The observer of the member class. Supposed to update the member status, and to apply the penalties given to
 * the member by the PenaltyCalculator
 * @see PenaltyCalculator
 */
public  class MemberObserver implements Observer {
    /**
     * Method called each time a member wants to borrow an Item, and each day to update the status of the members.
     * Calling this method assures that the statuses are always up-to-date (ex : a warning becomes automatically
     * a suspension whether the delay is important enough).
     * @param o
     * @return void
     */
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Member)
        {
            Member member = (Member)o;
            updateStatus(member);
            PenaltyCalculator.checkPenalty(member);

        }

    }



    /**
     * This function is called each day, and checks if the penalty period is over. If the duration is over and the member has
     * given back the books forgiven, his status become again to normal
     *
     * This function will also change the normal member's status to frequent if he has borrowed enough items in the last M months,
     * or change the frequent member's status to normal in the other case
     * @param member
     */
    public static void updateStatus(Member member) {

        // The parameters given by the library
        int m = member.getCurrentLibrary().getNumberOfMonthsToBeFrequent();
        int mPrime = member.getCurrentLibrary().getNumberOfMonthsToBeStandard();
        int n = member.getCurrentLibrary().getNumberToBeFrequent();

        // The date from which the counting of borrowed items begins
        GregorianCalendar startDate = (GregorianCalendar)Calendar.getInstance();
        startDate.roll(Calendar.MONTH,-m);

        // The number of items borrowed in the given period
        int nbItem=0;
        // Adds the number of items in the borrowedHistory who were borrowed during this period
        for(LibraryItem e : member.getBorrowedItemsHistory().keySet()){
            if (member.getBorrowedItemsHistory().get(e)[1].after(startDate)){
                nbItem++;
            }
        }

        // Adds the current borrowed items
        nbItem+=member.getBorrowedItems().size();
        double averageBorrowNumber = nbItem/m; // Used to decide whether the member should become frequent
        double averageBorrowNumberPrime = nbItem/mPrime; // Used to decide whether the member should come back to normal


        // Makes the member frequent if he borrowed enough items in the given period
        if((averageBorrowNumber >= n) && member.getMemberCard().getName().equals("Standard")){
            CardFactory cardFactory = (CardFactory)FactoryMaker.createFactory("cardFactory");
            Card frequent = cardFactory.create("Frequent");
            member.setMemberCard(frequent);
        }

        // Makes the member normal if he has not borrowed enough items in the given period
        if((averageBorrowNumberPrime >= n) && member.getMemberCard().getName().equals("Frequent")){
            CardFactory cardFactory = (CardFactory)FactoryMaker.createFactory("cardFactory");
            Card standard = cardFactory.create("Standard");
            member.setMemberCard(standard);
        }
    }



}
