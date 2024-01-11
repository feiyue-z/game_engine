package engine.ui.button;

import engine.support.Vec2d;
import engine.ui.Screen;
import engine.ui.TextBox;
import engine.ui.button.Button;

public class TextButton extends Button {
	protected TextBox textBox;

	public TextButton(Screen screen, Vec2d pos, Vec2d size, String text) {
		super(screen, pos, size);

		this.textBox = new TextBox(
			screen,
			new Vec2d(pos.x + 8, pos.y + 33),
			size,
			text
		);
	}
}
