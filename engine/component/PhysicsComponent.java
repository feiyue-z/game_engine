package engine.component;

import engine.GameObject;
import engine.support.Vec2d;

public class PhysicsComponent extends Component {

	private final float MAX_VELOCITY = 100;
	private final long MAX_NANOS_PER_TICK = 17_000_000L;

	private boolean shapeStatic;
	private float mass, res;
	private Vec2d vel, impulse, force;

	public PhysicsComponent(GameObject gameObject, float mass, float res, boolean shapeStatic) {
		super("physics", gameObject);

		this.shapeStatic = shapeStatic;
		this.mass = mass;
		this.res = res;

		this.vel = new Vec2d(0, 0);
		this.impulse = new Vec2d(0, 0);
		this.force = new Vec2d(0, 0);
	}

	public void applyForce(Vec2d f) {
		if (shapeStatic) {
			return;
		}

		force = force.plus(f);
	}

	public void applyImpulse(Vec2d p) {
		if (shapeStatic) {
			return;
		}

		impulse = impulse.plus(p);
	}

	@Override
	public void onTick(long nanosSinceLastTick) {
		if (nanosSinceLastTick > MAX_NANOS_PER_TICK) {
			for (int i = 0; i < nanosSinceLastTick / MAX_NANOS_PER_TICK; i++) {
				onTick(MAX_NANOS_PER_TICK);
			}
			return;
		}

		double t = nanosSinceLastTick / 1_000_000_000.0;

		Vec2d a = force.smult(t).sdiv(mass);
		Vec2d b = impulse.sdiv(mass);
		vel = vel.plus(a.plus(b));

		// cap velocity
//		System.out.println("vel.x = " + vel.x + ", .y = " + vel.y);

		if (vel.x > MAX_VELOCITY) {
			vel = new Vec2d(MAX_VELOCITY, vel.y);
		} else if (vel.x < -MAX_VELOCITY) {
			vel = new Vec2d(-MAX_VELOCITY, vel.y);
		}

		if (vel.y > MAX_VELOCITY) {
			vel = new Vec2d(vel.x, MAX_VELOCITY);
		} else if (force.y < -MAX_VELOCITY) {
			vel = new Vec2d(vel.x, -MAX_VELOCITY);
		}

		Vec2d delta = vel.smult(t);

		CollisionComponent collisionComponent = (CollisionComponent) gameObject.getComponent("collision");
		collisionComponent.updatePos(delta);

		TransformComponent transformComponent = gameObject.getTransformComponent();
		transformComponent.updatePos(delta);

		force = new Vec2d(0, 0);
		impulse = new Vec2d(0, 0);
	}

	public boolean isShapeStatic() {
		return shapeStatic;
	}

	public float getMass() {
		return mass;
	}

	public float getRes() {
		return res;
	}

	public Vec2d getVel() {
		return vel;
	}

	public Vec2d getImpulse() {
		return impulse;
	}

	public Vec2d getForce() {
		return force;
	}
}
