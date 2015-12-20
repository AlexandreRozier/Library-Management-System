package lms_rozier_convers.CLIU;

import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Bookcase;
import lms_rozier_convers.core.library.Library;
import lms_rozier_convers.core.library.Room;
import lms_rozier_convers.core.library.Shelf;

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
}
