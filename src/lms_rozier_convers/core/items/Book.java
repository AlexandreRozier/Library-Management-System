package lms_rozier_convers.core.items;

import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.library.Location;

import java.util.ArrayList;

/**
 * 16/11/2015.
 * A basic book.
 */
public class Book extends LibraryItem {
    String ISBNcode;

    /**
     * MAIN CONSTRUCTOR
     * @param title
     * @param type
     * @param publisher
     * @param authors
     * @param year
     * @param numberVolume
     * @param borrowable
     * @param shape
     * @param ISBNcode
     */
    public Book(String title, String type, String publisher, ArrayList<String> authors, int year, int numberVolume, boolean borrowable, Cuboid shape, String ISBNcode) {
        super(title, type, publisher, authors, year, numberVolume, borrowable, shape);
        this.ISBNcode = ISBNcode;
    }

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

    public Book(String title, String publisher, int year, int numberVolume, boolean borrowable, Cuboid shape, String ISBNcode) {
        super(title, publisher, year, numberVolume, borrowable, shape);
        this.ISBNcode = ISBNcode;
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
