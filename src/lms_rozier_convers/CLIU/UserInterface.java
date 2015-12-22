package lms_rozier_convers.CLIU;

import lms_rozier_convers.core.library.Library;

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
            System.out.println("Bienvenue dans le système de gestion de la librairie " + currentLibrary.getName());
        }
        String[] commandAndParameters ;
        String str;
        while(open){
            System.out.println("test");
            Scanner sc = new Scanner(System.in);

            str = sc.nextLine();
            //Parsing : we assume that the user will separate command and variables with a space (like in a Linux Console)
            commandAndParameters = str.split(" ");
            //TODO : faire des exceptions quand il n'y a pas le bon nombre d'arguments

            switch (commandAndParameters[0]){
                case("use_library"):
                    if (commandAndParameters.length >= 2) {
                        String[] words = Arrays.copyOfRange(commandAndParameters, 1, commandAndParameters.length-1); // Creates a subarray with only the words of the research title
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
                        String[] words = Arrays.copyOfRange(commandAndParameters, 1, commandAndParameters.length-1); // Creates a subarray with only the words of the research title
                        String libraryName = String.join(" ",words);
                        Actions.list_items(libraryName);
                    } else System.out.println("Invalid input. Ex : list_item Miterrand");
                    break;
                case("list_room"):

                    break;
                case("list_bookcase"):

                    break;
                case("find_items"):
                    if (commandAndParameters.length >= 2){
                        Actions.find_items(commandAndParameters[1]);
                    }
                    else{
                        System.out.println("You must type only one parameter when using this command : the name of author(s) of items searched.");
                    }

                    break;

                //TODO ya une couille pour les fonctions search : le nom du truc à chercher sera splité en token par split, et donc en fait le nom du truc a chercher sera
                //egal à la somme des commandAndParameters concaténés. Il faut réunir les token d'indices supérieur à 0 pour avoir le nom du truc à chercher, idem pour toutes les fonctions de search
                //TODO regarde ce que j'ai fait dans list_items du switch, ça marche normalement
                case("search_title"):
                    if (commandAndParameters.length >= 2){
                        Actions.search_title(commandAndParameters[1]);
                    }
                    else{
                        System.out.println("You must type only one parameter when using this command : the title of items searched.");
                    }

                    break;

                case("add_member"):
                    if (commandAndParameters.length >= 5) {
                        Actions.add_member(commandAndParameters[1], commandAndParameters[2], commandAndParameters[3], commandAndParameters[4]);
                    } else {
                        System.out.println("You must type 4 parameters when using this command : the name of the member, his credit card number, his eamail and his type of membership.");
                    }
                    break;

                case("borrow_item"):
                    if (commandAndParameters.length >= 3){
                        Actions.borrow_item(commandAndParameters[1],commandAndParameters[2]);
                    }
                    else{
                        System.out.println("You must type 2 parameters when using this command : the name of the member and the name of the item.");
                    }

                    break;

                case("check_borrowed"):

                    break;

                case("exit")://stop the running of the console.
                    open=false;
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
        System.out.println("No library with this name was found.");
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
