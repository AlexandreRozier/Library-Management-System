package lms_rozier_convers.core.member;

/**
 * The status of the member.
 * NB : the WARNED status isn't implemented because of its fleeting state;
 * a message is just send to the user to warn him.
 * 17/11/2015.
 */
public abstract class MemberStatus {
    public static String NORMAL = "Normal";
    public static String SUSPENDED = "Suspended";
    public static String FINED = "Fined";
}
