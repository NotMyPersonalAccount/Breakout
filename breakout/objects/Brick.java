package breakout.objects;

import processing.core.PApplet;

public class Brick {
    public float x;
    public float y;
    private final float sizeX;
    private final float sizeY;
    private final int color;

    public Brick(float x, float y, float sizeX, float sizeY, int color) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.color = color;
    }

    public void draw(PApplet app) {
        app.fill(color);
        app.rect(x - (sizeX / 2), y, sizeX, sizeY);
    }

    public boolean isColliding(Ball ball) {
        return ball.x + ball.radius >= x - (sizeX / 2) && ball.x <= x + (sizeX / 2) && ball.y + ball.radius >= y && ball.y <= y + sizeY;
    }
}