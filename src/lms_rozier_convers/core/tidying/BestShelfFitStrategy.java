package lms_rozier_convers.core.tidying;

import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Bookcase;
import lms_rozier_convers.core.library.Location;
import lms_rozier_convers.core.library.Room;
import lms_rozier_convers.core.library.Shelf;

/**
 * 25/11/2015.
 */

public class BestShelfFitStrategy extends AbstractTidyingStrategy{


    @Override
    public void tidy(LibraryItem item) {
        double maxSpace = 0;
        Shelf selectedShelf = null;
        for (Room room : this.getLibrary().getRooms() ) {
            for (Bookcase bookcase : room.getBookcases())
            {
                for (Shelf shelf : bookcase.getShelves())
                {
                    double shelfSpace = shelf.getEmptySpace().getVolume();
                    if (shelfSpace > maxSpace && item.getShape().canFit(shelf.getEmptySpace()))
                    {
                        maxSpace = shelfSpace;
                        selectedShelf = shelf;
                    }
                }
            }
        }
        if (selectedShelf != null) {
            selectedShelf.addItem(item);
        }
        else {
            this.getLibrary().getStorageBox().addItem(item);
        }
    }
}
