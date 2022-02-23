package breakout.views.components;

import processing.core.PApplet;

import static breakout.Sketch.BASE_TEXT_SIZE;
import static breakout.Sketch.CANVAS_SIZE_X;

public class Button extends Component.Base {
    protected float width;
    protected float height;
    protected Text textComponent;
    protected Runnable onClick;

    public Button(PApplet app, BaseProperties properties, float width, float height, float textSize, String text, Runnable onClick) {
        super(app, properties);
        this.width = width;
        this.height = height;
        this.textComponent = new Text.Builder(app).setText(text).setTextSize(textSize).build();
        this.onClick = onClick;
    }

    public void draw() {
        app.fill(255, 255, 255, 96);
        app.stroke(225);
        app.rect(properties.x - width / 2, properties.y - height / 2, width, height, 5);

        textComponent.setPosition(properties.x, properties.y);
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
        private BaseProperties properties;
        private float width = CANVAS_SIZE_X / 2.4f;
        private float height = BASE_TEXT_SIZE;
        private float textSize = BASE_TEXT_SIZE;
        private String text;
        private Runnable onClick;

        public Builder(PApplet app) {
            this.app = app;
            this.properties = new BaseProperties.Builder().build();
        }

        public Builder setProperties(BaseProperties properties) {
            this.properties = properties;
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
            return new Button(app, properties, width, height, textSize, text, onClick);
        }
    }
}
