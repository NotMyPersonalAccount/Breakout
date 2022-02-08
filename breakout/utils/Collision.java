package breakout.utils;

import static processing.core.PApplet.dist;

public class Collision {
    public static boolean circleRect(float circleX, float circleY, float circleRadius, float rectX, float rectY, float rectWidth, float rectHeight) {
        float pointX = Math.max(rectX - rectWidth / 2, Math.min(circleX, rectX + rectWidth / 2));
        float pointY = Math.max(rectY - rectHeight / 2, Math.min(circleY, rectY + rectHeight / 2));
        return dist(circleX, circleY, pointX, pointY) <= circleRadius;
    }
}
