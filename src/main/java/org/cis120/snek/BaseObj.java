package org.cis120.snek;

import java.awt.*;

public abstract class BaseObj {

    /*
     * Current position of the object (in terms of graphics coordinates)
     *
     * Coordinates are given by the upper-left hand corner of the object. This
     * position should always be within bounds:
     * 0 <= px <= maxX 0 <= py <= maxY
     */
    private int x;
    private int y;

    /* Size of object, in pixels. */
    private int width;
    private int height;



    private int maxX;
    private int maxY;

    /**
     * Constructor
     */
    public BaseObj(
            int px, int py, int width, int height, int courtWidth,
            int courtHeight
    ) {

        this.x = px;
        this.y = py;
        this.width = width;
        this.height = height;


        this.maxX = courtWidth - width;
        this.maxY = courtHeight - height;
    }

    /***
     * GETTERS
     **********************************************************************************/
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    /**************************************************************************
     * SETTERS
     **************************************************************************/
    public void setX(int x) {
        this.x = x;
        clip();
    }

    public void setY(int y) {
        this.y = y;
        clip();
    }



    /**************************************************************************
     * UPDATES AND OTHER METHODS
     **************************************************************************/

    /**
     * Prevents the object from going outside of the bounds of the area
     * designated for the object (i.e. Object cannot go outside of the active
     * area the user defines for it).
     */
    private void clip() {
        this.x = Math.min(Math.max(this.x, 0), this.maxX);
        this.y = Math.min(Math.max(this.y, 0), this.maxY);
    }



    /**
     * Determine whether this game object is currently intersecting another
     * object.
     *
     * Intersection is determined by comparing bounding boxes. If the bounding
     * boxes overlap, then an intersection is considered to occur.
     *
     * @param that The other object
     * @return Whether this object intersects the other object.
     */
    public boolean intersects(BaseObj that) {
        return (this.x + this.width >= that.x
                && this.y + this.height >= that.y
                && that.x + that.width >= this.x
                && that.y + that.height >= this.y);
    }



    public abstract void draw(Graphics g);
}
