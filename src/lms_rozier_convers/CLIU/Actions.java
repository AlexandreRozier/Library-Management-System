package lms_rozier_convers.CLIU;

import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Bookcase;
import lms_rozier_convers.core.library.Library;
import lms_rozier_convers.core.library.Room;
import lms_rozier_convers.core.library.Shelf;
import lms_rozier_convers.core.member.Member;

import java.util.ArrayList;

/**
 * Created by hx on 20/12/2015.
 */
public abstract class Actions {




    //TODO : erreur si pas de librairie -> mettre dans userinterface (librairie donnée en argument = celle déterminée dans userinterface
    //TODO : display of the given library




    /**
     * Lists the items of the given library, and return a String containing the description
     * @param library
     * @return String
     */
    public static String list_items( Library library) {

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
             authorsList+=author+", ";
            }
            descr += "Item n°" + iter +
                    ", type :" + libraryItem.getType() +
                    ", author(s) : " + authorsList +
                    "\n";

            iter++;
        }
        return descr;
    }


    public static String borrow_item(String member_name, String item_title){
        Library currentLibrary = UserInterface.getCurrentLibrary();
        Member member = null;
        LibraryItem item = null;

        //Get the member (object) from its name
        for (Member m : currentLibrary.getMembers()){
            if (m.getName() == member_name){
                member = m;
            }
        }
        //Get the item (object) from its name
        for (LibraryItem i : currentLibrary.getItemsLinkedToTheLibrary()){
            if (i.getTitle() == item_title){
                item = i;
            }
        }
        if (member == null || item == null){
            return "The member or the item does not exist in this library";
        }

        else{
            member.getMemberCard().borrow(item);
            if (member.getBorrowedItems().containsKey(item)){
                return item.getTitle() + " has been borrowed by "+member.getName();
            }
            else if (item.getReservationList().contains(member)){
                return item.getTitle() + " was not available, it has been added to the reservation list.";
            }
            else{
                return member.getName() + "cannot borrow the item "+ item.getTitle();
            }
        }





    }
}
