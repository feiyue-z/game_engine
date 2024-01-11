package engine.ray;


import engine.shape.AAB;
import engine.shape.Circle;
import engine.shape.Polygon;
import engine.shape.Shape;
import engine.support.Vec2d;

public class Ray {
	public Vec2d p;
	public Vec2d d;

	public Ray(Vec2d p, Vec2d d) {
		this.p = p;
		this.d = d;
	}

	public float intersects(Shape o) {
		return o.intersects(this);
	}

	public float intersects(Circle c) {
		// p to circle center
		Vec2d p2c = c.getCenter().minus(p);

		// length of projection
		double proj = p2c.dot(d);

		// closest point on ray
		Vec2d closest = p.plus(d.smult(proj));

		// circle center to ray
		Vec2d c2ray = closest.minus(c.getCenter());

		// distance from circle center to ray
		double x= c2ray.mag();

		// no collision
		if (x > c.getRadius()) {
			return -1.f;
		}

		// distance between p and circle center
		double distance = (p.x - c.getCenter().x) * (p.x - c.getCenter().x) +
			(p.y - c.getCenter().y) * (p.y - c.getCenter().y);

		// determine if p is inside circle
		double t = distance > c.getRadius() * c.getRadius() ?
			proj - Math.sqrt(c.getRadius() * c.getRadius() - x * x) :
			proj + Math.sqrt(c.getRadius() * c.getRadius() - x * x);
		return (float) t;
	}

	public float intersects(AAB aab) {
		Vec2d min = aab.getMin();
		Vec2d max = aab.getMax();

		Polygon polygon = new Polygon(
			true,
			min,
			min.plus(0, (float) max.y),
			max,
			min.plus((float) max.x, 0)
		);
		return intersects(polygon);
	}

	public float intersects(Polygon p) {
		int size = p.getNumPoints();
		float min = Float.MAX_VALUE;

		for (int i = 0 ; i < size; i++) {
			float t = intersects(p.getPoint(i), p.getPoint((i + 1) % size));
			if (t != -1) {
				min = Math.min(t, min);
			}
		}

		return min == Float.MAX_VALUE ? -1 : min;
	}

	public float intersects(Vec2d a, Vec2d b) {
		double prod1 = a.minus(p).cross(d);
		double prod2 = b.minus(p).cross(d);

		if (prod1 * prod2 > 0) {
			return -1;
		}

		Vec2d n = b.minus(a).perpendicular();
		double t = b.minus(p).dot(n) / d.dot(n);

		if (t < 0) {
			return -1;
		}
		return (float) t;
	}
}
