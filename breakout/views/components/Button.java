package breakout.views.components;

import processing.core.PApplet;

import static breakout.Sketch.BASE_TEXT_SIZE;
import static breakout.Sketch.CANVAS_SIZE_X;

public class Button extends BaseComponent {
    protected float width;
    protected float height;
    protected Text textComponent;
    protected Runnable onClick;

    public Button(PApplet app, float x, float y, float marginX, float marginY, int backgroundColor, int borderColor, float width, float height, float textSize, String text, Runnable onClick) {
        super(app, x, y, marginX, marginY, backgroundColor, borderColor);
        this.width = width;
        this.height = height;
        this.textComponent = new Text.Builder(app).setText(text).setTextSize(textSize).build();
        this.onClick = onClick;
    }

    public void draw() {
        app.fill(255, 255, 255, 160);
        app.stroke(225);
        app.rect(x - width / 2, y - height / 2, width, height, 5);

        textComponent.setPosition(x, y);
        textComponent.draw();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void onClick() {
        onClick.run();
    }

    public static class Builder {
        private final PApplet app;
        private float x = 0;
        private float y = 0;
        private float marginX = BASE_TEXT_SIZE / 4f;
        private float marginY = BASE_TEXT_SIZE / 4f;
        private int backgroundColor;
        private int borderColor;
        private float width = CANVAS_SIZE_X / 2.4f;
        private float height = BASE_TEXT_SIZE;
        private float textSize = BASE_TEXT_SIZE;
        private String text;
        private Runnable onClick;

        public Builder(PApplet app) {
            this.app = app;
            backgroundColor = app.color(255, 255, 255, 160);
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

        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(float height) {
            this.height = height;
            return this;
        }

        public Builder setTextSize(float textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setOnClick(Runnable onClick) {
            this.onClick = onClick;
            return this;
        }

        public Button build() {
            return new Button(app, x, y, marginX, marginY, backgroundColor, borderColor, width, height, textSize, text, onClick);
        }
    }
}
