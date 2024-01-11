package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week2Reqs;
import engine.shape.Interval;

/**
 * Fill this class in during Week 2.
 */
public final class Week2 extends Week2Reqs {

	// AXIS-ALIGNED BOXES
	
	@Override
	public boolean isColliding(AABShape s1, AABShape s2) {
		// compare intervals on x and y axes

		Vec2d s1Min = s1.topLeft;
		Vec2d s1Max = s1.topLeft.plus(s1.size);

		Vec2d s2Min = s2.topLeft;
		Vec2d s2Max = s2.topLeft.plus(s2.size);

		Interval thisXInterval = new Interval(s1Min.x, s1Max.x);
		Interval otherXInterval = new Interval(s2Min.x, s2Max.x);

		Interval thisYInterval = new Interval(s1Min.y, s1Max.y);
		Interval otherYInterval = new Interval(s2Min.y, s2Max.y);

		return thisXInterval.overlaps(otherXInterval) && thisYInterval.overlaps(otherYInterval);
	}

	@Override
	public boolean isColliding(AABShape s1, CircleShape s2) {
		Vec2d s1Min = s1.topLeft;
		Vec2d s1Max = s1.topLeft.plus(s1.size);

		Vec2d center = s2.center;

		// center of circle is inside aab
		if (center.x >= s1Min.x &&
			center.x <= s1Max.x &&
			center.y >= s1Min.y &&
			center.y <= s1Max.y) {
			return true;
		}

		// center of circle is outside aab

		double closestX = Math.max(s1Min.x, Math.min(center.x, s1Max.x));
		double closestY = Math.max(s1Min.y, Math.min(center.y, s1Max.y));

		double deltaX = center.x - closestX;
		double deltaY = center.y - closestY;

		// from center of circle to closest point of AAB
		double distance = deltaX * deltaX + deltaY * deltaY;

		return distance <= s2.getRadius() * s2.getRadius();
	}

	@Override
	public boolean isColliding(AABShape s1, Vec2d s2) {
		Vec2d s1Min = s1.topLeft;
		Vec2d s1Max = s1.topLeft.plus(s1.size);

		return s2.x >= s1Min.x &&
			s2.x <= s1Max.x &&
			s2.y >= s1Min.y &&
			s2.y <= s1Max.y;
	}

	// CIRCLES
	
	@Override
	public boolean isColliding(CircleShape s1, AABShape s2) {
		Vec2d s2Min = s2.topLeft;
		Vec2d s2Max = s2.topLeft.plus(s2.size);

		Vec2d center = s1.center;

		// center of circle is inside aab
		if (center.x >= s2Min.x &&
			center.x <= s2Max.x &&
			center.y >= s2Min.y &&
			center.y <= s2Max.y) {
			return true;
		}

		// center of circle is outside aab

		double closestX = Math.max(s2Min.x, Math.min(center.x, s2Max.x));
		double closestY = Math.max(s2Min.y, Math.min(center.y, s2Max.y));

		double deltaX = center.x - closestX;
		double deltaY = center.y - closestY;

		// from center of circle to closest point of AAB
		double distance = deltaX * deltaX + deltaY * deltaY;

		return distance <= s1.radius * s1.radius;
	}

	@Override
	public boolean isColliding(CircleShape s1, CircleShape s2) {
		// from center of this circle to that of the other
		double distance =
			(s1.center.x - s2.center.x) * (s1.center.x - s2.center.x) +
				(s1.center.y - s2.center.y) * (s1.center.y - s2.center.y);

		// radii sum of 2 circles
		double radii = (s1.radius + s2.radius) * (s1.radius + s2.radius);

		return distance <= radii;
	}

	@Override
	public boolean isColliding(CircleShape s1, Vec2d s2) {
		double distance =
			(s1.center.x - s2.x) * (s1.center.x - s2.x) +
				(s1.center.y - s2.y) * (s1.center.y - s2.y);

		return distance <= s1.radius;
	}

}
