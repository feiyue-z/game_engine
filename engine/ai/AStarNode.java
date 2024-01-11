package engine.ai;

import java.util.Objects;

public class AStarNode {
	public final int x;
	public final int y;

	public AStarNode parent;
	public double g = Double.MAX_VALUE; // cost to move from start to this node
	public double h = 0; // heuristic, or estimated cost to move from this node to goal

	public AStarNode(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public double getF() {
		return g + h;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		AStarNode node = (AStarNode) obj;
		return x == node.x && y == node.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
