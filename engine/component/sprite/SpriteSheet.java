package engine.component.sprite;

import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteSheet {

	private List<Image> spriteList;
	private boolean isLoaded = false;
	private Map<Integer, Pair<Integer, Integer>> stateMap; // interval of sprites to display in particular state

	public SpriteSheet() {
		this.spriteList = new ArrayList<>();
		this.stateMap = new HashMap<>();
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public void setLoaded(boolean loaded) {
		isLoaded = loaded;

		if (isLoaded) {
			specifyState(0, 0, spriteList.size() - 1);
		}
	}

	public void loadSprite(String path) {
		spriteList.add(new Image(path));
	}

	public Image getSprite(int index) {
		return spriteList.get(index);
	}

	public int getSize() {
		return spriteList.size();
	}

	public void specifyState(int state, int start, int end) {
		stateMap.put(state, new Pair<>(start, end));
	}

	public Pair<Integer, Integer> getStateValue(int state) {
		return stateMap.get(state);
	}
}
