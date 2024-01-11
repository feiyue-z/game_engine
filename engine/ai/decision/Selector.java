package engine.ai.decision;

public class Selector extends Composite {

	@Override
	public Status update(long nanosSinceLastTick) {
		for (int i = 0; i < children.size(); i++) {
			Status status = children.get(i).update(nanosSinceLastTick);

			if (status == Status.SUCCESS || status == Status.RUNNING) {
				return status;
			}
		}

		return Status.FAIL;
	}

	@Override
	public void reset() {

	}
}
