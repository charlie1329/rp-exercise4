package part2;


import part_5_obstacles.DetectObstacles;
import ActionsForRobot.MovementChecker;
import configandsetup.GeoffSetUp;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Behavior;
import lists_and_maybe.IList;

/**this class provides the behaviour for the robot detecting and acting accordingly 
 * this will eventually be programmed such that the robot can take a 'script' of moves
 * @author Charlie Street
 * last edited:10/02/2015
 */
public class JunctionBehavior implements Behavior{

	private GeoffSetUp geoff;
	private boolean bothHit;
	private final double threshold;
	private IList<MovementChecker> path;
	private final int RIGHTANGLEWITHERROR;
	private boolean firstTime;
	private Pose pose;
	private DetectObstacles obstacleChecker;
	
	/** this constructor initialises the attributes of the behaviour
	 * 
	 * @param geoff the geoff object being used
	 */
	public JunctionBehavior(GeoffSetUp geoff, IList<MovementChecker> path, Pose pose, DetectObstacles obstacleChecker) {
		this.geoff = geoff;
		this.bothHit = true;//explicitly making it activate straight away
		this.threshold =80.0;
		this.RIGHTANGLEWITHERROR = 95;
		this.path = path;
		this.firstTime = true;
		this.pose = pose;
		this.obstacleChecker = obstacleChecker;
	}
	

	/**method will check if one sensor has reached a line
	 * it will then give some amount of time to allow the second sensor to be pressed
	 * if this is so it takes control, otherwise it returns false
	 * this beLeft.haviour will have a higher priority than turnBehavior
	 */

	public boolean takeControl() {
		if(geoff.getLeftLight().readValue() < this.threshold && geoff.getRightLight().readValue() < this.threshold)
		{
			this.bothHit = true;
		}
		return bothHit;
	}

	/**this method will allow the robot to turn some amount as to change it's route on a grid
	 * 
	 */	
	
	public void action() {
		
		if(this.path.size() == 0)
		{
			geoff.stop();//terminate program
			geoff.setRun(false);
		}
		else
		{
			if(!this.firstTime )//I don't want this occurring on the first action
			{
				geoff.travel(4);
			}
			else
			{
				geoff.travel(-4);
			}
			this.path.head().run();
			if(!this.path.head().isMovement() && this.path.tail().size() != 0)//if sound action and there is a next item in the list
			{
				this.path = this.path.tail();
				this.path.head().run();
			}
			
			this.path = this.path.tail();
	
			this.bothHit = false;
			this.firstTime = false;
			obstacleChecker.lookForObstacle(this.pose,this);
		}
		

	}

	/**during normal running this will have the highest priority so will not be suppressed
	 * it is really 2nd priority but the 1st priority runs at the start and then never runs again
	 */
	public void suppress() {

	}
	
	/**method changes value of bothHit;required to stop issue at start of program
	 * 
	 * @param bothHit the new Value of bothHit
	 */
	public void setBothHit(boolean bothHit)
	{
		this.bothHit = bothHit;
	}
	
	

}
