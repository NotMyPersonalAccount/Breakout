package breakout.views.components;

import breakout.Sketch;

import static breakout.Sketch.*;

// Component is an interface containing the bare minimum requirements for a component.
public interface Component {
    void draw();

    float getWidth();

    float getHeight();

    float getMarginX();

    float getMarginY();

    void setPosition(float x, float y);

    void onClick();

    boolean isMouseOver();

    // Base partially implements the Component interface w/ methods for margin & position.
    abstract class Base implements Component {
        protected final Sketch app;

        protected BaseProperties properties;

        public Base(Sketch app, BaseProperties properties) {
            this.app = app;
            this.properties = properties;
        }

        public float getMarginX() {
            return properties.marginX;
        }

        public float getMarginY() {
            return properties.marginY;
        }

        public void setPosition(float x, float y) {
            properties.x = x;
            properties.y = y;
        }

        public boolean isMouseOver() {
            return app.mouseX > properties.x - this.getWidth() / 2 && app.mouseX < properties.x + this.getWidth() / 2 && app.mouseY > properties.y - this.getHeight() / 2 && app.mouseY < properties.y + this.getHeight() / 2;
        }
    }

    // BaseProperties holds component properties required for all components.
    class BaseProperties {
        public float x;
        public float y;

        public float marginX;
        public float marginY;

        public int backgroundColor;
        public int borderColor;

        public BaseProperties(float x, float y, float marginX, float marginY, int backgroundColor, int borderColor) {
            this.x = x;
            this.y = y;
            this.marginX = marginX;
            this.marginY = marginY;
            this.backgroundColor = backgroundColor;
            this.borderColor = borderColor;
        }

        public static class Builder {
            public float x = CANVAS_SIZE_X / 2f;
            public float y = CANVAS_SIZE_Y / 2f;

            public float marginX = BASE_TEXT_SIZE / 4f;
            public float marginY = BASE_TEXT_SIZE / 4f;

            public int backgroundColor = -1;
            public int borderColor = -1;

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

            public BaseProperties build() {
                return new BaseProperties(x, y, marginX, marginY, backgroundColor, borderColor);
            }
        }
    }
}
