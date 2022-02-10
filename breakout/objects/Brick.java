package breakout.objects;

import processing.core.PApplet;

import static breakout.Sketch.BASE_TEXT_SIZE;
import static processing.core.PConstants.*;

public class Brick {
    public final float x;
    public final float y;
    public final float width;
    public final float height;
    public float health;
    public final float maxHealth;
    public final int color;

    public Brick(float x, float y, float width, float height, float health, float maxHealth, int color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.maxHealth = maxHealth;
        this.color = color;
    }

    public void draw(PApplet app) {
        app.colorMode(app.HSB);
        // Brick color is based on health. Lower health results in a less saturated color.
        app.fill(app.hue(color), app.saturation(color) * (health / maxHealth), app.brightness(color));
        app.rect(x - width / 2, y - height / 2, width, height);
        app.fill(0);
        // Only display health text if the brick has been damaged.
        if (health != maxHealth) {
            app.textAlign(CENTER, CENTER);
            app.textSize(BASE_TEXT_SIZE / 1.5f);
            app.text((int) health, x, y);
        }
        app.colorMode(RGB);
    }
}