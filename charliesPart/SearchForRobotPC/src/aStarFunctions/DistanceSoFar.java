package aStarFunctions;

import lists_and_maybe.Function;
import graphs.*;

/**this class acts as the cost function, that is the distance so far
 * 
 * @author charlie street 
 *
 */
public class DistanceSoFar implements Function<Node<Coord>,Integer> 
{

	private Graph<Coord> graph;
	private Node<Coord> startNode;
	
	/** constructor initialises the attributes
	 * 
	 * @param graph the graph being used
	 * @param startNode the node started from in the search
	 */
	public DistanceSoFar(Graph<Coord> graph, Node<Coord> startNode) 
	{
		this.graph = graph;
		this.startNode = startNode;
	}

	/**this method calculates the distance to a given node from the start 
	 * it does this via recalculating the root and getting the size of the path
	 * the reason the size can be used is because although there will be 1 more node than links (what we are counting)
	 * it starts from the parent of the current node as the current node will not yet be in the map
	 * therefore the +1 and -1 cancel out, meaning that the size can just be taken
	 */
	public Integer apply(Node<Coord> currentNode) {
		if (startNode.getContent().equals(currentNode.getContent()))
		{
			return 0;//if start node
		}
		else
		{
			return this.graph.reconstruct(startNode,currentNode.getParent().fromMaybe()).fromMaybe().size();
		}
	}

}
