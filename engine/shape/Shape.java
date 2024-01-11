package engine.shape;

import engine.ray.Ray;
import engine.support.Vec2d;

public interface Shape {
	void updatePos(Vec2d delta);

	boolean isStatic();

	float intersects(Ray r);
	Vec2d collides(Shape o);
	Vec2d collidesCircle(Circle c);
	Vec2d collidesAAB(AAB aab);
	Vec2d collidesPolygon(Polygon p);
	Vec2d getCenter();

	Interval projectOntoAxis(Vec2d axis);
}
