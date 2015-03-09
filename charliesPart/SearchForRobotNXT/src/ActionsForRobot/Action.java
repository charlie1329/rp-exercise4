package ActionsForRobot;

import configandsetup.GeoffSetUp;
import lejos.robotics.navigation.Pose;

public class Action implements MovementChecker
{
	public static final int UP = 0;
	public static final int LEFT = 90;
	public static final int RIGHT = -90;
	public static final int DOWN = 180;
	
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
		float desired = this.direction;
		float difference = desired - currentHead;
		robot.rotate((int)difference);
		pose.rotateUpdate(difference);
		switch(this.direction)//keeping track of pos on line map
		{
		case Action.UP: this.pose.setLocation(this.pose.getX(),this.pose.getY()+30);//JUNCTION SIZE MAY CHANGE!!!!!
		
		case Action.RIGHT: this.pose.setLocation(this.pose.getX() + 30, this.pose.getY());
		
		case Action.LEFT: this.pose.setLocation(this.pose.getX()-30, this.pose.getY());
		
		case Action.DOWN: this.pose.setLocation(this.pose.getX(), this.pose.getY() - 30);
		}
		
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
