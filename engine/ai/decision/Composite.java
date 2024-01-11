package engine.ai.decision;

import java.util.ArrayList;
import java.util.List;

public class Composite implements BehaviorTreeNode {

	protected List<BehaviorTreeNode> children;
	protected int lastRunningIndex = 0;

	public Composite() {
		children = new ArrayList<>();
	}

	@Override
	public Status update(long nanosSinceLastTick) {
		return null;
	}

	@Override
	public void reset() {

	}

	public void addChild(BehaviorTreeNode behaviorTreeNode) {
		children.add(behaviorTreeNode);
	}
}
