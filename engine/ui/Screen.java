package engine.ui;

import engine.GameWorld;
import engine.support.Vec2d;
import engine.ui.button.Button;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;

public class Screen {
    private String tag;
    private Vec2d screenSize;
    private List<Button> buttonList;

    protected GameWorld gameWorld;
    protected List<UIElement> uiElementList;

    public Screen(String tag, Vec2d screenSize) {
        this.tag = tag;
        this.screenSize = screenSize;

        uiElementList = new ArrayList<>();
        buttonList = new ArrayList<>();
    }

    public void onDraw(GraphicsContext g) {
        for (UIElement each : uiElementList) {
            each.onDraw(g);
        }
    }

    public void onMouseClicked(MouseEvent e) {
        double x = e.getX(), y = e.getY();

        for (Button each : buttonList) {
            if (x >= each.pos.x &&
                x <= each.pos.x + each.size.x &&
                y >= each.pos.y &&
                y <= each.pos.y + each.size.y) {
                each.onMouseClicked(e);
            }
        }
    }

    public void onMouseMoved(MouseEvent e) {
        double x = e.getX(), y = e.getY();

        for (Button each : buttonList) {
            if (x >= each.pos.x &&
                x <= each.pos.x + each.size.x &&
                y >= each.pos.y &&
                y <= each.pos.y + each.size.y ) {
                if (!each.isMouseHovered()) {
                    each.onMouseHovered();
                }
            } else {
                if (each.isMouseHovered()) {
                    each.offMouseHovered();
                }
            }
        }
    }

    public void onMousePressed(MouseEvent e) {
        double x = e.getX(), y = e.getY();

        for (Button each : buttonList) {
            if (x >= each.pos.x &&
                x <= each.pos.x + each.size.x &&
                y >= each.pos.y &&
                y <= each.pos.y + each.size.y ) {
                each.onMousePressed(e);
            }
        }
    }

    public void onMouseReleased(MouseEvent e) {
        double x = e.getX(), y = e.getY();

        for (Button each : buttonList) {
            each.onMouseReleased(e);
        }
    }

    public void onResize(Vec2d resizeFactor) {
        for (UIElement each : uiElementList) {
            each.onResize(resizeFactor);
        }
    }

    public void onTick(long nanosSincePreviousTick) {

    }

    public Vec2d getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(Vec2d screenSize) {
        this.screenSize = screenSize;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public String getTag() {
        return tag;
    }

    public List<Button> getButtonList() {
        return buttonList;
    }
}
