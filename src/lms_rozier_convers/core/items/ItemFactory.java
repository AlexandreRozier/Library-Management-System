package lms_rozier_convers.core.items;

import lms_rozier_convers.core.AbstractFactory;
import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.library.Location;

/**
 * 16/11/2015.
 * A Factory dedicated to provide the desired item type.
 * Follows the Factory Pattern explained in our rapport.
 */
public class ItemFactory extends AbstractFactory {


    public static LibraryItem createItem(String itemType, String title, String publisher, int year, int numberVolume, boolean borrowable, Location location, Cuboid shape){
        if(itemType.equalsIgnoreCase("CD")){

            CD cd = new CD(title, publisher, year, numberVolume, borrowable, location, shape);
            return cd;
        }
        else if(itemType.equalsIgnoreCase("DVD")){
            DVD dvd = new DVD(title, publisher, year, numberVolume, borrowable, location, shape);
            return dvd;
        }
        else if(itemType.equalsIgnoreCase("Book")){
            Book book = new Book(title, publisher, year, numberVolume, borrowable, location, shape);
            return book;
        }
        else System.out.println("Ce type n'est pas reconnu.");
        return null;


    }




}
