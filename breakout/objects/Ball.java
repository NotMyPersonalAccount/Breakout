package breakout.objects;

import processing.core.PApplet;

public class Ball {
    public float radius;
    public float x;
    public float y;
    public float xSpeed;
    public float ySpeed;


    public Ball(float radius, float x, float y, float xSpeed, float ySpeed) {
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void draw(PApplet app) {
        app.fill(255);
        app.ellipse(x, y, radius * 2, radius * 2);
    }
}