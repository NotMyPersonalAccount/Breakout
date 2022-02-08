package breakout.objects;

import processing.core.PApplet;

public class Brick {
    public final float x;
    public final float y;
    public final float width;
    public final float height;
    private final int color;

    public Brick(float x, float y, float sizeX, float sizeY, int color) {
        this.x = x;
        this.y = y;
        this.width = sizeX;
        this.height = sizeY;
        this.color = color;
    }

    public void draw(PApplet app) {
        app.fill(color);
        app.rect(x - (width / 2), y - (height / 2), width, height);
    }
}