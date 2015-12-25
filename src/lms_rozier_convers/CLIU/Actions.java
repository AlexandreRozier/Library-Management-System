package lms_rozier_convers.CLIU;

import lms_rozier_convers.core.FactoryMaker;
import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.exceptions.ObjectNotFoundException;
import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.items.ItemFactory;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.library.*;
import lms_rozier_convers.core.member.Member;
import lms_rozier_convers.core.tidying.*;

import java.util.*;

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
        for (LibraryItem libraryItem : library.getStorageBox().getItems()) {
            itemList.add(libraryItem);
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
            if (m.getName()!=null) {
                if (m.getName().equals(member_name)) {
                    member = m;
                }
            }
        }
        return member;
    }

    /**
     * Method which returns a LibraryItem object from a given name
     * If the item does not exist, returns a null object.
     *
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
     *
     * @param item
     * @return
     */
    public static String itemborrowable(LibraryItem item) {
        String state;
        if (item.isBorrowable()) {
            state = "This item is borrowable.";
        } else {
            state = "This item is not borrowable.";
        }
        return state;
    }


    /**
     * Check the borrowing situation of the member. (The member is automatically penalized if needed)
     *
     * @param member_name
     */
    public static void check_borrowed(String member_name) {
        Member member = Actions.findMember(member_name);
        if (member == null) {
            return;
        }
        Library currentLibrary = UserInterface.getCurrentLibrary();
        String descr = member_name + " is currently borrowing the items : ";
        for (LibraryItem item : member.getBorrowedItems().keySet()) {
            descr += item.getTitle() + "(" + item.getType() + ")" + "\n";
        }

        ArrayList<LibraryItem> reservationList = new ArrayList<LibraryItem>();
        for (LibraryItem item : currentLibrary.getItemsLinkedToTheLibrary()) {
            if (item.getReservationList().contains(member)) {
                reservationList.add(item);
            }
        }
        descr += member_name + " has in his reservation list : \n";
        for (LibraryItem item : reservationList) {
            descr += item.getTitle() + "(" + item.getType() + ")" + "\n";
        }

        descr += "The current status of the member is " + member.getStatus();

        System.out.println(descr);
    }

    /**
     * If it is possible, let the member borrow the specified item. Display a message describing the result.
     *
     * @param member_name
     * @param item_title
     * @return
     */
    public static void borrow_item(String member_name, String item_title) {
        LibraryItem item = Actions.findLibraryItem(item_title);
        Member member = Actions.findMember(member_name);
        if (member == null || item == null) {
            System.out.println("The member or the item does not exist in this library, please retype your command properly");
        } else {
            member.getMemberCard().borrow(item);
            if (member.getBorrowedItems().containsKey(item)) {
                System.out.println(item.getTitle() + " has been borrowed by " + member.getName());
            } else if (item.getReservationList().contains(member)) {
                System.out.println(item.getTitle() + " was not available, it has been added to the reservation list.");
            } else {
                System.out.println(member.getName() + "cannot borrow the item " + item.getTitle());
            }
        }
    }

    /**
     * Add a member to the current library
     *
     * @param member_name
     * @param numCreditCard
     * @param email
     * @param memberType
     * @return
     */
    public static void add_member(String member_name, String numCreditCard, String email, String memberType) {
        if (memberType.equals("Standard")||memberType.equals("Frequent")||memberType.equals("Golden")){
            if (email.contains("@")) {
                Library currentLibrary = UserInterface.getCurrentLibrary();
                if (currentLibrary == null) {
                    System.out.println("Please define a current library before adding a member.");
                    return;
                }
                    Member member1 = new Member();
                    member1.setName(member_name);
                    member1.setCurrentLibrary(currentLibrary);
                    member1.setCreditCardNumber(numCreditCard);
                    member1.setEmail(email);
                    CardFactory cardFactory = (CardFactory) FactoryMaker.createFactory("cardFactory");
                    Card card = cardFactory.create(memberType);
                    member1.setMemberCard(card);
                    System.out.println("The member " + member_name + " has been added to the library");

            } else {
                System.out.println("Please type a correct email. Please renew the command");
            }
        }
        else{
            System.out.println("Please type a correct type of card (Standard, Golden)");
        }
    }

    /**
     * List all items whose title is title_name
     * @param title_name
     */
    public static void search_title(String title_name) {
        Library library = UserInterface.getCurrentLibrary();
        ArrayList<LibraryItem> items_to_print = new ArrayList<>();
        for (Room room : library.getRooms()) {
            for (Bookcase bookcase : room.getBookcases()) {
                for (Shelf shelf : bookcase.getShelves()) {
                    for (LibraryItem item : shelf.getItemsContained()) {
                        if (item.getTitle().equalsIgnoreCase(title_name)) {
                            items_to_print.add(item);
                        }
                    }
                }
            }
        }
        String descr = "";
        for (LibraryItem item : items_to_print) {
            descr += "Author(s) : " + item.getAuthors() + ", Year : " + item.getYear() + ", Type : " + item.getType() + ". " + itemborrowable(item) + "\n";
        }
        if(descr.isEmpty()){
            descr = "No item with the title "+title_name+" were found.";
        }
        System.out.println(descr);
    }


    /**
     * List all items whose author is author_name
     * @param author_name
     */
    public static void find_items(String author_name) {
        Library library = UserInterface.getCurrentLibrary();
        ArrayList<LibraryItem> items_to_print = new ArrayList<>();
        for (Room room : library.getRooms()) {
            for (Bookcase bookcase : room.getBookcases()) {
                for (Shelf shelf : bookcase.getShelves()) {
                    for (LibraryItem item : shelf.getItemsContained()) {
                        if (item.getAuthors().contains(author_name)) {
                            items_to_print.add(item);
                        }
                    }
                }
            }
        }
        String descr = "";
        int i = 1;
        for (LibraryItem item : items_to_print) {
            descr += "Item n°" + i + " Title : " + item.getTitle() + ", Year : " + item.getYear() + ", Type : " + item.getType() + ". " + itemborrowable(item) + "\n";
            i++;
        }
        if(descr.isEmpty()){
            descr = "No item with the author "+author_name+" were found.";
        }
        System.out.println(descr);
    }

    /**
     * list all shelves with their content of of bookshelf bookcase_name in the room room_name.
     *
     * @param room_name
     * @param bookcase_name
     */
    public static void list_bookcase(String room_name, String bookcase_name) {
        Library currentLibrary = UserInterface.getCurrentLibrary();
        Room room = null;
        for (Room r : currentLibrary.getRooms()) {
            if (r.getName().equalsIgnoreCase(room_name)) {
                room = r;
            }
        }
        if (room != null) {
            Bookcase bookcase = null;
            for (Bookcase bk : room.getBookcases()) {
                if (bk.getName().equalsIgnoreCase(bookcase_name)) {
                    bookcase = bk;
                }
            }
            if (bookcase != null) {
                String descr = "In the room " + room_name + " and in the bookcase " + bookcase_name + ", there are the following shelves : \n";
                for (Shelf shelf : bookcase.getShelves()) {
                    descr += "The self " + shelf.getName() + "contains : \n";
                    for (LibraryItem item : shelf.getItemsContained()) {
                        descr += item.getTitle() + "(" + item.getType() + ")";
                    }
                }
                System.out.println(descr);
            } else {
                System.out.println("This bookcase does not exist in this room. Please try again");
            }
        } else {
            System.out.println("This room does not exist in the library. Please try again.");
        }
    }

    /**
     * List all bookcases with their content in room room_name.
     * @param room_name
     */
    public static void list_room(String room_name) {
        Library currentLibrary = UserInterface.getCurrentLibrary();
        Room room = null;
        for (Room r : currentLibrary.getRooms()) {
            if (r.getName().equalsIgnoreCase(room_name)) {
                room = r;
            }
        }
        if (room != null) {
            String descr = "The room contains the following bookcases : ";
            for (Bookcase bk : room.getBookcases()) {
                descr += bk.getName() + "\n";
            }
            descr += "\n Each bookcase contains the following items : ";
            for (Bookcase bk : room.getBookcases()) {
                descr += "For " + bk.getName() + " : ";
                for (Shelf s : bk.getShelves()) {
                    for (LibraryItem item : s.getItemsContained()) {
                        descr += item.getTitle() + "(" + item.getType() + ", in the shelf" + item.getLocation().getShelf().getName() + ") \n";
                    }
                }
            }
            System.out.println(descr);
        } else {
            System.out.println("This room does not exist in the library. Please try again.");
        }
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
            System.out.println("This library (" + libraryName + ")is already selected !");
        } else System.out.println("Library not found");
    }

    //TODO dire si la library existe déjà
    //TODO vérifier que la library current est toujours dans libraries

    /**
     * This method creates a new library, but doesn't select it :
     * it is necessary to call "use_library" to use the new library (just like in SQL)
     *
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

        Library library = new Library(strategy, numberToBeFrequent, numberOfMonthsToBeFrequent, numberOfMonthsToBeStandard, numberOfMaximumBorrows, libraryName);
        UserInterface.addLibrary(library);
        //Adds an updater to the library
        Timer timer = new Timer();
        timer.schedule(new LibraryUpdater(library), 24 * 3600 * 1000);
        System.out.println("Library " + libraryName + " successfully added.");

    }

    /**
     * Lists the items contained in the given library
     *
     * @return the list of items
     */
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

    /**
     * Adds a room with a given name (provided by user input) to the selected library
     *
     * @param libraryName the name of the library
     */
    public static void add_room(String libraryName) {
        Library library = null;
        for (Library lib : UserInterface.getLibraries()) {
            if (lib.getName().equals(libraryName)) {
                library = lib;
            }
        }
        if (library != null) {
            System.out.println("Please enter a name for the room :");
            Scanner sc = new Scanner(System.in);
            String roomName = sc.nextLine();
            boolean alreadyExists = true;
            while (alreadyExists) {
                alreadyExists = false;
                for (Room room : library.getRooms()) {
                    if (room.getName().equals(roomName)) {
                        alreadyExists = true;
                    }
                }

                if (alreadyExists) {
                    System.out.println("The given name already exists ! Please choose another one.");
                    roomName = sc.nextLine();
                }
            }
            library.addRoom(new Room(roomName));
            System.out.println("The room " + roomName + " was added successfully to the library " + libraryName);
        } else System.out.println("Library not found, please try again.");
    }

    /**
     * Adds a bookcase to the given room in the given library
     *
     * @param libraryName the library's name
     * @param num_shelves the number of shelves in the bookcase
     * @param room_name   the name of the room containing the bookcase
     */
    public static void add_bookcase(String libraryName, int num_shelves, String room_name) {
        Library library = null;
        for (Library lib : UserInterface.getLibraries()) {
            if (lib.getName().equals(libraryName)) {
                library = lib;
            }
        }
        if (library != null) {
            Room selectedRoom = null;
            for (Room room : library.getRooms()) {
                if (room.getName().equals(room_name)) {
                    selectedRoom = room;
                }
            }
            if (selectedRoom != null) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter the name of the bookcase");
                String bookcaseName = sc.nextLine();
                System.out.println("Please enter the length of the bookcase");
                double length = sc.nextInt();
                System.out.println("Please enter the width of the bookcase");
                double width = sc.nextInt();
                System.out.println("Please enter the heigh of the bookcase");
                double heigh = sc.nextInt();
                Bookcase bookcase = new Bookcase(bookcaseName);
                for (int i = 0; i < num_shelves; i++) {
                    bookcase.addShelf(new Shelf(new Cuboid(length, heigh / num_shelves, width / num_shelves), bookcaseName + "_" + i)); // Creation of the desired number of shelves
                }
                selectedRoom.addBookcase(bookcase);
                System.out.println("Bookcase " + bookcaseName + " successfully added in the room " + room_name + ".");
            } else System.out.println("Room not found in the library " + libraryName + " , please try again.");
        } else System.out.println("Library not found, please try again.");
    }

    public static void add_item(String author, String title, String item_type, int date, String publisher, String library_name) {
        Library library = null;
        for (Library lib : UserInterface.getLibraries()) {
            if (lib.getName().equals(library_name)) {
                library = lib;
            }
        }
        if (library != null) {
            Scanner sc = new Scanner(System.in);
            ItemFactory factory = (ItemFactory) FactoryMaker.createFactory("itemFactory");
            ArrayList<String> authors = new ArrayList<>();
            authors.add(author);
            System.out.println("Please add the volume number :");
            int volumeNumber = sc.nextInt();
            System.out.println("Please tell if the item is borrowable (y/n)");
            sc.nextLine();
            String cmd = sc.nextLine();
            boolean isBorrowable = true;
            switch (cmd) {
                case "y":
                    isBorrowable = true;
                    break;
                case "n":
                    isBorrowable = false;
                    break;
                default:
                    System.out.println("Command not understood, the item will be set by default borrowable.");
            }
            System.out.println("Please enter the length of the item :");
            double length = sc.nextDouble();
            System.out.println("Please enter the width of the item :");
            double width = sc.nextDouble();
            System.out.println("Please enter the height of the item :");
            double heigth = sc.nextDouble();
            LibraryItem item;
            if (item_type.equals("Book")) {
                System.out.println("Please enter the ISBN number of the item :");
                sc.nextLine();
                String isbn = sc.nextLine();
                item = factory.createItem(item_type, title, authors, publisher, date, volumeNumber, isBorrowable, new Cuboid(length, heigth, width), isbn);
            } else {
                item = factory.createItem(item_type, title, authors, publisher, date, volumeNumber, isBorrowable, new Cuboid(length, heigth, width));

            }
            library.getStorageBox().addItem(item);
            System.out.println("Item added successfully to the library " + library_name);
        } else System.out.println("Library not found, please try again.");
    }

    public static void store_items(String libraryName, String strategy_name) {
        Library library = null;
        for (Library lib : UserInterface.getLibraries()) {
            if (lib.getName().equals(libraryName)) {
                library = lib;
            }
        }
        if (library == null) {
            System.out.println("Library not found, please try again.");
            return;
        }
        Scanner sc = new Scanner(System.in);
        LibraryItem[] itemsToStore = new LibraryItem[library.getStorageBox().getItems().size()];

        for (int i =0;i<library.getStorageBox().getItems().size();i++) {
            itemsToStore[i] = library.getStorageBox().getItems().get(i);
        }

        AbstractTidyingStrategy strategy = null;
        switch (strategy_name) {

            case "AnyFitStrategy":
                strategy = new AnyFitStrategy();
                library.setTidyingStrategy(strategy);
                for (LibraryItem libraryItem : itemsToStore) {
                    library.getTidyingStrategy().tidy(libraryItem);
                    library.getStorageBox().getItems().remove(libraryItem);
                }
                break;

            case "BestBookcaseFitStrategy":
                strategy = new BestBookcaseFitStrategy();
                library.setTidyingStrategy(strategy);
                for (LibraryItem libraryItem : itemsToStore) {

                    System.out.println("Please enter the name of the bookcase in which store "+libraryItem.getTitle());
                    String bookcaseName = sc.nextLine();
                    Bookcase bookcase = library.findBookcaseByName(bookcaseName);

                    try {
                        library.getTidyingStrategy().tidy(libraryItem,bookcase);
                        library.getStorageBox().getItems().remove(libraryItem);

                    } catch (ObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "BestRoomFitStrategy":
                strategy = new BestRoomFitStrategy();
                library.setTidyingStrategy(strategy);

                for (LibraryItem libraryItem : itemsToStore) {

                    System.out.println("Please enter the name of the room in which store "+libraryItem.getTitle());
                    String roomName = sc.nextLine();
                    Room room = library.findRoomByName(roomName);

                    try {
                        library.getTidyingStrategy().tidy(libraryItem,room);
                        library.getStorageBox().getItems().remove(libraryItem);


                    } catch (ObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "BestShelfFitStrategy":
                strategy = new BestShelfFitStrategy();
                library.setTidyingStrategy(strategy);
                for (LibraryItem libraryItem : itemsToStore) {
                    library.getTidyingStrategy().tidy(libraryItem);
                    library.getStorageBox().getItems().remove(libraryItem);

                }

                break;
            default:
                System.out.println("Strategy not found. Please try again");
                return;
        }
        System.out.println("Tidying done successfully ! The following items were not sorted : ");
        String itemsRemaining = "";
        for (LibraryItem libraryItem : library.getStorageBox().getItems()) {
            itemsRemaining += libraryItem;
        }
        System.out.println(itemsRemaining);


    }

    public static void unstore_items(String libraryName) {
        Library library = null;
        for (Library lib : UserInterface.getLibraries()) {
            if (lib.getName().equals(libraryName)) {
                library = lib;
            }
        }
        if (library == null) {
            System.out.println("Library not found, please try again.");
            return;
        }
        for (Room room : library.getRooms()) {
            for (Bookcase bookcase : room.getBookcases()) {
                for (Shelf shelf : bookcase.getShelves()) {
                    ArrayList<LibraryItem> itemsToUnstore = new ArrayList<>();
                    for (LibraryItem libraryItem : shelf.getItemsContained()) {
                        itemsToUnstore.add(libraryItem);
                    }
                    for (LibraryItem libraryItem : itemsToUnstore) {
                        shelf.removeItem(libraryItem);
                        library.getStorageBox().addItem(libraryItem);
                    }
                }
            }
        }
        System.out.println("Each item has successfully been stored in the storage box");
    }
}

