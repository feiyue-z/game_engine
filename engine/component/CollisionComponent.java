package engine.component;

import engine.GameObject;
import engine.shape.Shape;
import engine.support.Vec2d;

public class CollisionComponent extends Component {

	private Shape shape;

	public CollisionComponent(GameObject gameObject, Shape shape) {
		super("collision", gameObject);

		this.shape = shape;
	}

	public Shape getShape() {
		return shape;
	}

	public void updatePos(Vec2d delta) {
		shape.updatePos(delta);
	}

	public void onCollide(Collision c) {
		applyMtv(this.gameObject, c.other, this.shape, c.mtv);
		applyMtv(c.other, this.gameObject, c.otherShape, c.mtv.reflect());

//		if (gameObject.getClass() == PolygonObject.class) {
//			System.out.println("1.. mtv.x = " + c.mtv.x + ", mtv.y = " + c.mtv.y);
//		} else if (c.other.getClass() == PolygonObject.class) {
//			System.out.println("2.. mtv.x = " + c.mtv.reflect().x + ", mtv.y = " + c.mtv.reflect().y);
//		}

		applyImpulse(this.gameObject, c.other, c.otherShape, c.mtv);
		applyImpulse(c.other, this.gameObject, this.shape, c.mtv.reflect());
	}

	private void applyMtv(GameObject thisGameObject, GameObject otherGameObject, Shape thisShape, Vec2d mtv) {
		if (thisShape.isStatic()) {
			return;
		}

		CollisionComponent otherCp = (CollisionComponent) otherGameObject.getComponent("collision");

		if (otherCp.getShape().isStatic()) {
			thisGameObject.getTransformComponent().updatePos(mtv.sdiv(2));
			thisShape.updatePos(mtv.sdiv(2));
		} else {
			thisGameObject.getTransformComponent().updatePos(mtv);
			thisShape.updatePos(mtv);
		}
	}

	private void applyImpulse(GameObject thisGameObject, GameObject otherGameObject, Shape otherShape, Vec2d mtv) {
		PhysicsComponent thisPhysicsComponent = (PhysicsComponent) thisGameObject.getComponent("physics");
		PhysicsComponent otherPhysicsComponent = (PhysicsComponent) otherGameObject.getComponent("physics");

		// mass
		float ma = thisPhysicsComponent.getMass();
		float mb = otherPhysicsComponent.getMass();

		// restitution
		float ra = thisPhysicsComponent.getRes();
		float rb = otherPhysicsComponent.getRes();

		// initial velocity
		Vec2d ua = thisPhysicsComponent.getVel();
		Vec2d ub = otherPhysicsComponent.getVel();

		// component of initial velocity on mtv-axis
		Vec2d normalizedMtv = mtv.normalize();
		Vec2d ca = normalizedMtv.smult(ua.dot(normalizedMtv));
		Vec2d cb = normalizedMtv.smult(ub.dot(normalizedMtv));

		// impulse
		float cor = (float) Math.sqrt(ra * rb);
		Vec2d ia = otherShape.isStatic() ?
			cb.minus(ca).smult(ma * (1 + cor)) :
			cb.minus(ca).smult(ma * mb * (1 + cor)).sdiv(ma + mb);

		thisPhysicsComponent.applyImpulse(ia);
	}
}

