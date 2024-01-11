package engine.ui.button;

import engine.support.Vec2d;
import engine.ui.Screen;
import engine.ui.UIElement;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.xml.parsers.ParserConfigurationException;

public class Button extends UIElement {

    private static final int DEFAULT_LINE_WIDTH = 8;

    private int lineWidth = DEFAULT_LINE_WIDTH;

    private boolean mouseHovered = false;

    public Button(Screen screen, Vec2d pos, Vec2d size) {
        super(screen, pos, size);
        screen.getButtonList().add(this);
    }

    public void onMouseClicked(MouseEvent e) {

    }

    public void onMouseHovered() {
        mouseHovered = true;
        lineWidth = 12;
    }

    public void offMouseHovered() {
        mouseHovered = false;
        lineWidth = DEFAULT_LINE_WIDTH;
    }

    public void onMousePressed(MouseEvent e) {

    }

    public void onMouseReleased(MouseEvent e) {

    }

    public boolean isMouseHovered() {
        return mouseHovered;
    }

    protected void onDraw(GraphicsContext g) {
        g.setStroke(Color.GRAY);
        g.setLineWidth(lineWidth);
        g.strokeRoundRect(pos.x, pos.y, size.x, size.y, 30, 30);
    }
}
