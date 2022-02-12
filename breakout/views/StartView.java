package breakout.views;

import breakout.Sketch;
import breakout.views.input.Button;
import breakout.views.input.Input;

import static breakout.Sketch.*;

public class StartView extends View {
    public StartView(Sketch app) {
        super(app, new Input[]{
                new Button(app, "Start", BASE_TEXT_SIZE, 200, CANVAS_SIZE_X / 2f, CANVAS_SIZE_Y / 2f + BASE_TEXT_SIZE * 2, () -> app.setView(new GameView(app)))
        });
    }

    public void draw() {
        super.draw();
        String title = "Breakout";

        app.textSize(BASE_TEXT_SIZE * 4);
        app.textAlign(CENTER, CENTER);

        app.fill(255, 255, 255, 96);
        app.noStroke();
        app.rect(CANVAS_SIZE_X / 2f - app.textWidth(title) * 1.0625f / 2, CANVAS_SIZE_Y / 2f - BASE_TEXT_SIZE * 3, app.textWidth(title) * 1.0625f, BASE_TEXT_SIZE * 4, 5);
        app.stroke(0);

        app.fill(255);
        app.text(title, CANVAS_SIZE_X / 2f, CANVAS_SIZE_Y / 2f - BASE_TEXT_SIZE * 2);
    }
}
