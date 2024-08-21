package org.cis1200.galaga;

import java.awt.*;

public class Explosion {
    private int start;
    private int end;
    private int centerX;
    private int centerY;
    private int angle;
    private Color color1;
    private Color color2;

    private int points;
    private boolean bugKilled = false;

    public Explosion(int centerX, int centerY, Color color1, Color color2) {
        this.start = 1;
        this.end = 9;
        this.centerX = centerX;
        this.centerY = centerY;
        this.angle = 0;
        this.color1 = color1;
        this.color2 = color2;
    }

    public Explosion(int centerX, int centerY, Color color1, Color color2, int points) {
        this.start = 1;
        this.end = 9;
        this.centerX = centerX;
        this.centerY = centerY;
        this.angle = 0;
        this.color1 = color1;
        this.color2 = color2;
        this.points = points;
    }

    public void set(boolean b) {
        this.bugKilled = b;
    }

    public Explosion() {
        this.start = 0;
        this.end = 8;
    }

    public void drawHelper(Graphics g, int incr) {
        for (int i = 0; i < 8; i++) {
            int s = start + incr;
            int e = end + incr;
            g.drawLine((int) (centerX + s * Math.sin((angle + 90) * Math.PI / 180)),
                    (int) (centerY + s * Math.cos((angle + 90) * Math.PI / 180)),
                    (int) (centerX + e * Math.sin((angle + 90) * Math.PI / 180)),
                    (int) (centerY + e * Math.cos((angle + 90) * Math.PI / 180)));
            angle += 45;
        }
    }

    public void drawPoints(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Mont Blanc", Font.ITALIC, 12));
        g.drawString("+" + this.points, centerX + 12, centerY - 12);
    }

    public void draw(Graphics g) {
        if (start != 0 && start < 30) {
            angle = 0;
            g.setColor(color1);
            drawHelper(g, 0);
            angle = 45 / 2;
            g.setColor(color2);
            drawHelper(g, 3);
            if (bugKilled) {
                drawPoints(g);
            }
            start++;
            end++;
        } else {
            angle = 0;
            start = 0;
            end = 8;
        }
    }


}
