package lms_rozier_convers.core.card;

import lms_rozier_convers.core.Duration;
import lms_rozier_convers.core.items.LibraryItem;

import java.util.HashMap;

/**
 * A frequent member card : granted by the MemberObserver.checkStatus() method
 * if the number of borrowings of the linked member is sufficient.
 * 15/11/2015.
 */
public class FrequentMCard extends Card
{

    /**
     * CONSTRUCTOR
     * @param fineAmount
     * @param suspensionDuration
     * @param fineDuration
     * @param borrowingLengthPerItem
     */
    public FrequentMCard(double fineAmount, Duration suspensionDuration, Duration fineDuration, HashMap<LibraryItem, Duration> borrowingLengthPerItem) {
        super(fineAmount, suspensionDuration, fineDuration, borrowingLengthPerItem);
    }

    /**
     * CONSTRUCTOR
     * @param fineAmount
     * @param suspensionDuration
     * @param fineDuration
     * @param borrowingLengthPerItem
     * @param numberOfSimultaneousBorrows
     * @param name
     */
    public FrequentMCard(double fineAmount, Duration suspensionDuration, Duration fineDuration, HashMap<LibraryItem, Duration> borrowingLengthPerItem, int numberOfSimultaneousBorrows, String name) {
        super(fineAmount, suspensionDuration, fineDuration, borrowingLengthPerItem, numberOfSimultaneousBorrows, name);
    }
}
