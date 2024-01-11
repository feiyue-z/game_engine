package engine.component;

import engine.GameObject;
import engine.support.Vec2d;
import engine.ui.Viewport;

public class CenterComponent extends Component {

	private Viewport viewport;
	private Vec2d lastPos;

	public CenterComponent(GameObject gameObject, Viewport viewport) {
		super("center", gameObject);

		this.viewport = viewport;
		this.lastPos = gameObject.getTransformComponent().getPos();

		initializeViewportPos();
	}

	@Override
	public void onTick(long nanosSinceLastTick) {
		TransformComponent transformComponent = gameObject.getTransformComponent();

		Vec2d currPos = transformComponent.getPos();
		Vec2d delta = currPos.minus(lastPos);

		lastPos = currPos;
		viewport.updatePos(delta);
	}

	public Vec2d getLastPos() {
		return lastPos;
	}

	private void initializeViewportPos() {
		viewport.resetPos();

		Vec2d playerPos = gameObject.getTransformComponent().getPos();

		Vec2d vpPos = viewport.getPos();
		Vec2d vpSize = viewport.getSize();
		Vec2d vpCenterPos = vpPos.plus(vpSize.sdiv(2));

		Vec2d delta = playerPos.minus(vpCenterPos);
		viewport.setPos(delta);
	}
}
