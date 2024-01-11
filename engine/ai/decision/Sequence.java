package engine.ai.decision;

public class Sequence extends Composite {

	@Override
	public Status update(long nanosSinceLastTick) {
		int i = lastRunningIndex;

		for (; i < children.size(); i++) {
			Status status = children.get(i).update(nanosSinceLastTick);

			if (status == Status.FAIL || status == Status.RUNNING) {
				lastRunningIndex = i;
				return status;
			}
		}

		lastRunningIndex = 0;
		return Status.SUCCESS;
	}

	@Override
	public void reset() {

	}
}
