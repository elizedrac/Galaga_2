package org.cis1200.galaga;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.swing.*;

/**
 * GameCourt
 * This class holds the primary game logic for how different objects interact
 * with one another.
 */
public class GameCourt extends JPanel {
    //Court Variables
    public static final int START_X = 77;
    public static final int START_Y = 65;
    public static final int GAME_WIDTH = 850;
    public static final int GAME_HEIGHT = 850;
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 20;
    //keeps track of milliseconds
    private int count = 0;


    // Game status variables
    private boolean playing = false; //whether the game is running
    private boolean gameEnd = false; //if game is over
    private boolean win = false; //win or lose
    private int points;
    private int highScore = 0;
    private final JLabel status; // Current status text, i.e. "Running..."

    private boolean pauseMode = false; //for when bugs and/or fighters are resetting
    private boolean endgame; //final three bugs


    //Bug variables;
    private final int w = 4; //grid rows
    private final int h = 8; //grid columns
    private final Bug[][] bugGrid = new Bug[w][h];
    private int bugsRemaining;

    //Generates values that stores which bugs are falling
    private LinkedList<Integer> rand;
    //for updating start values of moving bugs
    private int moveCount;
    private int direction;

    //bug fire list
    private final Fire[] bugFires = new Fire[2];

    //green bug effects
    private int bugBeam;
    private Bug fighterBug = null;
    private Fighter takenFighter = null;


    //Fighter variables
    private final Fighter[] fighters = new Fighter[3]; //for multiple players
    private Fighter fighter;
    private int currFighter = 0; //keeps track of lives/fighter number
    private LinkedList<Fire> fire; //fire coming from shooter
    private ListIterator<Fire> fireIterator; //iterates through fire
    private boolean left = false; //fighter direction


    //explosion variables
    private Explosion fighterExplosion = new Explosion();
    private Explosion bugExplosion = new Explosion();


    //background effects
    private final RandomDot[] dots = new RandomDot[300];


    //key variables
    int k1 = KeyEvent.VK_UP;
    //set keys helper
    public void setKeys(int k1) {
        this.k1 = k1;
    }


    //fire stats
    int hits;
    int total;

    //test bug
    BlueBug bug = new BlueBug(-10, 1000);

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.BLACK);

        //timer that calls tick function at each interval
        Timer timer = new Timer(INTERVAL, e -> tick());
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        //key listeners
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                //alternative method to restart
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    playing = !playing;
                    reset();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameEnd) { //pause button
                    playing = !playing;
                }
                //fighter motion
                if ((e.getKeyCode() == KeyEvent.VK_RIGHT ||
                        e.getKeyCode() == KeyEvent.VK_LEFT)
                        && (playing || win)) {
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        left = false;
                    } else {
                        left = true;
                    }
                    fighter.setGo(true);
                }
                //fighter shooting
                if (e.getKeyCode() == k1 && (playing || win) && !pauseMode && (count % 40 == 0 || count % 180 == 0)) {
                    total++;
                    if (!fighter.getDub()) {
                        fighterShooter(0);
                    } else {
                        fighterShooter((fighter.WIDTH + 6)/ 2);
                        fighterShooter(-(fighter.WIDTH + 6)/ 2);
                    }
                }
            }

            //for smoother motion
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    fighter.setGo(false);
                }
            }
        });
        this.status = status;
    }


    //fighterShooter helper function
    public Fire getCurrentFire() {
        if (fireIterator.hasNext()) {
            Fire f = fireIterator.next();
            f.setGo(true);
            return f;
        }
        return (new Fire(Color.WHITE));
    }

    //sets up fighter's fire
    public void fighterShooter(int adj) {
        //iterator starts over once it reaches its end to avoid having an infinite list
        if (!this.fireIterator.hasNext()) {
            this.fireIterator = fire.listIterator();
        }
        Fire f = getCurrentFire();
        //accounts for fighter being in motion when up is pressed
        if (fighter.getGo() && fighter.getX() != (Fighter.WIDTH + 6) / 2
                && fighter.getX() != GAME_WIDTH - (Fighter.WIDTH + 6) / 2) {
            if (left) {
                f.setX(fighter.getX() - 7);
            } else {
                f.setX(fighter.getX() + 7);
            }
        } else {
            //sets fire position to current fighter position before shooting
            f.setX(fighter.getX());
        }
        f.moveX(adj); //adjusts position if double fighter
    }


    //setBugFire helper
    public void BugFires(int i, Bug bug) {
        bugFires[i].setX(bug.getX());
        bugFires[i].setY(bug.getY());
        bugFires[i].setGo(true);
    }

    Bug currBug = null; //makes sure bugFire isn't sent from same bug
    //add rand helper specifically for bug fire
    public void setBugFire(Bug bug) {
        if (currBug == null || currBug != bug || bugsRemaining == 1) {
            if (!bugFires[0].getGo() && (!bugFires[1].getGo() || (Math.random() < 0.5)) || bugsRemaining == 1) {
                BugFires(0, bug);
            } else if (!bugFires[1].getGo() && Math.random() < 0.5) {
                BugFires(1, bug);
            }
            currBug = bug;
        }
    }


    //adds new random value to rand for new bug to fall
    public void addRand() {
        int x = (int)Math.round(Math.random() * (w * h - 1));
        //iteratively gets rid of repeats unless there's only three left (fix)
        while ((rand.contains(x) || !bugGrid[x / h][x % h].status())) {
            x = (int) Math.round(Math.random() * (w * h - 1));
        }
        rand.add(x);
        Bug bug = bugGrid[x / h][x % h];
        bug.setGo(true);
        //if bugFire is not in motion, sends it to a new bug to shoot
        setBugFire(bug);
    }


    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        hits = 0;
        total = 0;

        pauseMode = false;
        endgame = false;
        r = true;

        currFighter = 0;
        takenFighter = null;
        bugBeam = (int)(Math.random() * 8); //chooses which green bug to project beam

        //3 fighters
        fighters[0] = new Fighter(428, 670);
        fighters[1] = new Fighter(50, 710);
        fighters[2] = new Fighter(90, 710);

        fighter = fighters[currFighter];

        //bugs moving on top
        moveCount = 0;
        direction = 1;

        //background
        for (int i = 0; i < 150; i++) {
            dots[i] = new RandomDot();
        }

        //fire variables
        //bug fire
        for (int i = 0; i < bugFires.length; i++) {
            bugFires[i] = new Fire(Color.RED);
        }

        //fighter fire
        fire = new LinkedList<>();
        for (int i = 0; i < 50; i++) {
            fire.add(new Fire(Color.WHITE));
        }
        fireIterator = fire.listIterator();


        //bug variables
        bugsRemaining = 0;
        for (int j = 0; j < bugGrid[0].length; j++) {
            for (int i = 0; i < bugGrid.length; i++) {
                Bug bug;
                if (i == 0) {
                    bug = new GreenBug(START_X + 100 * j, START_Y + 50 * i);
                } else if (i == 3) {
                    bug = new BlueBug(START_X + 100 * j, START_Y + 50 * i);
                } else {
                    bug = new RedBug(START_X + 100 * j, START_Y + 50 * i);
                }
                bugGrid[i][j] = bug;
                bugsRemaining += 1;
            }
        }
        rand = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            addRand();
        }

        //game status variables
        points = 0;
        playing = false;
        gameEnd = false;
        win = false;
        repaint();
        status.setText("Running... Press SPACE to start/pause and R to reset");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }


    //tick helper functions to simplify code
    //background falling/blinking dots effect
    public void background() {
        if (count % 40 == 0) {
            for (int i = 0; i < 150; i++) {
                dots[i].moveY(2);
                if (dots[i].getY() >= GAME_HEIGHT) {
                    dots[i].setY(0);
                }
                if (count % 480 == 0) {
                    dots[i].setOpacity(100);
                } else if (count % 240 == 0) {
                    dots[i].setOpacity(90);
                }
            }
        }
    }

    public void updateFighter() {
        int adj = 1;
        if (fighter.getDub()) {
            adj = 2;
        } //accounts for position shift if double fighter

        //updates fighter position
        if (fighter.getGo()) {
            if (left) {
                //makes sure fighter can't exit borders
                if (fighter.getX() - (20 + (Fighter.WIDTH + 6) / 2) >= 0) {
                    fighter.moveX(- 7);
                } else {
                    fighter.setX((Fighter.WIDTH + 6) / 2 * adj);
                }
            } else {
                //makes sure fighter can't exit borders
                if (fighter.getX() + 20 + (Fighter.WIDTH + 6) / 2 <= GAME_WIDTH) {
                    fighter.moveX(7);
                } else {
                    fighter.setX(GAME_WIDTH - (Fighter.WIDTH + 6) / 2 * adj);
                }
            }
        }
    }

    public void checkFire() {
        //checks if fire objects should be moving
        for (Fire f:
                fire) {
            if (f.getGo()) {
                f.shoot();
            }
        }

        for (int i = 0; i < bugFires.length; i++) {
            if (bugFires[i].getGo() && playing) {
                bugFires[i].bugShoot();
            }
        }
    }

    //update random helper function
    public void update(ArrayList<Integer> arr) {
        for (int i = 0; i < arr.size(); i++) {
            rand.remove(arr.get(i));
            if (!pauseMode) {
                addRand();
            }
        }
    }

    public void updateRandom() {
        ArrayList<Integer> arr = new ArrayList<>();
        for (Integer r: rand) {
            //if bug has been hit or reached its original position, new bug is called to fall
            if (!bugGrid[r / h][r % h].getGo()) {
                arr.add(r);
            }
        }
        update(arr);
        //when nearing endgame, to make sure that at least one bug is falling
        if (!pauseMode) {
            if (rand.isEmpty()) {
                addRand();
            }
        }
    }

    //Update move count for bugs moving at top of the screen
    //this affects the new starting X position that the bugs must return to when resetting
    public void moveBugs() {
        if (count % 420 == 0) {
            if (moveCount % 3 == 0) {
                direction *= -1;
            }
            moveCount++;
        }
    }

    public void hitsHelper(Bug bug) {
        if (fighter.getDub()){
            fighter.setDub(false);
        } else {
            pauseMode = true;
            fighter.setStatus(false);
            currFighter++;
        }

        if (currFighter == 3) {
            playing = false;
            gameEnd = true;
            status.setText("You lose!");
        }
        //resets explosion and sets it next to bug's position
        if (!fighter.getTaken()) {
            fighterExplosion = new Explosion(fighter.getX(), fighter.getY(), Color.RED, Color.WHITE);
            if (bug != null) {
                bugExplosion = new Explosion(bug.getX(), bug.getY(), Color.GREEN, Color.MAGENTA);
                bugExplosion.set(false);
            }
        }
    }

    //iterates through bug grid to check each bug's status
    public void checkHits() {
        for (int j = 0; j < bugGrid[0].length; j++) {
            for (int i = 0; i < bugGrid.length; i++) {
                Bug bug = bugGrid[i][j];
                if (bug.status()) {

                    //makes sure bugs continue firing during endgame
                    if (endgame && bug.getY() < 175 && bug.getY() > 135) {
                        setBugFire(bug);
                    }

                    //must be multiple of bug fall timer to work
                    if (count % 420 == 0) {
                        if (!bug.getGo()) {
                            bug.move(direction * 3);
                        }
                        bug.updateStartX(bug.getStartX() + direction * 3);
                    }

                    //bugs fall at 60ms interval timer
                    if (count % 60 == 0) {
                        if (bug.getGo()) {
                            try {
                                if (bug == bugGrid[0][bugBeam] && bug.getY() > 400) {
                                    bugBeam = -1;
                                    bug.setBeam(true);
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {}
                            bug.fall();
                        }
                    }

                    if (!pauseMode) { //doesn't update when new fighter being set
                        // check for if fighter has been taken
                        if (bug.taken(fighter)) {
                            bug.setBeam(false);
                            fighterBug = bug;
                            fighter.setTaken(true, fighterBug);
                            takenFighter = fighters[currFighter];
                            hitsHelper(null);
                        }
                        // check for if fighter has been hit
                        if (bug.hit(fighter) && fighter.getStatus() && !pauseMode) {
                            if (takenFighter != null && fighterBug != null && bug == fighterBug) {
                                takenFighter.taken(false);
                                takenFighter.setStatus(false);
                            }
                            hitsHelper(bug);
                            if (bugsRemaining != 1) {
                                bug.setStatus(false);
                                bugsRemaining--;
                            } else {
                                bug.setY(900);
                            }
                        }
                    }
                    //iterates through fire list to see if any bug has been hit
                    for (Fire f :
                            fire) {
                        if (bug.status() && f.hit(bug) && f.getGo()) {
                            hits++;
                            bug.beenHit();
                            try {
                                if (bug == bugGrid[0][bugBeam] && !bug.status()) {
                                    int c = 0;
                                    while (!bugGrid[0][bugBeam].status() && c < 8) {
                                        bugBeam = (int)(Math.random() * 8);
                                        c++;
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException e){}

                            if (takenFighter != null && bug == fighterBug && !bug.status()) {
                                takenFighter.setDub(true);
                                pauseMode = true;
                            }

                            f.restart();
                            f.setGo(false);
                            //updates points
                            points += bug.getPoints();
                            if (!bug.status()) {
                                bugsRemaining--;
                                //checks if game is over or not
                                if (bugsRemaining == 0) {
                                    playing = false;
                                    gameEnd = true;
                                    win = true;
                                    status.setText("You win!");
                                }
                                //resets explosion and sets it next to bug's position
                                bugExplosion = new Explosion(bug.getX(), bug.getY(), Color.GREEN, Color.MAGENTA, bug.getPoints());
                                bugExplosion.set(true);
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < bugFires.length; i++) {
            if (bugFires[i].hit(fighter) && fighter.getStatus() && !fighter.getTaken() && !pauseMode) {
                hitsHelper(null);
                bugFires[i].setGo(false);
                bugFires[i].restart();
            }
        }
    }

    public void updateHighScore() {
        if (gameEnd) {
            if (points > highScore) {
                highScore = points;
            }
        }
    }

    boolean r = true; //only updates fighter once
    public void updatePause() {
        // and new fighter takes its place
        if (endgame) {
            endgame = false;
            for (int j = 0; j < bugGrid[0].length; j++) {
                for (int i = 0; i < bugGrid.length; i++) {
                    Bug b = bugGrid[i][j];
                    if (b.status()) {
                        b.setSpeed(b.getSpeed() - 3);
                    }
                }
            }
        }

        if (fighterBug != null && fighter.getY() <= fighterBug.getY()) {
            fighterBug.setBeamNum(0);
        }

        if (takenFighter != null && takenFighter.getY() >= fighter.getY() - HEIGHT/2 && takenFighter.getDub()) {
            fighter.setDub(true);
            takenFighter.taken(false);
            takenFighter.setStatus(false);
            takenFighter.setDub(false);
        }

        if (rand.isEmpty() && (takenFighter == null || fighter.getDub() || !takenFighter.getDub())) {
            if (r) {
                fighter = fighters[currFighter];
                fighter.setX(428);
                fighter.setY(670);
                r = false;
            }
            int num = 3;
            if (bugsRemaining < 3) {
                num = bugsRemaining;
            }
            if (count % 2400 == 0) { //gives a sec before running
                for (int i = 0; i < num; i++) {
                    addRand(); //resets falling bugs
                }
                pauseMode = false;
                r = true;
            }
        }
    }


    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    public void tick() {

        //count allows for objects to react in slightly different time frames
        count += INTERVAL;

        background();
        if (!pauseMode) {
            updateFighter();
        }
        checkFire();
        if (playing) {
            if (pauseMode) {
                updatePause();
            }
            if (!endgame) {
                updateRandom();
            }
            if (bugsRemaining <= 3 && !endgame && !pauseMode) {
                endgame = true;
                bugBeam = -1;
                for (int i = 0; i < bugGrid.length; i++) {
                    for (int j = 0; j < bugGrid[0].length; j++) {
                        Bug bug = bugGrid[i][j];
                        if (bug.status()) {
                            bug.setSpeed(bug.getSpeed() + 3);
                        }
                    }
                }
            }
            moveBugs();
            checkHits();
            updateHighScore();
        }
        // update the display
        repaint();
    }


    //draw screen
    int c = 0;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //background
        for (int i = 0; i < 150; i++) {
            dots[i].draw(g);
        }

        //game objects
        if (!gameEnd) {
            for (int i = 0; i < bugFires.length; i++) {
                bugFires[i].draw(g);
            }
            for (Fire f :
                    fire) {
                f.draw(g);
            }
        }

        for (int i = 0; i < bugGrid.length; i++) {
            for (int j = 0; j < bugGrid[0].length; j++) {
                Bug bug = bugGrid[bugGrid.length - 1 - i][j];
                if (bug.status()) {
                    bug.draw(g);
                }
            }
        }

        //start/pause screen
        if (!playing && !gameEnd) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Mont Blanc", Font.ITALIC, 60));
            g.drawString("Galaga 2.0", 250, GAME_HEIGHT / 2 - 20);
            g.setColor(Color.RED);
            g.setFont(new Font("Mont Blanc", Font.ITALIC, 20));
            g.drawString("Press Space to Begin", 300, GAME_HEIGHT / 2 + 25);
        }

        //during pause mode
        if (pauseMode && !gameEnd && playing) {
            String s = new String();
            int x = 0;

            if (takenFighter != null && takenFighter.getTaken() && !takenFighter.getDub()) {
                g.setColor(Color.RED);
                s = "fighter taken";
                x = 330;
            } else {
                g.setColor(Color.WHITE);
                x = 210;
                if (count % 300 == 0) {
                    c++;
                }
                s = "bugs resetting, please wait";
                for (int i = 0; i < c % 4; i++) {
                    s += ".";
                }
            }

            g.setFont(new Font("Mont Blanc", Font.ITALIC, 32));
            g.drawString(s, x, GAME_HEIGHT / 2 - 50);
        }

        //screen printed after game ends
        if (gameEnd) {
            if (win) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Mont Blanc", Font.BOLD, 40));
                g.drawString("You Win!!", 315, GAME_HEIGHT / 2 - 50);
            } else {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Mont Blanc", Font.BOLD, 40));
                g.drawString("Womp Womp", 315, GAME_HEIGHT / 2 - 50);
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Mont Blanc", Font.ITALIC, 20));
            g.drawString("Points = " + points, 315, GAME_HEIGHT / 2);
            g.drawString("High Score = " + highScore, 315, GAME_HEIGHT / 2 + 40);
            g.drawString("Hit Ratio = " + (hits * 100) / total + "%", 315, GAME_HEIGHT / 2 + 80);
        }

        //draw fighters
        for (int i = 0; i < fighters.length; i++) {
            if (fighters[i].getStatus()) {
                fighters[i].draw(g);
            }
        }

        //effects for when bug or fighter is killed
        fighterExplosion.draw(g);
        bugExplosion.draw(g);

        //status at bottom of the page
        g.setColor(Color.WHITE);
        g.setFont(new Font("Mont Blanc", Font.ITALIC, 15));
        g.drawString("Lives: " + (3 - currFighter), 30, GAME_HEIGHT - 105);
        g.drawString("Points: " + points, GAME_WIDTH - 140, GAME_HEIGHT - 130);
        g.drawString("High Score: " + highScore, GAME_WIDTH - 140, GAME_HEIGHT - 105);
    }

    //helper functions
    public void setPlaying(boolean b) {
        playing = b;
    }

}