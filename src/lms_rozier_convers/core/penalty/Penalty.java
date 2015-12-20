package lms_rozier_convers.core.penalty;

import lms_rozier_convers.core.Duration;

/**
 * An object in which we have the attributes of a penalty.
 * 08/12/2015.
 */
public class Penalty {

    private String type;
    private Duration penaltyDuration;

    /**
     * CONSTRUCTOR
     * @param type
     * @param penaltyDuration
     */
    public Penalty(String type, Duration penaltyDuration) {
        this.type = type;
        this.penaltyDuration = penaltyDuration;
    }

    //Getters and setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Duration getPenaltyDuration() {
        return penaltyDuration;
    }

    public void setPenaltyDuration(Duration penaltyDuration) {
        this.penaltyDuration = penaltyDuration;
    }
}
