//package org.cis1200;
//
//import org.cis1200.galaga.*;
//import org.junit.jupiter.api.*;
//
//import javax.swing.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class GameCourtTest {
//    //Game court
//    final JLabel status = new JLabel();
//    final GameCourt court = new GameCourt(status);
//
//    Bug[][] bugs;
//
//    //helper function to choose number of initial bugs and which ones
//    public void removeBugs(int v, int row) {
//        court.reset();
//        bugs = court.getBugs(v, row);
//    }
//
//    //main game test 1
//    @Test
//    public void gameTestFirst() {
//        int x = 0;
//        //all green bugs
//        removeBugs(2, 0);
//
//        for (int i = 0; i < bugs.length; i++) {
//            for (int j = 0; j < bugs[0].length; j++) {
//                if (bugs[i][j].status()) {
//                    x++;
//                    bugs[i][j].setGo(true);
//                }
//            }
//        }
//
//        //test if only 2 bugs left
//        assertEquals(2, x);
//
//
//        Fire f = court.getCurrentFire();
//        Bug bug = bugs[0][0];
//
//        //sets f so that after 60ms, f hits the bug
//        f.setX(bug.getX());
//        f.setY(bug.getY() + 45);
//        assertTrue(f.getGo());
//
//        court.setPlaying(true);
//
//        //call tick three times since bugs fall at 60ms interval
//        court.tick();
//        court.tick();
//        court.tick();
//
//        //bullet shout be reset/stop moving
//        assertFalse(f.getGo());
//        //green bug shouldn't be killed on first shot
//        assertTrue(bug.status());
//
//        //reset similar set-up as above to hit bug a second time
//        f = court.getCurrentFire();
//
//        f.setX(bug.getX());
//        f.setY(bug.getY() + 45);
//
//        court.tick();
//        court.tick();
//        court.tick();
//
//        //bug killed
//        assertFalse(f.getGo());
//        assertFalse(bug.status());
//
//        //tests bug reaching initial position after falling
//        bug = bugs[0][1];
//        bug.setY(court.START_Y - 15);
//
//        court.tick();
//        court.tick();
//        court.tick();
//
//        //bug repositioned means it no longer moves
//        assertFalse(bug.getGo());
//
//        //set up so that bug hits the fighter after 60ms
//        Fighter fighter = court.getFighter();
//        bug.setStatus(true);
//        bug.setX(fighter.getX());
//        bug.setY(fighter.getY() - 15);
//
//        court.tick();
//        court.tick();
//        court.tick();
//
//        //checks that bug hitting fighter ends the game
//        assertTrue(court.endGame());
//        assertFalse(court.win());
//    }
//
//    //main game test 2
//    @Test
//    public void gameTestSecond() {
//        court.reset();
//        court.setPlaying(true);
//
//        Fighter fighter = court.getFighter();
//        Fire fire = court.bugFire();
//
//        fire.setGo(true);
//
//        //check the fire is reset when leaving the board
//        fire.setY(900);
//        court.tick();
//        assertFalse(fire.getGo());
//
//        fire.setGo(true);
//
//        fire.setX(fighter.getX());
//        fire.setY(fighter.getY() - 8);
//
//        court.tick();
//
//        //checks that bugFire hitting fighter ends the game
//        assertTrue(court.endGame());
//        assertFalse(court.win());
//    }
//
//    //runs test for a single bug remaining
//    @Test
//    public void lastBugTest() {
//        int x = 0;
//
//        //single green bug
//        removeBugs(1, 1);
//
//        court.setPlaying(true);
//
//        for (int i = 0; i < bugs.length; i++) {
//            for (int j = 0; j < bugs[0].length; j++) {
//                if (bugs[i][j].status()) {
//                    x++;
//                } else {
//                    bugs[i][j].setGo(false);
//                }
//            }
//        }
//
//        //test if only 1 bug left
//        assertEquals(1, x);
//
//        Bug bug = bugs[1][0];
//
//        //checks that last bug is always chosen to fall
//        court.updateRandom();
//        assertTrue(bug.getGo());
//
//        //last bug is killed means endGame and win is true
//        Fire fire = court.getCurrentFire();
//        bug.setX(105);
//        bug.setY(100);
//
//        fire.setX(100);
//        fire.setY(140);
//
//        court.tick();
//        court.tick();
//        court.tick();
//
//        assertEquals(-10, fire.getX());
//        assertEquals(550, fire.getY());
//        assertFalse(fire.getGo());
//
//        assertTrue(court.endGame());
//        assertTrue(court.win());
//    }
//
//    //tests cases if all three bugs hit on the way down
//    @Test
//    public void shootBugsTest() {
//        //all green bugs
//        removeBugs(7, 0);
//
//        court.setPlaying(true);
//
//        for (int i = 0; i < 7; i++) {
//            if (bugs[0][i].getGo()) {
//                bugs[0][i].setGo(false);
//            }
//        }
//
//        //resets random values to existing bugs
//        court.emptyRand();
//        court.getRand();
//
//        //test for if three bugs are killed at three different ticks
//        for (int i = 0; i < 7; i++) {
//            if (bugs[0][i].getGo()) {
//                bugs[0][i].setGo(false);
//                bugs[0][i].setStatus(false);
//                break;
//            }
//        }
//
//        court.updateRandom();
//
//        for (int i = 0; i < 7; i++) {
//            if (bugs[0][i].getGo()) {
//                bugs[0][i].setGo(false);
//                bugs[0][i].setStatus(false);
//                break;
//            }
//        }
//
//        court.updateRandom();
//
//        for (int i = 0; i < 7; i++) {
//            if (bugs[0][i].getGo()) {
//                bugs[0][i].setGo(false);
//                bugs[0][i].setStatus(false);
//                break;
//            }
//        }
//
//        court.updateRandom();
//
//        int x = 0;
//        for (int i = 0; i < 7; i++) {
//            if (bugs[0][i].getGo()) {
//                x++;
//            }
//        }
//
//        //three should still be falling
//        assertEquals(3, x);
//
//        x = 0;
//
//        //tests if all three get hit at the same time (edge case)
//        for (int i = 0; i < 7; i++) {
//            if (bugs[0][i].getGo()) {
//                bugs[0][i].setGo(false);
//                bugs[0][i].setStatus(false);
//                break;
//            }
//        }
//
//        court.updateRandom();
//
//        for (int i = 0; i < 7; i++) {
//            if (bugs[0][i].getGo()) {
//                x++;
//            }
//        }
//
//        //three should still be falling
//        assertEquals(3, x);
//    }
//
//    //Tests bug motion at top of the screen
//    @Test
//    public void testMove() {
//        removeBugs(7, 0);
//
//        court.setPlaying(true);
//
//        //move happens every 240ms (12 ticks)
//        for (int i = 0; i < 11; i++) {
//            court.tick();
//        }
//
//        Bug bug = bugs[0][0];
//        for (int i = 0; i < 11; i++) {
//            if (bugs[0][i].getGo()) {
//                bug = bugs[0][i];
//                break;
//            }
//        }
//
//        int x = bug.getStartX();
//
//        bug.setY(15);
//        court.tick();
//
//        //makes sure startX has been properly shifted
//        assertFalse(bug.getGo());
//        assertEquals(x - 5, bug.getStartX());
//        //checks that bug has been reset to correct location
//        assertEquals(x - 5, bug.getX());
//    }
//
//    //hit edge cases
//    @Test
//    public void testEdge() {
//        //single red bug
//        removeBugs(1, 1);
//
//        court.setPlaying(true);
//
//        Fighter fighter = court.getFighter();
//        Bug bug = bugs[1][0];
//
//        court.tick();
//        court.tick();
//        court.tick();
//
//        bug.setGo(true);
//
//        //bug at edge but not touching fighter
//        bug.setX(fighter.getX() + 5 - GalagaObj.WIDTH);
//        bug.setY(fighter.getY() - 13);
//
//        court.checkHits();
//
//        //makes sure position is correct
//        assertEquals(bug.getX(), fighter.getX() - GalagaObj.WIDTH);
//        assertEquals(bug.getY(), fighter.getY());
//
//        //bug should not have hit fighter and keeps moving
//        assertTrue(bug.getGo());
//        //game shouldn't end
//        assertFalse(court.endGame());
//
//        //siimilar test with fire and bug
//        bug.setX(100);
//        bug.setY(97);
//
//        Fire fire = court.getCurrentFire();
//        fire.setX(bug.getX() - 5);
//        fire.setY(140 + GalagaObj.HEIGHT);
//
//        court.tick();
//        court.tick();
//        court.tick();
//
//        //checks position
//        assertEquals(bug.getX(), fire.getX());
//        assertEquals(bug.getY() + GalagaObj.HEIGHT, fire.getY());
//
//        //game hasn't ended
//        assertFalse(court.endGame());
//        //fire keeps going, shouldn't have hit bug
//        assertTrue(fire.getGo());
//    }
//
//}