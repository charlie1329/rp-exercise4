package graphs;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import rp.*;
import rp.robotics.mapping.IGridMap;

//import rp.robotics.visualisation.GridMapViewer;
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
		for(int i = 0; i < this.grid.getXSize(); i++)
		{
			for(int j = 0; j < this.grid.getYSize(); j++)
			{
				Coord currentCoord = new Coord(i,j);
				this.nodeWith(currentCoord).setParent(new Nothing<Node<Coord>>());//need to reset parents so dynamic obstacles work
			}//this also cleans up from any previous runs on the graph
		}
		
		Collection<Node<Coord>> set = new SimpleSet<Node<Coord>>();//using for efficiency
		this.links = new HashMap<Node<Coord>,Maybe<Node<Coord>>>();//maybe used to stop null pointers
		ds.insertItem(x);
		System.out.println("searching");
		while(!ds.isEmpty())//this gives us precautions to allow use of some of the'unsafe' methods as they will never be used in such a scenario
		{
			if(set.contains(ds.getFront().fromMaybe()))//fromMaybe can be used as Nothing will never enter this loop
			{
				ds.removeFront();
			}
			else if(ds.getFront().fromMaybe().getBlocked() && (ds.getFront().fromMaybe().getParent().size() != 0) && ds.getFront().fromMaybe().getParent().fromMaybe().getBlocked())//stops blocked edges being used in reconstructions
			{ //the parent size != 0 is required to prevent the use of nothing in from maybe hence the really long if statement
				System.out.println("blockage");
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
		
		for(int i2 = 0; i2 < this.grid.getXSize(); i2++)
		{
			for(int j2 = 0; j2 < this.grid.getYSize(); j2++)
			{//for this use, we will assume the blockage is there for one iteration; if it is still there the robot will re-detect it
				//this means that we can re-test making our system more versatile rather than stopping the use of an edge.
				Coord currentCoord = new Coord(i2,j2);
				this.nodeWith(currentCoord).setBlocked(false);//need to reset blocking
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
		Node<Coord> goal = testGraph.nodeWith(new Coord(3,0));
		GoalPredicate zeroTwo = new GoalPredicate(new Coord(3,0));
		Cost costFunction = new Cost(testGraph,start,goal);
		PriorityQueue<Node<Coord>,Integer> testPri = new PriorityQueue<Node<Coord>,Integer>(costFunction);
		System.out.println(testGraph.findPathFrom(start, zeroTwo, testPri));
		
		Node<Coord> start2 = testGraph.nodeWith(new Coord(3,0));
		Node<Coord> goal2 = testGraph.nodeWith(new Coord(4,1));
		GoalPredicate zeroTwo2 = new GoalPredicate(new Coord(4,1));
		Cost costFunction2 = new Cost(testGraph,start2,goal2);
		PriorityQueue<Node<Coord>,Integer> testPri2 = new PriorityQueue<Node<Coord>,Integer>(costFunction2);
		System.out.println(testGraph.findPathFrom(start2, zeroTwo2, testPri2));
		
		testGraph.nodeWith(new Coord(2,0)).setBlocked(true);
		testGraph.nodeWith(new Coord(3,0)).setBlocked(true);
		
		
		Node<Coord> start3 = testGraph.nodeWith(new Coord(2,0));
		Node<Coord> goal3 = testGraph.nodeWith(new Coord(3,0));
		GoalPredicate zeroTwo3 = new GoalPredicate(new Coord(3,0));
		Cost costFunction3 = new Cost(testGraph,start3,goal3);
		PriorityQueue<Node<Coord>,Integer> testPri3 = new PriorityQueue<Node<Coord>,Integer>(costFunction3);
		System.out.println(testGraph.findPathFrom(start3, zeroTwo3, testPri3));
		
		
		
	}*/

}
