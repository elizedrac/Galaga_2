package org.cis1200;

import org.cis1200.galaga.RunGalaga;

import javax.swing.*;

public class Game {
    /**
     * Main method run to start and run the game. Initializes the runnable game
     * class of your choosing and runs it.
     */
    public static void main(String[] args) {
        // Set the game you want to run here
        Runnable game = new RunGalaga();

        SwingUtilities.invokeLater(game);
    }
}
