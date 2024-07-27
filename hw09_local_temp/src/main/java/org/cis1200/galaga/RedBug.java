package org.cis1200.galaga;

import java.awt.*;

public class RedBug extends Bug {
    //colors
    private final Color colorRed = new Color(255, 72, 56);
    private final Color colorBlue = new Color(26, 111, 255);

    //for bug falling motion
    private int count = 0;
    private int amp = 10;
//    private boolean left;

    private static final int POINTS = 40;

    public RedBug(int x, int y) {
        super(x, y);
        this.setSpeed(12);
        this.setPoints(POINTS);
//        this.left = Math.random() < 0.5;
    }

    @Override
    public void fall() {
        if (count == 0) {
            setPoints(getPoints() + 10);
        }

        moveY(this.getSpeed());
        if (!getTop()) {
            if (count % 40 == 0) {
                amp = (int) Math.round((Math.random() * 3)) + 10;
            }
//            moveY(12);
            moveX((int) ((amp - 3) * Math.sin(this.getY() / 40)));

            this.setAngle(180 - 180 * (Math.atan((amp) * Math.cos((this.getY() + 16) / 40) / 40)) / Math.PI);
        }

        count++;
        super.fall();
        if (!this.getGo() && this.status()) {
            count = 0;
            setPoints(getPoints() - 10);
        }
    }

    @Override
    public void draw(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;

        double angle = this.getAngle();

        g.translate(this.getX(), this.getY());
        g.rotate(angle * Math.PI/180);
        g.translate(-this.getX(), -this.getY());


        g.setColor(colorRed);
        g.fillRect(this.getX() - 13, this.getY() + 3, 4, 13);
        g.fillRect(this.getX() + 9, this.getY() + 3, 4, 13);
        g.fillRect(this.getX() - 11, this.getY() - 1, 4, 12);
        g.fillRect(this.getX() + 7, this.getY() - 1, 4, 12);
        g.fillRect(this.getX() - 8, this.getY() - 4, 16, 12);
        g.fillRect(this.getX() - 13, this.getY() - 5, 26, 3);
        g.fillRect(this.getX() - 13, this.getY() - 14, 4, 9);
        g.fillRect(this.getX() + 9, this.getY() - 14, 4, 9);

        g.setColor(Color.WHITE);
        g.fillRect(this.getX() - 6, this.getY() - 10, 12, 7);
        g.fillRect(this.getX() - 3, this.getY() - 7, 6, 16);

        g.setColor(colorRed);
        g.fillRect(this.getX() - 4, this.getY() - 10, 3, 3);
        g.fillRect(this.getX() + 1, this.getY() - 10, 3, 3);

        g.setColor(colorBlue);
        g.fillRect(this.getX() - 4, this.getY() - 14, 3, 4);
        g.fillRect(this.getX() + 1, this.getY() - 14, 3, 4);
        g.fillRect(this.getX() - 3, this.getY() + 9 , 6, 3);
        g.fillRect(this.getX() - 3, this.getY() + 1, 6, 4);

        g.translate(this.getX(), this.getY());
        g.rotate(-angle * Math.PI/180);
        g.translate(-this.getX(), -this.getY());
    }
}
