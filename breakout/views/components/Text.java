package breakout.views.components;

import processing.core.PApplet;

import static breakout.Sketch.BASE_TEXT_SIZE;
import static processing.core.PConstants.CENTER;

public class Text extends BaseComponent {
    protected float textSize;
    protected String text;

    public Text(PApplet app, float x, float y, float marginX, float marginY, int backgroundColor, int borderColor, float textSize, String text) {
        super(app, x, y, marginX, marginY, backgroundColor, borderColor);
        this.textSize = textSize;
        this.text = text;
    }

    public void draw() {
        app.textAlign(CENTER, CENTER);
        app.textSize(textSize);
        app.fill(255);
        app.text(text, x, y);

    }

    public float getWidth() {
        return app.textWidth(text) * textSize / app.g.textSize;
    }

    public float getHeight() {
        return textSize;
    }

    public void onClick() {
    }

    public static class Builder {
        private final PApplet app;
        private float x = 0;
        private float y = 0;
        private float marginX = BASE_TEXT_SIZE / 4f;
        private float marginY = BASE_TEXT_SIZE / 4f;
        private int backgroundColor;
        private int borderColor;
        private float textSize = BASE_TEXT_SIZE;
        private String text;

        public Builder(PApplet app) {
            this.app = app;
            backgroundColor = app.color(255, 255, 255, 96);
            borderColor = app.color(255);
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

        public Builder setTextSize(float textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Text build() {
            return new Text(app, x, y, marginX, marginY, backgroundColor, borderColor, textSize, text);
        }
    }
}
