package breakout.views;

import breakout.Sketch;
import breakout.views.input.BasicButton;
import breakout.views.input.Input;

import static breakout.Sketch.*;

public class PauseView extends View {
    public PauseView(Sketch app, View pausedView) {
        super(app, new Input[]{new BasicButton(app, "Resume", BASE_TEXT_SIZE, BASE_TEXT_SIZE * 8, CANVAS_SIZE_X / 2f, CANVAS_SIZE_Y / 2f, () -> {
            app.setView(pausedView);
            app.loop();
        })});

        this.draw();
        app.noLoop();
    }
}
