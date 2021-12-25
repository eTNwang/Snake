package org.cis120.snake;

import org.cis120.snek.Snake;
import org.cis120.snek.Segment;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.*;

public class SnakeTest {
    private Snake snake = new Snake(15, 15, 30, 30);

    @Test
    public void testposget() {
        assertEquals(15, snake.gethead().getX());
        assertEquals(15, snake.gethead().getY());
    }
    @Test
    public void testnextsegment() {
        Segment next = snake.nextsegment();
        assertEquals(next.getY(), snake.nextsegment().getY());
        assertEquals(next.getX(), snake.nextsegment().getX());
    }

    @Test
    public void testsetters() {
        assertEquals(0, snake.getdir());
        snake.setdir(2);
        assertEquals(2, snake.getdir());
    }



    @Test
    public void testlenget() {
        snake.getLonger();
        assertEquals(2, snake.length());
    }

    @Test
    public void testlenmove() {
        Segment next = snake.nextsegment();
        snake.move(next);
        assertEquals(1, snake.length());
    }

    @Test
    public void testforwardbackmove() {
        Segment next = snake.nextsegment();
        snake.setdir(0);
        snake.move(next);
        snake.setdir(1);
        snake.move(next);
        assertEquals(15, snake.gethead().getX());
        assertEquals(14 , snake.gethead().getY());
    }

    @Test
    public void testposgetaftermove() {
        Segment next = snake.nextsegment();
        snake.setdir(0);
        snake.move(next);
        assertEquals(15, snake.gethead().getX());
        assertEquals(14, snake.gethead().getY());
        snake.setdir(2);
        snake.move(next);
        assertEquals(15, snake.gethead().getX());
        assertEquals(14, snake.gethead().getY());
        snake.setdir(1);
        snake.move(next);
        assertEquals(15, snake.gethead().getX());
        assertEquals(14, snake.gethead().getY());
        snake.setdir(3);
        snake.move(next);
        assertEquals(15, snake.gethead().getX());
        assertEquals(14, snake.gethead().getY());
    }


}