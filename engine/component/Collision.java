package engine.component;

import engine.GameObject;
import engine.shape.Shape;
import engine.support.Vec2d;

public class Collision {
	public final GameObject other;
	public final Vec2d mtv;
	public final Shape thisShape;
	public final Shape otherShape;

	public Collision(GameObject other, Vec2d mtv, Shape thisShape, Shape otherShape) {
		this.other = other;
		this.mtv = mtv;
		this.thisShape = thisShape;
		this.otherShape = otherShape;
	}
}
