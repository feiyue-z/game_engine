package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week3Reqs;
import engine.shape.Interval;

/**
 * Fill this class in during Week 3. Make sure to also change the week variable in Display.java.
 */
public final class Week3 extends Week3Reqs {

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

}
