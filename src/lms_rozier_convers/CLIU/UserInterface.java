package lms_rozier_convers.CLIU;

import lms_rozier_convers.core.library.Library;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by hx on 20/12/2015.
 */
public abstract class UserInterface {

    private static List<Library> libraries = new ArrayList<>(); // The managed libraries
    private static Library currentLibrary; // The library on which will be done the actions


    public static void launch() {
        boolean open = true;
        if (currentLibrary != null) {
            System.out.println("Welcome to the User Interface of the  " + currentLibrary.getName()+" library. \n" +
                    "Remember to use the command help if you have any doubts about the commands.");
        }
        String command;
        String[] inputSplitted ;
        String str;
        while(open){
            System.out.println("Please enter a command :");
            Scanner sc = new Scanner(System.in);

            str = sc.nextLine();
            //Parsing : we assume that the user will separate command and variables with a space (like in a Linux Console)
            inputSplitted = str.split(" ");
            command = inputSplitted[0];
            // We get rid of the command, and keep only the parameters
            String[] parametersSplitted= Arrays.copyOfRange(inputSplitted, 1, inputSplitted.length);
            String parametersInString = String.join(" ", parametersSplitted);
            // The last split allows to keep only the parameters, even if they contain spaces
            String[] parameters = parametersInString.split(",");

            //TODO : faire des exceptions quand il n'y a pas le bon nombre d'arguments!

            switch (command){
                case ("list_libraries"):
                    System.out.println(Actions.list_libraries());
                    break;
                case("use_library"):
                    if (parameters.length == 1) {
                        String libraryName = parameters[0];
                        Actions.use_library(libraryName);
                    } else System.out.println("Invalid input. Ex : use_library Miterrand");
                    break;
                case("create_library"):
                    if (parameters.length == 1) {
                        String libraryName = parameters[0];
                        Actions.create_library(libraryName);
                    } else System.out.println("Invalid input. Ex : create_library Miterrand");

                    break;
                case("add_room"):
                    if (parameters.length == 1) {
                        String libraryName = parameters[0];
                        Actions.add_room(libraryName);
                    } else System.out.println("Invalid input. Ex : add_room Miterrand");

                    break;
                case("add_bookcase"):
                    if (parameters.length == 3) {
                        String libraryName = parameters[0];
                        int num_shelves = Integer.valueOf(parameters[1]);
                        String room_name = parameters[2];
                        Actions.add_bookcase(libraryName,num_shelves,room_name);
                    } else System.out.println("Invalid input. Ex : add_bookcase Miterrand,8,bookcase_name");
                    break;

                case("add_item"):
                    if (parameters.length == 6) {
                        String author = parameters[0];
                        String title = parameters[1];
                        String item_type = parameters[2];
                        int date = Integer.valueOf(parameters[3]);
                        String publisher = parameters[4];
                        String library_name = parameters[5];

                        Actions.add_item(author, title, item_type, date, publisher, library_name);
                    } else System.out.println("Invalid input. Ex : add_item Albert Camus,Les Fleurs du Mal,Book,1857,Auguste Poulet-Malassis,Miterrand");
                    break;

                case("store_items"):
                    if (parameters.length == 2) {
                        String libraryName = parameters[0];
                        String strategy_name = parameters[1];

                        Actions.store_items(libraryName, strategy_name);
                    }else System.out.println("Invalid input : Ex : store_items Miterrand,AnyFitStrategy");

                    break;
                case("unstore_items"):

                    break;
                //TODO compatibiliser ce qui est en dessous avec les nouveaux arguments
                case("list_items"):
                    if (inputSplitted.length >= 2) {
                        String[] words = Arrays.copyOfRange(inputSplitted, 1, inputSplitted.length); // Creates a subarray with only the words of the library name
                        String libraryName = String.join(" ",words);
                        System.out.println(Actions.list_items(libraryName));
                    } else System.out.println("Invalid input. Ex : list_item Miterrand");
                    break;


                case("list_room"):
                    if(inputSplitted.length>=2){
                        String[] words = Arrays.copyOfRange(inputSplitted,1,inputSplitted.length);
                        String room_name = String.join(" ", words);
                        Actions.list_room(room_name);
                    }
                    else{
                        System.out.println("Invalid input.");
                    }
                    break;


                case("list_bookcase"):
                    if(inputSplitted.length>=3){
                        String[] words = Arrays.copyOfRange(inputSplitted,2,inputSplitted.length);
                        String room_name = inputSplitted[1];
                        String bookcase_name = String.join(" ", words);
                        Actions.list_bookcase(room_name,bookcase_name);
                    }
                    else{
                        System.out.println("Invalid input");
                    }

                    break;
                case("find_items"):
                    if (inputSplitted.length >= 2){
                        String[] words = Arrays.copyOfRange(inputSplitted, 1, inputSplitted.length); // Creates a subarray with only the words of the research title
                        String author = String.join(" ",words);
                        Actions.find_items(author);
                    }
                    else{
                        System.out.println("You must type only one parameter when using this command : the name of author(s) of items searched.");
                    }

                    break;

                case("search_title"):
                    if (inputSplitted.length >= 2){
                        String[] words = Arrays.copyOfRange(inputSplitted, 1, inputSplitted.length); // Creates a subarray with only the words of the research title
                        String title = String.join(" ",words);
                        Actions.search_title(title);
                    }
                    else{
                        System.out.println("You must type only one parameter when using this command : the title of items searched.");
                    }

                    break;

                case("add_member"):
                    if (inputSplitted.length >= 5) {
                        Actions.add_member(inputSplitted[1], inputSplitted[2], inputSplitted[3], inputSplitted[4]);
                    } else {
                        System.out.println("You must type 4 parameters when using this command : the name of the member, his credit card number, his email and his type of membership.");
                    }
                    break;

                case("borrow_item"):
                    if (inputSplitted.length >= 3){
                        String[] words = Arrays.copyOfRange(inputSplitted, 2, inputSplitted.length); // Creates a sub-array with only the words of the research title
                        String itemName = String.join(" ",words);
                        Actions.borrow_item(inputSplitted[1],itemName);
                    }
                    else{
                        System.out.println("You must type 2 parameters when using this command : the name of the member and the name of the item.");
                    }

                    break;

                case("check_borrowed"):
                    if (!inputSplitted[1].isEmpty() && inputSplitted[2].isEmpty()){
                        Actions.check_borrowed(inputSplitted[1]);
                    }
                    else{
                        System.out.println("You must type only one parameter when using this command : the name of the member checked.");
                    }


                    break;

                case("exit")://stop the running of the console.
                    open = false;
                    sc.close();
                    break;

                case("help"):
                    String descr = "\nUse this console to control your libraries. Please note that you are controlling only one library when using the commands." +
                            "Here are the commands you can use : \n\n" +
                            "exit will close the console.\n" +
                            "list_libraries will give you the lists of libraries you can use.\n" +
                            "use_library <library_name> will attribute the given library to the current library.\n" +
                            "create_library <library_name> will create a library with the given name.\n" +
                            "add_room <library_name> will add a room to the specified library.\n" +
                            "add_bookcase <library_name, num_shelves, room name> will add a bookcase to the specified library, with the specified number of shelves. \n" +
                            "add_item <author_name,title,item_type,year,publisher,library_name> will add an item to the current library, with the specified description. \n" +
                            "store_item <library_name,storing_strategy> will change the sorting strategy of the given library and store the items according to the new strategy.\n" +
                            "unstore_iem <library_name> will remove all items stored in the specified library and place them into the storage box.\n" +
                            "list_items <library_name> will list all the items of the specified library.\n" +
                            "list_room <room_name> will list all bookcases and their contents of the specified room.\n"+
                            "list_bookcase <room_name,bookcase_name> will list all shelves of bookcase specified in the room specified.\n" +
                            "find items <author_name> will find all items whose author is the specified author.\n" +
                            "search_title <title_name> will find all items whose title is the specified title.\n" +
                            "add_member<member_name,ccard_num,email,membership_type> will create and add a member to the current library with the given data.\n" +
                            "borrow_item <member_name,item_title> will let the member specified borrow the item if this is possible, and will do the appropriate actions otherwise.\n" +
                            "check_borrowed <member_name> will give you information about the situation of the member. \n\n" +
                            "Please be careful to separate the command from your arguments with a space and each " +
                            "command from the other with a virgule (,).\n";
                    System.out.println(descr);
                    break;

                default:
                    System.out.println("Invalid command. Please be attentive to use low case.");

            }
        }




    }

    public static Library getLibraryByName(String libraryName) {

        if (currentLibrary.getName().equals(libraryName)) {
            return currentLibrary;
        }
        for (Library library : libraries) {
            if (library.getName().equals(libraryName))
            {
                return library;
            }
        }
        System.out.println("No library with this name was found :"+libraryName);
        return null;
    }





    public static List<Library> getLibraries() {
        return libraries;
    }

    public static Library getCurrentLibrary() {
        return currentLibrary;
    }

    public static void setLibraries(List<Library> libraries) {
        UserInterface.libraries = libraries;
    }

    public static void setCurrentLibrary(Library currentLibrary) {
        UserInterface.currentLibrary = currentLibrary;
    }

    public static void addLibrary(Library currentLibrary) {
        UserInterface.getLibraries().add(currentLibrary);

    }
}
