package org.cis120.snake;

import org.cis120.snek.*;
import org.junit.jupiter.api.*;

import java.awt.*;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.*;

public class BoardTest {
    final JLabel status = new JLabel("Running...");
    GameBoard court = new GameBoard(status);

    @Test
    public void initialtest() throws IOException {
        court.reset();
        court.tick();
        assertEquals(1, court.getconsumables().size());
        assertEquals(0, court.getrunningscore());
        assertTrue(court.poweruponboardstats());
        int numobjects = 0;
        for (boolean[] boollist : court.getgamearray()) {
            for (boolean bool : boollist) {
                if (bool) {
                    numobjects++;
                }
            }

        }
        assertEquals(numobjects, 2);
    }

    @Test
    public void eatappletest() throws IOException {
        court.reset();
        court.changetriggermovetstaus(true);
        court.addconsumable(new PowerUp(15, 14, 750, 750, Color.BLACK));
        court.changesnakedir(0);
        court.tick();
        court.tick();
        assertEquals(court.returnsnake().returnlist().size(), 2);

    }

    @Test
    public void eatappletestsaveload() throws IOException {
        court.reset();
        court.writeGamedata();
        court.changetriggermovetstaus(true);
        court.addconsumable(new PowerUp(15, 14, 750, 750, Color.BLACK));
        court.changesnakedir(0);
        court.tick();
        court.tick();
        assertEquals(court.returnsnake().returnlist().size(), 2);
        court.writeGamedata();
        assertEquals(court.returnsnake().returnlist().size(), 2);

    }

    @Test
    public void eatbadappletestsaveload() throws IOException {
        court.reset();
        court.writeGamedata();
        court.changetriggermovetstaus(true);
        court.addconsumable(new PowerDown(15, 14, 750, 750, Color.BLACK));
        court.changesnakedir(0);
        court.tick();
        court.tick();
        court.tick();
        court.tick();
        assertEquals(court.returnsnake().returnlist().size(), 0);
        court.writeGamedata();
        assertEquals(court.returnsnake().returnlist().size(), 0);

    }

    @Test
    public void basicsaveloadtest() throws IOException {
        court.reset();
        court.writeGamedata();
        court.changetriggermovetstaus(true);
        court.changesnakedir(0);
        court.tick();
        court.tick();
        court.tick();
        court.tick();
        court.readGamedata();
        assertEquals(court.returnsnake().gethead().getX(), 15);
        assertEquals(court.returnsnake().gethead().getY(), 15);

    }



    @Test
    public void selfcollidetest() throws IOException {
        court.reset();
        court.changetriggermovetstaus(true);
        court.returnsnake().getLonger();
        court.returnsnake().getLonger();
        court.returnsnake().getLonger();
        court.returnsnake().getLonger();
        court.returnsnake().getLonger();
        court.returnsnake().getLonger();
        court.returnsnake().getLonger();
        court.changesnakedir(0);
        court.tick();
        court.changesnakedir(2);
        court.tick();
        court.changesnakedir(1);
        court.tick();
        court.changesnakedir(3);
        court.tick();
        assertFalse(court.returnplayingstatus());

    }

    @Test
    public void eatbadappletest() throws IOException {
        court.reset();
        court.changetriggermovetstaus(true);
        court.addconsumable(new PowerDown(15, 14, 750, 750, Color.BLACK));
        court.changesnakedir(0);
        court.tick();
        court.tick();
        court.tick();
        assertFalse(court.returnplayingstatus());
    }

    @Test
    public void wallcollidetest() throws IOException {
        court.reset();
        court.changetriggermovetstaus(true);
        for (int x = 0; x < 30; x++) {
            court.tick();
        }
        assertFalse(court.returnplayingstatus());
    }


}