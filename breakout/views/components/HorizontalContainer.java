package breakout.views.components;

import processing.core.PApplet;

import static breakout.Sketch.BASE_TEXT_SIZE;

// HorizontalContainer holds a collection of components and automatically positions them horizontally.
final public class HorizontalContainer extends Component.Base {
    private final float fixedHeight;
    private final float fixedWidth;
    private final float paddingX;
    private final float paddingY;
    private final ComponentAlignment.Y alignment;
    private final Component[] components;

    public HorizontalContainer(PApplet app, BaseProperties properties, float fixedWidth, float fixedHeight, float paddingX, float paddingY, ComponentAlignment.Y alignment, Component... components) {
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
        float currentX = properties.x - getWidth() / 2;
        for (Component component : components) {
            float currentY = switch (alignment) {
                case TOP -> properties.y - getHeight() / 2 + component.getHeight() / 2;
                case BOTTOM -> properties.y + getHeight() / 2 - component.getHeight() / 2;
                default -> properties.y;
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
        return Math.max(width, fixedWidth);
    }

    // getHeight returns the height of the container; it is the height of the tallest component.
    public float getHeight() {
        float height = fixedHeight;
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
        private BaseProperties properties;
        private float fixedWidth = -1;
        private float fixedHeight = -1;
        private float paddingX = BASE_TEXT_SIZE / 2f;
        private float paddingY = BASE_TEXT_SIZE / 2f;
        private ComponentAlignment.Y alignment = ComponentAlignment.Y.CENTER;
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

        public Builder setAlignment(ComponentAlignment.Y alignment) {
            this.alignment = alignment;
            return this;
        }

        public Builder withComponents(Component... components) {
            this.components = components;
            return this;
        }

        public HorizontalContainer build() {
            return new HorizontalContainer(app, properties, fixedWidth, fixedHeight, paddingX, paddingY, alignment, components);
        }
    }
}
