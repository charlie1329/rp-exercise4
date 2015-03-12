package part_5_obstacles;

import ActionsForRobot.Action;
import part2.JunctionBehavior;
import lejos.robotics.navigation.Pose;
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
	
	/**constructor will initialise attributes
	 * 
	 */
	public DetectObstacles(GeoffSetUp geoff, IGridMap grid)
	{
		this.geoff = geoff;
		this.grid = grid;
	}
	
	/** this method checks for unexpected obstacles in the robots direct path
	 * it then deals accordingly if the robot finds one
	 */
	public void lookForObstacle(Pose pose, JunctionBehavior junction)
	{
		double currentDistance = geoff.getOpticalDistance();
		double currentHeader = pose.getHeading();
		int xPos = (int)pose.getX();
		int yPos = (int)pose.getY();
		switch((int)currentHeader)
		{
		case Action.UP: yPos++; break;
		
		case Action.LEFT: xPos--; break;
		
		case Action.RIGHT: xPos++; break;
		
		case Action.DOWN: yPos--; break;
		}
		
		if(currentDistance < 32 && !this.grid.isObstructed(xPos,yPos))//sort out error and junction size
		{
			System.out.println("OBSTACLE!!");//WRONG ORDER!!!!!!!!!!!
			geoff.stop();
		}
	}
	
	

}
