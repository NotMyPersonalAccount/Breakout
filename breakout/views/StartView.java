package breakout.views;

import breakout.Sketch;
import breakout.views.input.BasicButton;
import breakout.views.input.Input;

import static breakout.Sketch.*;

public class StartView extends View {
    public StartView(Sketch app) {
        super(app, new Input[]{
                new BasicButton(app, "Start", BUTTON_TEXT_SIZE, 240, CANVAS_SIZE_X / 2, CANVAS_SIZE_Y / 2, () -> {
                    app.setView(new GameView(app));
                })
        });
    }
}
