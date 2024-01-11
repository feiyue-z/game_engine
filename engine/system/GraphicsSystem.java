package engine.system;

import engine.GameObject;
import javafx.scene.canvas.GraphicsContext;

import java.util.Comparator;
import java.util.TreeSet;

public class GraphicsSystem extends System {

	public GraphicsSystem() {
		// sort by priority in descending order
		// e.g. objects with priority 0 will be drawn on the very top layer
		this.gameObjectSet = new TreeSet<>(new Comparator<GameObject>() {
			@Override
			public int compare(GameObject o1, GameObject o2) {
				if (o1.getPriority() == o2.getPriority()) {
					return -1;
				}
				return o2.getPriority() - o1.getPriority();
			}
		});
	}

	@Override
	public void onDraw(GraphicsContext g) {
		for (GameObject each : gameObjectSet) {
			each.onDraw(g);
		}
	}
}
