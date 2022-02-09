package breakout.views;

import breakout.Sketch;
import breakout.objects.Ball;
import breakout.objects.Brick;
import breakout.utils.Collision;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Map;

import static breakout.Sketch.*;

public class GameView extends View {
    private static final int BALL_RADIUS = CANVAS_SIZE_X / 60;

    public static final float BASE_PADDLE_WIDTH = CANVAS_SIZE_X / 4.8f;
    public static final int PADDLE_SIZE_CYCLE_DURATION = (int) (BASE_PADDLE_WIDTH * 15);
    public static final float PADDLE_SIZE_CYCLE_MIN = 0.65f;
    private static float PADDLE_WIDTH = BASE_PADDLE_WIDTH;

    private static final float PADDLE_HEIGHT = CANVAS_SIZE_X / 48f;
    private static final float PADDLE_Y = CANVAS_SIZE_Y - PADDLE_HEIGHT;

    private int level = 0;
    private ArrayList<Brick[]> levels;

    private int lives = 3;
    private int score = 0;

    private Ball ball;

    private float paddleX;

    private final PImage background;

    public GameView(Sketch app) {
        super(app, null);

        initGame();
        background = app.loadImage("pig.jpg");
        background.resize(CANVAS_SIZE_X, CANVAS_SIZE_Y);
    }

    public void draw() {
        tickGame();

        app.image(background, 0, 0);

        Brick[] bricks = levels.get(level);
        for (Brick brick : bricks) {
            if (brick != null) brick.draw(app);
        }
        ball.draw(app);

        app.fill(0);
        app.rect(paddleX - (PADDLE_WIDTH / 2), PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        app.fill(255);
        app.textSize(BASE_TEXT_SIZE);
        app.textAlign(RIGHT, CENTER);
        app.text("Lives: " + lives, CANVAS_SIZE_X - BASE_TEXT_SIZE * 2, CANVAS_SIZE_Y - BASE_TEXT_SIZE * 4);
        app.text("Level: " + (level + 1), CANVAS_SIZE_X - BASE_TEXT_SIZE * 2, CANVAS_SIZE_Y - BASE_TEXT_SIZE * 3);
        app.text("Score: " + score, CANVAS_SIZE_X - BASE_TEXT_SIZE * 2, CANVAS_SIZE_Y - BASE_TEXT_SIZE * 2);
    }

    public void tickGame() {
        if (app.view != this) return;

        paddleX = app.mouseX;

        int paddleSizeTime = app.millis() % PADDLE_SIZE_CYCLE_DURATION;
        if (paddleSizeTime < PADDLE_SIZE_CYCLE_DURATION / 2) {
            PADDLE_WIDTH = BASE_PADDLE_WIDTH * (PADDLE_SIZE_CYCLE_MIN + paddleSizeTime / (float) PADDLE_SIZE_CYCLE_DURATION * (1 - PADDLE_SIZE_CYCLE_MIN));
        } else if (paddleSizeTime > PADDLE_SIZE_CYCLE_DURATION / 2) {
            PADDLE_WIDTH = BASE_PADDLE_WIDTH * (PADDLE_SIZE_CYCLE_MIN + (PADDLE_SIZE_CYCLE_DURATION - paddleSizeTime) / (float) PADDLE_SIZE_CYCLE_DURATION * (1 - PADDLE_SIZE_CYCLE_MIN));
        }

        if (ball.xSpeed == 0 && ball.ySpeed == 0) {
            ball.x = paddleX;
        } else {
            float newX = ball.x + ball.xSpeed;
            float newY = ball.y + ball.ySpeed;

            if (newX < BALL_RADIUS || newX > CANVAS_SIZE_X - BALL_RADIUS)
                ball.xSpeed *= -1;
            if (newY < BALL_RADIUS) ball.ySpeed *= -1;

            if (newY > PADDLE_Y - BALL_RADIUS) {
                if (Collision.circleRect(ball.x, ball.y, BALL_RADIUS, paddleX, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT)) {
                    ball.ySpeed *= -1;
                } else {
                    lives--;
                    if (lives == 0) {
                        app.setView(new EndView(app, EndView.Conclusion.LOSE, level, score));
                        return;
                    }
                    resetBall();
                }
            }

            ball.x = newX;
            ball.y = newY;
        }

        Brick[] bricks = levels.get(level);
        int remainingBricks = 0;
        for (int i = 0; i < bricks.length; i++) {
            Brick brick = bricks[i];
            if (brick != null) {
                remainingBricks++;

                if (Collision.circleRect(ball.x, ball.y, ball.radius, brick.x, brick.y, brick.width, brick.height)) {
                    ball.ySpeed *= -1;
                    bricks[i].health--;
                    if (bricks[i].health <= 0) {
                        bricks[i] = null;
                    }
                    score++;
                }
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
    }

    public void mousePressed() {
        if (ball.xSpeed == 0 && ball.ySpeed == 0) {
            ball.xSpeed = 5;
            ball.ySpeed = -3;
        }
    }

    public void keyPressed() {
        if (app.key == 'p') app.setView(new PauseView(app, this));
    }

    // initGame (re)starts the game. Game levels and the ball are set up here.
    private void initGame() {
        app.colorMode(RGB);
        Map<String, Integer> commonColors = Map.of("r", app.color(255, 0, 0), "o", app.color(255, 165, 0), "y", app.color(255, 255, 0), "g", app.color(0, 255, 0), "b", app.color(0, 0, 255), "p", app.color(128, 0, 128));

        int margin = CANVAS_SIZE_X / 160;

        levels = new ArrayList<>();
        levels.add(levelFromStrings(commonColors, CANVAS_SIZE_X / 10 - margin * 2, CANVAS_SIZE_Y / 20 - margin * 2, margin, margin, CANVAS_SIZE_X, CANVAS_SIZE_Y, "r".repeat(10), "o".repeat(10), "y".repeat(10), "g".repeat(10), "b".repeat(10), "p".repeat(10)));
        levels.add(levelFromStrings(commonColors, CANVAS_SIZE_X / 15 - margin * 2, CANVAS_SIZE_Y / 20 - margin * 2, margin, margin, CANVAS_SIZE_X, CANVAS_SIZE_Y, "r" + " r".repeat(7), "y" + "oy".repeat(7), "g" + "bg".repeat(7), "p" + " p".repeat(7), "roygbp   pbgyor"));
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
