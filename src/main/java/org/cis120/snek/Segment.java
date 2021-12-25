package org.cis120.snek;

import java.awt.*;

public class Segment extends BaseObj {

    public static final int SIZE = 25;


    private Color color;

    /**
     * Note that, because we don't need to do anything special when constructing
     * a Square, we simply use the superclass constructor called with the
     * correct parameters.
     */
    public Segment(int x, int y, int courtWidth, int courtHeight, Color color) {
        super(x, y, SIZE, SIZE, courtWidth, courtHeight);
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(25 * this.getX(), 25 * this.getY(), this.getWidth(), this.getHeight());
    }
}
