package org.cis1200.galaga;

import java.awt.*;

public class GreenBug extends Bug {
    //colors
    private final Color colorRed = new Color(255, 72, 56);
    private final Color colorYellow = new Color(247, 255, 41);
    private final Color colorGreen = new Color(26, 255, 130);
    private final Color colorPurple = new Color(211, 148, 245);
    private final Color colorBlue = new Color(26, 111, 255);
    private Color color;

    //number of hits (only dies on second one)
    private int hits;

    //points
    private static final int POINTS_FIRST = 20;
//    private static final int POINTS_SECOND = 30;

    public GreenBug(int x, int y) {
        super(x, y);
        this.hits = 0;
        this.color = colorGreen;
        setPoints(POINTS_FIRST);
        this.setSpeed(15);
    }

    //changes color when hit the first time, dies the second
    @Override
    public void beenHit() {
        hits += 1;
        if (hits == 1) {
            color = colorPurple;
        } else if (hits == 2) {
            setPoints(getPoints() + 10);
            super.beenHit();
        }
    }

    int c = 0;
    double n = 0.5;
    @Override
    public void fall() {
        if (c == 0) {
            setPoints(getPoints() + 10);
            c++;
        }

        if (this.beam) {
            setBeamNum(this.beamNum + n);
            if (beamNum == 18){
                n *= -1;
            }
            if (this.beamNum == 0) {
                setBeam(false);
            }
        } else if (beamNum == 0) {
            moveY(this.getSpeed());
            this.setAngle(180);
            super.fall();
        }

        if (!this.getGo() && this.status()) {
            c = 0;
            setPoints(getPoints() - 10);
        }
    }

    public void drawBeam(Graphics g) {
        for (int i = 0; i < (int)beamNum; i++) {
            if (i % 2 == 0) {
                g.setColor(Color.cyan);
            } else {
                g.setColor(colorBlue);
            }

            g.drawArc(getX() - 7 - 2 * i, getY() + 20 + 15 * i, 15 + 4 * i, 10, -30, -120);
        }
    }

    @Override
    public void draw(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;

        double angle = this.getAngle();

        g.translate(this.getX(), this.getY());
        g.rotate(angle * Math.PI/180);
        g.translate(-this.getX(), -this.getY());

        g.setColor(color);
        g.fillRect(this.getX() - 7, this.getY() - 8, 14, 14);
        g.fillRect(this.getX() - 10, this.getY() - 5, 3, 14);
        g.fillRect(this.getX() - 14, this.getY() - 1, 5, 16);
        g.fillRect(this.getX() - 13, this.getY() + 15, 3, 1);
        g.fillRect(this.getX() + 7, this.getY() - 5, 3, 14);
        g.fillRect(this.getX() + 9, this.getY() - 1, 5, 16);
        g.fillRect(this.getX() + 10, this.getY() + 15, 3, 1);

        g.setColor(colorYellow);
        g.fillRect(this.getX() - 7, this.getY() - 2, 14, 8);

        g.setColor(color);
        g.fillRect(this.getX() - 2, this.getY() - 2, 4, 3);
        g.fillRect(this.getX() - 1, this.getY() - 12, 2, 5);
        g.fillRect(this.getX() - 7, this.getY() - 14, 2, 4);
        g.fillRect(this.getX() - 10, this.getY() - 14, 5, 2);
        g.fillRect(this.getX() - 2, this.getY() - 17, 1, 3);
        g.fillRect(this.getX() + 5, this.getY() - 14, 2, 4);
        g.fillRect(this.getX() + 5, this.getY() - 14, 5, 2);
        g.fillRect(this.getX() + 1, this.getY() - 17, 1, 3);

        g.setColor(colorRed);
        g.fillRect(this.getX() - 5, this.getY() - 14, 4, 6);
        g.fillRect(this.getX() + 1, this.getY() - 14, 4, 6);
        g.fillRect(this.getX() - 4, this.getY() + 6, 3, 5);
        g.fillRect(this.getX() + 1, this.getY() + 6, 3, 5);
        g.fillRect(this.getX() - 13, this.getY() + 5, 2, 7);
        g.fillRect(this.getX() - 12, this.getY() + 3, 2, 4);
        g.fillRect(this.getX() + 11, this.getY() + 5, 2, 7);
        g.fillRect(this.getX() + 10, this.getY() + 3, 2, 4);

        g.translate(this.getX(), this.getY());
        g.rotate(-angle * Math.PI/180);
        g.translate(-this.getX(), -this.getY());

        drawBeam(g);
    }
}
