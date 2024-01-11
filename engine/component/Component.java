package engine.component;

import engine.GameObject;
import javafx.scene.canvas.GraphicsContext;

public class Component {

	protected GameObject gameObject;
	protected String tag;

	public Component(String tag, GameObject gameObject) {
		this.tag = tag;
		this.gameObject = gameObject;
	}

	public void onTick(long nanosSinceLastTick) {

	}

	public void onLateTick() {

	}
	public void onDraw(GraphicsContext g) {

	}

	public String getTag() {
		return tag;
	}

	public GameObject getGameObject() {
		return gameObject;
	}
}
