package engine.component;

import engine.GameObject;
import engine.ai.decision.Composite;
import engine.ai.decision.Selector;
import engine.support.Vec2d;

public class DecisionComponent extends Component {

	private Selector root;

	public DecisionComponent(GameObject gameObject, Selector root) {
		super("decision", gameObject);

		this.root = root;
	}

	@Override
	public void onTick(long nanosSinceLastTick) {
		root.update(nanosSinceLastTick);
	}
}
