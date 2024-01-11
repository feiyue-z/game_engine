package engine.component;

import engine.GameObject;

public class HealthComponent extends Component {

	private int totalHealthPoints;
	private int currHealthPoints;

	public HealthComponent(GameObject gameObject, int totalHealthPoints) {
		super("health", gameObject);

		this.totalHealthPoints = totalHealthPoints;
		this.currHealthPoints = totalHealthPoints;
	}

	public HealthComponent(GameObject gameObject, int totalHealthPoints, int currHealthPoints) {
		super("health", gameObject);

		this.totalHealthPoints = totalHealthPoints;
		this.currHealthPoints = currHealthPoints;
	}

//	@Override
//	public void onTick(long nanosSinceLastTick) {
//
//	}

	public int getTotalHealthPoints() {
		return totalHealthPoints;
	}

	public int getCurrHealthPoints() {
		return currHealthPoints;
	}

	public void damage(int point) {
		currHealthPoints -= point;

		if (currHealthPoints == 0) {
			gameObject.onHpBelowZero();
		}
	}
}
