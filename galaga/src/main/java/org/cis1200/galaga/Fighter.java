package org.cis1200.galaga;

import java.awt.*;

public class Fighter extends GalagaObj {
    private boolean status = true;
    private boolean taken = false;
    private Bug bug = null;

    int width;

    private boolean dub = false;

    public Fighter(int x, int y) {
        super(x, y);
        width = WIDTH + 6;
        setWidth(width);
    }

    public void setStatus(boolean b) {
        if (!taken) {
            this.status = b;
        }
    }

    public boolean getDub() {
        return this.dub;
    }

    public void setDub(boolean b) {
        if (b) {
            this.setWidth(this.getWidth() * 2);
        }
        this.dub = b;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setTaken(boolean b, Bug bug) {
        this.taken = b;
        this.bug = bug;
    }

    public void taken(boolean b) {
        this.taken = b;
    }

    public boolean getTaken() {
        return this.taken;
    }

    public void drawFighter(Graphics g, int posX) {
        g.setColor(Color.RED);
        g.fillRect(posX - width / 2, getY() + width / 2 - 10, 2, 6);
        g.fillRect(posX + width / 2 - 2, getY() + width / 2 - 10, 2, 6);
        g.fillRect(posX - width / 2 + 4, getY() + width / 2 - 16, 2, 6);
        g.fillRect(posX + width / 2 - 6, getY() + width / 2 - 16, 2, 6);

        g.setColor(Color.WHITE);
        g.fillRect(posX - 1, getY() - width / 2 - 1, 2, 8);
        g.fillRect(posX - 4, getY() - width / 2 + 4, 8, 4);
        g.fillRect(posX - 6, getY() - width / 2 + 8, 12, 8);
        g.fillRect(posX - width / 2, getY() + width / 2 - 6, 3, 6);
        g.fillRect(posX + width / 2 - 3, getY() + width / 2 - 6, 3, 6);
        g.fillRect(posX - width / 2 + 4, getY() + width / 2 - 12, 3, 6);
        g.fillRect(posX + width / 2 - 7, getY() + width / 2 - 12, 3, 6);
        int[] x = {posX, posX - width / 2, posX + width / 2};
        int[] y = {getY() - width / 2 + 4, getY() + width / 2, getY() + width / 2};
        g.fillPolygon(x,y,3);

        g.setColor(Color.RED);
        g.fillRect(posX - 2, getY() - 3, 4, 6);
        g.fillRect(posX - 5, getY() + 1, 4, 4);
        g.fillRect(posX + 1, getY() + 1, 4, 4);

        g.setColor(Color.BLACK);
        int[] x1 = {posX - 5, posX - 2, posX - 11};
        int[] y1 = {getY() + width / 2 - 8, getY() + width / 2, getY() + width / 2};
        g.fillPolygon(x1, y1,3);
        int[] x2 = {posX + 5, posX + 2, posX + 11};
        int[] y2 = {getY() + width / 2 - 8, getY() + width / 2, getY() + width / 2};
        g.fillPolygon(x2, y2,3);
    }

    @Override
    public void draw(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;

        if (bug != null && taken) {
            if (bug.getY() > 0 && this.getY() > bug.getY() - bug.getHeight() && !dub) {
                this.setAngle(getAngle() + 10);
                moveY(-3);
            } else if (dub) {
                moveY(4);
            } else {
                this.setAngle(0);
                this.setX(bug.getX());
                this.setY(bug.getY() - (HEIGHT + 8));
            }
        } else {
            this.setAngle(0);
        }

        double angle = this.getAngle();

        g.translate(this.getX(), this.getY());
        g.rotate(angle * Math.PI / 180);
        g.translate(-this.getX(), -this.getY());


        if (!dub || taken) {
            drawFighter(g, getX());
        } else {
            drawFighter(g, getX() - (width / 2));
            drawFighter(g, getX() + (width / 2));
        }

        g.translate(this.getX(), this.getY());
        g.rotate(-angle * Math.PI / 180);
        g.translate(-this.getX(), -this.getY());
    }

}
