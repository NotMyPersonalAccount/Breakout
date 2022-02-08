package breakout.views.input;

import processing.core.PApplet;

public class BasicButton implements Input {

    protected PApplet app;
    protected String text;
    protected float textSize;
    protected float width;
    protected int x;
    protected int y;
    protected Runnable callback;

    public BasicButton(PApplet app, String text, float textSize, float width, int x, int y, Runnable callback) {
        this.app = app;
        this.text = text;
        this.textSize = textSize;
        this.width = width;
        this.x = x;
        this.y = y;
        this.callback = callback;
    }

    public void draw() {
        app.textSize(textSize);

        app.fill(255);
        app.rect(x - width / 2 - 5, y - textSize / 2 - 5, width + 10, textSize + 10, 5);
        app.fill(0);
        app.text(text, x - app.textWidth(text) / 2, y + textSize / 2);
    }

    public void onClick() {
        callback.run();
    }

    public boolean isHovering() {
        float textWidth = app.textWidth(text);
        return app.mouseX > x - width / 2 - 5 && app.mouseX < x + width / 2 + 5 && app.mouseY > y - textSize / 2 - 5 && app.mouseY < y + textSize / 2 + 5;
    }
}
