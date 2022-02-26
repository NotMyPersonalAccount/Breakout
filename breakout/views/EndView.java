package breakout.views;

import breakout.Sketch;
import breakout.views.components.*;

import static breakout.Sketch.*;

public class EndView extends View {
    public EndView(Sketch app, Conclusion conclusion, int level, int score) {
        super(app, new Component[]{new Container.Builder(app).setDirection(Container.Direction.VERTICAL).withComponents(
                new Text.Builder(app).setText(conclusion.text).setTextSize(BASE_TEXT_SIZE * 3).build(),
                new Text.Builder(app).setText("Level: " + (level + 1)).build(),
                new Text.Builder(app).setText("Score: " + score).build(),
                new Button.Builder(app).setText("Play Again").setOnClick(() -> app.setView(new GameView(app))).build()
        ).build()});
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
