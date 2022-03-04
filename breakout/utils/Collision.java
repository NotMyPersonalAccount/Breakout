package breakout.utils;

import static processing.core.PApplet.dist;

public enum Collision {
    HORIZONTAL, VERTICAL, CORNER, NONE;

    public static Collision circleRect(float circleX, float circleY, float circleRadius, float rectX, float rectY, float rectWidth, float rectHeight) {
        float pointX = Math.max(rectX - rectWidth / 2, Math.min(circleX, rectX + rectWidth / 2));
        float pointY = Math.max(rectY - rectHeight / 2, Math.min(circleY, rectY + rectHeight / 2));
        if (dist(circleX, circleY, pointX, pointY) <= circleRadius) {
            if (circleX == pointX)
                return VERTICAL;
            if (circleY == pointY)
                return HORIZONTAL;
            return CORNER;
        }
        return NONE;
    }
}
