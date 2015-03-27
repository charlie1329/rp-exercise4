package Part1;

import configandsetup.GeoffSetUp;
import lejos.robotics.subsumption.Behavior;


/**this behaviour is used to calibrate the robot to whatever light setting it is in
 * 
 * @author Charlie Street, Tom Bates
 *
 */
public class CalibrateBehavior implements Behavior {

	private boolean calibrating;//used to stop this action and never let it be active again
	private final GeoffSetUp geoff;
	
	/**constructor initialises attributes and sets rotate speed
	 * 
	 * @param geoff the geoff object being used
	 */
	public CalibrateBehavior(GeoffSetUp geoff){	
		this.geoff = geoff;
		this.calibrating = true;
	}
	
	/**method is in control when it is calibrating, after that it can never be active again
	 * @return whether calibrating is happening or not
	 */

	public boolean takeControl() {
		return calibrating;
	}

	/**action turns bot round in a circle and calibrating it when it finds a new minimum/maximum for a sensor
	 * 
	 */
	
	public void action() {
		
		double minl = Double.MAX_VALUE;//ASK TOM ABOUT THIS!!!
		double maxl = 0;
		double minr = Double.MAX_VALUE;
		double maxr = 0;
		
		
		geoff.rotate(393, true);//380 due to error in turning circle
		
		while(geoff.isMoving()){
			
			double left = geoff.getLeftLight().readValue();
			double right = geoff.getRightLight().readValue();
			
			if (left < minl)
			{
				minl = left;
				geoff.getLeftLight().calibrateLow();//setting new threshold values for robot
			}
			else if (left > maxl)
			{	
				maxl = left;
				geoff.getLeftLight().calibrateHigh();
			}
			
			if (right < minr)
			{	
				minr = right;
				geoff.getRightLight().calibrateLow();
			}
			else if (right > maxr)
			{
				maxr = right;
				geoff.getRightLight().calibrateHigh();
			}
		}
		
		
		
		calibrating = false;
	}

	/**this method will never be suppressed as it is the highest priority behaviour
	 * 
	 */

	public void suppress()
	{
		
	}

}
