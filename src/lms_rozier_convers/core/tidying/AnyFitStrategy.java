package lms_rozier_convers.core.tidying;

import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Bookcase;
import lms_rozier_convers.core.library.Location;
import lms_rozier_convers.core.library.Room;
import lms_rozier_convers.core.library.Shelf;

/**
 * 24/11/2015.
 */
public class AnyFitStrategy extends AbstractTidyingStrategy {

    @Override
    public void tidy(LibraryItem item) {

        for (Room room : this.getLibrary().getRooms() ) {
            for (Bookcase bookcase : room.getBookcases())
            {
                for (Shelf shelf : bookcase.getShelves())
                {

                    if (item.getShape().canFit(shelf.getEmptySpace()))
                    {
                        shelf.addItem(item);
                        this.getLibrary().getStorageBox().getItems().remove(item);
                        return;
                    }
                }
            }
        }
        item.setLocation(new Location(this.getLibrary().getStorageBox()));
    }
}
