package org.cis1200.galaga;

public abstract class Bug extends GalagaObj {
    //x-motion
    private int startY;
    private int startX;

    private boolean isAlive;

    private int speed;
    private boolean endgame = true;

    private boolean top = false;
    private int points;

    double beamNum = 0;
    boolean beam = false;


    public Bug(int x, int y) {
        super(x, y);
        this.isAlive = true;
        this.startY = y;
        this.startX = x;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int s) {
        this.speed = s;
        endgame = !endgame;
    }

    public void setPoints(int p) {
        this.points = p;
    }

    public void updateStartX(int v) {
        this.startX = v;
    }

    public int getStartX() {
        return this.startX;
    }

    //to update bug array based on kills
    public void beenHit() {
        this.isAlive = false;
        this.setGo(false);
    }

    //since startX is updated for bugs
    @Override
    public void restart() {
        super.restart();
        this.setX(this.startX);
    }

    public void move(int val) {
        moveX(val);
    }

    public int getPoints() {
        return this.points;
    };

    public boolean status() {
        return isAlive;
    }

    //for testing purposes
    public void setStatus(boolean b) {
        isAlive = b;
        if (!b) {
            setGo(false);
        }
    }

    //if bug re-emerges from top
    public boolean getTop() {
        return this.top;
    }

    public void setTop(boolean b) {
        this.top = b;
    }

    public void setBeamNum(double b) {
        this.beamNum = b;
    }

    public void setBeam(boolean b) {
        this.beam = b;
    }

    public boolean taken(GalagaObj that) {
        return (this.beam && (this.getX() + this.WIDTH + 14 > that.getX()
                && that.getX() + that.WIDTH + 14 > this.getX()) &&
                beamNum > 15);
    }

    public boolean getEndgame() {
        return this.endgame;
    }

    //called by tick
    public void fall() {
        //bug emerges from top if it makes it to the bottom of the frame unharmed
        if (this.getY() - GalagaObj.HEIGHT / 2 >= GameCourt.GAME_HEIGHT) {
            this.moveY(-this.getY());
            this.setX(startX);
            if (!endgame) {
                setTop(true);
                this.setAngle(180);
            }
        }

        //resets back to original position upon returning
        if (this.startY < (this.getY() + 7) && this.startY > (this.getY() - 7)) {
            if (!endgame) {
                this.setAngle(0);
                setGo(false);
                setTop(false);
                this.restart();
            }
        }
    }
}
