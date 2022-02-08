package breakout;

import breakout.views.StartView;
import breakout.views.View;
import processing.core.PApplet;

public class Sketch extends PApplet {
    public View view = new StartView(this);

    public static final int CANVAS_SIZE_X = 480;
    public static final int CANVAS_SIZE_Y = 480;

    public static final int BASE_TEXT_SIZE = CANVAS_SIZE_X / 20;

    public void settings() {
        size(CANVAS_SIZE_X, CANVAS_SIZE_Y);
    }

    public void draw() {
        background(255);

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