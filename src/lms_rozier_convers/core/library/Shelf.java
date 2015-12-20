package lms_rozier_convers.core.library;

import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.items.LibraryItem;

import java.util.ArrayList;

/**
 * 24/11/2015.
 * A shelf of the library, contained in a bookcase.
 * It has a list of items contained, and a cuboid to represent the remaining free space.
 */
public class Shelf {
    private Bookcase bookcase;
    private Cuboid shape;
    private Cuboid emptySpace;
    private ArrayList<LibraryItem> itemsContained = new ArrayList<>();
    private String name = "";

    public Shelf(String name) {
        this.name = name;
    }

    /**
     * CONSTRUCTOR
     * @param shape
     */
    public Shelf(Cuboid shape) {
        this.shape = shape;
        setEmptySpace();
    }

    /**
     * CONSTRUCTOR
     * @param cuboid
     * @param shelf1
     */
    public Shelf(Cuboid cuboid, String shelf1) {
        this.shape = cuboid;
        this.name = shelf1;
        setEmptySpace();
    }



    /**
     * Updates the available space of the shelf
     */
    public void setEmptySpace()
    {
        emptySpace = shape;
        for (LibraryItem item : itemsContained) {
            if(item.getShape()!=null){
                emptySpace.setWidth(emptySpace.getWidth()-item.getShape().getWidth());
            }
        }
    }

    /**
     * Adds an item to the current shelf
     * @param item
     */
    public void addItem(LibraryItem item){
        if(item.getShape().canFit(this.emptySpace)){
            this.itemsContained.add(item);
            Location l = new Location(this.getBookcase().getRoom(), this.getBookcase(), this);
            item.setLocation(l);
            setEmptySpace();
        }
    }

    /**
     * Removes an item of the shelf
     * @param item
     */
    public void removeItem(LibraryItem item){
        if(this.getItemsContained().contains(item)){
            this.getItemsContained().remove(item);
            item.setLocation(null);
            setEmptySpace();
        }
    }

    public Cuboid getEmptySpace() {
        return this.emptySpace;
    }

    public void setShape(Cuboid shape) {
        this.shape = shape;
    }




    public ArrayList<LibraryItem> getItemsContained() {
        return itemsContained;
    }

    public void setItemsContained(ArrayList<LibraryItem> itemsContained) {
        this.itemsContained = itemsContained;
    }

    public Bookcase getBookcase() {
        return bookcase;
    }

    public void setBookcase(Bookcase bookcase) {
        this.bookcase = bookcase;
    }

    public Cuboid getShape() {
        return shape;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
