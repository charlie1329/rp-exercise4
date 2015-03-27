package Part1;

import configandsetup.GeoffSetUp;

import lejos.robotics.subsumption.Behavior;

/** class is a behaviour that makes the robot drive forward; this is a low priority behaviour
 * 
 * @author charlie street, tom bates
 *
 */
public class DriveForwardBehavior implements Behavior{
	
	private GeoffSetUp geoff;
	private boolean suppressed;

	/**constructor initialises attributes
	 * 
	 * @param geoff the geoff object being used
	 */
	public DriveForwardBehavior(GeoffSetUp geoff)
	{
		this.geoff = geoff;
		this.suppressed = false;
	}
	
	/**low priority so returns true
	 * 
	 * @return true
	 */

	public boolean takeControl() {
		return true;
	}

	/**while action not suppressed, drive forward
	 * 
	 */

	public void action() {
		
		this.geoff.forward();
		while(!this.suppressed)
		{
			Thread.yield();
		}		
	}
	
	/**stops the action running when behaviour suppressed
	 * 
	 */
	
	public void suppress() {
		this.suppressed = true;
		
	}

}
