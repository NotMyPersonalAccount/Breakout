package breakout.views;

import processing.core.PApplet;
import breakout.views.input.Input;
import breakout.views.input.TextInput;

public class View {
    protected PApplet app;
    protected Input[] inputs;

    public View(PApplet app, Input[] inputs) {
        this.app = app;
        this.inputs = inputs;
    }

    public void draw() {
        for (Input input : inputs) {
            input.draw();
        }
    }

    public void mousePressed() {
        for (Input input : inputs) {
            if (input.isHovering()) input.onClick();
        }
    }

    public void keyPressed() {
        for (Input input : inputs) {
            if (input instanceof TextInput) {
                ((TextInput) input).onInput(app.key);
            }
        }
    }

    public Input[] getInputs() {
        if (inputs == null) {
            return new Input[]{};
        }
        return inputs;
    }
}
