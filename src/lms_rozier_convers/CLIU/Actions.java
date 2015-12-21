package lms_rozier_convers.CLIU;

import lms_rozier_convers.core.FactoryMaker;
import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Bookcase;
import lms_rozier_convers.core.library.Library;
import lms_rozier_convers.core.library.Room;
import lms_rozier_convers.core.library.Shelf;
import lms_rozier_convers.core.member.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx on 20/12/2015.
 */
public abstract class Actions {

    //TODO : display of the given library


    /**
     * Method which returns a Member object from a given name
     * If the member does not exist, returns a null object.
     *
     * @param member_name
     * @return member
     */
    public static Member findMember(String member_name) {
        Member member = null;
        Library currentLibrary = UserInterface.getCurrentLibrary();
        for (Member m : currentLibrary.getMembers()) {
            if (m.getName() == member_name) {
                member = m;
            }
        }
        return member;
    }

    /**
     * Method which returns a LibraryItem object from a given name
     * If the item does not exist, returns a null object.
     * @param item_title
     * @return
     */
    public static LibraryItem findLibraryItem(String item_title) {
        LibraryItem item = null;
        Library currentLibrary = UserInterface.getCurrentLibrary();
        for (LibraryItem i : currentLibrary.getItemsLinkedToTheLibrary()) {
            if (i.getTitle() == item_title) {
                item = i;
            }
        }
        return item;
    }

    /**
     * Lists the items of the given library, and return a String containing the description
     *
     * @param library
     * @return String
     */
    public static String list_items(Library library) {

        ArrayList<LibraryItem> itemList = new ArrayList<>();

        for (Room room : library.getRooms()) {
            for (Bookcase bookcase : room.getBookcases()) {
                for (Shelf shelf : bookcase.getShelves()) {
                    for (LibraryItem libraryItem : shelf.getItemsContained()) {
                        itemList.add(libraryItem);
                    }
                }
            }
        }
        String descr = null;
        String authorsList = null;
        int iter = 0;
        for (LibraryItem libraryItem : itemList) {
            for (String author : libraryItem.getAuthors()) {
                authorsList += author + ", ";
            }
            descr += "Item n°" + iter +
                    ", type :" + libraryItem.getType() +
                    ", author(s) : " + authorsList +
                    "\n";

            iter++;
        }
        return descr;
    }

    /**
     * If it is possible, let the member borrow the specified item. Display a message describing the result.
     *
     * @param member_name
     * @param item_title
     * @return
     */
    public static String borrow_item(String member_name, String item_title) {
        LibraryItem item = Actions.findLibraryItem(item_title);
        Member member = Actions.findMember(member_name);
        if (member == null || item == null) {
            return "The member or the item does not exist in this library, please retype your command properly";
        } else {
            member.getMemberCard().borrow(item);
            if (member.getBorrowedItems().containsKey(item)) {
                return item.getTitle() + " has been borrowed by " + member.getName();
            } else if (item.getReservationList().contains(member)) {
                return item.getTitle() + " was not available, it has been added to the reservation list.";
            } else {
                return member.getName() + "cannot borrow the item " + item.getTitle();
            }
        }
    }

    /**
     * Add a member to the current library
     * @param member_name
     * @param numCreditCard
     * @param email
     * @param memberType
     * @return
     */
    public static String add_member(String member_name, String numCreditCard, String email, String memberType) {
        if(email.contains("@")) {
            Library currentLibrary = UserInterface.getCurrentLibrary();
            Member member1 = new Member();
            member1.setName(member_name);
            member1.setCurrentLibrary(currentLibrary);
            member1.setCreditCardNumber(numCreditCard);
            member1.setEmail(email);
            CardFactory cardFactory = (CardFactory) FactoryMaker.createFactory("cardFactory");
            Card card = cardFactory.create(memberType);
            member1.setMemberCard(card);
            return "The member " + member_name + "has been added to the library";
        }
        else{
            return "Please type a correct email";
        }
    }

    public static String search_title(String title_name){
        Library library = UserInterface.getCurrentLibrary();
        ArrayList<LibraryItem> items_to_print = new ArrayList<>();
        for (Room room : library.getRooms()){
            for (Bookcase bookcase : room.getBookcases()){
                for (Shelf shelf : bookcase.getShelves()){
                    for (LibraryItem item : shelf.getItemsContained()){
                        if (item.getTitle()==title_name){
                            items_to_print.add(item);
                        }
                    }
                }
            }
        }

        String descr=null;

        for (LibraryItem item : items_to_print){
            String state=null;
            if(item.isBorrowable()){
                state = "This item is borrowable.";
            }
            else{
                state = "This item isn't borrowable.";
            }
            descr += "Author(s) : "+item.getAuthors() +", Year : "+item.getYear() + ", Type : "+item.getType() + ". "+state + "\n";
        }

        return descr;


    }
}
