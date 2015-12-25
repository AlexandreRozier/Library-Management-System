package lms_rozier_convers.main;

import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.library.*;
import lms_rozier_convers.core.member.Member;
import lms_rozier_convers.core.tidying.AnyFitStrategy;

import java.util.*;

/**
 * 04/12/2015.
 * The main class of our library management system
 */
public class main {
    /**
     * Creates a library, fill it with members and items, and sets up a daily updater called each day.
     * Asksthe user for inputs, later transformed in concrete actions.
     * @param args
     */
    public static void main(String[] args) {
        Library library = new Library(new AnyFitStrategy(),1,3,5,5,"Miterrand");

        //Set up the rooms
        List<Room> rooms = new ArrayList<>();

        Room room1 = new Room("room1");
        Room room2 = new Room("room2");

        rooms.add(room1);
        rooms.add(room2);

        library.setRooms(rooms);

        //Set up the Bookcases
        List<Bookcase> bookCases1 = new ArrayList<>();
        List<Bookcase> bookCases2 = new ArrayList<>();

        Bookcase bookcase1 = new Bookcase("bookCase1");
        Bookcase bookcase2 = new Bookcase("bookCase2");
        Bookcase bookcase3 = new Bookcase("bookCase3");
        Bookcase bookcase4 = new Bookcase("bookcase4");
        bookCases1.add(bookcase1);
        bookCases1.add(bookcase2);
        bookCases2.add(bookcase3);
        bookCases2.add(bookcase4);

        room1.setBookcases(bookCases1);
        room2.setBookcases(bookCases2);

        //Set up the shelves
        List<Shelf> shelves1 = new ArrayList<>();
        List<Shelf> shelves2 = new ArrayList<>();
        List<Shelf> shelves3 = new ArrayList<>();
        List<Shelf> shelves4 = new ArrayList<>();

        Shelf shelf1 = new Shelf(new Cuboid(1, 1, 1),"shelf1");
        Shelf shelf2 = new Shelf(new Cuboid(2, 2, 2),"shelf2");
        Shelf shelf3 = new Shelf(new Cuboid(3, 3, 3),"shelf3");
        Shelf shelf4 = new Shelf(new Cuboid(4, 4, 4),"shelf4");
        Shelf shelf5 = new Shelf(new Cuboid(5, 5, 5),"shelf5");
        Shelf shelf6 = new Shelf(new Cuboid(6, 6, 6),"shelf6");
        Shelf shelf7 = new Shelf(new Cuboid(7, 7, 7),"shelf7");
        Shelf shelf8 = new Shelf(new Cuboid(8, 8, 8),"shelf8");


        //Creates the structure
        shelves1.add(shelf1);
        shelves1.add(shelf2);
        shelves2.add(shelf3);
        shelves2.add(shelf4);
        shelves3.add(shelf5);
        shelves3.add(shelf6);
        shelves4.add(shelf7);
        shelves4.add(shelf8);

        bookcase1.setShelves(shelves1);
        bookcase2.setShelves(shelves2);
        bookcase3.setShelves(shelves3);
        bookcase4.setShelves(shelves4);

        //+++++++++++++++++++++++++++
        // Creates the members

        Member member = new Member();
        Member member2 = new Member();
        Member member3 = new Member();

        library.addMember(member);
        library.addMember(member2);
        library.addMember(member3);

        // Set the member's cards
        CardFactory factoryCard = new CardFactory();
        Card standardCard = factoryCard.create("Standard");
        Card frequentCard = factoryCard.create("Frequent");
        Card goldenCard = factoryCard.create("Golden");
        member.setMemberCard(standardCard);
        member2.setMemberCard(goldenCard);
        member3.setMemberCard(frequentCard);

        //+++++++++++++++++++++++++++
        // The inputs of the user will be catched there
        Scanner sc = new Scanner(System.in);
        String command = "";
        System.out.println("Bienvenue dans le système de gestion de la librairie "+library.getName());
        while (true)
        {
            command = sc.nextLine();
            //TODO ajouter CLI pour le 25/12/2015
        }
    }
}
