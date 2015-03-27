package Part1;

import configandsetup.GeoffSetUp;
import lejos.robotics.subsumption.Behavior;

/** class provides a behavior for the robot turning under the condition set by the light sensors
 * 
 * @author charlie street, vlad-marian toncu, tom bates
 *
 */
public class TurnBehavior implements Behavior{
	
	private GeoffSetUp geoff;
	private final double threshold;
	private boolean leftOff;
	private boolean rightOff;
	private boolean suppressed;
	
	public TurnBehavior(GeoffSetUp geoff)
	{
		this.geoff = geoff;
		this.threshold = 70.0;
		this.leftOff = false;
		this.rightOff = false;
		this.suppressed = false;
	}

	/**method decides if this behaviour should take control
	 * 
	 */
	public boolean takeControl()
	{
		if(geoff.getLeftLight().readValue() < threshold)
		{
			this.leftOff = true;
		}
		else if(geoff.getRightLight().readValue() < threshold)
		{
			this.rightOff = true;
		}
		return this.leftOff||this.rightOff;
	}

	/**method carries out the action
	 * if a light sensor is 'hit' it will turn in the appropriate direction until it is fine again
	 */
	public void action() 
	{
		this.suppressed = false;
		if(this.leftOff)
		{
			geoff.rotateLeft();
			this.leftOff = false;
		}
		else if(this.rightOff)
		{
			geoff.rotateRight();
			this.rightOff = false;
		}
		
		while(!suppressed)
		{
			if(geoff.getLeftLight().readValue() >= this.threshold && geoff.getRightLight().readValue() >= this.threshold)
			{
				suppressed = true;
			}
			
		}
	}

	/**method decides what to do when the behaviour is suppressed
	 * in this case it just sets a flag to true
	 */
	
	public void suppress() 
	{
		this.suppressed = true;
		
	}

}
