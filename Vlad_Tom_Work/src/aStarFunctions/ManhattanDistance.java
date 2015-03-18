package aStarFunctions;

import lists_and_maybe.Function;
import graphs.*;

/** a function which will return the Manhattan distance to the goal node
 * http://en.wiktionary.org/wiki/Manhattan_distance
 * Manhattan distance is a better heuristic function for grid based search
 * as is the case with the robot programming example. This is because it is still admissible
 * however it gives a larger value than SLD and larger values without over-estimating are better heuristics 
 * @author Charlie Street
 *
 */
public class ManhattanDistance implements Function<Node<Coord>, Integer>
{
	private Node<Coord> goalNode;
	
	/**constructor initialises attribute
	 * 
	 * @param goal the goal node in the search
	 */
	public ManhattanDistance(Node<Coord> goal) 
	{
		this.goalNode = goal;
	}

	/** returns the Manhattan distance to the goal node to the current node
	 * @param currentNode the current Node you are at
	 * @return the Manhattan distance from that node
	 */
	public Integer apply(Node<Coord> currentNode) 
	{
		Coord currentCoord = currentNode.getContent();
		Coord goalCoord = goalNode.getContent();
		int manhattanValue = Math.abs(currentCoord.getX() - goalCoord.getX()) + Math.abs(currentCoord.getY() - goalCoord.getY());
		return manhattanValue;
	}

}
