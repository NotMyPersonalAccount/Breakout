package breakout.views.components;

import processing.core.PApplet;

import static breakout.Sketch.*;

// HorizontalContainer holds a collection of components and automatically positions them horizontally.
final public class VerticalContainer extends Component.Base {
    private final float fixedHeight;
    private final float fixedWidth;
    private final float paddingX;
    private final float paddingY;
    private final ComponentAlignment.X alignment;
    private final Component[] components;

    public VerticalContainer(PApplet app, BaseProperties properties, float fixedWidth, float fixedHeight, float paddingX, float paddingY, ComponentAlignment.X alignment, Component... components) {
        super(app, properties);
        this.fixedWidth = fixedWidth;
        this.fixedHeight = fixedHeight;
        this.paddingX = paddingX;
        this.paddingY = paddingY;
        this.alignment = alignment;
        this.components = components;
    }

    public void draw() {
        if (properties.backgroundColor >= 0) {
            app.fill(properties.backgroundColor);
            app.stroke(properties.borderColor);
            app.rect(properties.x - getWidth() / 2, properties.y - getHeight() / 2, getWidth(), getHeight(), 5);
        }
        float currentY = properties.y - getHeight() / 2 + paddingY;
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            float currentX = switch (alignment) {
                case LEFT -> properties.x - getWidth() / 2 + component.getWidth() / 2 + paddingX;
                case RIGHT -> properties.x + getWidth() / 2 - component.getWidth() / 2 - paddingX;
                default -> properties.x;
            };
            if (i != 0) {
                currentY += component.getMarginY();
            }
            component.setPosition(currentX, currentY + component.getHeight() / 2);
            component.draw();
            currentY += component.getHeight();
            if (i != components.length - 1) {
                currentY += component.getMarginY();
            }
        }
    }

    // getWidth returns the width of the container; it is the width of the widest component.
    public float getWidth() {
        float width = fixedWidth;
        for (Component component : components) {
            width = Math.max(width, component.getWidth() + paddingX * 2);
        }
        return width;
    }

    // getHeight returns the height of the container; it is the sum of the height of all components.
    public float getHeight() {
        float height = 0;
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            if (i != 0) {
                height += component.getMarginY();
            }
            height += component.getHeight();
            if (i != components.length - 1) {
                height += component.getMarginY();
            }
        }
        return Math.max(fixedHeight, height + paddingY * 2);
    }

    public void onClick() {
        for (Component component : components) {
            if (component.isMouseOver()) component.onClick();
        }
    }

    public static class Builder {
        private final PApplet app;
        private BaseProperties properties;
        private float fixedWidth = -1;
        private float fixedHeight = -1;
        private float paddingX = BASE_TEXT_SIZE / 2f;
        private float paddingY = BASE_TEXT_SIZE / 2f;
        private ComponentAlignment.X alignment = ComponentAlignment.X.CENTER;
        private Component[] components;

        public Builder(PApplet app) {
            this.app = app;
            this.properties = new BaseProperties.Builder().setBackgroundColor(app.color(255, 255, 255, 64)).setBorderColor(app.color(255)).build();
        }

        public Builder setProperties(BaseProperties properties) {
            this.properties = properties;
            return this;
        }

        public Builder setFixedWidth(float fixedWidth) {
            this.fixedWidth = fixedWidth;
            return this;
        }

        public Builder setFixedHeight(float fixedHeight) {
            this.fixedHeight = fixedHeight;
            return this;
        }

        public Builder setPaddingX(float paddingX) {
            this.paddingX = paddingX;
            return this;
        }

        public Builder setPaddingY(float paddingY) {
            this.paddingY = paddingY;
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
            return new VerticalContainer(app, properties, fixedWidth, fixedHeight, paddingX, paddingY, alignment, components);
        }
    }
}
