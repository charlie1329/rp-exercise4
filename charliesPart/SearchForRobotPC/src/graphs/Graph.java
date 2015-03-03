package graphs;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import rp.*;
import lists_and_maybe.*;
import data_structures.*;
import aStarFunctions.*;

/**class represents a graph and all operations that can be carried out on it
 * i feel it is cleaner to put these methods in this class so the nodes can be created within it
 * @author Charlie Street
 *
 */
public class Graph {
	
	private Map<Coord,Node<Coord>> nodes;
	private Map<Node<Coord>,Maybe<Node<Coord>>> links;
	private GridMap grid;
	
	
	/**constructor initialises Map of nodes in graph
	 * 
	 */
	public Graph(GridMap grid) 
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
		
		for(int x = 0; x < grid.getXSize(); x++)
		{
			for(int y = 0; y < grid.getYSize(); y++)
			{
				Coord gridPos = new Coord(x,y);
				Node<Coord> node = this.nodeWith(a);
				
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
				System.out.println(ds);
			}
		}
		return new Nothing<IList<Node<Coord>>>();
		
	}
	
	
	/**main method creates graph for example given by Nick Hawes and then runs tests on it
	 * in order to check it is producing correct results
	 */
	public static void main (String[] args)
	{
		/*Graph<Coord> robotGraph = new Graph<Coord>();
		int [] [] nicksGraph = {
				{0,0,1,0,0,1}, 
				{0,1,0,0,1,1,0,2}, 
				{0,2,0,3,0,1}, 
				{0,3,0,2,0,4}, 
				{0,4,0,3,0,5}, 
				{0,5,0,6,1,5,0,4}, 
				{0,6,1,6,0,5}, 
				{1,0,0,0,1,1,2,0}, 
				{1,1,1,2,2,1,1,0,0,1}, 
				{1,2,2,2,1,1,1,3}, 
				{1,3,1,2,1,4,2,3}, 
				{1,4,2,4,1,5,1,3}, 
				{1,5,1,4,2,5,1,6,0,5}, 
				{1,6,0,6,1,5,2,6}, 
				{2,0,3,0,2,1,1,0}, 
				{2,1,2,2,1,1,2,0,3,1}, 
				{2,2,1,2,2,1,2,3,3,2}, 
				{2,3,2,2,2,4,3,3,1,3}, 
				{2,4,1,4,2,5,2,3,3,4}, 
				{2,5,2,4,1,5,2,6,3,5}, 
				{2,6,3,6,2,5,1,6}, 
				{3,0,2,0,3,1}, 
				{3,1,3,0,4,1,2,1,3,2}, 
				{3,2,2,2,4,2,3,1}, 
				{3,3,2,3,3,4}, 
				{3,4,2,4,3,3}, 
				{3,5,3,6,2,5,4,5}, 
				{3,6,2,6,3,5}, 
				{4,0}, 
				{4,1,4,2,5,1,3,1}, 
				{4,2,4,1,5,2,3,2}, 
				{4,3}, 
				{4,4}, 
				{4,5,5,5,3,5}, 
				{4,6}, 
				{5,0}, 
				{5,1,4,1,5,2,6,1}, 
				{5,2,4,2,5,1,6,2}, 
				{5,3}, 
				{5,4}, 
				{5,5,4,5,6,5}, 
				{5,6}, 
				{6,0,7,0,6,1}, 
				{6,1,6,0,5,1,6,2,7,1}, 
				{6,2,5,2,6,1,7,2}, 
				{6,3,7,3,6,4}, 
				{6,4,6,3,7,4}, 
				{6,5,5,5,6,6,7,5}, 
				{6,6,7,6,6,5}, 
				{7,0,6,0,7,1,8,0}, 
				{7,1,8,1,7,0,6,1,7,2}, 
				{7,2,7,3,8,2,6,2,7,1}, 
				{7,3,6,3,7,2,7,4,8,3}, 
				{7,4,7,3,8,4,6,4,7,5}, 
				{7,5,8,5,7,6,7,4,6,5}, 
				{7,6,6,6,7,5,8,6}, 
				{8,0,8,1,7,0,9,0}, 
				{8,1,8,2,9,1,7,1,8,0}, 
				{8,2,8,1,7,2,8,3}, 
				{8,3,8,2,7,3,8,4}, 
				{8,4,8,5,8,3,7,4}, 
				{8,5,9,5,8,4,7,5,8,6}, 
				{8,6,8,5,7,6,9,6}, 
				{9,0,9,1,8,0}, 
				{9,1,8,1,9,2,9,0}, 
				{9,2,9,1,9,3}, 
				{9,3,9,2,9,4}, 
				{9,4,9,5,9,3}, 
				{9,5,8,5,9,4,9,6}, 
				{9,6,9,5,8,6} 
				};
		for(int i = 0; i<nicksGraph.length;i++)
		{
			int x = nicksGraph[i][0];
			int y = nicksGraph[i][1];
			Coord c = new Coord(x,y);
			Node<Coord> node = robotGraph.nodeWith(c);
			for(int j = 2; j<nicksGraph[i].length;j = j+2)
			{
				int sx = nicksGraph[i][j];
				int sy = nicksGraph[i][j+1];
				Coord sc = new Coord(sx,sy);
				Node<Coord> s = robotGraph.nodeWith(sc);
				node.addSuccessor(s);
			}
		}
		
		for(Map.Entry<Coord,Node<Coord>> e: robotGraph.getNodes().entrySet())
		{
			Coord c = e.getKey();
			Node<Coord> node = e.getValue();
			System.out.print(c+":" );
			for(Node<Coord> n : node.getSuccessors())
			{
				System.out.print(n.getContent()+", ");
			}
			System.out.println();
		}
		
		Node<Coord> start = robotGraph.nodeWith(new Coord(0,0));
		Node<Coord> goal = robotGraph.nodeWith(new Coord(0,2));
		GoalPredicate zeroTwo = new GoalPredicate(new Coord(0,2));
		Cost costFunction = new Cost(robotGraph,start,goal);
		PriorityQueue<Node<Coord>,Integer> testPri = new PriorityQueue<Node<Coord>,Integer>(costFunction);
		System.out.println(robotGraph.findPathFrom(start, zeroTwo, testPri));*/
		
		
		
	}

}
