package engine.system;

import engine.GameObject;
import engine.component.Collision;
import engine.component.CollisionComponent;
import engine.ray.Ray;
import engine.support.Vec2d;
import nin.object.PlayerObject;
import nin.object.RayObject;

public class CollisionSystem extends System {

	private final long MAX_NANOS_PER_TICK = 17_000_000L;

	private RayObject rayObject;
	private GameObject[] gameObjects;

	public CollisionSystem() { }

	public void buildArray() {
		gameObjects = gameObjectSet.toArray(new GameObject[0]);
	}

	public void setRayObject(RayObject rayObject) {
		this.rayObject = rayObject;
	}

	@Override
	public void onTick(long nanosSinceLastTick) {
		if (nanosSinceLastTick > MAX_NANOS_PER_TICK) {
			for (int i = 0; i < nanosSinceLastTick / MAX_NANOS_PER_TICK; i++) {
				onTick(MAX_NANOS_PER_TICK);
			}
			return;
		}

		for (int i = 0; i < gameObjects.length; i++) {
			for (int j = i + 1; j < gameObjects.length; j++) {
				GameObject thisGameObject = gameObjects[i];
				GameObject otherGameObject = gameObjects[j];

				CollisionComponent thisCp = (CollisionComponent) thisGameObject.getComponent("collision");
				CollisionComponent otherCp = (CollisionComponent) otherGameObject.getComponent("collision");

				Vec2d mtv = thisCp.getShape().collides(otherCp.getShape());

				if (mtv != null && !mtv.isZero()) {
					thisCp.onCollide(
						new Collision(
							otherGameObject,
							mtv,
							thisCp.getShape(),
							otherCp.getShape()
						)
					);
				}
			}
		}

		if (rayObject == null) {
			return;
		}

		Ray ray = rayObject.getRay();
		GameObject intersectionObject = null;
		float minIntersection = Float.MAX_VALUE;

		for (int i = 0; i < gameObjects.length; i++) {
			GameObject gameObject = gameObjects[i];
			if (gameObject.getClass() == RayObject.class || gameObject.getClass() == PlayerObject.class) {
				continue;
			}

			CollisionComponent cp = (CollisionComponent) gameObject.getComponent("collision");

			float t = ray.intersects(cp.getShape());

			if (t != -1 && !Float.isNaN(t)) {
				minIntersection = Math.min(t, minIntersection);
				intersectionObject = gameObject;
			}
		}

		if (minIntersection != Float.MAX_VALUE) {
			rayObject.castRay(minIntersection, intersectionObject);
//			java.lang.System.out.println("min t = " + minIntersection);
		}
	}
}
