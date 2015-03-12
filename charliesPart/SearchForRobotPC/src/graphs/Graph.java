package graphs;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import aStarFunctions.Cost;
import rp.*;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;
import lists_and_maybe.*;
import data_structures.*;

/**class represents a graph and all operations that can be carried out on it
 * i feel it is cleaner to put these methods in this class so the nodes can be created within it
 * this graph has been modified from my solution to the software workshop exercise
 * the modifications are:
 * -removing the generalisation as the graph should only work in a coordinate in this case
 * -changing the used data structures to allow functionality within lejos
 * -added conversion from grid map to my graph set up
 * @author Charlie Street
 *
 */
public class Graph {
	
	private Map<Coord,Node<Coord>> nodes;
	private Map<Node<Coord>,Maybe<Node<Coord>>> links;
	private IGridMap grid;
	
	
	/**constructor initialises Map of nodes in graph
	 * 
	 */
	public Graph(IGridMap grid) 
	{
		this.nodes = new HashMap<Coord,Node<Coord>>();
		this.links = new HashMap<Node<Coord>,Maybe<Node<Coord>>>();
		this.grid = grid;
		this.constructGraphFromGrid();//constructs the graph
	}
	
	/**this method will take the grid map object supplied to the graph and convert it into a graph of nodes and successors
	 * 
	 */
	private void constructGraphFromGrid()
	{
		int xDistance = grid.getXSize();
		int yDistance = grid.getYSize();
		
		for(int x = 0; x < xDistance; x++)
		{
			for(int y = 0; y < yDistance; y++)
			{
				Coord gridPos = new Coord(x,y);
				Node<Coord> node = this.nodeWith(gridPos);
				
				for (int i = -1; i <= 1; i++)
				{
					for (int j = -1; j <=1; j++)
					{
						if(Math.abs(i) + Math.abs(j) == 1)//only want up, down, left and right
						{
							int potentialX = x+i;
							int potentialY = y+j;
							if(this.grid.isValidTransition(x,y,potentialX,potentialY))//checking for successors
							{
								Coord successorCoord = new Coord(potentialX, potentialY);
								Node<Coord> newNode = this.nodeWith(successorCoord);
								node.addSuccessor(newNode);//adding this new item as a successor of the node being checked
							}
						}
					}
				}
			}
		}
		
	}
	
	/**returns the nodes in the graph
	 * 
	 * @return all of the nodes
	 */
	public Map<Coord,Node<Coord>> getNodes()
	{
		return this.nodes;
	}
	
	/**method needed for writing functions for Coord* search
	 * 
	 * @return the links map
	 */
	public Map<Node<Coord>,Maybe<Node<Coord>>> getLinks()
	{
		return this.links;
	}
	
	/**method searches for a node, if it doesn't exist it is then created
	 * 
	 * @param a the content of the node
	 * @return the node
	 */
	public Node<Coord> nodeWith(Coord a)
	{
		Node<Coord> node;
		if(this.nodes.containsKey(a))
		{
			node =  nodes.get(a);
		}
		else
		{
			node = new Node<Coord>(a);
			nodes.put(a, node);
		}
		return node;
		
	}
	
	
	/**this method finds a node from x which satisfies a predicate p; from maybe can be used as nothing will never be reached
	 * 
	 * @param x the node to start from
	 * @param p the predicate
	 * @param ds the data structure being used
	 * @return the first value satisfying the predicate
	 */
	public Maybe<Node<Coord>> findNodeFrom(Node<Coord> x, Predicate<Coord> p, DataStructure<Node<Coord>> ds)
	{
		Collection<Node<Coord>> set = new SimpleSet<Node<Coord>>();//using for efficiency
		ds.insertItem(x);
		while(!ds.isEmpty())//this gives us precautions to allow use of some of the'unsafe' methods as they will never be used in such a scenario
		{
			if(set.contains(ds.getFront().fromMaybe()))//fromMaybe can be used as Nothing will never enter this loop
			{
				ds.removeFront();
			}
			else if(p.holds(ds.getFront().fromMaybe().getContent()))
			{
				return ds.getFront();
			}
			else
			{
				Node<Coord> toExpand = ds.getFront().fromMaybe();
				set.add(toExpand);
				ds.removeFront();
				ds.insertList(toExpand.getSuccessorsList());
			}
		}
		return new Nothing<Node<Coord>>();
	}
	
	/**method for findPathFrom reconstructs the path. Made public as I  will need it for various heuristic functions in a* search later on
	 * in the main method when I carry out a* search
	 * @param start the start point
	 * @param goal the goal node
	 * @return the reconstructed path from the start to the node
	 */
	public Maybe<IList<Node<Coord>>> reconstruct(Node<Coord> start, Node<Coord> goal)
	{
		Node<Coord> n = goal;
		IList<Node<Coord>> path = new Nil<Node<Coord>>();
		while(!n.getContent().equals(start.getContent()))
		{
			path = path.append(n);
			n = links.get(n).fromMaybe();
		}
		
		path = path.append(start);//execution of while loop will stop when it reaches the start node so this should be added independently	
		path = path.reverse();//simpler than using a stack given I am using IList a lot
		return new Just<IList<Node<Coord>>>(path);//returning the path in the right form for findPathFrom
	}
	
	/**method finds a node and then reconstructs the path from the origin to it
	 * 
	 * @param x the starting node
	 * @param p the predicate for finding the node
	 * @param ds the data structure (search strategy) being employed
	 * @return
	 */
	public Maybe<IList<Node<Coord>>> findPathFrom(Node<Coord> x, Predicate<Coord> p, DataStructure<Node<Coord>> ds)
	{
	
		Collection<Node<Coord>> set = new SimpleSet<Node<Coord>>();//using for efficiency
		this.links = new HashMap<Node<Coord>,Maybe<Node<Coord>>>();//maybe used to stop null pointers
		ds.insertItem(x);
		while(!ds.isEmpty())//this gives us precautions to allow use of some of the'unsafe' methods as they will never be used in such a scenario
		{
			if(set.contains(ds.getFront().fromMaybe()))//fromMaybe can be used as Nothing will never enter this loop
			{
				ds.removeFront();
			}
			else if(p.holds(ds.getFront().fromMaybe().getContent()))
			{
				Node<Coord> goal = ds.getFront().fromMaybe();
				links.put(goal, goal.getParent());
				return reconstruct(x, goal);
			}
			else
			{
				Node<Coord> toExpand = ds.getFront().fromMaybe();
				set.add(toExpand);
				ds.removeFront();
				for(Node<Coord> n : toExpand.getSuccessors())
				{
					//the shouldIKeepParent is used to deal with scenarios of identical items being added to the 
					//data structure and changing parents when they shouldn't be changed.
					//it essentially checks if there is a duplicate item and then returns a boolean stating if
					//the new duplicate should have it's parent changed or not (as it might not ever get expanded)
					//this has different behaviours for stacks and queues and so had to be placed in their respective classes
					//this will also work with priority queues as a new duplicate will be placed after the first
					if(!set.contains(n) && !ds.shouldIKeepParent(n))
					{
						n.setParent(new Just<Node<Coord>>(toExpand));
					}
				}
				links.put(toExpand, toExpand.getParent());
				ds.insertList(toExpand.getSuccessorsList());
			}
		}
		return new Nothing<IList<Node<Coord>>>();
		
	}
	
	
	/**main method creates graph for example given by Nick Hawes and then runs tests on it
	 * in order to check it is producing correct results
	 */
	/*public static void main (String[] args)
	{
		
	RPLineMap lineMap = MapUtils.create2014Map2();
	IGridMap grid = GridMapViewer.createGridMap(lineMap, 10, 7, 14, 31, 30);
	Graph testGraph = new Graph(grid);
		
		Node<Coord> start = testGraph.nodeWith(new Coord(0,0));
		Node<Coord> goal = testGraph.nodeWith(new Coord(9,4));
		GoalPredicate zeroTwo = new GoalPredicate(new Coord(9,4));
		Cost costFunction = new Cost(testGraph,start,goal);
		PriorityQueue<Node<Coord>,Integer> testPri = new PriorityQueue<Node<Coord>,Integer>(costFunction);
		System.out.println(testGraph.findPathFrom(start, zeroTwo, testPri));
		
		
	}*/

}
