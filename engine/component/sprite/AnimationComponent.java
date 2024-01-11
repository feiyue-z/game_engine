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

public class AnimationComponent extends Component {

	private SpriteSheet spriteSheet; // should only contain animation sprites for this GameObject
	private int currFrame = 0;
	private int currState = 0;

	public AnimationComponent(GameObject gameObject, SpriteSheet spriteSheet) {
		super("animation", gameObject);

		this.spriteSheet = spriteSheet;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		Image sprite = spriteSheet.getSprite(currFrame);

		if (sprite != null) {
			Vec2d pos = gameObject.getTransformComponent().getPos();
			Vec2d size = gameObject.getTransformComponent().getSize();
			g.drawImage(sprite, pos.x, pos.y, size.x, size.y);
		}
	}

	@Override
	public void onTick(long nanosSinceLastTick) {
		Pair<Integer, Integer> interval = spriteSheet.getStateValue(currState);
		int k = interval.getKey();
		int v = interval.getValue();
		int num = v - k + 1;

		currFrame = (currFrame + 1) % num + k;
	}

	public void setCurrState(int state) {
		this.currState = state;
		this.currFrame = 0;
	}

	public int getCurrState() {
		return currState;
	}
}
