package breakout.views.components;

import processing.core.PApplet;

import static breakout.Sketch.BASE_TEXT_SIZE;

// Container is a component that contains and automatically arranges child components. Awful code, BEWARE!
final public class Container extends Component.Base {
    private final float fixedHeight;
    private final float fixedWidth;
    private final float paddingX;
    private final float paddingY;
    private final Alignment.X alignmentX;
    private final Alignment.Y alignmentY;
    private final Direction direction;
    private final Component[] components;

    public Container(PApplet app, BaseProperties properties, float fixedWidth, float fixedHeight, float paddingX, float paddingY, Alignment.X alignmentX, Alignment.Y alignmentY, Direction direction, Component... components) {
        super(app, properties);
        this.fixedWidth = fixedWidth;
        this.fixedHeight = fixedHeight;
        this.paddingX = paddingX;
        this.paddingY = paddingY;
        this.alignmentX = alignmentX;
        this.alignmentY = alignmentY;
        this.direction = direction;
        this.components = components;
    }

    public void draw() {
        if (properties.backgroundColor >= 0) {
            app.fill(properties.backgroundColor);
            app.stroke(properties.borderColor);
            app.rect(properties.x - getWidth() / 2, properties.y - getHeight() / 2, getWidth(), getHeight(), 5);
        }
        //TODO: Cleanup
        float primaryStart = switch (direction) {
            case HORIZONTAL -> switch (alignmentX) {
                case LEFT -> properties.x - getWidth() / 2 + paddingX;
                case CENTER -> properties.x - getPrimarySide() / 2;
                case RIGHT -> properties.x + getWidth() / 2 - paddingX;
            };
            case VERTICAL -> switch (alignmentY) {
                case TOP -> properties.y - getHeight() / 2 + paddingY;
                case CENTER -> properties.y - getPrimarySide() / 2;
                case BOTTOM -> properties.y + getHeight() / 2 - paddingY;
            };
        };
        float directionFactor;
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            switch (direction) {
                case HORIZONTAL -> {
                    directionFactor = alignmentX == Alignment.X.RIGHT ? -1 : 1;
                    if (i != 0) {
                        primaryStart += getMarginX() * directionFactor;
                    }
                    component.setPosition(primaryStart + component.getWidth() / 2 * directionFactor, switch (alignmentY) {
                        case TOP -> properties.x - getHeight() / 2 - paddingY;
                        case CENTER -> properties.x - component.getHeight() / 2;
                        case BOTTOM -> properties.x + getHeight() / 2 + paddingY;
                    } + component.getHeight() / 2 * directionFactor);
                    primaryStart += component.getWidth() * directionFactor;
                    if (i != components.length - 1) {
                        primaryStart += getMarginX() * directionFactor;
                    }
                }
                case VERTICAL -> {
                    directionFactor = alignmentY == Alignment.Y.BOTTOM ? -1 : 1;
                    if (i != 0) {
                        primaryStart += getMarginY() * directionFactor;
                    }
                    component.setPosition(switch (alignmentX) {
                        case LEFT -> properties.x - getWidth() / 2 + paddingX;
                        case CENTER -> properties.x - component.getWidth() / 2;
                        case RIGHT -> properties.x + getWidth() / 2 - paddingX;
                    } + component.getWidth() / 2 * directionFactor, primaryStart + component.getHeight() / 2 * directionFactor);
                    primaryStart += component.getHeight() * directionFactor;
                    if (i != components.length - 1) {
                        primaryStart += getMarginX() * directionFactor;
                    }
                }
            }
            component.draw();
        }
    }

    public float getWidth() {
        //TODO: Cleanup
        return Math.max(fixedWidth, (direction == Direction.HORIZONTAL ? getPrimarySide() : getSecondarySide()) + paddingX * 2);
    }

    public float getHeight() {
        //TODO: Cleanup
        return Math.max(fixedHeight, (direction == Direction.HORIZONTAL ? getSecondarySide() : getPrimarySide()) + paddingY * 2);
    }

    public float getPrimarySide() {
        //TODO: Cleanup
        float primary = 0;
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            float margin = direction == Direction.HORIZONTAL ? component.getMarginX() : component.getMarginY();

            if (i != 0) {
                primary += margin;
            }
            primary += direction == Direction.HORIZONTAL ? component.getWidth() : component.getHeight();
            if (i != components.length - 1) {
                primary += margin;
            }
        }
        return primary;
    }

    public float getSecondarySide() {
        //TODO: Cleanup
        float secondary = 0;
        for (Component component : components) {
            secondary = Math.max(secondary, direction == Direction.HORIZONTAL ? component.getHeight() : component.getWidth());
        }
        return secondary;
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
        private Alignment.X alignmentX = Alignment.X.CENTER;
        private Alignment.Y alignmentY = Alignment.Y.CENTER;
        public Direction direction;
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

        public Builder setAlignmentX(Alignment.X alignment) {
            this.alignmentX = alignment;
            return this;
        }

        public Builder setAlignmentY(Alignment.Y alignment) {
            this.alignmentY = alignment;
            return this;
        }

        public Builder setDirection(Direction direction) {
            this.direction = direction;
            return this;
        }

        public Builder withComponents(Component... components) {
            this.components = components;
            return this;
        }

        public Container build() {
            return new Container(app, properties, fixedWidth, fixedHeight, paddingX, paddingY, alignmentX, alignmentY, direction, components);
        }
    }

    public static class Alignment {
        public static enum X {
            LEFT, CENTER, RIGHT
        }

        public static enum Y {
            TOP, CENTER, BOTTOM
        }
    }

    public static enum Direction {
        HORIZONTAL, VERTICAL
    }
}
