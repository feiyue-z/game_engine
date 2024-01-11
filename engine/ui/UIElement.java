package engine.ui;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import java.util.Iterator;

public abstract class UIElement {
    protected Screen screen;
    protected Vec2d pos; // top-left position
    protected Vec2d size;

    public UIElement(Screen screen, Vec2d pos, Vec2d size) {
        this.screen = screen;
        this.pos = pos;
        this.size = size;

        screen.uiElementList.add(this);
    }

    protected abstract void onDraw(GraphicsContext g);

    protected void onResize(Vec2d resizeFactor) {
        pos = pos.pmult(resizeFactor);
        size = size.pmult(resizeFactor);
    }

    public void removeElement() {
        Iterator<UIElement> iterator = screen.uiElementList.iterator();

        while (iterator.hasNext()) {
            if (iterator.next() == this) {
                iterator.remove();
            }
        }
    }

    public Vec2d getPos() {
        return pos;
    }

    public Vec2d getSize() {
        return size;
    }

    public void setPos(Vec2d pos) {
        this.pos = pos;
    }

    public void setSize(Vec2d size) {
        this.size = size;
    }
}
