package breakout.views;

import breakout.Sketch;
import breakout.views.components.Button;
import breakout.views.components.Component;
import breakout.views.components.VerticalContainer;

public class PauseView extends View {
    private final View pausedView;

    public PauseView(Sketch app, View pausedView) {
        super(app, new Component[]{new VerticalContainer.Builder(app).setBackgroundColor(app.color(255, 255, 255, 96)).setBorderColor(app.color(255)).withComponents(
                new Button.Builder(app).setText("Resume").setOnClick(() -> app.setView(pausedView)).build(),
                new Button.Builder(app).setText("Restart").setOnClick(() -> app.setView(new ConfirmationView(app, pausedView, "Are you sure you would like to restart?", () -> app.setView(new GameView(app)), () -> app.setView(new PauseView(app, pausedView))))).build()
        ).build()});
        this.pausedView = pausedView;
    }

    public void draw() {
        pausedView.draw();
        super.draw();
    }
}
