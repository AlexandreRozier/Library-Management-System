package lms_rozier_convers.core.tidying;

import lms_rozier_convers.core.exceptions.ObjectNotFoundException;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Bookcase;
import lms_rozier_convers.core.library.Location;
import lms_rozier_convers.core.library.Shelf;

/**
 * 26/11/2015.
 */
public class BestBookcaseFitStrategy extends AbstractTidyingStrategy {




    @SuppressWarnings("Duplicates")
    @Override
    public void tidy(LibraryItem item, Bookcase bookcase) throws ObjectNotFoundException {

        if (item.getLocation().getStorageBox() != null) {
            item.getLocation().getStorageBox().getItems().remove(item);
        }
        // Checks whether the bookcase is in the library
        if (!this.getLibrary().isContained(bookcase)) throw new ObjectNotFoundException();

        double maxSpace = 0;
        Shelf selectedShelf = null;

        for (Shelf shelf : bookcase.getShelves()) {
            double shelfSpace = shelf.getEmptySpace().getVolume();
            if (shelfSpace > maxSpace && item.getShape().canFit(shelf.getEmptySpace())) {
                maxSpace = shelfSpace;
                selectedShelf = shelf;
            }
        }


        if (selectedShelf != null) {
            selectedShelf.addItem(item);

        } else {
            this.getLibrary().getStorageBox().addItem(item);
        }
    }

}
