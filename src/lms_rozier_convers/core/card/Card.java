package lms_rozier_convers.core.card;

import lms_rozier_convers.core.*;
import lms_rozier_convers.core.Messagerie.Messagerie;
import lms_rozier_convers.core.exceptions.ObjectNotFoundException;
import lms_rozier_convers.core.items.*;
import lms_rozier_convers.core.member.Member;
import lms_rozier_convers.core.member.MemberStatus;
import lms_rozier_convers.core.penalty.PenaltyCalculator;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * The class card always has a mamber attribute, and represents its membership.
 * It it used in a strategy pattern to determine how the linked member borrows items.
 */
public abstract class Card {

    /**
     * These  parameters are used by the PenaltyCalculator to calculate the penalty
     * according to the member type. It is a implementation of a visitor pattern.
     * @see lms_rozier_convers.core.penalty.PenaltyCalculator
     */
    protected double fineAmount;
    protected Duration suspensionDuration;
    protected Duration fineDuration;
    protected Member member;
    /**
     *This Hashmap contains the borrowing duration of library items according to their type.
     */
    protected HashMap<LibraryItem,Duration> borrowingLengthPerItem;
    protected int numberOfSimultaneousBorrows;
    protected String name;

    /**
     * CONSTRUCTOR
     * @param fineAmount
     * @param suspensionDuration
     * @param fineDuration
     * @param borrowingLengthPerItem
     * @param numberOfSimultaneousBorrows
     * @param name
     */
    public Card(double fineAmount, Duration suspensionDuration, Duration fineDuration, HashMap<LibraryItem, Duration> borrowingLengthPerItem, int numberOfSimultaneousBorrows, String name) {
        this.member = new Member();
        this.fineAmount = fineAmount;
        this.suspensionDuration = suspensionDuration;
        this.fineDuration = fineDuration;
        this.borrowingLengthPerItem = borrowingLengthPerItem;
        this.numberOfSimultaneousBorrows = numberOfSimultaneousBorrows;
        this.name = name;
    }

    /**
     * CONSTRUCTOR
     * @param fineAmount
     * @param suspensionDuration
     * @param fineDuration
     * @param borrowingLengthPerItem
     * @param numberOfSimultaneousBorrows
     */
    public Card(double fineAmount, Duration suspensionDuration, Duration fineDuration, HashMap<LibraryItem, Duration> borrowingLengthPerItem, int numberOfSimultaneousBorrows) {
        this.member = new Member();
        this.fineAmount = fineAmount;
        this.suspensionDuration = suspensionDuration;
        this.fineDuration = fineDuration;
        this.borrowingLengthPerItem = borrowingLengthPerItem;
        this.numberOfSimultaneousBorrows = numberOfSimultaneousBorrows;
    }

    /**
     * CONSTRUCTOR
     * @param fineAmount
     * @param suspensionDuration
     * @param fineDuration
     * @param borrowingLengthPerItem
     */
    public Card(double fineAmount, Duration suspensionDuration, Duration fineDuration, HashMap<LibraryItem,Duration> borrowingLengthPerItem) {

        this.member = new Member();
        this.fineAmount = fineAmount;
        this.suspensionDuration = suspensionDuration;
        this.fineDuration = fineDuration;
        this.borrowingLengthPerItem = borrowingLengthPerItem;
    }

    /**
     * Method called whenever a member wants to borrow an item.
     * @param item
     */
    public void borrow(LibraryItem item)
    {
        /**
         * The member can borrow the item only if nobody is currently borrowing the item, and if the member isn't suspended
         * and if the member hasn't got more than the number maximum of borrows
         */
        if(item.isBorrowable() && item.getBorrower() == null && member.getStatus().equals(MemberStatus.NORMAL) && member.getBorrowedItems().size()<member.getMemberCard().getNumberOfSimultaneousBorrows())
        {
            //Put the item in the hashmap of borrowedItems of the member and the date associated
            this.member.getBorrowedItems().put(item, new GregorianCalendar());

            item.setBorrower(member);

            //If the item has the member in his reservation list, we remove it from the list.
            if (item.getReservationList().contains(this.member)){
                item.getReservationList().remove(this.member);
            }
            // Removes the location of the item, and frees the linked shelf of the item
            item.getLocation().getShelf().removeItem(item);

        }
        // If the item is already borrowed, adds to the reservation list
        else if (item.getBorrower() != null && item.isBorrowable() && member.getStatus().equals(MemberStatus.NORMAL) && member.getBorrowedItems().size()<member.getMemberCard().getNumberOfSimultaneousBorrows()){
            item.getReservationList().add(this.member);
        }

        member.notifyObservers();
    }

    /**
     * This method is called to giveback an item. It also checks if the member should have a penalty, send
     * a message to first member who has reserved and fit the item with the fit strategy of the library.
     * @param item
     * @throws ObjectNotFoundException
     */
    public void giveBack(LibraryItem item) throws ObjectNotFoundException {

        // Check whether the item is given back on time and penalises the member if not
        PenaltyCalculator.penalise(this.member,item);

        // Remove the item from the current borrowed list
        this.member.getBorrowedItems().remove(item);

        // Add the item to the borrowedHistory of the member, with its borrow date and return date as values
        Calendar[] dates = new Calendar[2];
        dates[0] = this.member.getBorrowedItems().get(item);
        dates[1] =  new GregorianCalendar();

        // Add the item to the history of the member
        this.member.getBorrowedItemsHistory().put(item,dates);
        // Empties the borrower field of the item
        item.setBorrower(null);
        // Sort the item in the library
        member.getCurrentLibrary().getTidyingStrategy().tidy(item);

        // Alerts the first member of the reservation list
        if(!item.getReservationList().isEmpty()){
            Member nextBorrower = item.getReservationList().get(0);
            Messagerie.sendMessage(nextBorrower, "L'item " + item + " est disponible à l'emprunt");
        }
        member.notifyObservers();
    }


    //Getters and setters

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Duration getSuspensionDuration() {
        return suspensionDuration;
    }

    public void setSuspensionDuration(Duration suspensionDuration) {
        this.suspensionDuration = suspensionDuration;
    }

    public Duration getFineDuration() {
        return fineDuration;
    }

    public void setFineDuration(Duration fineDuration) {
        this.fineDuration = fineDuration;
    }

    public HashMap<LibraryItem, Duration> getBorrowingLengthPerItem() {
        return borrowingLengthPerItem;
    }

    public void setBorrowingLengthPerItem(HashMap<LibraryItem, Duration> borrowingLengthPerItem) {
        this.borrowingLengthPerItem = borrowingLengthPerItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSimultaneousBorrows() {
        return numberOfSimultaneousBorrows;
    }

    public void setNumberOfSimultaneousBorrows(int numberOfSimultaneousBorrows) {
        this.numberOfSimultaneousBorrows = numberOfSimultaneousBorrows;
    }
}
