package breakout.views;

import breakout.Sketch;
import breakout.views.input.Input;
import breakout.views.input.TextInput;

public class View {
    protected Sketch app;
    protected Input[] inputs;

    public View(Sketch app, Input[] inputs) {
        this.app = app;
        this.inputs = inputs == null ? new Input[0] : inputs;
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
}
