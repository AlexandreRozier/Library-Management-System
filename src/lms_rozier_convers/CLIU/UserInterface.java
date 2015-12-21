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
        currentLibrary = selectedLibrary;
        librairies.add(selectedLibrary);
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
                    if (!commandAndParameters[1].isEmpty() && commandAndParameters[2].isEmpty()){
                        Actions.find_items(commandAndParameters[1]);
                    }
                    else{
                        System.out.println("You must type only one parameter when using this command : the name of author(s) of items searched.");
                    }

                    break;
                case("search_title"):
                    if (!commandAndParameters[1].isEmpty() && commandAndParameters[2].isEmpty()){
                        Actions.search_title(commandAndParameters[1]);
                    }
                    else{
                        System.out.println("You must type only one parameter when using this command : the title of items searched.");
                    }

                    break;

                case("add_member"):
                    if(!commandAndParameters[1].isEmpty() && !commandAndParameters[2].isEmpty() && commandAndParameters[3].isEmpty() && commandAndParameters[4].isEmpty()){
                        Actions.add_member(commandAndParameters[1],commandAndParameters[2],commandAndParameters[3],commandAndParameters[4]);
                    }
                    else{
                        System.out.println("You must type 4 parameters when using this command : the name of the member, his credit card number, his eamail and his type of membership.");
                    }
                    break;

                case("borrow_item"):
                    if (!commandAndParameters[1].isEmpty() && !commandAndParameters[2].isEmpty() && commandAndParameters[3].isEmpty()){
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



        //KEUR
    }
}
