package breakout.views;

import breakout.Sketch;
import breakout.views.components.*;

import static breakout.Sketch.*;

public class StartView extends View {
    public StartView(Sketch app) {
        super(app, new Component[]{
                new VerticalContainer.Builder(app).setProperties(new Component.BaseProperties.Builder().setBackgroundColor(-1).build()).withComponents(
                        new Text.Builder(app).setText("Breakout").setTextSize(BASE_TEXT_SIZE * 4).build(),
                        new Button.Builder(app).setText("Start").setOnClick(() -> app.setView(new GameView(app))).build()
                ).build()
        });
    }
}
