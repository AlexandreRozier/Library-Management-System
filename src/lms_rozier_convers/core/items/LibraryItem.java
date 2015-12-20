package lms_rozier_convers.core.items;

import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.library.Location;
import lms_rozier_convers.core.member.Member;

import java.util.ArrayList;

/**
 * The abstract item class, inherited by all the item objects
 * 16/11/2015.
 */

public abstract class LibraryItem {

    private static int idCounter;
    private int id;
    private String title;

    private String publisher;
    private ArrayList<String> authors = new ArrayList<>();

    private int year;
    private int numberVolume; //number of the volume
    private boolean borrowable;

    private Cuboid shape;
    private Member borrower;
    private Location location;

    private ArrayList<Member> reservationList = new ArrayList<>();

    /**
     * CONSTRUCTOR
     * @param shape
     */
    public LibraryItem(Cuboid shape) {
        this.shape = shape;
        this.id = idCounter;
        idCounter++;
    }

    /**
     * CONSTRUCTOR
     * Creates a library item with an unique ID
     * @param title
     * @param publisher
     * @param year
     * @param numberVolume
     * @param borrowable
     * @param location
     * @param shape
     */
    public LibraryItem(String title, String publisher, int year, int numberVolume, boolean borrowable, Location location, Cuboid shape) {
        this.id = idCounter;
        idCounter++;
        this.title = title;
        this.publisher = publisher;
        this.year = year;
        this.numberVolume = numberVolume;
        this.borrowable = borrowable;
        this.location = location;
        this.shape = shape;
    }

    public LibraryItem() {
        this.id = idCounter;
        idCounter++;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public int getNumberVolume() {
        return numberVolume;
    }

    public boolean isBorrowable() {
        return borrowable;
    }

    public Member getBorrower() {
        return borrower;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setNumberVolume(int numberVolume) {
        this.numberVolume = numberVolume;
    }

    public void setBorrowable(boolean borrowable) {
        this.borrowable = borrowable;
    }

    public void setBorrower(Member borrower) {
        this.borrower = borrower;
    }

    public Cuboid getShape() {
        return shape;
    }

    public void setShape(Cuboid shape) {
        this.shape = shape;
    }

    public ArrayList<Member> getReservationList() {
        return reservationList;
    }

    public void setReservationList(ArrayList<Member> reservationList) {
        this.reservationList = reservationList;
    }


    @Override
    public String toString() {
        String descr = "Titre : " + title + "\n";
        if (authors.size()==1){
            descr += "Auteur : " + authors + "\n";
        }
        if (authors.size() > 1) {
            descr += "Auteur : ";
            for (String author : authors) {
                descr += author + ", ";
            }
        }
        return descr;
    }
}
