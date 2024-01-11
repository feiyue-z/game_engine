package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week6Reqs;
import debugger.support.shapes.PolygonShapeDefine;
import engine.shape.Interval;

/**
 * Fill this class in during Week 6. Make sure to also change the week variable in Display.java.
 */
public final class Week6 extends Week6Reqs {

	// AXIS-ALIGNED BOXES
	
	@Override
	public Vec2d collision(AABShape s1, AABShape s2) {
		Vec2d s1Min = s1.topLeft;
		Vec2d s1Max = s1.topLeft.plus(s1.size);

		Vec2d s2Min = s2.topLeft;
		Vec2d s2Max = s2.topLeft.plus(s2.size);

		// compare intervals on x and y axes

		Interval thisXInterval = new Interval(s1Min.x, s1Max.x);
		Interval otherXInterval = new Interval(s2Min.x, s2Max.x);

		Interval thisYInterval = new Interval(s1Min.y, s1Max.y);
		Interval otherYInterval = new Interval(s2Min.y, s2Max.y);

		// no collision
		if (!(thisXInterval.overlaps(otherXInterval) && thisYInterval.overlaps(otherYInterval))) {
			return null;
		}

		// distances if moved towards different directions
		double up = Math.abs(s1Max.y - s2Min.y);
		double down = Math.abs(s1Min.y - s2Max.y);
		double left = Math.abs(s1Max.x - s2Min.x);
		double right = Math.abs(s1Min.x - s2Max.x);

		double distance = Math.min(up, Math.min(down, Math.min(left, right)));
		Vec2d dir;

		if (up == distance) {
			dir = new Vec2d(
				0,
				s2Min.y - s1Max.y
			);
		} else if (down == distance) {
			dir = new Vec2d(
				0,
				s2Max.y - s1Min.y
			);
		} else if (left == distance) {
			dir = new Vec2d(
				s2Min.x - s1Max.x,
				0
			);
		} else {
			dir = new Vec2d(
				s2Max.x - s1Min.x,
				0
			);
		}

		if (dir.isZero()) {
			return new Vec2d(0, 0);
		}
		return dir.normalize().smult(distance);
	}

	@Override
	public Vec2d collision(AABShape s1, CircleShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(AABShape s1, Vec2d s2) {
		Vec2d s1Min = s1.topLeft;
		Vec2d s1Max = s1.topLeft.plus(s1.size);

		if (!(s2.x >= s1Min.x &&
			s2.x <= s1Max.x &&
			s2.y >= s1Min.y &&
			s2.y <= s1Max.y)) {
			return null;
		}

		double closestX = Math.abs(s1Min.x - s2.x) < Math.abs(s1Max.x - s2.x) ? s1Min.x : s1Max.x;
		double closestY = Math.abs(s1Min.y - s2.y) < Math.abs(s1Max.y - s2.y) ? s1Min.y : s1Max.y;

		double deltaX = Math.abs(s2.x - closestX);
		double deltaY = Math.abs(s2.y - closestY);

		// from center of circle to the closest point on AAB edge
		double distance;
		Vec2d dir;

		if (deltaX < deltaY) {
			distance = deltaX;
			dir = new Vec2d(
				closestX - s2.x,
				0
			);
		} else {
			distance = deltaY;
			dir = new Vec2d(
				0,
				closestY - s2.y
			);
		}

		if (dir.isZero()) {
			return new Vec2d(0, 0);
		}
		double mag = distance;
		return dir.normalize().smult(mag);
	}

	@Override
	public Vec2d collision(AABShape s1, PolygonShape s2) {
		PolygonShape aab = new PolygonShapeDefine(
			s1.topLeft, // top-left
			s1.topLeft.plus(0.f, (float) s1.size.y), // bottom-left
			s1.topLeft.plus(s1.size), // bottom-right
			s1.topLeft.plus((float) s1.size.x, 0.f) // top-right,
		);

		return collision(aab, s2);
	}

	// CIRCLES
	
	@Override
	public Vec2d collision(CircleShape s1, AABShape s2) {
		Vec2d s2Min = s2.topLeft;
		Vec2d s2Max = s2.topLeft.plus(s2.size);

		Vec2d center = s1.center;

		// center of circle is inside aab
		if (center.x >= s2Min.x &&
			center.x <= s2Max.x &&
			center.y >= s2Min.y &&
			center.y <= s2Max.y) {

			double closestX = Math.abs(s2Min.x - center.x) < Math.abs(s2Max.x - center.x) ? s2Min.x : s2Max.x;
			double closestY = Math.abs(s2Min.y - center.y) < Math.abs(s2Max.y - center.y) ? s2Min.y : s2Max.y;

			double deltaX = Math.abs(center.x - closestX);
			double deltaY = Math.abs(center.y - closestY);

			// from center of circle to the closest point on AAB edge
			double distance;
			Vec2d dir;

			if (deltaX < deltaY) {
				distance = deltaX;
				dir = new Vec2d(
					closestX - center.x,
					0
				);
			} else {
				distance = deltaY;
				dir = new Vec2d(
					0,
					closestY - center.y
				);
			}

			if (dir.isZero()) {
				return new Vec2d(0, 0);
			}
			double mag = s1.radius + distance;
			return dir.normalize().smult(mag);
		}

		// center of circle is outside aab
		// clamp circle center to AAB

		double closestX = Math.max(s2Min.x, Math.min(center.x, s2Max.x));
		double closestY = Math.max(s2Min.y, Math.min(center.y, s2Max.y));

		double deltaX = s1.center.x - closestX;
		double deltaY = s1.center.y - closestY;

		// from center of circle to the closest point on AAB edge
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		// no collision
		if (distance > s1.radius) {
			return null;
		}

		Vec2d dir = new Vec2d(
			center.x - closestX,
			center.y - closestY
		);

		if (dir.isZero()) {
			return new Vec2d(0, 0);
		}
		double mag = s1.radius - distance;
		return dir.normalize().smult(mag);
	}

	@Override
	public Vec2d collision(CircleShape s1, CircleShape s2) {
		// from center of this circle to that of the other
		double distance =
			Math.sqrt(
				(s1.center.x - s2.center.x) * (s1.center.x - s2.center.x) +
					(s1.center.y - s2.center.y) * (s1.center.y - s2.center.y)
			);

		// radii sum of 2 circles
		double radii = s1.radius + s2.radius;

		// no collision
		if (distance > radii) {
			return null;
		}

		Vec2d dir = new Vec2d(
			s1.center.x - s2.center.x,
			s1.center.y - s2.center.y
		);

		if (dir.isZero()) {
			return new Vec2d(0, 0);
		}
		double mag = s1.radius + s2.radius - distance;
		return dir.normalize().smult(mag);
	}

	@Override
	public Vec2d collision(CircleShape s1, Vec2d s2) {
		double distance =
			(s1.center.x - s2.x) * (s1.center.x - s2.x) +
				(s1.center.y - s2.y) * (s1.center.y - s2.y);

		if (distance > s1.radius * s1.radius) {
			return null;
		}

		Vec2d dir = new Vec2d(
			s2.x - s1.center.x,
			s2.y - s1.center.y
		);

		if (dir.isZero()) {
			return new Vec2d(0, 0);
		}
		double mag = s1.radius - distance;
		return dir.normalize().smult(mag);
	}

	@Override
	public Vec2d collision(CircleShape s1, PolygonShape s2) {
		Vec2d circleNormal = getCircleNormal(s1, s2);
		Vec2d[] s2Normals = constructPolygonNormals(s2);

		Vec2d minMtvDir = null;
		double minMtvMag = Double.POSITIVE_INFINITY;

		Interval a = projectCircleOntoAxis(s1, circleNormal);
		Interval b = projectPolygonOntoAxis(s2, circleNormal);

		if (!a.overlaps(b)) {
			return null;
		}

		double mtvMagnitude = getMtvMagnitude(a, b);

		if (mtvMagnitude < minMtvMag) {
			minMtvMag = mtvMagnitude;
			minMtvDir = circleNormal;
		}

		for (Vec2d axis : s2Normals) {
			a = projectCircleOntoAxis(s1, axis);
			b = projectPolygonOntoAxis(s2, axis);

			if (!a.overlaps(b)) {
				return null;
			}

			mtvMagnitude = getMtvMagnitude(a, b);

			if (mtvMagnitude < minMtvMag) {
				minMtvMag = mtvMagnitude;
				minMtvDir = axis;
			}
		}

		// determine the direction to push away
		Vec2d diff = s1.getCenter().minus(s2.getCenter());
		if (diff.dot(minMtvDir) < 0) {
			minMtvDir = minMtvDir.smult(-1);
		}

		return minMtvDir.smult(minMtvMag);
	}

	public Vec2d getCircleNormal(CircleShape s1, PolygonShape s2) {
		int size = s2.getNumPoints();
		double minDistance = Double.POSITIVE_INFINITY;
		Vec2d closestVertex = null;

		for (int i = 0; i < size; i++) {
			double distance = s1.center.minus(s2.getPoint(i)).mag();

			if (distance < minDistance) {
				minDistance = distance;
				closestVertex = s2.getPoint(i);
			}
		}

		return closestVertex.minus(s1.center).normalize();
	}

	public Interval projectCircleOntoAxis(CircleShape s, Vec2d axis) {
		Vec2d point = s.center;
		double projection = axis.dot(point);

		return new Interval(projection - s.radius, projection + s.radius);
	}
	
	// POLYGONS

	@Override
	public Vec2d collision(PolygonShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(PolygonShape s1, CircleShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(PolygonShape s1, Vec2d s2) {
		int size = s1.getNumPoints();

		for (int i = 0; i < size; i++) {
			Vec2d base = s1.getPoint(i % size);

			Vec2d edge = s1.getPoint((i + 1) % size).minus(base);
			Vec2d point = s2.minus(base);

			double t = edge.cross(point);

			if (edge.cross(point) > 0) {
				return null;
			}
		}

		return new Vec2d(1, 1);
	}

	@Override
	public Vec2d collision(PolygonShape s1, PolygonShape s2) {
		Vec2d[] s1Normals = constructPolygonNormals(s1);
		Vec2d[] s2Normals = constructPolygonNormals(s2);

		Vec2d minMtvDir = null;
		double minMtvMag = Double.POSITIVE_INFINITY;

		for (Vec2d axis : s1Normals) {
			Interval a = projectPolygonOntoAxis(s1, axis);
			Interval b = projectPolygonOntoAxis(s2, axis);

			if (!a.overlaps(b)) {
				return null;
			}

			double mtvMagnitude = getMtvMagnitude(a, b);

			if (mtvMagnitude < minMtvMag) {
				minMtvMag = mtvMagnitude;
				minMtvDir = axis;
			}
		}

		for (Vec2d axis : s2Normals) {
			Interval a = projectPolygonOntoAxis(s1, axis);
			Interval b = projectPolygonOntoAxis(s2, axis);

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
		Vec2d diff = s1.getCenter().minus(s2.getCenter());
		if (diff.dot(minMtvDir) < 0) {
			minMtvDir = minMtvDir.smult(-1);
		}

		return minMtvDir.smult(minMtvMag);
	}

	public Vec2d[] constructPolygonNormals(PolygonShape s) {
		int size = s.getNumPoints();
		Vec2d[] edges = new Vec2d[size];
		for (int i = 0; i < size; i++) {
			edges[i] = s.getPoint((i + 1) % size).minus(s.getPoint(i));
		}

		Vec2d[] normals = new Vec2d[edges.length];
		for (int i = 0; i < size; i++) {
			normals[i] = new Vec2d(-edges[i].y, edges[i].x).normalize();
		}

		return normals;
	}

	public Interval projectPolygonOntoAxis(PolygonShape s, Vec2d axis) {
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < s.getNumPoints(); i++) {
			Vec2d point = s.getPoint(i);

			double projection = axis.dot(point);

			min = Math.min(min, projection);
			max = Math.max(max, projection);
		}

		return new Interval(min, max);
	}

	/**
	 * @param a interval on the left
	 * @param b interval on the right
	 * @return magnitude of minimum 1D mtv between a and b
	 */
	public double getMtvMagnitude(Interval a, Interval b) {
		if (a.min > b.min) {
			return getMtvMagnitude(b, a);
		}
		return a.max - b.min;
	}
	
	// RAYCASTING
	
	@Override
	public float raycast(AABShape s1, Ray s2) {
		PolygonShape aab = new PolygonShapeDefine(
			s1.topLeft,
			s1.topLeft.plus(0, (float) s1.size.y),
			s1.topLeft.plus(s1.size),
			s1.topLeft.plus((float) s1.size.x, 0)
		);
		return raycast(aab, s2);
	}
	
	@Override
	public float raycast(CircleShape s1, Ray s2) {
		// source of ray to center
		Vec2d s2c = s1.center.minus(s2.src);

		// length of projection
		double projection = s2c.dot(s2.dir);

		// closet point on ray
		Vec2d closest = s2.src.plus(s2.dir.smult(projection));

		// center to ray
		Vec2d c2ray = closest.minus(s1.center);

		// distance from center to ray
		double x = c2ray.mag();

		// no collision
		if (x > s1.radius) {
			return 0.f;
		}

		// distance between source of ray and center
		double distance = (s2.src.x - s1.center.x) * (s2.src.x - s1.center.x) +
			(s2.src.y - s1.center.y) * (s2.src.y - s1.center.y);

		// determine if source of ray is inside circle
		double t = distance > s1.radius * s1.radius ?
			projection - Math.sqrt(s1.radius * s1.radius - x * x) :
			projection + Math.sqrt(s1.radius * s1.radius - x * x);
		return (float) t;
	}
	
	@Override
	public float raycast(PolygonShape s1, Ray s2) {
		int size = s1.getNumPoints();
		float min = Float.MAX_VALUE;

		for (int i = 0; i < size; i++) {
			float t = rayEdge(s2, s1.getPoint(i), s1.getPoint((i + 1) % size));

			if (t != -1) {
				min = Math.min(t, min);
			}
		}

		return min == Float.MAX_VALUE ? -1 : min;
	}

	public float rayEdge(Ray r, Vec2d a, Vec2d b) {
		double prod1 = a.minus(r.src).cross(r.dir);
		double prod2 = b.minus(r.src).cross(r.dir);

		if (prod1 * prod2 > 0) {
			return -1;
		}

		Vec2d n = b.minus(a).perpendicular();
		double t = b.minus(r.src).dot(n) / r.dir.dot(n);

		if (t < 0) {
			return -1;
		}
		return (float) t;
	}
}
