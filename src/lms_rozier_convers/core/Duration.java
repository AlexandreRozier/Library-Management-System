package lms_rozier_convers.core;

/**
 * This class represents a duration, and is used to manage the borrow durations
 * 16/11/2015.
 */
public class Duration {
    private int weeks;
    private int days;
    private int hours;

    /**
     * CONSTRUCTOR
     * @param weeks
     */
    public Duration(int weeks)
    {
        this.weeks = weeks;
        this.days = weeks * 7;
        this.hours = this.days*24;
    }

    public int getWeeks() {
        return weeks;
    }

    public void removeDays(int days) {
        this.days -= days;
        this.hours -= days * 24;
        this.weeks = (int) Math.ceil((double)this.days / 7);
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
