package breakout.views;

import breakout.Sketch;
import breakout.views.components.Component;

public class View {
    protected Sketch app;
    protected Component[] components;

    public View(Sketch app, Component[] components) {
        this.app = app;
        this.components = components == null ? new Component[0] : components;
    }

    public void draw() {
        for (Component component : components) {
            component.draw();
        }
    }

    public void mousePressed() {
        for (Component component : components) {
            if (component.isMouseOver()) component.onClick();
        }
    }

    public void keyPressed() {

    }
}
