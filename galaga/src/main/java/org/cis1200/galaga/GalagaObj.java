package org.cis1200.galaga;

import java.awt.*;

public abstract class GalagaObj {
    public static final int WIDTH = 26;
    public static final int HEIGHT = 28;
    private int height;
    private int width;
    //position variables
    private int x;
    private int y;

    private int startX;
    private int startY;

    private boolean go;

    private double angle = 0;

    public GalagaObj(int x, int y) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.go = false;
        this.width = WIDTH;
        this.height = HEIGHT;
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double d) {
        this.angle = d;
    }

    public void setX(int n) {
        this.x = n;
    }

    public void setY(int n) {
        this.y = n;
    }

    public void restart() {
        this.x = startX;
        this.y = startY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void moveX(int v) {
        this.x += v;
    }

    public void moveY(int v) {
        this.y += v;
    }

    public boolean getGo() {
        return this.go;
    }

    public void setGo(boolean b) {
        this.go = b;
    }

    public void setWidth(int x) {
        this.width = x;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int x) {
        this.height = x;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean hit(GalagaObj that) {
        return (this.getX() + this.width / 2 + that.width / 2 >= that.getX()
                && this.getY() + this.height / 2 + that.height / 2 >= that.getY()
                && that.getX() + this.width / 2 + that.width / 2 >= this.getX()
                && that.getY() + this.height / 2 + that.height / 2 >= this.getY());
    }

    public abstract void draw(Graphics g);
}
