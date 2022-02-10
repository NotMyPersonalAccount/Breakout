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

    private static final float BASE_PADDLE_WIDTH = CANVAS_SIZE_X / 4.8f;
    private static final int PADDLE_SIZE_CYCLE_DURATION = (int) (BASE_PADDLE_WIDTH * 15);
    private static final float PADDLE_SIZE_CYCLE_MIN = 0.65f;
    private static float PADDLE_WIDTH = BASE_PADDLE_WIDTH;

    private static final float PADDLE_HEIGHT = CANVAS_SIZE_X / 48f;
    private static final float PADDLE_Y = CANVAS_SIZE_Y - PADDLE_HEIGHT;

    private int level = 0;
    private ArrayList<Brick[]> levels;

    private int lives = 9;
    private int score = 0;

    private Ball ball;

    private float paddleX;

    private final PImage background;

    private GameView simulation;

    public GameView(Sketch app) {
        this(app, false);
    }

    public GameView(Sketch app, boolean simulation) {
        super(app, null);

        initGame();
        background = app.loadImage("pig.jpg");
        background.resize(CANVAS_SIZE_X, CANVAS_SIZE_Y);
        if (!simulation) {
            this.simulation = new GameView(app, true);
        }
    }

    public void draw() {
        if (!tickGame(false)) return;

        app.image(background, 0, 0);

        Brick[] bricks = levels.get(level);
        for (Brick brick : bricks) {
            if (brick != null) brick.draw(app);
        }
        ball.draw(app);

        app.fill(0);
        app.rect(paddleX - (PADDLE_WIDTH / 2), PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        updateSimulationInstance();
        for (int i = 0; i < 500; i++) {
            float oldBallX = simulation.ball.x;
            float oldBallY = simulation.ball.y;
            simulation.tickGame(true);
            app.line(oldBallX, oldBallY, simulation.ball.x, simulation.ball.y);
        }

        app.fill(255);
        app.textSize(BASE_TEXT_SIZE);
        app.textAlign(RIGHT, CENTER);
        app.text("Lives: " + lives, CANVAS_SIZE_X - BASE_TEXT_SIZE * 2, CANVAS_SIZE_Y - BASE_TEXT_SIZE * 4);
        app.text("Level: " + (level + 1), CANVAS_SIZE_X - BASE_TEXT_SIZE * 2, CANVAS_SIZE_Y - BASE_TEXT_SIZE * 3);
        app.text("Score: " + score, CANVAS_SIZE_X - BASE_TEXT_SIZE * 2, CANVAS_SIZE_Y - BASE_TEXT_SIZE * 2);
    }

    // tickGame ticks the game physics. It returns true if the game should be redrawn.
    private boolean tickGame(boolean simulate) {
        // Don't tick if GameView is not the active view.
        if (app.view != this && !simulate) return true;

        paddleX = app.mouseX;

        // Change paddle width over time from 100% to PADDLE_SIZE_CYCLE_MIN and back over PADDLE_SIZE_CYCLE_DURATION milliseconds.
        int paddleSizeTime = app.millis() % PADDLE_SIZE_CYCLE_DURATION;
        if (paddleSizeTime < PADDLE_SIZE_CYCLE_DURATION / 2) {
            PADDLE_WIDTH = BASE_PADDLE_WIDTH * (PADDLE_SIZE_CYCLE_MIN + paddleSizeTime / (float) PADDLE_SIZE_CYCLE_DURATION * (1 - PADDLE_SIZE_CYCLE_MIN));
        } else if (paddleSizeTime > PADDLE_SIZE_CYCLE_DURATION / 2) {
            PADDLE_WIDTH = BASE_PADDLE_WIDTH * (PADDLE_SIZE_CYCLE_MIN + (PADDLE_SIZE_CYCLE_DURATION - paddleSizeTime) / (float) PADDLE_SIZE_CYCLE_DURATION * (1 - PADDLE_SIZE_CYCLE_MIN));
        }

        // Set the ball's position to the paddle's position if not moving.
        if (ball.xSpeed == 0 && ball.ySpeed == 0) {
            ball.x = paddleX;
        } else {
            // Calculate the ball's new position based off of speed.
            float newX = ball.x + ball.xSpeed;
            float newY = ball.y + ball.ySpeed;

            // Check if hitting the left or right walls.
            if (newX < ball.radius || newX > CANVAS_SIZE_X - ball.radius)
                ball.xSpeed *= -1;
            // Check if hitting the top wall.
            if (newY < ball.radius) ball.ySpeed *= -1;

            // Check if ball is below the height of the paddle.
            if (newY > PADDLE_Y - ball.radius) {
                // Check if the ball is colliding with paddle.
                if (Collision.circleRect(ball.x, ball.y, ball.radius, paddleX, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT)) {
                    ball.ySpeed *= -1;
                } else {
                    lives--;
                    if (lives == 0) {
                        app.setView(new EndView(app, EndView.Conclusion.LOSE, level, score));
                        return false;
                    }
                    resetBall();
                }
            }

            // Set the ball's new position.
            ball.x = newX;
            ball.y = newY;
        }

        // Get bricks of the current level.
        Brick[] bricks = levels.get(level);
        int remainingBricks = 0;
        // Iterate over all bricks.
        for (int i = 0; i < bricks.length; i++) {
            Brick brick = bricks[i];
            // Check if the brick is null.
            if (brick != null) {
                remainingBricks++;

                // Check if the ball is colliding with the brick.
                if (Collision.circleRect(ball.x, ball.y, ball.radius, brick.x, brick.y, brick.width, brick.height)) {
                    ball.ySpeed *= -1;
                    // Decrease the brick's health.
                    bricks[i].health--;
                    // Check if the brick is destroyed.
                    if (bricks[i].health <= 0) {
                        bricks[i] = null;
                    }
                    score++;
                }
            }
        }

        // If no bricks are remaining, go to the next level.
        if (remainingBricks == 0) {
            level++;
            // If the current level is the last level, go to the end view.
            if (level == levels.size()) {
                app.setView(new EndView(app, EndView.Conclusion.WIN, level, score));
                return false;
            }
            resetBall();
        }
        return true;
    }

    public void mousePressed() {
        // Check that the ball is not already moving.
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
        levels.add(levelFromStrings(commonColors, CANVAS_SIZE_X / 10 - margin * 2, CANVAS_SIZE_Y / 20 - margin * 2, margin, margin, CANVAS_SIZE_X, CANVAS_SIZE_Y,
                "r".repeat(10),
                "o" + " ".repeat(8) + "o",
                "y" + " ".repeat(8) + "y",
                "g" + " ".repeat(8) + "g",
                "b" + " ".repeat(8) + "b",
                "p".repeat(10)
        ));
        levels.add(levelFromStrings(commonColors, CANVAS_SIZE_X / 10 - margin * 2, CANVAS_SIZE_Y / 20 - margin * 2, margin, margin, CANVAS_SIZE_X, CANVAS_SIZE_Y,
                "r".repeat(10),
                "o".repeat(10),
                "y".repeat(10),
                "g".repeat(10),
                "b".repeat(10),
                "p".repeat(10)
        ));
        levels.add(levelFromStrings(commonColors, CANVAS_SIZE_X / 15 - margin * 2, CANVAS_SIZE_Y / 20 - margin * 2, margin, margin, CANVAS_SIZE_X, CANVAS_SIZE_Y,
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
        ball.y = PADDLE_Y - ball.radius;
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
                    level[i * row.length() + j] = new Brick(x, y, brickSizeX, brickSizeY, 3, 3, brickType);
                } catch (NullPointerException ignored) {
                    // No brick at this position
                }
            }
        }
        return level;
    }

    // updateSimulationInstance updates a clone of the game instance with all vital information for simulating the
    // future ball path. This is not particularly ideal, but the tickGame method includes unrelated core logic that
    // can break the simulation.
    private void updateSimulationInstance() {
        simulation.lives = MAX_INT;
        simulation.ball.x = ball.x;
        simulation.ball.y = ball.y;
        simulation.ball.xSpeed = ball.xSpeed;
        simulation.ball.ySpeed = ball.ySpeed;
        simulation.paddleX = paddleX;
        Brick[] bricks = levels.get(level);
        Brick[] simulatedBricks = simulation.levels.size() == 0 ? new Brick[bricks.length] : simulation.levels.get(0);
        for (int i = 0; i < bricks.length; i++) {
            Brick brick = bricks[i];
            Brick simulatedBrick = simulatedBricks[i];
            if (brick != null) {
                if (simulatedBrick == null) {
                    simulatedBricks[i] = new Brick(brick.x, brick.y, brick.width, brick.height, brick.health, brick.maxHealth, brick.color);
                } else {
                    simulatedBrick.health = brick.health;
                }
            } else {
                if (simulatedBrick != null) {
                    simulatedBricks[i] = null;
                }
            }
        }
        if (simulation.levels.size() == 0) {
            simulation.levels = new ArrayList<>();
            simulation.levels.add(simulatedBricks);
        }
    }
}
