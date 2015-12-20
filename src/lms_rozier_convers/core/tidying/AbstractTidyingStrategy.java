package lms_rozier_convers.core.tidying;

import lms_rozier_convers.core.exceptions.ObjectNotFoundException;
import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Bookcase;
import lms_rozier_convers.core.library.Library;
import lms_rozier_convers.core.library.Room;

/**
 * 24/11/2015.
 */
public abstract class AbstractTidyingStrategy implements TidyingStrategyInterface{
    private Library library;

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;

    }

    @Override
    public void tidy(LibraryItem item) {}

    @Override
    public void tidy(LibraryItem item, Bookcase bookcase) throws  ObjectNotFoundException {}

    @Override
    public void tidy(LibraryItem item, Room room) throws ObjectNotFoundException {}
}
