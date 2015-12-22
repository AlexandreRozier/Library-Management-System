package lms_rozier_convers.core.items;

import lms_rozier_convers.core.AbstractFactory;
import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.library.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * 16/11/2015.
 * A Factory dedicated to provide the desired item type.
 * Follows the Factory Pattern explained in our rapport.
 */
public class ItemFactory extends AbstractFactory {


    public  LibraryItem createItem(String itemType, String title, ArrayList<String> authors, String publisher, int year, int numberVolume, boolean borrowable, Cuboid shape){
        if(itemType.equalsIgnoreCase("CD")){

            CD cd = new CD(title,itemType,publisher,authors,year,numberVolume,borrowable,shape);
            return cd;
        }
        else if(itemType.equalsIgnoreCase("DVD")){
            DVD dvd = new DVD(title,itemType,publisher,authors,year,numberVolume,borrowable,shape);
            return dvd;
        }
        else System.out.println("Ce type n'est pas reconnu.");
        return null;
    }

    public LibraryItem createItem(String itemType, String title, ArrayList<String> authors, String publisher, int year, int numberVolume, boolean borrowable, Cuboid shape,String ISBN){
        if(itemType.equalsIgnoreCase("Book")){
            return new Book(title,itemType,publisher,authors,year,numberVolume,borrowable,shape,ISBN);
        }
        else System.out.println("Ce type n'est pas reconnu.");
        return null;
    }




}
