package ActionsForRobot;

import configandsetup.GeoffSetUp;
import lejos.robotics.navigation.Pose;
/**this class provides a basic action for the robot when carrying ouy the path provided to it by search
 * 
 * @author Charlie Street
 *
 */
public class Action implements MovementChecker
{
	public static final int UP = 90;//the use of static variables in this case 
	public static final int LEFT = 180;//I believe is acceptable as this prevents me writing 4 classes
	public static final int RIGHT = 0;
	public static final int DOWN = -90;
	public static boolean firstTime = true;
	
	
	private GeoffSetUp robot;
	private Pose pose;
	private int direction;
	private final float ERROR;
	
	
	/** constructor initialises the robot
	 * 
	 * @param robot the robot being used
	 * @param pose the pose of the robot
	 */
	public Action(GeoffSetUp robot, Pose pose, int direction)
	{
		this.robot = robot;
		this.pose = pose;
		this.direction = direction;
		this.ERROR = 1.1f;
	}

	/**method will rectify robots pose and then carry on moving
	 * as we can assume this, the robot will start from a north facing position
	 */
	@Override
	public void run() 
	{
		int desired = this.direction;
		
		float currentHead = this.pose.getHeading();
		float difference = desired - currentHead;
		if(difference > 180)//making robot turn minimum amount
		{
			difference = (360 - difference)*-1;
		}
		else if(difference < -180)
		{
			difference = 360 + difference;
		}
		
		robot.rotate((int)(difference * this.ERROR));
		pose.rotateUpdate(difference);
		
		if(!Action.firstTime)
		{
			switch(desired)//keeping track of pos on grid map
			{
			case Action.UP: this.pose.setLocation(this.pose.getX(),this.pose.getY()+1);break;
				
			case Action.RIGHT: this.pose.setLocation(this.pose.getX() + 1, this.pose.getY());break;
				
			case Action.LEFT: this.pose.setLocation(this.pose.getX()-1, this.pose.getY());break;
				
			case Action.DOWN: this.pose.setLocation(this.pose.getX(), this.pose.getY() - 1);break;
			}
		}
		else
		{
			Action.firstTime = false;
		}
		System.out.println(this.pose);
		
	}

	/**this action involves the robots movement so returns true
	 * 
	 */
	@Override
	public boolean isMovement() 
	{
		return true;
	}

	/**this action is not used for localisation so returns false
	 * 
	 */
	public boolean isLocaliser()
	{
		return false;
	}
	
	/**method simply returns the direction the robot wants to be heading in
	 * 
	 * @return the direction
	 */
	public int getDirection()
	{
		return this.direction;
	}
}
