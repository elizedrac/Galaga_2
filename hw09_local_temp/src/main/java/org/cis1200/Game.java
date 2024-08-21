package org.cis1200;

import org.cis1200.galaga.RunGalaga;
import javax.swing.*;

//Main method to run game
public class Game {
    public static void main(String[] args) {
        Runnable game = new RunGalaga();
        SwingUtilities.invokeLater(game);
    }
}
