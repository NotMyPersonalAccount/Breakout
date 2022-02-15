package breakout.views.components;

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
}
