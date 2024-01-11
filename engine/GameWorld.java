package engine;

import engine.support.Vec2d;
import engine.system.CollisionSystem;
import engine.system.GraphicsSystem;
import engine.system.TimerSystem;
import engine.ui.Screen;
import engine.ui.Viewport;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.*;

public class GameWorld {
	private static int TRANSLATION_OFFSET = 50;
	private static int SCALE_DENOMINATOR = 1000;

	protected Viewport viewport;
	protected Set<GameObject> allGameObjectSet;
	protected Map<String, GameObject> gameObjectMap;
	protected GraphicsSystem graphicsSystem;
	protected CollisionSystem collisionSystem;
	protected TimerSystem timerSystem;

	private boolean zoomEnabled = false;
	private double zoomFactor = 1;
	private double xShift = 0, yShift = 0;

	public GameWorld(Screen screen, Vec2d viewportPos, Vec2d viewportSize) {
		this.viewport = new Viewport(
			this,
			screen,
			viewportPos,
			viewportSize
		);

		this.allGameObjectSet = new HashSet<>();

		this.graphicsSystem = new GraphicsSystem();
		this.collisionSystem = new CollisionSystem();
		this.timerSystem = new TimerSystem();

		this.gameObjectMap = new HashMap<>();
	}

	public void addGameObject(GameObject object) {
		allGameObjectSet.add(object);
		gameObjectMap.put(object.getTag(), object);
	}

	public void removeGameObject(GameObject object) {
//		allGameObjectSet.remove(object);
//
//		if (graphicsSystem.getGameObjectSet().contains(object)) {
//			graphicsSystem.getGameObjectSet().remove(object);
//		}
//
//		if (collisionSystem.getGameObjectSet().contains(object)) {
//			collisionSystem.getGameObjectSet().remove(object);
//		}

		// remove in allGameObjectSet
		Iterator<GameObject> iterator = allGameObjectSet.iterator();
		while (iterator.hasNext()) {
			if (iterator.next() == object) {
				iterator.remove();
				break;
			}
		}

		// remove in gameObjectSet of graphicsSystem
		iterator = graphicsSystem.getGameObjectSet().iterator();
		while (iterator.hasNext()) {
			if (iterator.next() == object) {
				iterator.remove();
				break;
			}
		}

		// remove in gameObjectSet of timerSystem
		iterator = timerSystem.getGameObjectSet().iterator();
		while (iterator.hasNext()) {
			if (iterator.next() == object) {
				iterator.remove();
				break;
			}
		}

		// remove in gameObjectSet of collisionSystem
		iterator = collisionSystem.getGameObjectSet().iterator();
		while (iterator.hasNext()) {
			if (iterator.next() == object) {
				iterator.remove();
				break;
			}
		}

		if (gameObjectMap.containsKey(object.getTag())) {
			gameObjectMap.remove(object.getTag());
		}
	}

	public void addGraphicsGameObject(GameObject object) {
		graphicsSystem.getGameObjectSet().add(object);
	}

	public void addTimerGameObject(GameObject object) {
		timerSystem.getGameObjectSet().add(object);
	}

	public void addCollisionGameObject(GameObject object) {
		collisionSystem.getGameObjectSet().add(object);
		collisionSystem.buildArray();
	}

	public void onTick(long nanosSinceLastTick) {
		timerSystem.onTick(nanosSinceLastTick);
		collisionSystem.onTick(nanosSinceLastTick);
	}

	public void onDraw(GraphicsContext g) {
		graphicsSystem.onDraw(g);
	}

	public void onKeyPressed(KeyEvent e) {
		switch (e.getCode()) {
			case UP:
				yShift += TRANSLATION_OFFSET;
				break;
			case DOWN:
				yShift -= TRANSLATION_OFFSET;
				break;
			case LEFT:
				xShift += TRANSLATION_OFFSET;
				break;
			case RIGHT:
				xShift -= TRANSLATION_OFFSET;
				break;
		}
	}

	public void onMouseClicked(MouseEvent e) {
		double x = e.getX(), y = e.getY();

		for (GameObject each : allGameObjectSet) {
			Vec2d pos = each.getTransformComponent().getPos();
			Vec2d size = each.getTransformComponent().getSize();

			if (x >= pos.x &&
				x <= pos.x + size.x &&
				y >= pos.y &&
				y <= pos.y + size.y
			) {
				each.onMouseClicked(e);
			}
		}
	}

	public void onMousePressed(MouseEvent e) {
		double x = e.getX(), y = e.getY();

		for (GameObject each : allGameObjectSet) {
			Vec2d pos = each.getTransformComponent().getPos();
			Vec2d size = each.getTransformComponent().getSize();

			if (x >= pos.x &&
				x <= pos.x + size.x &&
				y >= pos.y &&
				y <= pos.y + size.y
			) {
				each.onMousePressed(e);
			}
		}
	}

	public void onMouseReleased(MouseEvent e) {
		double x = e.getX(), y = e.getY();

		for (GameObject each : allGameObjectSet) {
			Vec2d pos = each.getTransformComponent().getPos();
			Vec2d size = each.getTransformComponent().getSize();

			if (x >= pos.x &&
				x <= pos.x + size.x &&
				y >= pos.y &&
				y <= pos.y + size.y &&
				each.isPressed()
			) {
				each.onMouseReleased(e);
			}
		}
	}

	public void onMouseWheelMoved(ScrollEvent e) {
		if (!zoomEnabled) {
			return;
		}

		zoomFactor = 1 + e.getDeltaY() / SCALE_DENOMINATOR;

		for (GameObject each : allGameObjectSet) {
			each.onResize(zoomFactor);
		}
	}

	public void onMouseDragged(MouseEvent e) {
		double x = e.getX(), y = e.getY();

		for (GameObject each : allGameObjectSet) {
			if (each.isPressed()) {
				each.onMouseDragged(e);
			}
		}
	}

	public double getXShift() {
		return xShift;
	}

	public double getYShift() {
		return yShift;
	}

	public double getZoomFactor() {
		return zoomFactor;
	}

	public Set<GameObject> getGraphicsGameObjectSet() {
		return graphicsSystem.getGameObjectSet();
	}
}
