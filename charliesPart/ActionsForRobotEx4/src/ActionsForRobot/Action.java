package ActionsForRobot;

import configandsetup.GeoffSetUp;
import lejos.robotics.navigation.Pose;

public class Action implements MovementChecker
{
	public static final int UP = 0;//the use of static variables in this case 
	public static final int LEFT = 90;//I believe is acceptable as this prevents me writing 4 classes
	public static final int RIGHT = -90;
	public static final int DOWN = 180;
	private static boolean firstTime = true;//I need to keep track of how many actions have been executed within the class hence this static variable
	
	private GeoffSetUp robot;
	private Pose pose;
	private int direction;
	
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
	}

	/**method will rectify robots pose and then carry on moving
	 * as we can assume this, the robot will start from a north facing position
	 */
	@Override
	public void run() 
	{
		float currentHead = this.pose.getHeading();
		int desired = this.direction;
		float difference = desired - currentHead;
		if(difference > 180)//making robot turn minimum amount
		{
			difference = (360 - difference)*-1;
		}
		else if(difference < -180)
		{
			difference = 360 + difference;
		}
		robot.rotate((int)difference);
		pose.rotateUpdate(difference);
		if(!Action.firstTime)//i don't want the position to change first time due to the nature of this robot running
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

}
