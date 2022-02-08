package breakout.views;

import breakout.Sketch;
import breakout.views.input.BasicButton;
import breakout.views.input.Input;

import static breakout.Sketch.*;

public class StartView extends View {
    public StartView(Sketch app) {
        super(app, new Input[]{
                new BasicButton(app, "Start", BASE_TEXT_SIZE, 200, CANVAS_SIZE_X / 2f, CANVAS_SIZE_Y / 2f, () -> {
                    app.setView(new GameView(app));
                })
        });
    }
}
