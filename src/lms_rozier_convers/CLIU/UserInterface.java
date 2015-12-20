package lms_rozier_convers.CLIU;

import lms_rozier_convers.core.library.Library;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx on 20/12/2015.
 */
public class UserInterface {

    private List<Library> librairies = new ArrayList<>(); // The managed librairies
    private Library currentLibrary; // The library on which will be done the actions


    /**
     * CONSTRUCTO
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

    private void launch() {
        // TODO ARNAUD : implémenter ici le parsing, le switch, etc :3
        //KEUR
    }
}
