package engine.component.sprite;

import engine.GameObject;
import engine.component.Component;
import engine.component.sprite.SpriteSheet;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class SpriteComponent extends Component {

	private SpriteSheet spriteSheet;
	private int spriteIndex;

	public SpriteComponent(GameObject gameObject, SpriteSheet spriteSheet, int spriteIndex) {
		super("sprite", gameObject);

		this.spriteSheet = spriteSheet;
		this.spriteIndex = spriteIndex;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		Image sprite = spriteSheet.getSprite(spriteIndex);

		if (sprite != null) {
			Vec2d pos = gameObject.getTransformComponent().getPos();
			Vec2d size = gameObject.getTransformComponent().getSize();
			g.drawImage(sprite, pos.x, pos.y, size.x, size.y);
		}
	}

	public int getSpriteIndex() {
		return spriteIndex;
	}
}
