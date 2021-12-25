package org.cis120.snek;

import java.awt.*;

public abstract class Consumable extends BaseObj {
    public static final int SIZE = 25;

    private Color color;


    /**
     * Constructor
     *
     * @param px
     * @param py
     * @param courtWidth
     * @param courtHeight
     */
    public Consumable(int px, int py, int courtWidth, int courtHeight) {
        super(px, py, SIZE, SIZE, courtWidth, courtHeight);
        this.color = color;
    }


    @Override
    public abstract void draw(Graphics g);

    public abstract void wheneaten(Snake snake);

    public abstract String getID();

    public boolean intersects(BaseObj that) {
        return (this.getX() + this.getWidth() >= that.getX()
                && this.getY() + this.getHeight() >= that.getY()
                && that.getX() + that.getWidth() >= this.getX()
                && that.getY() + that.getHeight() >= this.getY());
    }
}
