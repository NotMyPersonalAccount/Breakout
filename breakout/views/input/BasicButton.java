package breakout.views.input;

import processing.core.PApplet;

import static processing.core.PConstants.CENTER;

public class BasicButton implements Input {

    protected PApplet app;
    protected String text;
    protected float textSize;
    protected float width;
    protected float x;
    protected float y;
    protected Runnable callback;

    public BasicButton(PApplet app, String text, float textSize, float width, float x, float y, Runnable callback) {
        this.app = app;
        this.text = text;
        this.textSize = textSize;
        this.width = width;
        this.x = x;
        this.y = y;
        this.callback = callback;
    }

    public void draw() {
        app.textAlign(CENTER, CENTER);
        app.textSize(textSize);

        app.fill(255);
        app.rect(x - width / 2 - 5, y - textSize / 2 - 5, width + 10, textSize + 10, 5);
        app.fill(0);
        app.text(text, x, y);
    }

    public void onClick() {
        callback.run();
    }

    public boolean isHovering() {
        return app.mouseX > x - width / 2 - 5 && app.mouseX < x + width / 2 + 5 && app.mouseY > y - textSize / 2 - 5 && app.mouseY < y + textSize / 2 + 5;
    }
}
