package Part1;



import part2.Direction;
import part2.JunctionBehavior;
import GenericListeners.ExitListener;
import configandsetup.GeoffSetUp;
import lejos.nxt.Button;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**this class essentially brings the behaviours together to allow the robot to follow a line
 * it does this using an arbitrator
 * @author Charlie Street
 *
 */
public class LineFollower {
	private Arbitrator arbitrator;
	
	/**constructor configures robots speeds and creates the arbitrator
	 * 
	 * @param geoff the robot object being used
	 */
	public LineFollower(GeoffSetUp geoff){
		geoff.setTravelSpeed(15);
		geoff.setRotateSpeed(80);
		Button.ESCAPE.addButtonListener(new ExitListener(geoff));
		this.arbitrator = new Arbitrator(new Behavior[]{
				new DriveForwardBehavior(geoff),
				new TurnBehavior(geoff),
			//	new CalibrateBehavior(geoff,new JunctionBehavior(geoff,new ArrayList<Direction>()))//in order to make part2 work as intended, this 'dummy object is required when running part1
			});	
	}
	
	/**this method waits for a button press and then starts the arbitrator
	 * 
	 */
	public void Run(){
		Button.waitForAnyPress();
		arbitrator.start();
	}
	
	
	/**main method sets up two appropriate objects and then calls the run method
	 * 
	 */
	public static void main(String[] args){
		GeoffSetUp geoff = new GeoffSetUp();
		LineFollower follower = new LineFollower(geoff);
		follower.Run();
	}
	
}
