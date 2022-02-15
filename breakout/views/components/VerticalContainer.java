package breakout.views.components;

import processing.core.PApplet;

import static breakout.Sketch.*;

// HorizontalContainer holds a collection of components and automatically positions them horizontally.
final public class VerticalContainer extends BaseComponent {
    private final int backgroundColor;
    private final int borderColor;
    private final ComponentAlignment.X alignment;
    private final Component[] components;

    public VerticalContainer(PApplet app, float x, float y, float marginX, float marginY, int backgroundColor, int borderColor, ComponentAlignment.X alignment, Component... components) {
        super(app, x, y, marginX, marginY, backgroundColor, borderColor);
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.alignment = alignment;
        this.components = components;
    }

    public void draw() {
        if (backgroundColor >= 0) {
            app.fill(backgroundColor);
            app.stroke(borderColor);
            app.rect(x - getWidth() / 2, y - getHeight() / 2, getWidth(), getHeight(), 5);
        }
        float currentY = y - getHeight() / 2;
        for (Component component : components) {
            float currentX = switch (alignment) {
                case LEFT -> x - getWidth() / 2 + component.getWidth() / 2;
                case RIGHT -> x + getWidth() / 2 - component.getWidth() / 2;
                default -> x;
            };
            currentY += component.getMarginY();
            component.setPosition(currentX, currentY + component.getHeight() / 2);
            component.draw();
            currentY += component.getHeight() + +component.getMarginY();
        }
    }

    // getWidth returns the width of the container; it is the width of the widest component.
    public float getWidth() {
        float width = 0;
        for (Component component : components) {
            width = Math.max(width, component.getWidth() + component.getMarginX() * 2);
        }
        return width;
    }

    // getHeight returns the height of the container; it is the sum of the height of all components.
    public float getHeight() {
        float height = 0;
        for (Component component : components) {
            height += component.getHeight() + component.getMarginY() * 2;
        }
        return height;
    }

    public void onClick() {
        for (Component component : components) {
            if (component.isMouseOver()) component.onClick();
        }
    }

    public static class Builder {
        private final PApplet app;
        private float x = CANVAS_SIZE_X / 2f;
        private float y = CANVAS_SIZE_Y / 2f;
        private float marginX = BASE_TEXT_SIZE / 4f;
        private float marginY = BASE_TEXT_SIZE / 4f;
        private int backgroundColor;
        private int borderColor;
        private ComponentAlignment.X alignment = ComponentAlignment.X.CENTER;
        private Component[] components;

        public Builder(PApplet app) {
            this.app = app;
            backgroundColor = app.color(255, 255, 255, 96);
            borderColor = app.color(225);
        }

        public Builder setPosition(float x, float y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder setMargins(float marginX, float marginY) {
            this.marginX = marginX;
            this.marginY = marginY;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setBorderColor(int borderColor) {
            this.borderColor = borderColor;
            return this;
        }

        public Builder setAlignment(ComponentAlignment.X alignment) {
            this.alignment = alignment;
            return this;
        }

        public Builder withComponents(Component... components) {
            this.components = components;
            return this;
        }

        public VerticalContainer build() {
            return new VerticalContainer(app, x, y, marginX, marginY, backgroundColor, borderColor, alignment, components);
        }
    }
}
