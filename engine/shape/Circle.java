package engine.shape;

import engine.ray.Ray;
import engine.support.Vec2d;

public class Circle implements Shape {

	private boolean isStatic;
	private Vec2d center;
	private double radius;

	public Circle(Vec2d center, double radius, boolean isStatic) {
		this.isStatic = isStatic;
		this.center = center;
		this.radius = radius;
	}

	@Override
	public Vec2d collides(Shape o) {
		return o.collidesCircle(this);
	}

	/**
	 * @return mtv if they collide, otherwise return null
	 */
	@Override
	public Vec2d collidesCircle(Circle other) {
		// from center of this circle to that of the other
		double distance =
			Math.sqrt(
				(this.center.x - other.center.x) * (this.center.x - other.center.x) +
					(this.center.y - other.center.y) * (this.center.y - other.center.y)
			);

		// radii sum of 2 circles
		double radii = this.radius + other.radius;

		// no collision
		if (distance > radii) {
			return null;
		}

		Vec2d dir = new Vec2d(
			this.center.x - other.center.x,
			this.center.y - other.center.y
		);

		if (dir.isZero()) {
			return new Vec2d(0, 0);
		}
		double mag = this.radius + other.radius - distance;
		return dir.normalize().smult(mag);
	}

	@Override
	public Vec2d collidesAAB(AAB aab) {
		Vec2d min = aab.getMin();
		Vec2d max = aab.getMax();

		// center of circle is inside aab
		if (this.center.x >= min.x &&
			this.center.x <= max.x &&
			this.center.y >= min.y &&
			this.center.y <= max.y) {

			double closestX = Math.abs(min.x - this.center.x) < Math.abs(max.x - this.center.x) ? min.x : max.x;
			double closestY = Math.abs(min.y - this.center.y) < Math.abs(max.y - this.center.y) ? min.y : max.y;

			double deltaX = Math.abs(this.center.x - closestX);
			double deltaY = Math.abs(this.center.y - closestY);

			// from center of circle to the closest point on AAB edge
			double distance;
			Vec2d dir;

			if (deltaX < deltaY) {
				distance = deltaX;
				dir = new Vec2d(
					closestX - this.center.x,
					0
				);
			} else {
				distance = deltaY;
				dir = new Vec2d(
					0,
					closestY - this.center.y
				);
			}

			if (dir.isZero()) {
				return new Vec2d(0, 0);
			}
			double mag = this.radius + distance;
			return dir.normalize().smult(mag);
		}

		// center of circle is outside aab

		double closestX = Math.max(min.x, Math.min(this.center.x, max.x));
		double closestY = Math.max(min.y, Math.min(this.center.y, max.y));

		double deltaX = this.center.x - closestX;
		double deltaY = this.center.y - closestY;

		// from center of circle to the closest point on AAB edge
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		// no collision
		if (distance > this.radius) {
			return null;
		}

		Vec2d dir = new Vec2d(
			this.center.x - closestX,
			this.center.y - closestY
		);

		if (dir.isZero()) {
			return new Vec2d(0, 0);
		}
		double mag = this.radius - distance;
		return dir.normalize().smult(mag);
	}

	@Override
	public Vec2d collidesPolygon(Polygon p) {
		return p.collidesCircle(this);
	}

	public void updatePos(Vec2d delta) {
		center = center.plus(delta);
	}

	public Vec2d getCenter() {
		return center;
	}

	public double getRadius() {
		return radius;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public Vec2d constructNormal(Polygon other) {
		int size = other.getNumPoints();
		double minDistance = Double.POSITIVE_INFINITY;
		Vec2d closestVertex = null;

		for (int i = 0; i < size; i++) {
			double distance = this.center.minus(other.getPoint(i)).mag();

			if (distance < minDistance) {
				minDistance = distance;
				closestVertex = other.getPoint(i);
			}
		}

		return closestVertex.minus(this.center).normalize();
	}

	public Interval projectOntoAxis(Vec2d axis) {
		double projection = axis.dot(this.center);

		return new Interval(projection - this.radius, projection + this.radius);
	}

	public float intersects(Ray r) {
		return r.intersects(this);
	}
}
