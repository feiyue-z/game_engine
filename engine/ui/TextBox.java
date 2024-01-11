package engine.ui;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TextBox extends UIElement{
    private String font;
    private String text;
    private Color color = Color.GRAY;

    public TextBox(Screen screen, Vec2d pos, Vec2d size, String text) {
        super(screen, pos, size);
        this.font = "Comic Sans MS";
        this.text = text;
    }

    @Override
    public void onDraw(GraphicsContext g) {
        g.restore();

        g.setFill(color);
        g.setFont(Font.font(this.font, 24));
        g.fillText(text, pos.x, pos.y);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
