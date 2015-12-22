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


    /**
     * CONSTRUCTOR
     * A constructor used to create an interface not linked to a library, for instance when
     * one wants to create a library via the CLUI.
     */
    public UserInterface() {
        launch();
    }

    /**
     * CONSTRUCTOR
     * A constructor used to launch an interface using an existing library
     * @param selectedLibrary The library that will be used
     */
    public UserInterface(Library selectedLibrary) {
        currentLibrary = selectedLibrary;
        libraries.add(selectedLibrary);
        launch();
    }


    public static void launch() {
        boolean open = true;
        if (currentLibrary != null) {
            System.out.println("Welcome to the User Interface of the  " + currentLibrary.getName()+" library.");
        }
        String[] commandAndParameters ;
        String str;
        while(open){
            //TODO implementer une fonction help qui liste les commandes
            System.out.println("Please enter a command :");
            Scanner sc = new Scanner(System.in);

            str = sc.nextLine();
            //Parsing : we assume that the user will separate command and variables with a space (like in a Linux Console)
            commandAndParameters = str.split(" ");
            //TODO : faire des exceptions quand il n'y a pas le bon nombre d'arguments

            switch (commandAndParameters[0]){
                case("use_library"):
                    if (commandAndParameters.length >= 2) {
                        String[] words = Arrays.copyOfRange(commandAndParameters, 1, commandAndParameters.length); // Creates a subarray with only the words of the research title
                        String libraryName = String.join(" ",words);
                        Actions.use_library(libraryName);
                    } else System.out.println("Invalid input. Ex : use_library Miterrand");
                    break;
                case("create_library"):

                    break;
                case("add_room"):

                    break;
                case("add_bookcase"):

                    break;
                case("add_item"):
                    break;
                case("store_items"):

                    break;
                case("unstore_items"):

                    break;
                case("list_items"):
                    if (commandAndParameters.length >= 2) {
                        String[] words = Arrays.copyOfRange(commandAndParameters, 1, commandAndParameters.length); // Creates a subarray with only the words of the research title
                        String libraryName = String.join(" ",words);
                        System.out.println(Actions.list_items(libraryName));
                    } else System.out.println("Invalid input. Ex : list_item Miterrand");
                    break;
                case("list_room"):

                    break;
                case("list_bookcase"):

                    break;
                case("find_items"):

                    if (commandAndParameters.length >= 2){
                        String[] words = Arrays.copyOfRange(commandAndParameters, 1, commandAndParameters.length); // Creates a subarray with only the words of the research title
                        String author = String.join(" ",words);
                        System.out.println(Actions.find_items(author));
                    }
                    else{
                        System.out.println("You must type only one parameter when using this command : the name of author(s) of items searched.");
                    }

                    break;

                case("search_title"):
                    if (commandAndParameters.length >= 2){
                        String[] words = Arrays.copyOfRange(commandAndParameters, 1, commandAndParameters.length); // Creates a subarray with only the words of the research title
                        String title = String.join(" ",words);
                        Actions.search_title(title);
                    }
                    else{
                        System.out.println("You must type only one parameter when using this command : the title of items searched.");
                    }

                    break;

                case("add_member"):
                    if (commandAndParameters.length >= 5) {
                        Actions.add_member(commandAndParameters[1], commandAndParameters[2], commandAndParameters[3], commandAndParameters[4]);
                    } else {
                        System.out.println("You must type 4 parameters when using this command : the name of the member, his credit card number, his email and his type of membership.");
                    }
                    break;

                case("borrow_item"):
                    if (commandAndParameters.length >= 3){
                        String[] words = Arrays.copyOfRange(commandAndParameters, 2, commandAndParameters.length); // Creates a subarray with only the words of the research title
                        String itemName = String.join(" ",words);
                        Actions.borrow_item(commandAndParameters[1],itemName);
                    }
                    else{
                        System.out.println("You must type 2 parameters when using this command : the name of the member and the name of the item.");
                    }

                    break;

                case("check_borrowed"):
                    if (!commandAndParameters[1].isEmpty() && commandAndParameters[2].isEmpty()){
                        Actions.check_borrowed(commandAndParameters[1]);
                    }
                    else{
                        System.out.println("You must type only one parameter when using this command : the name of the member checked.");
                    }


                    break;

                case("exit")://stop the running of the console.
                    open = false;
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
}
