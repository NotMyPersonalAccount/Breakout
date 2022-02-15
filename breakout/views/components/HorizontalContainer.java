package breakout.views.components;

import processing.core.PApplet;

import static breakout.Sketch.*;

// HorizontalContainer holds a collection of components and automatically positions them horizontally.
final public class HorizontalContainer extends BaseComponent {
    private final int backgroundColor;
    private final int borderColor;
    private final ComponentAlignment.Y alignment;
    private final Component[] components;

    public HorizontalContainer(PApplet app, float x, float y, float marginX, float marginY, int backgroundColor, int borderColor, ComponentAlignment.Y alignment, Component... components) {
        super(app, x, y, marginX, marginY, backgroundColor, borderColor);
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.alignment = alignment;
        this.components = components;
    }

    public void draw() {
        if (backgroundColor != 0) {
            app.fill(backgroundColor);
            app.stroke(borderColor);
            app.rect(x - getWidth() / 2, y - getHeight() / 2, getWidth(), getHeight(), 5);
        }
        float currentX = x - getWidth() / 2;
        for (Component component : components) {
            float currentY = switch (alignment) {
                case TOP -> y - getHeight() / 2 + component.getHeight() / 2;
                case BOTTOM -> y + getHeight() / 2 - component.getHeight() / 2;
                default -> y;
            };
            currentX += component.getMarginX();
            component.setPosition(currentX + component.getWidth() / 2, currentY);
            component.draw();
            currentX += component.getWidth() + component.getMarginX();
        }
    }

    // getWidth returns the width of the container; it is the sum of the widths of all components.
    public float getWidth() {
        float width = 0;
        for (Component component : components) {
            width += component.getWidth() + component.getMarginX() * 2;
        }
        return width;
    }

    // getHeight returns the height of the container; it is the height of the tallest component.
    public float getHeight() {
        float height = 0;
        for (Component component : components) {
            height = Math.max(height, component.getHeight() + component.getMarginY() * 2);
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
        private ComponentAlignment.Y alignment = ComponentAlignment.Y.CENTER;
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

        public Builder setAlignment(ComponentAlignment.Y alignment) {
            this.alignment = alignment;
            return this;
        }

        public Builder withComponents(Component... components) {
            this.components = components;
            return this;
        }

        public HorizontalContainer build() {
            return new HorizontalContainer(app, x, y, marginX, marginY, backgroundColor, borderColor, alignment, components);
        }
    }
}
