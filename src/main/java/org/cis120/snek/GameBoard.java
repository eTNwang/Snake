package org.cis120.snek;


/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 *
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;


/**
 * GameCourt
 * <p>
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {
    private boolean triggermove = false;
    private boolean atebadapple = false;

    // the state of the game logic
    private Snake snake; // the Snake, keyboard control
    private boolean playing = false; // whether the game is running
    private JLabel status; // Current status text, i.e. "Running..."
    private int readwriteonce = 1;


    // Game constants
    public static final int COURT_WIDTH = 750;
    public static final int COURT_HEIGHT = 750;
    public static final int SQUARE_VELOCITY = 4;
    private int runningscore = 0;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 100;

    private boolean poweruponboard = false;
    private boolean justatepowerup = false;
    int timer = 0;


    private boolean[][] gamearray = new boolean[30][30];
    private LinkedList<Consumable> consumables = new LinkedList<Consumable>();
    Color powerupcolor = Color.GREEN;


    public GameBoard(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    tick();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key
        // is pressed, by changing the square's velocity accordingly. (The tick
        // method below actually moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_A && snake.getdir() != 3) {
                    snake.setdir(2);
                    triggermove = true;
                } else if (e.getKeyCode() == KeyEvent.VK_D && snake.getdir() != 2) {
                    snake.setdir(3);
                    triggermove = true;
                } else if (e.getKeyCode() == KeyEvent.VK_S && snake.getdir() != 0) {
                    snake.setdir(1);
                    triggermove = true;
                } else if (e.getKeyCode() == KeyEvent.VK_W && snake.getdir() != 1) {
                    snake.setdir(0);
                    triggermove = true;

                } else if (e.getKeyCode() == KeyEvent.VK_1) {
                    try {
                        writeGamedata();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_2) {
                    try {
                        readGamedata();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        });
        this.status = status;
    }

    public int getrunningscore() {
        return runningscore;
    }

    public boolean poweruponboardstats() {
        return poweruponboard;
    }

    public Snake returnsnake() {
        return snake;
    }

    public boolean returnplayingstatus() {
        return playing;
    }

    public void changetriggermovetstaus(boolean input) {
        triggermove = input;
    }

    public boolean[][] getgamearray() {
        return gamearray;
    }

    public LinkedList<Consumable> getconsumables() {
        return consumables;
    }

    public void addconsumable(Consumable consumable) {
        consumables.add(consumable);
    }

    public void changesnakedir(int newdir) {
        snake.setdir(newdir);
    }

    public void checkeatfruit() throws IOException {
        LinkedList<Consumable> copyconsumables = (LinkedList<Consumable>) consumables.clone();
        boolean consume = false;
        Consumable holder = null;
        for (Consumable consumable : copyconsumables) {
            if (snake.gethead().getY() == consumable.getY() &&
                    snake.gethead().getX() == consumable.getX()) {
                consume = true;
                holder = consumable;
                break;
            }
        }
        if (consume) {
            System.out.println(holder.getClass());
            if (holder.getID() == "powerdown") {
                writeScores();

            } else {
                runningscore++;

            }
            holder.wheneaten(snake);
            copyconsumables.remove(holder);
            gamearray[holder.getX()][holder.getY()] = false;
            poweruponboard = false;
            justatepowerup = true;
        }
        consumables = copyconsumables;
    }

    public void checkoutofbounds() throws IOException {
        for (Segment seg : snake.returnlist()) {
            if (seg.getY() > 29 || seg.getX() > 29
                    || seg.getX() < 0 || seg.getY() < 0) {
                writeScores();
                playing = false;
                status.setText("You lose! Your Score was" + runningscore);
            }
        }

    }

    public void checksnakecollision() throws IOException {
        if (snake.returnlist().size() == 0) {
            playing = false;
            status.setText("You lose! Your Score was" + runningscore);
        }
        LinkedList<Segment> bodynohead = (LinkedList<Segment>) snake.returnlist().clone();
        int headx = snake.gethead().getX();
        int heady = snake.gethead().getY();
        bodynohead.removeLast();
        for (Segment seg : bodynohead) {
            if (headx == seg.getX() && heady == seg.getY()) {
                playing = false;
                status.setText("You lose! Your Score was" + runningscore);
                writeScores();
            }
        }
    }

    public void createfruit() {
        boolean cond1 = false;
        boolean cond2 = false;
        boolean checksnakepos = false;
        Random rand = new Random();
        int randx = rand.nextInt(30);
        int randy = rand.nextInt(30);
        if (!poweruponboard) {
            while (!cond1) {
                if (gamearray[randy][randx]) {
                    randx = rand.nextInt(30);
                    randy = rand.nextInt(30);
                } else {
                    gamearray[randy][randx] = true;
                    consumables.add(new PowerUp(randx, randy,
                            COURT_WIDTH, COURT_HEIGHT, powerupcolor));

                    break;
                }
            }
        }
        poweruponboard = true;


        if (justatepowerup) {

            while (!cond2) {

                if (gamearray[randy][randx]) {
                    randx = rand.nextInt(30);
                    randy = rand.nextInt(30);
                } else {
                    gamearray[randy][randx] = true;
                    consumables.add(new PowerDown(randx, randy,
                            COURT_WIDTH, COURT_HEIGHT, Color.RED));

                    break;
                }
            }
        }

        justatepowerup = false;

    }

    ;

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        snake = new Snake(COURT_WIDTH / 25 / 2, COURT_HEIGHT / 25 / 2, COURT_WIDTH, COURT_HEIGHT);
        poweruponboard = false;
        justatepowerup = false;
        consumables.clear();
        gamearray = new boolean[30][30];
        playing = true;
        runningscore = 0;
        status.setText("Current Score: " + runningscore);


        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }


    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */

    public void writeScores() throws IOException {
        BufferedWriter somebuffwriter =
                new BufferedWriter(new FileWriter("roster.txt", true));
        somebuffwriter.write("\n" + snake.returnlist().size());
        somebuffwriter.close();
    }

    public void writeGamedata() throws IOException {
        PrintWriter pw = new PrintWriter("gamedata.txt");
        pw.close();
        BufferedWriter somebuffwriter =
                new BufferedWriter(new FileWriter("gamedata.txt", true));
        for (Segment seg : snake.returnlist()) {
            somebuffwriter.write("\n" + "segment" + "," + seg.getX() + "," + seg.getY());
        }
        for (Consumable consumable : consumables) {
            somebuffwriter.write("\n" + consumable.getID() + "," + consumable.getX()
                    + "," + consumable.getY());
        }
        somebuffwriter.write("\n" + "dir" + "," + snake.getdir());
        somebuffwriter.close();
    }

    public void readGamedata() throws IOException {
        Reader reader = new FileReader("gamedata.txt");
        BufferedReader buffreader = new BufferedReader(reader);
        String curr = "";
        snake.emptysnake();
        gamearray = new boolean[30][30];
        consumables.clear();


        while ((curr = buffreader.readLine()) != null) {
            System.out.println(curr + " printing curr");
            String[] current = curr.split(",");
            System.out.println(current[0] + " printing current[0]");
            if (current[0].equals("segment")) {

                int x = Integer.parseInt(current[1]);
                int y = Integer.parseInt(current[2]);
                if (!gamearray[y][x]) {
                    snake.addseg(new Segment(x, y, COURT_WIDTH, COURT_HEIGHT, Color.BLACK));
                    System.out.println("segment added");
                }


            }
            if (current[0].equals("dir")) {
                snake.setdir(Integer.parseInt(current[1]));
            }

            if (current[0].equals("powerup")) {
                int x = Integer.parseInt(current[1]);
                int y = Integer.parseInt(current[2]);
                gamearray[y][x] = true;
                consumables.add(new PowerUp(x, y,
                        COURT_WIDTH, COURT_HEIGHT, powerupcolor));
                poweruponboard = true;
            }

            if (current[0].equals("powerdown")) {
                int x = Integer.parseInt(current[1]);
                int y = Integer.parseInt(current[2]);
                gamearray[y][x] = true;
                consumables.add(new PowerDown(x, y,
                        COURT_WIDTH, COURT_HEIGHT, Color.RED));
                justatepowerup = false;
            }

        }
        System.out.println(snake.returnlist().size() +
                "this is the size of the snake after gamedata is read");
        buffreader.close();
        runningscore = snake.returnlist().size();


    }


    public void readScores() throws IOException {
        Reader reader = new FileReader("roster.txt");
        BufferedReader buffreader = new BufferedReader(reader);
        int currentlargest = 0;
        String curr = "1";
        while ((curr = buffreader.readLine()) != null) {
            if (curr != "") {
                if (Integer.parseInt(curr) > currentlargest) {
                    currentlargest = Integer.parseInt(curr);
                }
            }
            status.setText("You Lose! Your score was " + " " + runningscore + " "
                    + " The current high-score is " + currentlargest);
        }
        buffreader.close();


    }

    public void displayinstructions() throws IOException {
        JFrame frame = new JFrame("Instructions");
        frame.setLocation(375, 0);
        JPanel instructpanel1 = new JPanel();
        JPanel instructpanel = new JPanel();
        JPanel instructpanel2 = new JPanel();
        frame.add(instructpanel, BorderLayout.NORTH);
        frame.add(instructpanel1, BorderLayout.CENTER);
        frame.add(instructpanel2, BorderLayout.SOUTH);

        instructpanel.add(new JLabel(
                " THIS IS A SIMPLE RENDITION OF SNAKE. YOU CONTROL " +
                        "THE SNAKE ONSCREEN. USE WASD TO MOVE.  "));
        instructpanel1.add(new JLabel(
                "EAT GREEN APPLES TO GROW, BUT AVOID RED APPLES WHICH KILL YOU. E" +
                        "VERY TIME YOU GROW, ANOTHER RED APPLE WILL SPAWN"));
        instructpanel2.add(new JLabel(
                "TOUCHING THE WALLS OR YOUR OWN BODY WILL KILL YOU. 1 SAVES THE GAME " +
                        "STATE, 2 LOADS TO THE LAST SAVED STATE. PRESS W, A, S, OR D TO START"));


        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void tick() throws IOException {
        if (timer == 0) {
            displayinstructions();
            timer++;
        }
        if (!playing) {
            readScores();
        } else {
            if (snake.returnlist().size() != 0) {

                checkoutofbounds();
                checksnakecollision();
                for (Segment seg : snake.returnlist()) {
                    if (seg.getX() < 29 && seg.getY() < 29 && seg.getX() > 0 && seg.getY() > 0) {
                        gamearray[seg.getX()][seg.getY()] = true;
                    }
                }
                createfruit();
                checkeatfruit();
                if (snake.returnlist().size() != 0) {
                    Segment nextseg = snake.nextsegment();
                    if (triggermove) {
                        snake.move(nextseg);
                    }
                    snake.next = nextseg;

                }
                status.setText("Your current score is " + runningscore);

                // update the display
                repaint();
            } else {
                readScores();
                playing = false;
            }
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < COURT_WIDTH; x += 25) {
            g.drawLine(x, 0, x, COURT_HEIGHT);
        }
        for (int x = 0; x < COURT_HEIGHT; x += 25) {
            g.drawLine(0, x, COURT_WIDTH, x);
        }
        for (Segment seg : snake.body) {
            seg.draw(g);
        }
        for (Consumable consumable : consumables) {
            consumable.draw(g);
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}