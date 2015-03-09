package part_4_robot_control;

import part2.JunctionBehavior;
import ActionsForRobot.MovementChecker;
import Part1.CalibrateBehavior;
import Part1.DriveForwardBehavior;
import Part1.TurnBehavior;
import lejos.nxt.Button;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Arbitrator;
import lists_and_maybe.IList;
import graphs.Graph;
import graphs.Node;
import configandsetup.GeoffSetUp;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.NicksGridMap;
import rp.robotics.mapping.RPLineMap;
import graphs.Coord;
import lejos.robotics.subsumption.Behavior;


/**this class will make the robot search for the path to a destination and then follow it
 * 
 * @author charlie street
 *
 */
public class GridSearch 
{
	private GeoffSetUp geoff;
	private Graph graph;
	private IList<MovementChecker> path;
	private Arbitrator arby;
	
	/**constructor sets up everything for the task
	 * 
	 */
	public GridSearch(GeoffSetUp geoff,Coord startC, Coord interC, Coord goalC) 
	{
		this.geoff = geoff;
		RPLineMap lineMap = MapUtils.create2014Map2();
		IGridMap grid = new NicksGridMap(10, 7, 14, 31, 30, lineMap);
		this.graph = new Graph(grid);
		Node<Coord> start = this.graph.nodeWith(startC);
		Node<Coord> inter = this.graph.nodeWith(interC);
		Node<Coord> goal = this.graph.nodeWith(goalC);
		Pose pose = new Pose(14,31,0);
		SearchToAction search = new SearchToAction(start, inter, goal, geoff,pose, graph);
		this.path = search.getCompleteActionSet();
	}
	
	/**allows path to be retrieved
	 * 
	 * @return
	 */
	public IList<MovementChecker> getPath()
	{
		return this.path;
	}
	
	/**run method essentially starts the arbitrator with the given path returned
	 * 
	 */
	public void run()
	{
		Button.waitForAnyPress();
		DriveForwardBehavior drive = new DriveForwardBehavior(this.geoff);
		TurnBehavior turn = new TurnBehavior(this.geoff);
		JunctionBehavior junction = new JunctionBehavior(this.geoff, this.getPath());
		CalibrateBehavior calibrate = new CalibrateBehavior(this.geoff);
		this.arby = new Arbitrator(new Behavior[]{drive, turn, junction, calibrate});
		arby.start();
	}
	
	public static void main(String[] args)
	{
		GeoffSetUp geoff = new GeoffSetUp();
		geoff.setTravelSpeed(15);
		geoff.setRotateSpeed(80);
		GridSearch search = new GridSearch(geoff,new Coord(1,0),new Coord(2,2), new Coord(2,4));
		search.run();
	}

}
