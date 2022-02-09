package breakout.views;

import breakout.Sketch;
import breakout.views.input.BasicButton;
import breakout.views.input.Input;

import static breakout.Sketch.*;

public class ConfirmationView extends View {
    private final View previousView;
    private final String message;

    public ConfirmationView(Sketch app,  View previousView, String message, Runnable onConfirm, Runnable onCancel) {
        super(app, new Input[]{
                new BasicButton(app, "Confirm", BASE_TEXT_SIZE, BASE_TEXT_SIZE * 4, CANVAS_SIZE_X / 2f + BASE_TEXT_SIZE * 4, CANVAS_SIZE_X / 2f + BASE_TEXT_SIZE * 2, onConfirm),
                new BasicButton(app, "Cancel", BASE_TEXT_SIZE, BASE_TEXT_SIZE * 4, CANVAS_SIZE_X / 2f - BASE_TEXT_SIZE, CANVAS_SIZE_X / 2f + BASE_TEXT_SIZE * 2, onCancel)
        });
        this.previousView = previousView;
        this.message = message;
    }

    public void draw() {
        previousView.draw();
        app.fill(255);
        app.rect(CANVAS_SIZE_X / 6f, CANVAS_SIZE_Y / 10f * 3, CANVAS_SIZE_X / 1.5f, CANVAS_SIZE_X / 2.5f, 5);
        app.fill(0);
        app.textAlign(LEFT);
        app.text(message, CANVAS_SIZE_X / 6f + BASE_TEXT_SIZE, CANVAS_SIZE_Y / 10f * 3 + BASE_TEXT_SIZE * 2);
        super.draw();
    }
}
