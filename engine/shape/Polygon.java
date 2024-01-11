package engine.shape;

import engine.ray.Ray;
import engine.support.Vec2d;

public class Polygon implements Shape {

	private boolean isStatic;
	private Vec2d[] points;
	private Vec2d min, max;

	public Polygon(boolean isStatic, Vec2d ... points) {
		this.isStatic = isStatic;
		this.points = points;

		populateMinMax();
	}

	public int getNumPoints() {
		return points.length;
	}

	public Vec2d getPoint(int i) {
		return points[i];
	}

	public boolean isStatic() {
		return isStatic;
	}

	@Override
	public Vec2d collides(Shape o) {
		Vec2d f = o.collidesPolygon(this);
		return f == null ? null : f.reflect();
//		return o.collidesPolygon(this);
	}

	@Override
	public Vec2d collidesCircle(Circle other) {
		Vec2d[] thisNormals = this.constructNormals();
		Vec2d otherNormal = other.constructNormal(this);

		Vec2d minMtvDir = null;
		double minMtvMag = Double.POSITIVE_INFINITY;

		for (int i = 0; i < thisNormals.length + 1; i++) {
			Vec2d axis;

			if (i == thisNormals.length) {
				axis = otherNormal;
			} else {
				axis = thisNormals[i];
			}

			Interval a = this.projectOntoAxis(axis);
			Interval b = other.projectOntoAxis(axis);

			if (!a.overlaps(b)) {
				return null;
			}

			double mtvMagnitude = getMtvMagnitude(a, b);

			if (mtvMagnitude < minMtvMag) {
				minMtvMag = mtvMagnitude;
				minMtvDir = axis;
			}
		}

		// determine the direction to push away
		Vec2d diff = this.getCenter().minus(other.getCenter());
		if (diff.dot(minMtvDir) < 0) {
			minMtvDir = minMtvDir.smult(-1);
		}

		return minMtvDir.smult(minMtvMag);
	}

	@Override
	public Vec2d collidesAAB(AAB other) {
		Vec2d[] thisNormals = this.constructNormals();
		Vec2d[] otherNormals = other.constructNormals();

		Vec2d minMtvDir = null;
		double minMtvMag = Double.POSITIVE_INFINITY;

		for (Vec2d axis : thisNormals) {
			Interval a = this.projectOntoAxis(axis);
			Interval b = other.projectOntoAxis(axis);

			if (!a.overlaps(b)) {
				return null;
			}

			double mtvMagnitude = getMtvMagnitude(a, b);

			if (mtvMagnitude < minMtvMag) {
				minMtvMag = mtvMagnitude;
				minMtvDir = axis;
			}
		}

		for (Vec2d axis : otherNormals) {
			Interval a = this.projectOntoAxis(axis);
			Interval b = other.projectOntoAxis(axis);

			if (!a.overlaps(b)) {
				return null;
			}

			double mtvMagnitude = getMtvMagnitude(a, b);

			if (mtvMagnitude < minMtvMag) {
				minMtvMag = mtvMagnitude;
				minMtvDir = axis;
			}
		}

		// determine the direction to push away
		Vec2d diff = this.getCenter().minus(other.getCenter());
//		Vec2d diff = other.getCenter().minus(this.getCenter());
		if (diff.dot(minMtvDir) < 0) {
			minMtvDir = minMtvDir.smult(-1);
		}
//		System.out.println("minMtvDir.x = " + minMtvDir.x + ", .y = " + minMtvDir.y);

		return minMtvDir.smult(minMtvMag);
	}

	@Override
	public Vec2d collidesPolygon(Polygon other) {
		Vec2d[] thisNormals = this.constructNormals();
		Vec2d[] otherNormals = other.constructNormals();

		Vec2d minMtvDir = null;
		double minMtvMag = Double.POSITIVE_INFINITY;

		for (Vec2d axis : thisNormals) {
			Interval a = this.projectOntoAxis(axis);
			Interval b = other.projectOntoAxis(axis);

			if (!a.overlaps(b)) {
				return null;
			}

			double mtvMagnitude = getMtvMagnitude(a, b);

			if (mtvMagnitude < minMtvMag) {
				minMtvMag = mtvMagnitude;
				minMtvDir = axis;
			}
		}

		for (Vec2d axis : otherNormals) {
			Interval a = this.projectOntoAxis(axis);
			Interval b = other.projectOntoAxis(axis);

			if (!a.overlaps(b)) {
				return null;
			}

			double mtvMagnitude = getMtvMagnitude(a, b);

			if (mtvMagnitude < minMtvMag) {
				minMtvMag = mtvMagnitude;
				minMtvDir = axis;
			}
		}

		// determine the direction to push away
		Vec2d diff = this.getCenter().minus(other.getCenter());
		if (diff.dot(minMtvDir) < 0) {
			minMtvDir = minMtvDir.smult(-1);
		}

		return minMtvDir.smult(minMtvMag);
	}

	@Override
	public void updatePos(Vec2d delta) {
		for (int i = 0; i < points.length; i++) {
			points[i] = points[i].plus(delta);
		}

		min.plus(delta);
		max.plus(delta);
	}

	public Vec2d[] constructNormals() {
		int size = points.length;

		Vec2d[] normals = new Vec2d[size];

		for (int i = 0; i < size; i++) {
			Vec2d edge = points[(i + 1) % size].minus(points[i]);
			normals[i] =  new Vec2d(-edge.y, edge.x).normalize();
		}

		return normals;
	}

	public Interval projectOntoAxis(Vec2d axis) {
		double intervalMin = Double.POSITIVE_INFINITY;
		double intervalMax = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < points.length; i++) {
			Vec2d point = points[i];

			double projection = axis.dot(point);

			intervalMin = Math.min(intervalMin, projection);
			intervalMax = Math.max(intervalMax, projection);
		}

		return new Interval(intervalMin, intervalMax);
	}

	/**
	 * @param a interval on the left
	 * @param b interval on the right
	 * @return magnitude of minimum 1D mtv between a and b
	 */
	private double getMtvMagnitude(Interval a, Interval b) {
		if (a.min > b.min) {
			return getMtvMagnitude(b, a);
		}
		return a.max - b.min;
	}

	private void populateMinMax() {
		double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY;
		double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;

		for(Vec2d point : points) {
			minX = Double.min(minX, point.x);
			minY = Double.min(minY, point.y);
			maxX = Double.max(maxX, point.x);
			maxY = Double.max(maxY, point.y);
		}

		min = new Vec2d(minX, minY);
		max = new Vec2d(maxX, maxY);
	}

	public Vec2d getCenter() {
		return min.plus(max).sdiv(2);
	}

	public Vec2d getMin() {
		return min;
	}

	public Vec2d getMax() {
		return max;
	}

	public float intersects(Ray r) {
		return r.intersects(this);
	}
}
