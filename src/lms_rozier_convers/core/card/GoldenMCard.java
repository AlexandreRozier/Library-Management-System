package lms_rozier_convers.core.card;

import lms_rozier_convers.core.Duration;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.member.MemberStatus;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * 15/11/2015.
 * A Golden member card : granted after the member has payed the correct amount.
 */
public class GoldenMCard extends Card {


    /**
     * CONSTRUCTOR
     *
     * @param fineAmount
     * @param suspensionDuration
     * @param fineDuration
     * @param borrowingLengthPerItem
     */
    public GoldenMCard(double fineAmount, Duration suspensionDuration, Duration fineDuration, HashMap<LibraryItem, Duration> borrowingLengthPerItem) {
        super(fineAmount, suspensionDuration, fineDuration, borrowingLengthPerItem);
    }

    /**
     * CONSTRUCTOR
     *
     * @param fineAmount
     * @param suspensionDuration
     * @param fineDuration
     * @param borrowingLengthPerItem
     * @param numberOfSimultaneousBorrows
     * @param name
     */
    public GoldenMCard(double fineAmount, Duration suspensionDuration, Duration fineDuration, HashMap<LibraryItem, Duration> borrowingLengthPerItem, int numberOfSimultaneousBorrows, String name) {
        super(fineAmount, suspensionDuration, fineDuration, borrowingLengthPerItem, numberOfSimultaneousBorrows, name);
    }



    /**
     * Method called whenever a gold member wants to borrow an item.
     * This is pretty much the same method as in the Card class, but we do not check if the item is borrowable
     * because a goldenMember can borrow all items.
     * @param item
     */
    @Override
    public void borrow(LibraryItem item)
    {
        if(item.getBorrower() == null && member.getStatus().equals(MemberStatus.NORMAL) && member.getBorrowedItems().size()<member.getMemberCard().getNumberOfSimultaneousBorrows())
        {
            //Put the item in the hashmap of borrowedItems of the member and the date associated
            this.member.getBorrowedItems().put(item,new GregorianCalendar());
            item.setBorrower(member);

            //If the member has the item in his reservation list, we remove it from the list.
            if (item.getReservationList().contains(this.member)){
                item.getReservationList().remove(this.member);
            }
            // Removes the location of the item, and frees the linked shelf of the item
            item.getLocation().getShelf().removeItem(item);

        }

        // If the item is already borrowed, adds to the reservation list
        else if (item.getBorrower() != null && member.getStatus().equals(MemberStatus.NORMAL) && member.getBorrowedItems().size()<member.getMemberCard().getNumberOfSimultaneousBorrows()){
            item.getReservationList().add(this.member);
        }

        member.notifyObservers();
    }
}
