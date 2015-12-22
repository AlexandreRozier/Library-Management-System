package lms_rozier_convers.core.tests;

import lms_rozier_convers.CLIU.UserInterface;
import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.items.Book;
import lms_rozier_convers.core.library.*;
import lms_rozier_convers.core.member.Member;
import lms_rozier_convers.core.tidying.AnyFitStrategy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

import static org.junit.Assert.*;

/**
 * Created by hx on 20/12/2015.
 */

public class Test1 {

    @Test
    public void testList_items() throws Exception {
        Library library = new Library(new AnyFitStrategy(),1,3,5,5,"Miterrand");
        //+++++++++++++++++++++++++++
        //Schedules an update of all the members of the library each day
        Timer timer = new Timer();
        timer.schedule(new LibraryUpdater(library), 24 * 3600 * 1000);
        //+++++++++++++++++++++++++++

        int roomNumber = 3;
        int bookcaseNumber = 4;
        int shelfNumber = 8;



        for (int i = 0; i < roomNumber; i++) {
            library.getRooms().add(new Room("room"+i));
        }

        for (int i = 0; i < roomNumber*bookcaseNumber; i++) {
            Room room = UserInterface.getCurrentLibrary().findRoomByName("room" + i % bookcaseNumber); // An easy way to set up the correct number of bookcases per room
            room.getBookcases().add(new Bookcase("bookcase"+i));
        }

        for (int i = 0; i < roomNumber*bookcaseNumber*shelfNumber; i++) {
            Bookcase bookcase = UserInterface.getCurrentLibrary().findBookcaseByName("bookcase" + i % shelfNumber);
            bookcase.getShelves().add(new Shelf(new Cuboid(1, 1, 1),"shelf"+i));
        }


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