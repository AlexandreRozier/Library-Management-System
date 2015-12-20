package lms_rozier_convers.core.library;

import lms_rozier_convers.core.member.Member;

import java.util.TimerTask;

/**
 * 24/11/2015.
 * Each instance is linked to a library, and is called each day to update all the members' statuses.
 *
 */
public class LibraryUpdater extends TimerTask {

    Library library;

    /**
     * CONSTRUCTOR
     * @param library
     */
    public LibraryUpdater(Library library) {
        this.library = library;
    }

    /**
     * Is called in a new thread, executed each day to update the penalty durations of all the members,
     * according to their penaltyList.
     */
    @Override
    public void run() {
        for (Member member : library.getMembers()) {
            member.notifyObservers();
            // If the member is penalised, removes one day to its penalisation
            if(member.getPenalisationDuration().getDays()!=0){
                member.getPenalisationDuration().removeDays(1);
            }

            member.notifyObservers();
        }
    }


    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
