package Testing;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;


import aStarFunctions.Cost;
import data_structures.PriorityQueue;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;
import rp.robotics.visualisation.GridMapViewer;
import graphs.*;
import lists_and_maybe.*;

/**this class contains ten testng tests that will confirm that the graph search feature of this system works
 * as of 05/03/2015 the test method has passed meaning all 11 tests have passed successfully
 * @author Charlie Street
 *
 */
@Test
public class TestSearch {

	IGridMap grid;
	private Graph testGraph;
	private IList<Coord> startNodes;//data to be used in tests
	private IList<Coord> goalNodes;
	/**setting up the graph before any tests are carried out
	 * 
	 */
	@BeforeClass
	public void setUp()
	{
		RPLineMap lineMap = MapUtils.create2014Map2();
		grid = GridMapViewer.createGridMap(lineMap, 10, 7, 14, 31, 30);
		this.testGraph = new Graph(grid);
		this.startNodes = new Nil<Coord>();
		this.startNodes = new Cons<Coord>(new Coord(0,0),this.startNodes);
		this.startNodes = new Cons<Coord>(new Coord(3,2),this.startNodes);
		this.startNodes = new Cons<Coord>(new Coord(9,0),this.startNodes);
		this.startNodes = new Cons<Coord>(new Coord(6,1),this.startNodes);
		this.startNodes = new Cons<Coord>(new Coord(5,5),this.startNodes);
		this.startNodes = new Cons<Coord>(new Coord(1,4),this.startNodes);
		this.startNodes = new Cons<Coord>(new Coord(2,6),this.startNodes);
		this.startNodes = new Cons<Coord>(new Coord(4,2),this.startNodes);
		this.startNodes = new Cons<Coord>(new Coord(7,3),this.startNodes);
		this.startNodes = new Cons<Coord>(new Coord(8,5),this.startNodes);
		this.startNodes = new Cons<Coord>(new Coord(9,6),this.startNodes);
		
		this.goalNodes = new Nil<Coord>();
		this.goalNodes = new Cons<Coord>(new Coord(3,1),this.goalNodes);
		this.goalNodes = new Cons<Coord>(new Coord(2,5),this.goalNodes);
		this.goalNodes = new Cons<Coord>(new Coord(0,6),this.goalNodes);
		this.goalNodes = new Cons<Coord>(new Coord(1,3),this.goalNodes);
		this.goalNodes = new Cons<Coord>(new Coord(9,2),this.goalNodes);
		this.goalNodes = new Cons<Coord>(new Coord(7,4),this.goalNodes);
		this.goalNodes = new Cons<Coord>(new Coord(8,5),this.goalNodes);
		this.goalNodes = new Cons<Coord>(new Coord(6,4),this.goalNodes);
		this.goalNodes = new Cons<Coord>(new Coord(0,4),this.goalNodes);
		this.goalNodes = new Cons<Coord>(new Coord(1,1),this.goalNodes);
		this.goalNodes = new Cons<Coord>(new Coord(0,0),this.goalNodes);
	}
	
	/**method will carry out test on each of the pairs of start goal coordinates
	 * to pass the test the findPath method must:
	 * return a path, i.e. not Nothing (something of the Just type)
	 * the start and goal nodes in the path must be correct 
	 * for each step made in the path, this must be a valid transition in the grid map, otherwise an invalid path is being produced
	 */
	@Test
	public void testPaths()
	{
		
		PriorityQueue<Node<Coord>,Integer> priQueue;
		Cost cost;
		for(int i = 0; i < this.startNodes.size(); i++)
		{
			Node<Coord> start = testGraph.nodeWith(this.startNodes.head());
			Node<Coord> goal = testGraph.nodeWith(this.goalNodes.head());
			GoalPredicate goalPred = new GoalPredicate(this.goalNodes.head());
			cost = new Cost(testGraph,start,goal);
			priQueue = new PriorityQueue<Node<Coord>,Integer>(cost);
			Maybe<IList<Node<Coord>>> testPath = testGraph.findPathFrom(start,goalPred,priQueue);
			assertEquals(1,testPath.size());//checking a path has been found
			assertEquals(testPath.fromMaybe().head(),start);//cheking start and end nodes are correct
			assertEquals(testPath.fromMaybe().reverse().head(),goal);
			for(int j = 0; j < testPath.fromMaybe().size()-1; j++)//checking for a correct path
			{
				int x1 = testPath.fromMaybe().head().getContent().getX();
				int y1 = testPath.fromMaybe().head().getContent().getY();
				int x2 = testPath.fromMaybe().tail().head().getContent().getX();
				int y2 = testPath.fromMaybe().tail().head().getContent().getY();
				assertEquals(true,grid.isValidTransition(x1, y1, x2, y2));
				
				testPath = new Just<IList<Node<Coord>>>(testPath.fromMaybe().tail());//moving to next item
			}
			
		}
		
	}

}
