package breakout.views;

import breakout.Sketch;
import breakout.views.input.Button;
import breakout.views.input.Input;

import static breakout.Sketch.*;

public class PauseView extends View {
    private final View pausedView;

    public PauseView(Sketch app, View pausedView) {
        super(app, new Input[]{
                new Button(app, "Resume", BASE_TEXT_SIZE, BASE_TEXT_SIZE * 8, CANVAS_SIZE_X / 2f, CANVAS_SIZE_Y / 2f - BASE_TEXT_SIZE, () -> app.setView(pausedView)),
                new Button(app, "Restart", BASE_TEXT_SIZE, BASE_TEXT_SIZE * 8, CANVAS_SIZE_X / 2f, CANVAS_SIZE_Y / 2f + BASE_TEXT_SIZE, () -> app.setView(new ConfirmationView(app, pausedView, "Are you sure you would\nlike to restart?", () -> app.setView(new GameView(app)), () -> app.setView(new PauseView(app, pausedView)))))
        });
        this.pausedView = pausedView;
    }

    public void draw() {
        pausedView.draw();
        super.draw();
    }
}
