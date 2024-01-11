package engine.component;

import engine.GameObject;
import engine.support.Vec2d;

public class GravityComponent extends Component {

	private final long MAX_NANOS_PER_TICK = 17_000_000L;

	public GravityComponent(GameObject gameObject) {
		super("gravity", gameObject);
	}

	public void onTick(long nanosSinceLastTick) {
		if (nanosSinceLastTick > MAX_NANOS_PER_TICK) {
			for (int i = 0; i < nanosSinceLastTick / MAX_NANOS_PER_TICK; i++) {
				onTick(MAX_NANOS_PER_TICK);
			}
			return;
		}

		PhysicsComponent physicsComponent = (PhysicsComponent) gameObject.getComponent("physics");

		Vec2d gravityForce = new Vec2d(0, 20).smult(physicsComponent.getMass());
		physicsComponent.applyForce(gravityForce);
	}
}
