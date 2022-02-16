package breakout.views.components;

import processing.core.PApplet;

import static breakout.Sketch.BASE_TEXT_SIZE;
import static processing.core.PConstants.CENTER;

public class Text extends Component.Base {
    protected float textSize;
    protected String text;

    public Text(PApplet app, BaseProperties properties, float textSize, String text) {
        super(app, properties);
        this.textSize = textSize;
        this.text = text;
        this.properties = new BaseProperties.Builder().setBackgroundColor(app.color(255, 255, 255, 96)).setBorderColor(app.color(255)).build();
    }

    public void draw() {
        app.textAlign(CENTER, CENTER);
        app.textSize(textSize);
        app.fill(255);
        app.text(text, properties.x, properties.y);
    }

    public void setText(String text) {
        this.text = text;
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
        private BaseProperties properties;
        private float textSize = BASE_TEXT_SIZE;
        private String text;

        public Builder(PApplet app) {
            this.app = app;
        }

        public Builder setProperties(BaseProperties properties) {
            this.properties = properties;
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
            return new Text(app, properties, textSize, text);
        }
    }
}
