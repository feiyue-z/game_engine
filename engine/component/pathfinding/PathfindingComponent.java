package engine.component.pathfinding;

import engine.GameObject;
import engine.ai.AStarNode;
import engine.ai.AStarSearch;
import engine.component.Component;

import java.util.ArrayList;
import java.util.List;

public class PathfindingComponent extends Component {

	private List<AStarNode> path;
	private TileType[][] map;
	private int curr = 0; // current index in path

	public PathfindingComponent(GameObject gameObject, TileType[][] map) {
		super("pathfinding", gameObject);

		this.path = new ArrayList<>();
		this.map = map;
	}

//	@Override
//	public void onTick(long nanosSinceLastTick) {
//
//	}

	public MovementType getNextMovement() {
		// already reached goal
		if (path == null || path.size() == 0 || curr == path.size() - 1) {
			return null;
		}

		int[] currPos = {path.get(curr).x, path.get(curr).y};
		int[] nextPos = {path.get(curr + 1).x, path.get(curr + 1).y};

		MovementType ret;

		if (nextPos[0] - currPos[0] == 1) {
			ret = MovementType.DOWN;
		}  else if (nextPos[0] - currPos[0] == -1) {
			ret = MovementType.UP;
		} else if (nextPos[1] - currPos[1] == 1) {
			ret = MovementType.RIGHT;
		} else if (nextPos[1] - currPos[1] == -1) {
			ret = MovementType.LEFT;
		} else {
			 return null;
		}

		curr++;
		return ret;
	}

	public void generatePath(int[] start, int[] goal) {
		// debugf
//		System.out.println("start = " + start[0] + ", " + start[1]);
//		System.out.println("goal = " + goal[0] + ", " + goal[1]);

		AStarNode startNode = new AStarNode(start[0], start[1]);
		AStarNode goalNode = new AStarNode(goal[0], goal[1]);

		path = AStarSearch.mapSearch(startNode, goalNode, map);

		curr = 0;

		// debugf
//		if (path == null) {
//			return;
//		}
//
//		// debugf
//		for (AStarNode each : path) {
//			System.out.println("node = " + each.x + ", " + each.y);
//		}
	}
}
