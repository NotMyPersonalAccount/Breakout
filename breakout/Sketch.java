package breakout;

import breakout.views.StartView;
import breakout.views.View;
import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.SoundFile;

public class Sketch extends PApplet {
    public View view = new StartView(this);

    public static final int CANVAS_SIZE_X = 480;
    public static final int CANVAS_SIZE_Y = 480;

    public static final int BASE_TEXT_SIZE = CANVAS_SIZE_X / 20;

    private PImage background;

    public SoundFile brickHitSound;
    public SoundFile buttonClickSound;

    public void settings() {
        size(CANVAS_SIZE_X, CANVAS_SIZE_Y);
    }

    public void setup() {
        surface.setTitle("Breakout");
        background = loadImage("pig.jpg");
        background.resize(CANVAS_SIZE_X, CANVAS_SIZE_Y);

        brickHitSound = new SoundFile(this, "brick_hit.mp3");
        buttonClickSound = new SoundFile(this, "button_click.mp3");
    }

    public void draw() {
        image(background, 0, 0);

        pushMatrix();
        view.draw();
        popMatrix();
    }

    public void mousePressed() {
        view.mousePressed();
    }

    public void keyPressed() {
        view.keyPressed();
    }

    public void setView(View view) {
        this.view = view;
    }
}