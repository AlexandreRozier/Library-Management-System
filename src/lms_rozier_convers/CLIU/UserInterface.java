package lms_rozier_convers.CLIU;

import lms_rozier_convers.core.library.Library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by hx on 20/12/2015.
 */
public abstract class UserInterface {

    private static List<Library> librairies = new ArrayList<>(); // The managed librairies
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
     * @param selectedLibrary
     */
    public UserInterface(Library selectedLibrary) {
        this.currentLibrary = selectedLibrary;
        this.librairies.add(selectedLibrary);
        launch();
    }

    public static List<Library> getLibrairies() {
        return librairies;
    }

    public static Library getCurrentLibrary() {
        return currentLibrary;
    }

    private void launch() {
        boolean open = true;
        while(open){
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            //Parsing : we assume that the user will separate command and variables with a space (like in a Linux Console)
            String[] commandAndParameters ;
            commandAndParameters = str.split(" ",5);
            //TODO : faire des exceptions quand il n'y a pas le bon nombre de liste d'arguments

            switch (commandAndParameters[0]){
                case("use_library"):

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

                    break;
                case("list_room"):

                    break;
                case("list_bookcase"):

                    break;
                case("find_items"):

                    break;
                case("search_title"):

                    break;
                case("add_member"):

                    break;
                case("borrow_item"):
                    Actions.borrow_item(commandAndParameters[1],commandAndParameters[2]);
                    break;
                case("check_borrowed"):

                    break;
                case("exit"):
                    open=false;
                    break;
                default:
                    System.out.println("Invalid command. Please be attentive to use low case.");

            }
        }



        //KEUR
    }
}
