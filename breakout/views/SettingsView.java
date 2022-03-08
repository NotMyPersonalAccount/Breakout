package breakout.views;

import breakout.Sketch;
import breakout.utils.Settings;
import breakout.views.components.Button;
import breakout.views.components.Component;
import breakout.views.components.Container;
import breakout.views.components.Toggle;

import static breakout.Sketch.*;

public class SettingsView extends PauseView {
    private static final float PAGE_BUTTONS_SIZE = CANVAS_SIZE_X / 4.8f;

    private Container currentPage;
    private final Container generalPage;
    private final Container cheatsPage;

    private boolean showTrajectory;
    private boolean cursorControl;
    private boolean autoPlay;

    public SettingsView(Sketch app, GameView pausedView) {
        super(app, pausedView);

        this.showTrajectory = Settings.SHOW_TRAJECTORY;
        this.cursorControl = Settings.CURSOR_CONTROL;

        this.currentPage = this.generalPage = new Container.Builder(app).setDirection(Container.Direction.VERTICAL).setAlignmentX(Container.Alignment.X.LEFT).withComponents(
                new Button.Builder(app).setText("Reset Ball").setTextSize(BASE_TEXT_SIZE).setOnClick(pausedView::resetBall).build()
        ).build();
        this.cheatsPage = new Container.Builder(app).setDirection(Container.Direction.VERTICAL).setAlignmentX(Container.Alignment.X.LEFT).withComponents(
                new Toggle.Builder(app).setToggled(showTrajectory).setText("Show Trajectory").setTextSize(BASE_TEXT_SIZE).setOnToggle((toggled) -> showTrajectory = toggled).build(),
                new Toggle.Builder(app).setToggled(cursorControl).setText("Cursor Control").setTextSize(BASE_TEXT_SIZE).setOnToggle((toggled) -> cursorControl = toggled).build(),
                new Toggle.Builder(app).setToggled(autoPlay).setText("Auto Play").setTextSize(BASE_TEXT_SIZE).setOnToggle((toggled) -> autoPlay = toggled).build()
        ).build();

        rebuildComponents();
    }

    private void rebuildComponents() {
        this.components = new Component[]{new Container.Builder(app).setDirection(Container.Direction.VERTICAL).setFixedWidth(CANVAS_SIZE_X).setFixedHeight(CANVAS_SIZE_Y).setAlignmentX(Container.Alignment.X.LEFT).setAlignmentY(Container.Alignment.Y.TOP).setProperties(new Component.BaseProperties.Builder().setBackgroundColor(-1).build()).withComponents(
                new Container.Builder(app).setDirection(Container.Direction.HORIZONTAL).withComponents(
                        new Button.Builder(app).setWidth(PAGE_BUTTONS_SIZE).setText("General").setOnClick(() -> {
                            currentPage = generalPage;
                            rebuildComponents();
                        }).build(),
                        new Button.Builder(app).setWidth(PAGE_BUTTONS_SIZE).setText("Cheats").setOnClick(() -> {
                            currentPage = cheatsPage;
                            rebuildComponents();
                        }).build(),
                        new Button.Builder(app).setWidth(PAGE_BUTTONS_SIZE).setText("Exit").setOnClick(() -> app.setView(new PauseView(app, pausedView))).build(),
                        new Button.Builder(app).setWidth(PAGE_BUTTONS_SIZE).setText("Save").setOnClick(() -> {
                            Settings.SHOW_TRAJECTORY = showTrajectory;
                            Settings.CURSOR_CONTROL = cursorControl;
                            Settings.AUTO_PLAY = autoPlay;
                        }).build()
                ).build(),
                this.currentPage
        ).build()};
    }
}
