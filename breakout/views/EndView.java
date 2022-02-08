package breakout.views;

import breakout.Sketch;
import breakout.views.input.BasicButton;
import breakout.views.input.Input;

import static breakout.Sketch.*;

public class EndView extends View {
    private final Conclusion conclusion;
    private final int level;
    private final int score;

    public EndView(Sketch app, Conclusion conclusion, int level, int score) {
        super(app, new Input[]{new BasicButton(app, "Play Again", BASE_TEXT_SIZE, BASE_TEXT_SIZE * 8, CANVAS_SIZE_X / 2f, BASE_TEXT_SIZE * 11, () -> {
            app.setView(new GameView(app));
        })});
        this.conclusion = conclusion;
        this.level = level;
        this.score = score;
    }

    public void draw() {
        super.draw();

        app.textAlign(CENTER, CENTER);

        app.textSize(BASE_TEXT_SIZE * 2);
        app.text(conclusion.text, CANVAS_SIZE_X / 2f, BASE_TEXT_SIZE * 6);

        app.textSize(BASE_TEXT_SIZE);
        app.text("Level: " + level, CANVAS_SIZE_X / 2f, BASE_TEXT_SIZE * 8);
        app.text("Score: " + score, CANVAS_SIZE_X / 2f, BASE_TEXT_SIZE * 9);

    }

    public enum Conclusion {
        WIN("You Win!"),
        LOSE("You Lose!");

        public final String text;

        Conclusion(String text) {
            this.text = text;
        }
    }
}
