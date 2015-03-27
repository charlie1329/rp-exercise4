package part3_run_on_robot;

import lejos.nxt.Button;
import lists_and_maybe.IList;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.NicksGridMap;
import rp.robotics.mapping.RPLineMap;
import graphs.*;
import data_structures.*;
import aStarFunctions.*;

/**this class is for part 3 of rp-exercise 4. It will test whether the search will
 * run on the robot, via simply printing a path to the robot
 * @author charlie street
 *
 */
public class SearchPrint
{

	private IGridMap grid;
	private Graph graph;
	private PriorityQueue<Node<Coord>,Integer> priQueue;
	private Node<Coord> start;
	private Node<Coord> goal;
	private Cost cost;
	private GoalPredicate goalPred;
	
	/**constructor sets up grid map and graph
	 * it also initialises attributes
	 */
	public SearchPrint() 
	{
		RPLineMap lineMap = MapUtils.create2014Map2();
		this.grid = new NicksGridMap(10, 7, 14, 31, 30, lineMap);
		this.graph = new Graph(grid);
		this.start = graph.nodeWith(new Coord(0,0));
		this.goal = graph.nodeWith(new Coord(3,1));
		this.cost = new Cost(graph,start,goal);
		this.priQueue = new PriorityQueue<Node<Coord>,Integer>(cost);
		this.goalPred = new GoalPredicate(new Coord(3,1));
		
	}
	
	/**this method will print the result of a graph search on the screen to prove that the search is appropriate for use on the NXT
	 * 
	 */
	public void run()
	{
		IList<Node<Coord>> path = graph.findPathFrom(start, goalPred, priQueue).fromMaybe();
		System.out.println(path);
		System.out.println("Search Complete!!!");
		Button.waitForAnyPress();
		
	}
	
	public static void main(String[] args)
	{
		SearchPrint testingSearch = new SearchPrint();
		testingSearch.run();
	}

}
