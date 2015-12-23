package lms_rozier_convers.CLIU;

import lms_rozier_convers.core.FactoryMaker;
import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.Bookcase;
import lms_rozier_convers.core.library.Library;
import lms_rozier_convers.core.library.Room;
import lms_rozier_convers.core.library.Shelf;
import lms_rozier_convers.core.member.Member;
import lms_rozier_convers.core.tidying.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by hx on 20/12/2015.
 */
public abstract class Actions {

    //TODO : display of the given library



    /**
     * Lists the items of the given library, and return a String containing the description
     *
     * @param libraryName
     * @return String
     */
    public static String list_items(String libraryName) {

        ArrayList<LibraryItem> itemList = new ArrayList<>();
        Library library = UserInterface.getLibraryByName(libraryName);
        if (library == null) {
            return "The library wasn't found.";
        }
        for (Room room : library.getRooms()) {
            for (Bookcase bookcase : room.getBookcases()) {
                for (Shelf shelf : bookcase.getShelves()) {
                    for (LibraryItem libraryItem : shelf.getItemsContained()) {
                        itemList.add(libraryItem);
                    }
                }
            }
        }
        String descr = "";
        int iter = 0;
        for (LibraryItem libraryItem : itemList) {

            String authorsList = "";
            for (String author : libraryItem.getAuthors()) {
                authorsList += author + ", ";
            }
            descr += "Item n°" + iter +
                    ":\n type :" + libraryItem.getType() +
                    "\n author(s) : " + authorsList + "\n" +
                    libraryItem.getLocation() +
                    "\n";

            iter++;
        }
        return descr;
    }

    /**
     * Method which returns a Member object from a given name
     * If the member does not exist, returns a null object.
     *
     * @param member_name
     * @return member
     */
    public static Member findMember(String member_name) {
        Member member = null;
        Library currentLibrary = UserInterface.getCurrentLibrary();
        if (currentLibrary == null) {
            return null;
        }
        for (Member m : currentLibrary.getMembers()) {
            if (m.getName().equals(member_name)) {
                member = m;
            }
        }
        return member;
    }

    /**
     * Method which returns a LibraryItem object from a given name
     * If the item does not exist, returns a null object.
     * @param item_title
     * @return
     */
    public static LibraryItem findLibraryItem(String item_title) {
        LibraryItem item = null;
        Library currentLibrary = UserInterface.getCurrentLibrary();
        if (currentLibrary == null) {
            return null;
        }
        for (LibraryItem i : currentLibrary.getItemsLinkedToTheLibrary()) {
            if (i.getTitle().equals(item_title)) {
                item = i;
            }
        }
        return item;
    }


    /**
     * Method which return a string saying if the item is borrowable or not. Will be useful in next descriptions methods.
     * @param item
     * @return
     */
    public static String itemborrowable(LibraryItem item){
        String state;
        if(item.isBorrowable()){
            state = "This item is borrowable.";
        }
        else{
            state = "This item is not borrowable.";
        }
        return state;
    }



    /**
     * If it is possible, let the member borrow the specified item. Display a message describing the result.
     *
     * @param member_name
     * @param item_title
     * @return
     */
    public static String borrow_item(String member_name, String item_title) {
        LibraryItem item = Actions.findLibraryItem(item_title);
        Member member = Actions.findMember(member_name);
        if (member == null || item == null) {
            return "The member or the item does not exist in this library, please retype your command properly";
        } else {
            member.getMemberCard().borrow(item);
            if (member.getBorrowedItems().containsKey(item)) {
                return item.getTitle() + " has been borrowed by " + member.getName();
            } else if (item.getReservationList().contains(member)) {
                return item.getTitle() + " was not available, it has been added to the reservation list.";
            } else {
                return member.getName() + "cannot borrow the item " + item.getTitle();
            }
        }
    }

    /**
     * Add a member to the current library
     * @param member_name
     * @param numCreditCard
     * @param email
     * @param memberType
     * @return
     */
    public static String add_member(String member_name, String numCreditCard, String email, String memberType) {
        if(email.contains("@")) {
            Library currentLibrary = UserInterface.getCurrentLibrary();
            if (currentLibrary == null) {
                return null;
            }
            Member member1 = new Member();
            member1.setName(member_name);
            member1.setCurrentLibrary(currentLibrary);
            member1.setCreditCardNumber(numCreditCard);
            member1.setEmail(email);
            CardFactory cardFactory = (CardFactory) FactoryMaker.createFactory("cardFactory");
            Card card = cardFactory.create(memberType);
            member1.setMemberCard(card);
            return "The member " + member_name + "has been added to the library";
        }
        else{
            return "Please type a correct email";
        }
    }

    public static String search_title(String title_name){
        Library library = UserInterface.getCurrentLibrary();
        ArrayList<LibraryItem> items_to_print = new ArrayList<>();
        for (Room room : library.getRooms()){
            for (Bookcase bookcase : room.getBookcases()){
                for (Shelf shelf : bookcase.getShelves()){
                    for (LibraryItem item : shelf.getItemsContained()){
                        if (item.getTitle().equalsIgnoreCase(title_name)){
                            items_to_print.add(item);
                        }
                    }
                }
            }
        }
        String descr="";
        for (LibraryItem item : items_to_print){
            descr += "Author(s) : "+item.getAuthors() +", Year : "+item.getYear() + ", Type : "+item.getType() + ". "+ itemborrowable(item) + "\n";
        }
        return descr;
    }



    public static String find_items(String author_name){
        Library library = UserInterface.getCurrentLibrary();
        ArrayList<LibraryItem> items_to_print = new ArrayList<>();
        for (Room room : library.getRooms()){
            for (Bookcase bookcase : room.getBookcases()){
                for (Shelf shelf : bookcase.getShelves()){
                    for (LibraryItem item : shelf.getItemsContained()){
                        if (item.getAuthors().contains(author_name)){
                            items_to_print.add(item);
                        }
                    }
                }
            }
        }
        String descr = "";
        int i = 0;
        for (LibraryItem item : items_to_print){
            descr +="Item n°"+i+ " Title : "+item.getTitle() +", Year : "+item.getYear() + ", Type : "+item.getType() + ". "+ itemborrowable(item) + "\n";
            i++;
        }
        return descr;
    }

    /**
     * Selects the library on which should be applied the next commands
     * @param libraryName the desired library
     */
    public static void use_library(String libraryName) {
        for (Library library : UserInterface.getLibraries()) {
            if (library.getName().equals(libraryName)) {
                UserInterface.setCurrentLibrary(library);
            }
        }
        if (UserInterface.getCurrentLibrary().getName().equals(libraryName)) {
            System.out.println("This library ("+libraryName+")is already selected !");
        } else System.out.println("Library not found");
    }
//TODO à implémenter
    public static void check_borrowed(String commandAndParameter) {
    }

    //TODO dire si la library existe déjà
    //TODO vérifier que la library current est toujours dans libraries
    /**
     * This method creates a new library, but doesn't select it :
     * it is necessary to call "use_library" to use the new library (just like in SQL)
     * @param libraryName the name of the new library
     */
    public static void create_library(String libraryName) {
        Scanner sc = new Scanner(System.in);
        //+++++++++++++++++++++++++++
        //Checks whether the library already exists
        boolean alreadyExists = true;
        while (alreadyExists) {
            alreadyExists = false;
            for (Library library : UserInterface.getLibraries()) {
                if (library.getName().equals(libraryName)) {
                    alreadyExists = true;
                }
            }
            if (UserInterface.getCurrentLibrary().getName().equals(libraryName)) {
                alreadyExists = true;
            }
            if (alreadyExists) {
                System.out.println("The given name already exists ! Please choose another one.");
                libraryName = sc.nextLine();
            }
        }

        //+++++++++++++++++++++++++++
        //Creation of the arguments
        AbstractTidyingStrategy strategy = null;
        int numberToBeFrequent;
        int numberOfMonthsToBeFrequent;
        int numberOfMonthsToBeStandard;
        int numberOfMaximumBorrows;

        //+++++++++++++++++++++++++++
        //The users enters the desired values
        String cmd;
        System.out.println("Please select a sorting strategy : \n\tAnyFitStrategy\n\tBestBookcaseFitStrategy\n\tBestRoomFitStrategy\n\tBestShelfFitStrategy\n");
        boolean exit = false;
        while (!exit) {
            cmd = sc.nextLine();
            switch (cmd) {
                case "AnyFitStrategy":
                    strategy = new AnyFitStrategy();
                    exit = true;
                    break;
                case "BestBookcaseFitStrategy":
                    strategy = new BestBookcaseFitStrategy();
                    exit = true;
                    break;
                case "BestRoomFitStrategy":
                    strategy = new BestRoomFitStrategy();
                    exit = true;
                    break;
                case "BestShelfFitStrategy":
                    strategy = new BestShelfFitStrategy();
                    exit = true;
                    break;
                default:
                    System.out.println("Strategy not found. Please try again");
            }
        }
        System.out.println("Please enter the number of items one shall borrow on average to be granted Frequent :");
        numberToBeFrequent = sc.nextInt();
        System.out.println("Please enter the number of months on which is based the average borrow number :");
        numberOfMonthsToBeFrequent = sc.nextInt();
        System.out.println("Please enter the number of items one shall borrow on average to be deprived to Standard :");
        numberOfMonthsToBeStandard = sc.nextInt();
        System.out.println("Please enter the number of maximum borrows for a member :");
        numberOfMaximumBorrows = sc.nextInt();

        Library library = new Library(strategy, numberToBeFrequent, numberOfMonthsToBeFrequent, numberOfMonthsToBeStandard, numberOfMaximumBorrows,libraryName);
        UserInterface.addLibrary(library);
        System.out.println("Library "+libraryName+" successfully added.");

    }

    public static String list_libraries() {
        String list;
        if (!UserInterface.getLibraries().isEmpty()) {
            list = "The interface is linked to the following libraries : ";
            for (Library library : UserInterface.getLibraries()) {
                list += "\n\t" + library.getName();
            }
        } else list = "No library was found.";

        return list;
    }
}
