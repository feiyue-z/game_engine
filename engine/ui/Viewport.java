package engine.ui;

import engine.GameWorld;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

public class Viewport extends UIElement {

	private Vec2d initPos;
	private GameWorld gameWorld;
	private Affine affine;

	public Viewport(GameWorld gameWorld, Screen screen, Vec2d pos, Vec2d size) {
		super(screen, pos, size);

		this.initPos = pos;
		this.gameWorld = gameWorld;
		this.affine = new Affine();
	}

	public void updatePos(Vec2d delta) {
		pos = pos.plus(delta);
	}

	public void resetPos() {
		pos = initPos;
	}

	@Override
	protected void onDraw(GraphicsContext g) {
		g.save();

		// reset affine
		affine.setToIdentity();

		// position
		affine.appendTranslation(-pos.x, -pos.y);

		// panning
		affine.appendTranslation(gameWorld.getXShift(), gameWorld.getYShift());

		// zooming
//		affine.prependTranslation(-size.x / 2, -size.y / 2);
		affine.appendScale(gameWorld.getZoomFactor(), gameWorld.getZoomFactor());
//		affine.prependTranslation(size.x / 2, size.y / 2);

		// draw
		g.setTransform(affine);
		gameWorld.onDraw(g);

		g.restore();
	}
}
