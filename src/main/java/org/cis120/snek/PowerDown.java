package org.cis120.snek;

import java.awt.*;
import java.util.LinkedList;

public class PowerDown extends Consumable {

    public static final int SIZE = 25;

    private Color color;
    private java.util.LinkedList<Segment> link;

    /**
     * Note that, because we don't need to do anything special when constructing
     * a Square, we simply use the superclass constructor called with the
     * correct parameters.
     */
    public PowerDown(int x, int y, int courtWidth, int courtHeight, Color color) {
        super(x, y, courtWidth, courtHeight);
        this.color = color;
    }

    public void wheneaten(Snake snake) {
        snake.emptysnake();

    }

    public String getID() {
        return "powerdown";
    }



    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(25 * this.getX(), 25 * this.getY(), this.getWidth(), this.getHeight());
    }


    public boolean intersects(BaseObj that) {
        return (this.getX() + this.getWidth() >= that.getX()
                && this.getY() + this.getHeight() >= that.getY()
                && that.getX() + that.getWidth() >= this.getX()
                && that.getY() + that.getHeight() >= this.getY());
    }
}
