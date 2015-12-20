package lms_rozier_convers.core.items;

import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.library.Location;

/**
 * 16/11/2015.
 * A basic CD.
 */
public class CD extends LibraryItem {
    /**
     * CONSTRUCTOR
     * @param shape
     */
    public CD(Cuboid shape) {
        super(shape);
    }

    /**
     * CONSTRUCTOR
     */
    public CD() {
        super();
    }

    public CD(String title, String publisher, int year, int numberVolume, boolean borrowable, Location location, Cuboid shape)
    {
        super(title,publisher,year,numberVolume,borrowable,location,shape);
    }


}
