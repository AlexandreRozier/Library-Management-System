package lms_rozier_convers.core.tidying;

import lms_rozier_convers.core.exceptions.ObjectNotFoundException;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Bookcase;
import lms_rozier_convers.core.library.Library;
import lms_rozier_convers.core.library.Room;

/**
 * 24/11/2015.
 */
public interface TidyingStrategyInterface {

    void tidy(LibraryItem item);

    void tidy(LibraryItem item, Bookcase bookcase) throws ObjectNotFoundException;

    void tidy(LibraryItem item, Room room) throws ObjectNotFoundException;

}
