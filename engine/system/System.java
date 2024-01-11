package engine.system;

import engine.GameObject;
import javafx.scene.canvas.GraphicsContext;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class System {
	protected Set<GameObject> gameObjectSet;

	public System() {
//		this.gameObjectSet = new HashSet<>();
		this.gameObjectSet = new ConcurrentHashMap<Integer, GameObject>().newKeySet();
	}

	public Set<GameObject> getGameObjectSet() {
		return gameObjectSet;
	}

	public void onTick(long nanosSinceLastTick) {

	}

	public void onDraw(GraphicsContext g) {

	}
}
