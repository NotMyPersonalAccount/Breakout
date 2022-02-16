package breakout.views;

import breakout.Sketch;
import breakout.views.components.Button;
import breakout.views.components.Component;
import breakout.views.components.Text;
import breakout.views.components.VerticalContainer;

public class ConfirmationView extends View {
    private final View previousView;

    public ConfirmationView(Sketch app, View previousView, String message, Runnable onConfirm, Runnable onCancel) {
        super(app, new Component[]{new VerticalContainer.Builder(app).withComponents(
                new Text.Builder(app).setText(message).build(),
                new Button.Builder(app).setText("Confirm").setOnClick(onConfirm).build(),
                new Button.Builder(app).setText("Cancel").setOnClick(onCancel).build()
        ).build()});
        this.previousView = previousView;
    }

    public void draw() {
        previousView.draw();

        super.draw();
    }
}
