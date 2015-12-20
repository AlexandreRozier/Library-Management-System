package lms_rozier_convers.core.penalty;

import lms_rozier_convers.core.Duration;
import lms_rozier_convers.core.FactoryMaker;
import lms_rozier_convers.core.Messagerie.Messagerie;
import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.card.FrequentMCard;
import lms_rozier_convers.core.items.LibraryItem;
import lms_rozier_convers.core.member.Member;
import lms_rozier_convers.core.member.MemberStatus;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The strenght of the Penalty calculator is that he just needs an instance member. Thus, the addition of new
 * member types is invisible for it.
 * 16/11/2015.
 */
public abstract class PenaltyCalculator {
    private static double warningDelay = 7 * 24 * 3600 * 1000L;
    private static double suspensionDelay = 3 * 7 * 24 * 3600 * 1000L;
    private static double fineDelay = 6 * 7 * 24 * 3600 * 1000L;

    /**
     * Checks for a member and an item if the member is late for the return of the item. If this is the case,
     * it adds a penalty to the list of penalties of the member.
     * @param member
     * @param borrowedItem
     */
    public static void penalise(Member member, LibraryItem borrowedItem)
    {
        Card card = member.getMemberCard();
        Duration borrowDuration = new Duration(0);

        //We get the authorised borrowing duration corresponding to the item
        for (LibraryItem item : card.getBorrowingLengthPerItem().keySet()) {
            if(borrowedItem.getClass().equals(item.getClass())){
                borrowDuration = card.getBorrowingLengthPerItem().get(item);
            }
        }


        //We get the borrowing date
        Calendar borrowingDate = member.getBorrowedItems().get(borrowedItem);

        //We get the maximum borrowing date of return
        borrowingDate.add(Calendar.WEEK_OF_MONTH,borrowDuration.getWeeks());

        /**
         *We calculate the difference between the current date and the maximum return date. If this
         * value is positive, the client is late.
         */
        double delay = new GregorianCalendar().getTimeInMillis() - borrowingDate.getTimeInMillis();


        if (delay > fineDelay){
            Penalty finePenalty = new Penalty(MemberStatus.FINED, member.getMemberCard().getFineDuration());
            member.setBalance(member.getBalance() - member.getMemberCard().getFineAmount()); // Applies the fine directly
            member.getPenaltyList().add(finePenalty);
        }

        else if (delay > suspensionDelay){
            Penalty suspensionPenalty = new Penalty(MemberStatus.SUSPENDED,member.getMemberCard().getSuspensionDuration());
            member.getPenaltyList().add(suspensionPenalty);
        }

        else if (delay > warningDelay){
            Messagerie.sendMessage(member,"Warning ! Next time give back sooner your item.");
        }

        //Deranks the member if he is frequent and has a penalty
        if (member.getStatus().equals(MemberStatus.NORMAL) && member.getMemberCard() instanceof FrequentMCard) {
            if (delay > fineDelay || delay > suspensionDelay) {
                CardFactory cardFactory = (CardFactory) FactoryMaker.createFactory("cardFactory");
                Card standardCard = cardFactory.create("Standard");
                member.setMemberCard(standardCard);
            }
        }
    }

    /**
     * Applies penalties to the member if there are to.
     * @param member
     */
    public static void checkPenalty(Member member){
        if (member.getPenalisationDuration().getDays()== 0 && !member.getPenaltyList().isEmpty()){

            //The penalty which be applied to the member is the first of the queue. We remove it
            Penalty penaltyToApply = member.getPenaltyList().get(0);
            member.getPenaltyList().remove(0);
            member.setPenalisationDuration(penaltyToApply.getPenaltyDuration());
            member.setStatus(penaltyToApply.getType());
        }

        //If the member is no longer penalised, then his status goes back to normal
        if (member.getPenaltyList().isEmpty() && member.getPenalisationDuration().getDays()==0){
            member.setStatus(MemberStatus.NORMAL);
        }
    }

}
