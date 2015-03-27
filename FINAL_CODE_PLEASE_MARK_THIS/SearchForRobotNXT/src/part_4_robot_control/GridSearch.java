package part_4_robot_control;

import part2.JunctionBehavior;
import part_5_obstacles.DetectObstacles;
import ActionsForRobot.MovementChecker;
import Part1.CalibrateBehavior;
import Part1.DriveForwardBehavior;
import Part1.TurnBehavior;
import lejos.nxt.Button;
import lejos.robotics.subsumption.Arbitrator;
import lists_and_maybe.Cons;
import lists_and_maybe.Nil;
import localising_robot.LocaliseGeoff;
import configandsetup.GeoffSetUp;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;
import graphs.Coord;
import gridMapCode.GridMap;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;


/**this class will make the robot search for the path to a destination and then follow it
 * 
 * @author charlie street
 *
 */
public class GridSearch 
{
	private GeoffSetUp geoff;
	private Arbitrator arby;
	private IGridMap grid;
	private DetectObstacles detector;
	private LocaliseGeoff localiser;
	/**constructor sets up everything for the task
	 * 
	 */
	public GridSearch(GeoffSetUp geoff, Coord interC, Coord goalC) 
	{
		this.geoff = geoff;
		RPLineMap lineMap = MapUtils.create2015Map1();
		this.grid = new GridMap(lineMap,12, 8, 15, 15, 30);
		this.localiser = new LocaliseGeoff(grid, interC, goalC, this.geoff);
		this.detector =  new DetectObstacles(this.geoff,this.grid,this.localiser.getGraph(),this.localiser);
		System.out.println("FIXING!!");
		System.out.println("MORE FIXING!!");
	}
	
	
	/**run method essentially starts the arbitrator with the given path returned
	 * 
	 */
	public void run()
	{
		this.geoff.setTravelSpeed(15);
		this.geoff.setRotateSpeed(80);
		//Button.waitForAnyPress();
		DriveForwardBehavior drive = new DriveForwardBehavior(this.geoff);//creating all behaviours
		TurnBehavior turn = new TurnBehavior(this.geoff);
		JunctionBehavior junction = new JunctionBehavior(this.geoff, new Nil<MovementChecker>(),this.localiser,this.detector);
		this.localiser.produceRandomMove(junction);
		CalibrateBehavior calibrate = new CalibrateBehavior(this.geoff);
		this.arby = new Arbitrator(new Behavior[]{drive, turn, junction, calibrate});
		arby.start();
	}
	
	public static void main(String[] args)
	{
		GeoffSetUp geoff = new GeoffSetUp();
		GridSearch search = new GridSearch(geoff,new Coord(2,1), new Coord(5,4));
		search.run();
	}

}
