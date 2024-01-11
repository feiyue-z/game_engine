package engine.shape;

import engine.ray.Ray;
import engine.support.Vec2d;

public class AAB implements Shape {

	private boolean isStatic;
	private Vec2d min; // top-left
	private Vec2d max; // bottom-right

	public AAB(Vec2d min, Vec2d max, boolean isStatic) {
		this.isStatic = isStatic;
		this.min = min;
		this.max = max;
	}

	@Override
	public Vec2d collides(Shape o) {
//		return o.collidesAAB(this);
		Vec2d f = o.collidesAAB(this);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collidesCircle(Circle c) {
		Vec2d f = c.collidesAAB(this);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collidesAAB(AAB other) {
		// compare intervals on x and y axes
		Interval thisXInterval = new Interval(this.min.x, this.max.x);
		Interval otherXInterval = new Interval(other.min.x, other.max.x);

		Interval thisYInterval = new Interval(this.min.y, this.max.y);
		Interval otherYInterval = new Interval(other.min.y, other.max.y);

		// no collision
		if (!(thisXInterval.overlaps(otherXInterval) && thisYInterval.overlaps(otherYInterval))) {
			return null;
		}

		// distances if moved towards different directions
		double up = Math.abs(this.max.y - other.min.y);
		double down = Math.abs(this.min.y - other.max.y);
		double left = Math.abs(this.max.x - other.min.x);
		double right = Math.abs(this.min.x - other.max.x);

		double distance = Math.min(up, Math.min(down, Math.min(left, right)));
		Vec2d dir;

		if (up == distance) {
			dir = new Vec2d(
				0,
				other.min.y - this.max.y
//				this.max.y - other.min.y
			);
		} else if (down == distance) {
			dir = new Vec2d(
				0,
				other.max.y - this.min.y
//				this.min.y - other.max.y
			);
		} else if (left == distance) {
			dir = new Vec2d(
				other.min.x - this.max.x,
//				this.max.x - other.min.x,
				0
			);
		} else {
			dir = new Vec2d(
				other.max.x - this.min.x,
//				this.min.x - other.max.x,
				0
			);
		}

		if (dir.isZero()) {
			return new Vec2d(0, 0);
		}
		return dir.normalize().smult(distance);
	}

	@Override
	public Vec2d collidesPolygon(Polygon p) {
//		return p.collidesAAB(this);

		Vec2d f = p.collidesAAB(this);
//		if (f != null) {
//			System.out.println("f.reflect().x = " + f.reflect().x + ", .y = " + f.reflect().y);
//		}
		return f == null ? null : f.reflect();
	}

	public void updatePos(Vec2d delta) {
		min = min.plus(delta);
		max = max.plus(delta);
	}

	public Vec2d getMin() {
		return min;
	}

	public Vec2d getMax() {
		return max;
	}

	public Vec2d getCenter() {
		return new Vec2d(min.plus(max.minus(min).sdiv(2)));
	}

	public boolean isStatic() {
		return isStatic;
	}

	public Vec2d[] constructNormals() {
		return new Vec2d[] {
			new Vec2d(max.x - min.x, 0).normalize(),
			new Vec2d(0, max.y - min.y).normalize()
		};
	}

	public Interval projectOntoAxis(Vec2d axis) {
		double intervalMin = Double.POSITIVE_INFINITY;
		double intervalMax = Double.NEGATIVE_INFINITY;

		Vec2d[] points = new Vec2d[] {
			min, // top-left
			new Vec2d(min.x, max.y), // bottom-left
			max, // bottom-right
			new Vec2d(max.x, min.y), // top-right
		};

		for (int i = 0; i < points.length; i++) {
			Vec2d point = points[i];
			double projection = axis.dot(point);

			intervalMin = Math.min(intervalMin, projection);
			intervalMax = Math.max(intervalMax, projection);
		}

		return new Interval(intervalMin, intervalMax);
	}

	public float intersects(Ray r) {
		return r.intersects(this);
	}
}
