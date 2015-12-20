package lms_rozier_convers.core.card;

import lms_rozier_convers.core.AbstractFactory;
import lms_rozier_convers.core.Duration;
import lms_rozier_convers.core.items.Book;
import lms_rozier_convers.core.items.CD;
import lms_rozier_convers.core.items.DVD;
import lms_rozier_convers.core.items.LibraryItem;

import java.util.HashMap;

/**
 * This class is charged to provide the client with a requested type of Card.
 * It has in its source code the properties of the various cards, so that adding new Cards respects the IN/OUT
 * principle.
 * 16/11/2015.
 */
public class CardFactory extends AbstractFactory {
    /**
     * Creates an instance of a specified library item.
     * It is easy to add new types of items, one just have to add it as a new key of the  Card attribute borrowingLengthPerItem.
     *
     * @param cardType
     * @return
     * @see Card
     * @see lms_rozier_convers.core.member.Member
     */
    public Card create(String cardType) {
        String name = cardType;
        Card cardObject = null;
        Book book = new Book();
        CD cd = new CD();
        DVD dvd = new DVD();
        Duration suspensionDuration;
        Duration fineDuration;
        HashMap<LibraryItem, Duration> borrowingDurationPerItem = new HashMap<>();
        double fineAmount;

        int maxSimultaneousBorrowings = 0; //This is the default number. Adding the card to a member will change this value


        switch (cardType) {

            case "Standard":

                borrowingDurationPerItem.put(book, new Duration(4));
                borrowingDurationPerItem.put(cd, new Duration(1));
                borrowingDurationPerItem.put(dvd, new Duration(2));
                suspensionDuration = new Duration(6);
                fineDuration = new Duration(10);
                fineAmount = 50;

                cardObject = new StandardMCard(fineAmount,suspensionDuration,fineDuration,borrowingDurationPerItem,maxSimultaneousBorrowings,cardType);
                break;

            case "Frequent":

                borrowingDurationPerItem.put(book, new Duration(4));
                borrowingDurationPerItem.put(cd, new Duration(1));
                borrowingDurationPerItem.put(dvd, new Duration(2));
                suspensionDuration = new Duration(3);
                fineDuration = new Duration(5);
                fineAmount = 30;

                cardObject = new FrequentMCard(fineAmount,suspensionDuration,fineDuration,borrowingDurationPerItem,maxSimultaneousBorrowings,cardType);
                break;

            case "Golden":

                borrowingDurationPerItem.put(book, new Duration(8));
                borrowingDurationPerItem.put(cd, new Duration(3));
                borrowingDurationPerItem.put(dvd, new Duration(4));
                suspensionDuration = new Duration(2);
                fineDuration = new Duration(3);
                fineAmount = 20;

                cardObject = new GoldenMCard(fineAmount,suspensionDuration,fineDuration,borrowingDurationPerItem,maxSimultaneousBorrowings,cardType );
                break;

            default:
                System.out.println("This type doesn't exists. Please try again.");
        }
        return cardObject;
    }
}
