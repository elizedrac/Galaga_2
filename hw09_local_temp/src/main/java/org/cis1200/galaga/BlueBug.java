package org.cis1200.galaga;

import java.awt.*;

public class BlueBug extends Bug {
    //colors
    private final Color colorRed = new Color(255, 72, 56);
    private final Color colorYellow = new Color(247, 255, 41);
    private final Color colorBlue = new Color(26, 111, 255);

    private boolean first = false;

    private static final int POINTS = 30;

    public BlueBug(int x, int y) {
        super(x, y);
        this.setSpeed(12);
        setPoints(POINTS);
    }

    int currX  = 0;
    boolean done = false;
    boolean top = false;

    int rad = 100;
    int height = 620;
    int c = 0;
    @Override
    public void fall() {
        if (c == 0) {
            setPoints(getPoints() + 5);
            c++;
        }

        if (this.getY() - GalagaObj.HEIGHT / 2 >= GameCourt.GAME_HEIGHT
                - 245 && !done && !this.getEndgame()) {
            first = true;
            currX = this.getX();
            done = true;
            setPoints(getPoints() + 5);
        }

        int x = 0;
        if (first) {
            if (!top) {
                int m = - (int)(10 - 4 * Math.abs(x / (Math.sqrt(Math.pow(rad, 2)
                        - Math.sqrt(Math.pow(x, 2))))));
                moveX(m);
                x = this.getX() - currX + rad;
                setAngle(180 * Math.atan(-x / (Math.sqrt(Math.pow(rad, 2) -
                        Math.sqrt(Math.pow(x, 2))))) / Math.PI - 90);
                setY((int) (Math.sqrt(Math.pow(rad, 2) - Math.pow(x, 2)) + height));
                if (this.getY() == height) {
                    top = true;
                }
            } else {
                int m = (int)(10 - 4 * Math.abs(x / (Math.sqrt(Math.pow(rad, 2) -
                        Math.sqrt(Math.pow(x, 2))))));
                moveX(m);
                x = this.getX() - currX + rad;
                setAngle(90 - 180 * Math.atan(-x / (Math.sqrt(Math.pow(rad, 2) -
                        Math.sqrt(Math.pow(x, 2))))) / Math.PI);
                setY((int) (-Math.sqrt(Math.pow(rad, 2) -
                        Math.pow(this.getX() - currX + rad, 2)) + height));
                if (this.getY() == height) {
                    first = false;
                    top = false;
                    setPoints(getPoints() - 5);
                }
            }
        } else if (getTop()) {
            setAngle(180);
            moveY(getSpeed());
            done = false;
        } else {
            setAngle(180);
            moveY(getSpeed());
        }

        super.fall();
        if (!this.getGo() && this.status()) {
            c = 0;
            setPoints(getPoints() - 5);
        }
    }

    @Override
    public void draw(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;

        double angle = this.getAngle();

        g.translate(this.getX(), this.getY());
        g.rotate(angle * Math.PI / 180);
        g.translate(-this.getX(), -this.getY());

        g.setColor(colorBlue);
        g.fillRect(this.getX() - 11, this.getY() - 2, 22, 8);
        g.fillRect(this.getX() - 11, this.getY() - 7, 22, 3);
        g.fillRect(this.getX() - 13, this.getY() + 6, 6, 10);
        g.fillRect(this.getX() + 7, this.getY() + 6, 6, 10);
        g.fillRect(this.getX() - 13, this.getY() - 14, 3, 8);
        g.fillRect(this.getX() + 10, this.getY() - 14, 3, 8);

        g.setColor(colorYellow);
        g.fillRect(this.getX() - 6, this.getY() - 10, 12, 8);
        g.fillRect(this.getX() - 1, this.getY() - 12, 2, 2);
        g.fillRect(this.getX() - 3, this.getY() - 2, 6, 10);

        g.setColor(colorRed);
        g.fillRect(this.getX() - 3, this.getY() - 1, 6, 5);
        g.fillRect(this.getX() - 3, this.getY() + 6, 6, 2);
        g.fillRect(this.getX() - 1, this.getY() + 8, 2, 2);
        g.fillRect(this.getX() + 2, this.getY() - 10, 2, 3);
        g.fillRect(this.getX() + 2, this.getY() - 8, 4, 2);
        g.fillRect(this.getX() - 4, this.getY() - 10, 2, 3);
        g.fillRect(this.getX() - 6, this.getY() - 8, 4, 2);

        g.translate(this.getX(), this.getY());
        g.rotate(-angle * Math.PI / 180);
        g.translate(-this.getX(), -this.getY());
    }
}
