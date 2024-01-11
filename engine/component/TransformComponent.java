package engine.component;

import engine.support.Vec2d;

public class TransformComponent {
	private Vec2d pos; // top-left position
	private Vec2d size;

	public TransformComponent(Vec2d position, Vec2d size) {
		this.pos = position;
		this.size = size;
	}

	public Vec2d getPos() {
		return pos;
	}

	public Vec2d getSize() {
		return size;
	}

	public void setPos(Vec2d pos) {
		this.pos = pos;
	}

	public void onResize(Vec2d resizeFactor) {
		pos = pos.pmult(resizeFactor);
		size = size.pmult(resizeFactor);
	}

	public void updatePos(Vec2d delta) {
		pos = pos.plus(delta);
	}
}
