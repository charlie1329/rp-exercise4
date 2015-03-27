package ActionsForRobot;



import java.util.ArrayList;

import part2.JunctionBehavior;
import lejos.nxt.Button;
import localising_robot.LocaliseGeoff;
import configandsetup.GeoffSetUp;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.mapping.Heading;
import wrapper.LocaliseMe;
import wrapper.LocationProbability;

/** this class represents a localising action for the robot
 * 
 * @author charlie street
 *
 */
public class LocaliseAction implements MovementChecker {

	private float beforeAngle;
	private float afterAngle;
	private Heading after;
	private Heading before;
	private LocaliseMe localise;
	private GeoffSetUp geoff;
	private static boolean firstMove = true;
	private final float confidenceThreshold = 0.95f;
	private final float ERROR;
	private LocaliseGeoff robotLocaliser;
	
	/**constructor just initialises fields
	 * 
	 * @param before the heading before
	 * @param after the heading after rotation (where it is going
	 * @param localise the localising wrapper class
	 * @param geoff the robot being used
	 */
	public LocaliseAction(Heading before, Heading after, LocaliseMe localise, GeoffSetUp geoff, LocaliseGeoff robotLocaliser)
	{
		this.after = after;
		this.before = before;
		this.beforeAngle = Heading.toDegrees(before);
		this.afterAngle = Heading.toDegrees(after);
		this.localise = localise;
		this.geoff = geoff;
		this.ERROR = 1.1f;
		this.robotLocaliser = robotLocaliser;
	}
	/**this method causes the robot to turn and update its
	 * probability distribution
	 */
	@Override
	public void run() 
	{
		
		
		this.localise.update((float)geoff.getOpticalDistance(), before);
//		System.out.println(this.localise.itsAllAboutTheConfidence());
		
		
		float difference = this.afterAngle - this.beforeAngle;
		if(difference > 180)//making robot turn minimum amount
		{
			difference = (360 - difference)*-1;
		}
		else if(difference < -180)
		{
			difference = 360 + difference;
		}
		
		geoff.rotate((int)(difference * this.ERROR));
		
		
		while(geoff.getOpticalDistance() < 32)//while facing a wall
		{
			Heading furtherHead = this.createRandomHeading(this.localise.getDistribution());
			float differenceToMake = Heading.toDegrees(furtherHead) - Heading.toDegrees(this.after);
			if(differenceToMake != 180)//180 degree moves mess up the robot a bit
			{
				if(differenceToMake > 180)//need to bring robot back to its starting position
				{
					differenceToMake = (360 - differenceToMake)*-1;
				}
				else if(differenceToMake < -180)
				{
					differenceToMake = 360 + differenceToMake;
				}
				geoff.rotate((int)(differenceToMake * this.ERROR));//re-rotating the robot to stop it facing a wall; it should never have 4 walls surrounding it
				this.robotLocaliser.setHead(furtherHead);//making sure I keep track of the head correctly
			}
			
		}
	}
	
	/**this method will create a random move for the robot
	 * this is due to the scenario where the robot is facing a wall
	 * @return a new action to be immediately executed
	 */
	private LocaliseAction createRandomMove()
	{	
		return new LocaliseAction(this.after, this.createRandomHeading(this.localise.getDistribution()), this.localise, this.geoff,this.robotLocaliser);
		
	}
	
	/**this method will produce a random heading for an action
	 * 
	 * @param distribution the grid position distribution
	 * @return the heading of the new move
	 */
	public Heading createRandomHeading(GridPositionDistribution distribution){
		LocationProbability mostConfident = localise.itsAllAboutTheConfidence();
		
		System.out.println(mostConfident);
		while(true){
			int n = (int)(System.currentTimeMillis() % 4);//we had some issues with random, hence using the system time
				
			switch(n){//trying to find a smarter way of deciding where to turn next
				case 0:
					if (distribution
							.getGridMap()
							.rangeToObstacleFromGridPosition(
									mostConfident.getX(), mostConfident.getY(), Heading.toDegrees(Heading.PLUS_X)
							) > 32){
						System.out.println("X");
						return Heading.PLUS_X;
					}
					break;
				case 1:
					if (distribution
							.getGridMap()
							.rangeToObstacleFromGridPosition(
									mostConfident.getX(), mostConfident.getY(), Heading.toDegrees(Heading.PLUS_Y)
							) > 32){
						return Heading.PLUS_Y;
					}
					break;
				case 2:
					if (distribution
							.getGridMap()
							.rangeToObstacleFromGridPosition(
									mostConfident.getX(), mostConfident.getY(), Heading.toDegrees(Heading.MINUS_X)
							) > 32){
						return Heading.MINUS_X;
					}
					break;
				default:					
					if (distribution
							.getGridMap()
							.rangeToObstacleFromGridPosition(
									mostConfident.getX(), mostConfident.getY(), Heading.toDegrees(Heading.MINUS_Y)
							) > 32){
						return Heading.MINUS_Y;
					}
					break;
			}
		}
	}
		
	

	/**this method returns true as the action involves the robots movement
	 * 
	 */
	@Override
	public boolean isMovement() 
	{
		return true;
	}

	/**always returns true as this method is localising the robot
	 * 
	 */
	@Override
	public boolean isLocaliser() 
	{
		return true;
	}

}
