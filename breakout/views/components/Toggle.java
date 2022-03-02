package breakout.views.components;

import breakout.Sketch;


public class Toggle extends Container {
    private boolean toggled;

    private final float textSize;
    private final String text;
    private final ToggleHandler onToggle;

    public Toggle(Sketch app, BaseProperties properties, boolean toggled, float textSize, String text, ToggleHandler onToggle) {
        super(app, properties, -1, -1, textSize / 2f, textSize / 2f, Alignment.X.CENTER, Alignment.Y.CENTER, Direction.HORIZONTAL);

        this.toggled = toggled;
        this.textSize = textSize;
        this.text = text;
        this.onToggle = onToggle;
        rebuildComponents();
    }

    private void rebuildComponents() {
        this.components = new Component[]{
                new Text.Builder(app).setText(text).setTextSize(textSize).build(),
                new Button.Builder(app).setWidth(textSize).setHeight(textSize).setText(toggled ? "X" : "").setTextSize(textSize).setOnClick(() -> {
                    toggled = !toggled;
                    onToggle.call(toggled);
                    rebuildComponents();
                }).build()
        };
    }

    public static class Builder {
        private final Sketch app;
        private BaseProperties properties;
        private boolean toggled = false;
        private float textSize;
        private String text;
        private ToggleHandler onToggle;

        public Builder(Sketch app) {
            this.app = app;
            this.properties = new BaseProperties.Builder().setBackgroundColor(app.color(255, 255, 255, 64)).setBorderColor(app.color(255)).build();
        }

        public Builder setProperties(BaseProperties properties) {
            this.properties = properties;
            return this;
        }

        public Builder setToggled(boolean toggled) {
            this.toggled = toggled;
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

        public Builder setOnToggle(ToggleHandler onToggle) {
            this.onToggle = onToggle;
            return this;
        }

        public Toggle build() {
            return new Toggle(app, properties, toggled, textSize, text, onToggle);
        }
    }

    public interface ToggleHandler {
        void call(boolean toggled);
    }
}
