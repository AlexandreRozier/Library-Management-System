package lms_rozier_convers.core.card;

import lms_rozier_convers.core.Duration;
import lms_rozier_convers.core.items.LibraryItem;

import java.util.HashMap;

/**
 * 16/11/2015.
 * The standard card, provided automatically at each new member.
 */
public class StandardMCard extends Card {
    /**
     * CONSTRUCTOR
     *
     * @param fineAmount
     * @param suspensionDuration
     * @param fineDuration
     * @param borrowingLengthPerItem
     */
    public StandardMCard(double fineAmount, Duration suspensionDuration, Duration fineDuration, HashMap<LibraryItem, Duration> borrowingLengthPerItem) {
        super(fineAmount, suspensionDuration, fineDuration, borrowingLengthPerItem);
    }

    /**
     * CONSTRUCTOR
     *
     * @param fineAmount
     * @param suspensionDuration
     * @param fineDuration
     * @param borrowingLengthPerItem
     * @param numberOfSimultaneousBorrows
     * @param name
     */
    public StandardMCard(double fineAmount, Duration suspensionDuration, Duration fineDuration, HashMap<LibraryItem, Duration> borrowingLengthPerItem, int numberOfSimultaneousBorrows, String name) {
        super(fineAmount, suspensionDuration, fineDuration, borrowingLengthPerItem, numberOfSimultaneousBorrows, name);
    }
}
