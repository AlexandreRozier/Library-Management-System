package lms_rozier_convers.core.items;

import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.library.Location;

/**
 * 16/11/2015.
 * A basic DVD
 */
public class DVD extends  LibraryItem {

    /**
     * CONSTRUCTOR
     * @param shape
     */
    public DVD(Cuboid shape) {
        super(shape);
    }

    /**
     * CONSTRUCTOR
     */
    public DVD() {
        super();
    }

    public DVD(String title, String publisher, int year, int numberVolume, boolean borrowable, Location location, Cuboid shape)
    {
        super(title,publisher,year,numberVolume,borrowable,location,shape);
    }
}
