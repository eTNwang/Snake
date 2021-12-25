package org.cis120.snek;

import java.awt.*;
import java.util.LinkedList;

public class Snake {

    LinkedList<Segment> body = new LinkedList<>();
    int dir = 0;
    Segment next;
    int thecourtwidth = 0;
    int thecourtheight = 0;

    // 0 = up, 1 = down, 2 = left, 3 = right


    public Snake(int x, int y, int courtwidth, int courtheight) {
        next = new Segment(x, y, courtwidth, courtheight, Color.getColor("black"));
        body.addFirst(next);

        thecourtwidth = courtwidth;
        thecourtheight = courtheight;

    }

    public int length() {
        return body.size();
    }

    public void emptysnake() {
        body.clear();
    }

    public void addseg(Segment seg) {
        body.add(seg);
    }


    public LinkedList<Segment> returnlist() {
        return body;
    }

    public int snakelength() {
        return body.size();
    }

    public Segment nextsegment() {
        Segment nextseg = new Segment(0, 0, thecourtwidth, thecourtheight, Color.getColor("black"));
        int currx = body.getLast().getX();
        int curry = body.getLast().getY();
        if (dir == 0) {
            nextseg = new Segment(currx, curry - 1,
                    thecourtwidth, thecourtheight, Color.getColor("black"));
        } else if (dir == 1) {
            nextseg = new Segment(currx, curry + 1,
                    thecourtwidth, thecourtheight, Color.getColor("black"));
        } else if (dir == 2) {
            nextseg = new Segment(currx - 1, curry,
                    thecourtwidth, thecourtheight, Color.getColor("black"));
        } else if (dir == 3) {
            nextseg = new Segment(currx + 1, curry,
                    thecourtwidth, thecourtheight, Color.getColor("black"));
        }
        return nextseg;
    }


    public int getdir() {
        return this.dir;
    }

    public Segment gethead() {
        return body.getLast();
    }

    public void setdir(int newdir) {
        this.dir = newdir;
    }

    public void setbody(LinkedList<Segment> input) {
        this.body = input;
    }

    public void move(Segment nextseg) {
        body.add(nextseg);
        body.removeFirst();
    }

    public void getLonger() {
        body.add(next);
    }

}
