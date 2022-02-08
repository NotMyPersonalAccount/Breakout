package breakout.views;

import breakout.Sketch;
import breakout.objects.Ball;
import breakout.objects.Brick;

import java.util.ArrayList;
import java.util.Map;

import static breakout.Sketch.CANVAS_SIZE_X;
import static breakout.Sketch.CANVAS_SIZE_Y;

public class GameView extends View {
    private static final int BALL_RADIUS = 8;

    private static final float PADDLE_WIDTH = 100;
    private static final float PADDLE_HEIGHT = 10;
    private static final float PADDLE_Y = CANVAS_SIZE_Y - PADDLE_HEIGHT;

    private int level = 0;
    private ArrayList<Brick[]> levels;

    private int lives = 3;
    private int score = 0;

    private Ball ball;

    public GameView(Sketch app) {
        super(app, null);

        initGame();
    }

    public void draw() {
        Brick[] bricks = levels.get(level);
        int remainingBricks = 0;
        for (int i = 0; i < bricks.length; i++) {
            Brick brick = bricks[i];
            if (brick != null) {
                remainingBricks++;

                if (brick.isColliding(ball)) {
                    ball.ySpeed *= -1;
                    bricks[i] = null; //TODO: health
                    score++;
                }
                brick.draw(app);
            }
        }

        if (remainingBricks == 0) {
            level++;
            if (level == levels.size()) {
                app.setView(new EndView(app, EndView.Conclusion.WIN, level, score));
                return;
            }
            resetBall();
        }

        if (ball.xSpeed == 0 && ball.ySpeed == 0) {
            ball.x = app.mouseX;
        } else {
            ball.x += ball.xSpeed;
            ball.y += ball.ySpeed;

            if (ball.x < 0 || ball.x > CANVAS_SIZE_X) ball.xSpeed *= -1;
            if (ball.y < 0) ball.ySpeed *= -1;

            if (ball.y >= PADDLE_Y && ball.x >= app.mouseX - PADDLE_WIDTH / 2 && ball.x <= app.mouseX + PADDLE_WIDTH / 2)
                ball.ySpeed *= -1;
            else if (ball.y > CANVAS_SIZE_Y) {
                lives--;
                if (lives == 0) {
                    app.setView(new EndView(app, EndView.Conclusion.LOSE, level, score));
                    return;
                }
                resetBall();
            }
        }
        ball.draw(app);

        app.fill(0);
        app.rect(app.mouseX - (PADDLE_WIDTH / 2), PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    public void mousePressed() {
        if (ball.xSpeed == 0 && ball.ySpeed == 0) {
            ball.xSpeed = 5;
            ball.ySpeed = -3;
        }
    }

    // initGame (re)starts the game. Game levels and the ball are set up here.
    private void initGame() {
        Map<String, Integer> commonColors = Map.of(
                "r", app.color(255, 0, 0),
                "o", app.color(255, 165, 0),
                "y", app.color(255, 255, 0),
                "g", app.color(0, 255, 0),
                "b", app.color(0, 0, 255),
                "p", app.color(128, 0, 128)
        );

        levels = new ArrayList<>();
        levels.add(levelFromStrings(commonColors, 42, 18, 3, 3, CANVAS_SIZE_X, CANVAS_SIZE_Y, "r         "));
/*        levels.add(levelFromStrings(commonColors, 42, 18, 3, 3, MAP_SIZE_X, MAP_SIZE_Y,
                "r".repeat(10),
                "o".repeat(10),
                "y".repeat(10),
                "g".repeat(10),
                "b".repeat(10),
                "p".repeat(10)
        ));*/
        levels.add(levelFromStrings(commonColors, 480 / 15 - 3 * 2, 18, 3, 3, CANVAS_SIZE_X, CANVAS_SIZE_Y,
                "r" + " r".repeat(7),
                "y" + "oy".repeat(7),
                "g" + "bg".repeat(7),
                "p" + " p".repeat(7),
                "roygbp   pbgyor"
        ));
        level = 0;

        ball = new Ball(BALL_RADIUS, CANVAS_SIZE_X / 2f, PADDLE_Y - BALL_RADIUS, 0, 0);
    }

    private void resetBall() {
        ball.xSpeed = ball.ySpeed = 0;
        ball.y = PADDLE_Y - BALL_RADIUS;
    }

    // levelFromStrings produces a level by interpreting passed strings as rows. Characters represent different colored
    // bricks based on the passed Map<String, Integer>. For characters not in the map, they are interpreted as no brick
    // at that position.
    private Brick[] levelFromStrings(Map<String, Integer> brickTypes, int brickSizeX, int brickSizeY, int marginX, int marginY, int mapSizeX, int mapSizeY, String... rows) throws RuntimeException {
        int trueBrickSizeX = brickSizeX + marginX * 2;
        int trueBrickSizeY = brickSizeY + marginY * 2;
        if (rows.length * trueBrickSizeX > mapSizeY) {
            throw new RuntimeException("Too many row, max " + (mapSizeY / trueBrickSizeX) + ", got " + rows.length);
        }
        Brick[] level = new Brick[mapSizeX / trueBrickSizeX * mapSizeY / trueBrickSizeY];
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            if (row.length() * trueBrickSizeX != mapSizeX) {
                throw new RuntimeException("Incorrect number of columns in row " + i + ", expected " + (mapSizeX / trueBrickSizeX) + " but got " + row.length());
            }
            String[] characters = row.split("");
            for (int j = 0; j < characters.length; j++) {
                try {
                    int brickType = brickTypes.get(characters[j]);
                    int x = j * trueBrickSizeX + (brickSizeX / 2) + marginX;
                    int y = i * trueBrickSizeY + (brickSizeY / 2) + marginY;
                    level[i * row.length() + j] = new Brick(x, y, brickSizeX, brickSizeY, brickType);
                } catch (NullPointerException ignored) {
                    // No brick at this position
                }
            }
        }
        return level;
    }
}
