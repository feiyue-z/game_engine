package engine.ai;

import engine.component.pathfinding.TileType;

import java.util.*;

public class AStarSearch {
	private static final int[][] DIRECTIONS = {{-1,0}, {1, 0}, {0,-1}, {0,1}};

	public static List<AStarNode> mapSearch(AStarNode start, AStarNode goal, TileType[][] map) {
		Set<AStarNode> openSet = new HashSet<>();
		Set<AStarNode> closedSet = new HashSet<>();

		start.g = 0;
		start.h = getHeuristic(start, goal);

		openSet.add(start);

		while (!openSet.isEmpty()) {
			AStarNode curr = getLowestF(openSet);

			if (curr.equals(goal)) {
				return getShortestPath(curr);
			}

			openSet.remove(curr);
			closedSet.add(curr);

			for (int[] dir : DIRECTIONS) {
				AStarNode next = new AStarNode(curr.x + dir[0], curr.y + dir[1]);

				// out of bounds
				if (next.x < 0 || next.y < 0 || next.x == map.length || next.y == map[0].length) {
					continue;
				}

				if (closedSet.contains(next) || map[next.x][next.y] != TileType.FLOOR) {
					continue;
				}

				if (curr.g + 1 >= next.g) {
					continue;
				}

				next.parent = curr;
				next.g = curr.g + 1;
				next.h = getHeuristic(next, goal);

				openSet.add(next);
			}
		}

		return null;
	}

	private static double getHeuristic(AStarNode a, AStarNode b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}

	private static AStarNode getLowestF(Set<AStarNode> set) {
		AStarNode ret = null;

		for (AStarNode each : set) {
			if (ret == null || each.getF() < ret.getF()) {
				ret = each;
			}
		}
		return ret;
	}

	private static List<AStarNode> getShortestPath(AStarNode curr) {
		List<AStarNode> path = new ArrayList<>();

		while (curr != null) {
			path.add(curr);
			curr = curr.parent;
		}

		Collections.reverse(path);
		return path;
	}
}

