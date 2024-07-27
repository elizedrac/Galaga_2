package org.cis1200.galaga;

import java.awt.*;

public class Fire extends GalagaObj {
    private int width;
    private int height;
    private Color color;
    //at same height as fighter but out of screen to begin with
    public Fire(Color color) {
        super(-10, 660);
        this.setWidth(2);
        this.setHeight(8);
        this.color = color;
    }

    public void shoot() {
        moveY(-10);
        if (this.getY() + 4 < 0) {
            this.restart();
            this.setGo(false);
        }
    }

    public void bugShoot() {
        if (this.getY() - getHeight() >= GameCourt.GAME_HEIGHT) {
            this.setGo(false);
            this.restart();
        } else {
            moveY(+8);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(getX() - 1,getY() - 4, getWidth(), getHeight());
    }

}
