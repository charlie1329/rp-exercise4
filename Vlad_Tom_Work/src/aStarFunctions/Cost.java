package aStarFunctions;

import lists_and_maybe.Function;
import graphs.*;

/**class combines both functions for a* to give the total cost for a given node
 * 
 * @author charlie street
 *
 */
public class Cost implements Function<Node<Coord>, Integer> {

	private ManhattanDistance manhattan;
	private DistanceSoFar distance;
	
	/**constructor sets up two components of function
	 * 
	 * @param graph the graph being used 
	 * @param start the start node
	 * @param goal the goal node
	 */
	public Cost(Graph graph, Node<Coord> start, Node<Coord> goal) 
	{
		this.manhattan = new ManhattanDistance(goal);
		this.distance = new DistanceSoFar(graph, start);
	
	}

	/**returns the combined value of the distance travelled and the manhattan distance
	 * this gives the priority value for the node in question
	 */
	public Integer apply(Node<Coord> a) {
		return this.manhattan.apply(a) + this.distance.apply(a);
	}

}
