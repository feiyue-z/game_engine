package engine.ai.decision;

interface BehaviorTreeNode {
	Status update(long nanosSinceLastTick);
	void reset();
}
