package engine.ui.button;

import engine.support.Vec2d;
import engine.ui.Screen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class SwitchButton extends TextButton {

	protected boolean isOn;
	private SwitchController controller;

	public SwitchButton(Screen screen, Vec2d pos, Vec2d size, String text, SwitchController controller, boolean isOn) {
		super(screen, pos, size, text);

		this.controller = controller;
		this.isOn = isOn;

		if (isOn) {
			controller.setOnButton(this);
		}
	}

	public void setOn(boolean on) {
		this.isOn = on;
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		isOn = !isOn;

		if (isOn) {
			controller.setOnButton(this);
		}
	}

	@Override
	protected void onDraw(GraphicsContext g) {
		if (isOn) {
			g.setFill(Color.GRAY);
			g.fillRoundRect(pos.x, pos.y, size.x, size.y, 30, 30);

			textBox.setColor(Color.WHITE);
		} else {
			textBox.setColor(Color.GRAY);
		}

		super.onDraw(g);
	}
}