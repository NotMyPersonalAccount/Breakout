package breakout;

import breakout.views.StartView;
import breakout.views.View;
import breakout.views.input.Input;
import breakout.views.input.TextInput;
import processing.core.PApplet;

public class Sketch extends PApplet {
    public View view = new StartView(this);

    public static final int CANVAS_SIZE_X = 480;
    public static final int CANVAS_SIZE_Y = 480;

    public static final int BUTTON_TEXT_SIZE = 12;

    public void settings() {
        size(CANVAS_SIZE_X, CANVAS_SIZE_Y);
    }

    public void draw() {
        background(255);

        view.draw();
    }

    public void mousePressed() {
        view.mousePressed();
        for (Input input : view.getInputs()) {
            if (input.isHovering()) {
                input.onClick();
                break;
            }
        }
    }

    public void keyPressed() {
        view.keyPressed();
        for (Input input : view.getInputs()) {
            if (input instanceof TextInput) {
                ((TextInput) input).onInput(key);
                break;
            }
        }
    }

    public void setView(View view) {
        this.view = view;
    }
}