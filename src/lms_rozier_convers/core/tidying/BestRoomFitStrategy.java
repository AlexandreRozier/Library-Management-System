package lms_rozier_convers.core.tidying;

import lms_rozier_convers.core.exceptions.ObjectNotFoundException;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Bookcase;
import lms_rozier_convers.core.library.Location;
import lms_rozier_convers.core.library.Room;
import lms_rozier_convers.core.library.Shelf;

/**
 * 26/11/2015.
 */
@SuppressWarnings("Duplicates")
public class BestRoomFitStrategy extends AbstractTidyingStrategy{


    @Override
    public void tidy(LibraryItem item, Room room) throws ObjectNotFoundException {



        //Checks whether the bookcase is in the library
        if (!this.getLibrary().isContained(room)) throw new ObjectNotFoundException();

        double maxSpace = 0;
        Shelf selectedShelf = null;
        for (Bookcase bookcase : room.getBookcases()) {
            for (Shelf shelf : bookcase.getShelves()) {
                double shelfSpace = shelf.getEmptySpace().getVolume();
                if (shelfSpace > maxSpace && item.getShape().canFit(shelf.getEmptySpace())) {
                    maxSpace = shelfSpace;
                    selectedShelf = shelf;
                }
            }
        }

        if (selectedShelf != null) {
            selectedShelf.addItem(item);
            this.getLibrary().getStorageBox().getItems().remove(item);

        } else {
            this.getLibrary().getStorageBox().addItem(item);
        }
    }
}
