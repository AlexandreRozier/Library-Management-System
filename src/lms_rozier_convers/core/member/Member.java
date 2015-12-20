package lms_rozier_convers.core.member;

import lms_rozier_convers.core.AbstractFactory;
import lms_rozier_convers.core.Duration;
import lms_rozier_convers.core.FactoryMaker;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.exceptions.ObjectNotFoundException;
import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Library;
import lms_rozier_convers.core.penalty.Penalty;

import java.util.*;

/**
 * 15/11/2015.
 * Represents a member. It tkes part in an observer pattern, and is observed by a MemberObserver instance.
 * @see MemberObserver
 */

public class Member extends Observable{

    private int id;
    private String name;
    private ArrayList<Penalty> penaltyList = new ArrayList<>(); //The incoming penalties for the member

    /**
     * This value is set by the penalty manager. It has the following values :
     * - normal
     * - warned ( < 1 week of delay)
     * - suspended ( 0-3 weeks of delay)
     * - fined ( > 3 weeks of delay )
     */
    private String status = MemberStatus.NORMAL;
    private Duration penalisationDuration = new Duration(0);
    private String message; // Here are set the messages for the member : a warning, a notification of an available reserved item...
    private Library currentLibrary;
    private Date birthdayDate;
    private String creditCardNumber;
    private double balance;    //Amount of money on the member's account.
    private HashMap<LibraryItem,Calendar> borrowedItems = new HashMap<>(); //Hashmap with library items as keys, and their borrow Date as value.
    private HashMap<LibraryItem,Calendar[]> borrowedItemsHistory = new HashMap<>(); //Hashmap with library items as keys, and their [borrow Date,return Date] as values.
    private Card memberCard;





    /**
     * CONSTRUCTOR
     * Used to add an Observer and a card to the member
     * @see MemberObserver
     */
    public Member()  {
        super();
        this.addObserver(new MemberObserver());
        notifyObservers();
    }


    public HashMap<LibraryItem, Calendar> getBorrowedItems() {
        return borrowedItems;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
        this.setChanged();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.setChanged();
    }

    public Duration getPenalisationDuration() {
        setChanged();
        return penalisationDuration;
    }

    public void setPenalisationDuration(Duration penalisationDuration) {
        this.penalisationDuration = penalisationDuration;
        setChanged();
    }

    public void setBorrowedItems(HashMap<LibraryItem, Calendar> borrowedItems) {
        this.borrowedItems = borrowedItems;
        setChanged();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setChanged();
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
        setChanged();
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        setChanged();
    }

    public String getMessage() {
        setChanged();
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        setChanged();
    }

    public HashMap<LibraryItem, Calendar[]> getBorrowedItemsHistory() {
        setChanged();
        return borrowedItemsHistory;

    }

    public void setBorrowedItemsHistory(HashMap<LibraryItem, Calendar[]> borrowedItemsHistory) {
        setChanged();
        this.borrowedItemsHistory = borrowedItemsHistory;
    }

    public Card getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(Card memberCard) {
        this.memberCard = memberCard;
        memberCard.setMember(this);
        memberCard.setNumberOfSimultaneousBorrows(this.getCurrentLibrary().getNumberOfMaxSimultaneousBorrows());
        this.setChanged();
    }

    public Library getCurrentLibrary() {
        return currentLibrary;
    }

    public void setCurrentLibrary(Library currentLibrary) {
        setChanged();
        this.currentLibrary = currentLibrary;
    }

    public ArrayList<Penalty> getPenaltyList() {
        setChanged();
        return penaltyList;
    }

    public void setPenaltyList(ArrayList<Penalty> penaltyList) {
        setChanged();
        this.penaltyList = penaltyList;
    }
}
