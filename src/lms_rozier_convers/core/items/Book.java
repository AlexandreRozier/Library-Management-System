package lms_rozier_convers.core.items;

import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.library.Location;

/**
 * 16/11/2015.
 * A basic book.
 */
public class Book extends LibraryItem {
    String ISBNcode;

    /**
     * CONSTRUCTOR
     */
    public Book() {
        super();

    }

    /**
     * CONSTRUCTOR
     * @param title
     * @param publisher
     * @param year
     * @param numberVolume
     * @param borrowable
     * @param location
     * @param shape
     */
    public Book(String title, String publisher, int year, int numberVolume, boolean borrowable, Location location, Cuboid shape)
    {
      super(title,publisher,year,numberVolume,borrowable,location,shape);
    }
    /**
     * CONSTRUCTOR
     * @param shape
     */
    public Book(Cuboid shape) {
        super(shape);
    }

    public String getISBNcode() {
        return ISBNcode;
    }

    public void setISBNcode(String ISBNcode) {
        this.ISBNcode = ISBNcode;
    }

}
