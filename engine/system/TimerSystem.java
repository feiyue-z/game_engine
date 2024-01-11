package engine.system;

import engine.GameObject;

public class TimerSystem extends System {

	public TimerSystem() {

	}

	@Override
	public void onTick(long nanosSinceLastTick) {
		for (GameObject each : gameObjectSet) {
			each.onTick(nanosSinceLastTick);
		}
	}
}
