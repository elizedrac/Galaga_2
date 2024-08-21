package org.cis1200.galaga;

import java.awt.*;

//for backgroun effects- places randomly colored and positioned dot
public class RandomDot extends GalagaObj {
    private int r;
    private int g;
    private int b;
    private Color color;
    private int opacity;

    public RandomDot() {
        super((int)(Math.random() * GameCourt.GAME_WIDTH),
                (int)(Math.random() * GameCourt.GAME_HEIGHT));
        r = (int)(Math.random() * 200 + 55);
        g = (int)(Math.random() * 200 + 55);
        b = (int)(Math.random() * 200 + 55);
        this.opacity = 100;
        this.color = new Color(r, g, b, opacity);
    }

    public void setOpacity(int v) {
        this.opacity = v;
    }

    @Override
    public void draw(Graphics g) {
        this.color = new Color(r, this.g, b, opacity);
        g.setColor(this.color);
        g.fillOval(this.getX(), this.getY(), 4, 4);
    }

}
