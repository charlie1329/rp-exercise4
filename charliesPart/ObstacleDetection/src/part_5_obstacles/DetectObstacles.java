package part_5_obstacles;

import graphs.Coord;
import graphs.Graph;
import ActionsForRobot.Action;
import ActionsForRobot.MovementChecker;
import part2.JunctionBehavior;
import part_4_robot_control.SearchToAction;
import lejos.robotics.navigation.Pose;
import lists_and_maybe.IList;
import lists_and_maybe.Nil;
import rp.robotics.mapping.IGridMap;
import configandsetup.GeoffSetUp;

/**this class will be used to detect whether dynamic obstacles have been place within the robot
 * it will then deal with this by recalculating the path for the robot
 * @author charlie street
 *
 */
public class DetectObstacles 
{
	private GeoffSetUp geoff;
	private IGridMap grid;
	private Graph graph;
	private SearchToAction search;
	
	/**constructor will initialise attributes
	 * 
	 */
	public DetectObstacles(GeoffSetUp geoff, IGridMap grid, Graph graph, SearchToAction search)
	{
		this.geoff = geoff;
		this.grid = grid;
		this.graph = graph;
		this.search = search;
	}
	
	/** this method checks for unexpected obstacles in the robots direct path
	 * it then deals accordingly if the robot finds one
	 */
	public void lookForObstacle(Pose pose, JunctionBehavior junction)
	{
		double currentDistance = geoff.getOpticalDistance();//the distance from the optical distrance sensor
		double currentHeader = pose.getHeading();
		int xPos = (int)pose.getX();
		int yPos = (int)pose.getY();
		switch((int)currentHeader)//figuring out which point the robot is looking at using its heading
		{
		case Action.UP: yPos++; break;
		
		case Action.LEFT: xPos--; break;
		
		case Action.RIGHT: xPos++; break;
		
		case Action.DOWN: yPos--; break;
		}
		
		if(currentDistance < 32 && !this.grid.isObstructed(xPos,yPos))//sort out error and junction size
		{
			System.out.println("OBSTACLE!! ("+pose.getX()+", "+pose.getY()+") ("+xPos+", "+yPos+")");
			this.reRoute((int)pose.getX(),(int)pose.getY(), xPos, yPos, junction);
		}
	}

	/**method will reroute the robot around a dynamic obstacle
	 * 
	 * @param xOld the robots current position
	 * @param yOld the robots current position
	 * @param xNew the blocked junction
	 * @param yNew the blocked junction
	 * @param junction the junction behaviour where the path is set
	 */
	private void reRoute(int xOld, int yOld, int xNew, int yNew,JunctionBehavior junction)
	{
		System.out.println("rerouting");
		this.graph.nodeWith(new Coord(xOld, yOld)).setBlocked(true);//setting the edge between these two nodes as blocked
		this.graph.nodeWith(new Coord(xNew, yNew)).setBlocked(true);//this stops the search implementation using it
		IList<MovementChecker> currentPath = junction.getPath();
		int destinationsLeft = 0; 
		
		for(int i = 0; i < junction.getPath().size(); i++)//figures out how many destinations remaining
		{
			if(!currentPath.head().isMovement())
			{
				destinationsLeft++;
			}
			currentPath = currentPath.tail();
		}
		System.out.println(destinationsLeft);
		
		IList<MovementChecker> newPath = new Nil<MovementChecker>();
		
		if(destinationsLeft == 1)//sets new path depending on current position throughout route
		{
			newPath = this.search.getListOfActions(this.graph.nodeWith(new Coord(xOld, yOld)),this.search.getGoal());
		}
		else if(destinationsLeft == 2)
		{
			newPath = this.search.getListOfActions(this.graph.nodeWith(new Coord(xOld, yOld)), this.search.getInter()).append(
						this.search.getListOfActions(this.search.getInter(), this.search.getGoal()));
		}
		
		junction.setPath(newPath);//setting the new route for the robot
		
	}
	
	

}
