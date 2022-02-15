package breakout.views.components;

import processing.core.PApplet;

// BaseComponent partially implements the Component interface with logic shared across all components, namely position,
// margin, & color.
abstract class BaseComponent implements Component {
    protected final PApplet app;

    protected float x;
    protected float y;

    protected float marginX;
    protected float marginY;

    protected int backgroundColor;
    protected int borderColor;

    public BaseComponent(PApplet app, float x, float y, float marginX, float marginY, int backgroundColor, int borderColor) {
        this.app = app;
        this.x = x;
        this.y = y;
        this.marginX = marginX;
        this.marginY = marginY;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
    }

    public float getMarginX(){
        return marginX;
    }

    public float getMarginY(){
        return marginY;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean isMouseOver() {
        return app.mouseX > x - this.getWidth() / 2 && app.mouseX < x + this.getWidth() / 2 && app.mouseY > y - this.getHeight() / 2 && app.mouseY < y + this.getHeight() / 2;
    }
}
