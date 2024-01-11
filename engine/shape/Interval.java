package engine.shape;

import debugger.support.Vec2d;

public final class Interval {

	public double min;
	public double max;

	public Interval(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public boolean overlaps(Interval other) {
		return this.min <= other.max && this.max >= other.min;
	}

	public Vec2d getOverlap(Interval other) {
		if (!this.overlaps(other)) {
			return null;
		}

		return new Vec2d(
			Math.max(this.min, other.min),
			Math.min(this.max, other.max)
		);
	}
}
