package org.cis1200.galaga;

//Java Swing libraries
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class RunGalaga implements Runnable {
    public void keyClose(int k, GameCourt court, JFrame frame, JFrame mainFrame) {
        court.setKeys(k);
        frame.setVisible(false);
        mainFrame.setVisible(true);
        court.requestFocus(); //focus on game itself rather than other panel
    }

    public void pause(GameCourt court, JFrame frame) {
        court.setPlaying(false);
        court.repaint();
        court.requestFocusInWindow();
        frame.setVisible(true);
    }

    public void run() {
        final JFrame frame = new JFrame("Galaga2");
        frame.setLocation(320, 50);

        //status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running");
        status_panel.add(status);

        //Game court
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        //reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> court.reset());
        control_panel.add(reset);

        //instruction frame
        final JFrame frame1 = new JFrame("Galaga2");
        frame1.setLocation(540, 300);

        //close button
        final JPanel control_panel1 = new JPanel();
        frame1.add(control_panel1, BorderLayout.SOUTH);

        final JButton close = new JButton("Up Arrow Shoot Key");
        close.addActionListener(e -> this.keyClose(KeyEvent.VK_UP, court, frame1, frame));
        control_panel1.add(close);

        final JButton changeKeys = new JButton("W Shoot Key");
        changeKeys.addActionListener(e -> this.keyClose(KeyEvent.VK_W, court, frame1, frame));
        control_panel1.add(changeKeys);

        //instruction button
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> this.pause(court, frame1));
        control_panel.add(instructions);

        //instruction panel
        final JPanel instruction_panel = new JPanel();
        frame1.add(instruction_panel, BorderLayout.CENTER);
        final JTextArea instructionsText = new JTextArea("Instructions: This game follows the " +
                "basic structure\nof the classic Galaga arcade game.\n\n" +
                "Avoid the bugs at the topic of the screen that\n" +
                "will fall in groups of three. If you get hit by a bug\n" +
                "or one of its bullets you lose and the game is over.\n\n" +
                "Press the left and right arrow keys to move your fighter.\n" +
                "Press the up arrow key to shoot and hit the bugs. Note\n" +
                "that the green bugs must be hit twice to die. To win\nyou have to " +
                "kill all of the bugs.\n\n" +
                "Press the space bar to begin playing or pause/unpause\nat any time. " +
                        "Press the reset button or r key to restart at\nany time.");
        instructionsText.setEditable(false);
        instructionsText.setFont(new Font("Mont Blanc", Font.PLAIN, 14));
        instructionsText.setCaretColor(Color.WHITE);
        instruction_panel.add(instructionsText);

        //set all the frames/panels on screen
        frame.setSize(GameCourt.GAME_WIDTH,GameCourt.GAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame1.setSize(GameCourt.GAME_WIDTH / 2, GameCourt.GAME_HEIGHT / 2);
        frame1.setVisible(true);

        //set game variables
        court.reset();
    }
}