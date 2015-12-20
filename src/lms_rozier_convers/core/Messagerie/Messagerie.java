package lms_rozier_convers.core.Messagerie;

import lms_rozier_convers.core.member.Member;

/**
 * Abstract class which will have only one static function to send a message to the member.
 * It can later be improved with a mail management system.
 * 09/12/2015.
 */
public abstract class Messagerie {
    /**
     * Sends a message to the given member
     * @param member
     * @param message
     */
    public static void sendMessage(Member member, String message) {
        member.setMessage(message);
    }
}
