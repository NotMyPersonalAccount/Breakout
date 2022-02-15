package breakout.views;

import breakout.Sketch;
import breakout.views.components.*;

import static breakout.Sketch.*;

public class EndView extends View {
    private final Conclusion conclusion;
    private final int level;
    private final int score;

    public EndView(Sketch app, Conclusion conclusion, int level, int score) {
        super(app, new Component[]{new VerticalContainer.Builder(app).withComponents(
                new Text.Builder(app).setText(conclusion.text).build(),
                new Text.Builder(app).setText("Level: " + level).build(),
                new Text.Builder(app).setText("Score: " + score).build(),
                new Button.Builder(app).setText("Play Again").setOnClick(() -> app.setView(new GameView(app))).build()
        ).build()});
        this.conclusion = conclusion;
        this.level = level;
        this.score = score;
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
