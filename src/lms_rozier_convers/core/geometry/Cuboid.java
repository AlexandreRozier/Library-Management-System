package lms_rozier_convers.core.geometry;

/**
 * 18/11/2015.
 * The Cuboid class, which will represents the shape of each item and of shelves and others spaces of storing.
 *
 */

public class Cuboid {
    private double length;
    private double heigth;
    private double width;

    /**
     * CONSTRUCTOR
     * @param length
     * @param heigth
     * @param width
     */
    public Cuboid(double length, double heigth, double width){
        this.heigth=heigth;
        this.length=length;
        this.width=width;
    }
    /**
     * Tells whether the current item can fit into the given cuboid
     * @param cuboid
     * @return
     */
    public boolean canFit(Cuboid cuboid)
    {

        if ((heigth <= cuboid.getHeigth()) && (length <= cuboid.getLength()) && (width <= cuboid.getWidth())) {
            return true;
        }
        return false;
    }

    public double getVolume(){
        double v = this.heigth*this.length*this.width;
        return v;
    }


    public double getLength() {
        return length;
    }

    public double getHeigth() {
        return heigth;
    }

    public double getWidth() {
        return width;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setHeigth(double heigth) {
        this.heigth = heigth;
    }

    public void setWidth(double width) {
        this.width = width;
    }

}
