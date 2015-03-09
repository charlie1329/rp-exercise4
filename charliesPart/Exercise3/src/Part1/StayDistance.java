package Part1;

import lejos.nxt.Button;
import lejos.util.Delay;
import configandsetup.*;
import GenericListeners.ExitListener;


/** class keeps the robot a set distance from an object (a wooden wall maybe)
 * 
 * @author Charlie Street
 *
 */
public class StayDistance 
{
	private GeoffSetUp geoff;
	private ExitListener exit;
	private int loopDelay;
	private double proportion;
	private double setDistance;
	private final double MAXDISTANCE;//limit of sensor - value set
	
	/** constructor sets up attributes
	 * 
	 * @param geoff the geoff object being used
	 * @param setDistance The distance between the robot and the object at which the robot should stop
	 */
	public StayDistance(GeoffSetUp geoff,double setDistance)
	{
		this.geoff = geoff;
		this.exit = new ExitListener(this.geoff);
		Button.ESCAPE.addButtonListener(this.exit);
		this.setDistance = setDistance;
		this.MAXDISTANCE = 90.0 - setDistance; 
		this.loopDelay = 100;//may be changed with testing!!!
		this.proportion = 48/this.MAXDISTANCE;//Found through testing
	}
	/**method returns loopDelay attribute
	 * 
	 * @return the loop delay
	 */
	public double getLoopDelay()
	{
		return this.loopDelay;
	}
	
	/** method runs the program in a while loop changing the speed according to its distance from an object
	 * SENSOR NOISE LESS THAN 7.0 cm
	 */
	public void run()
	{
		Button.waitForAnyPress();
		geoff.setTravelSpeed(0);
		geoff.forward();
		while(geoff.getRun())
		{
			Delay.msDelay(this.loopDelay);
			double currentDistance = this.geoff.getOpticalDistance();
			double error = currentDistance - this.setDistance;
			double newSpeed = error*this.proportion;
			if(Math.abs(error)<=3 ) //Used an error of 3 for accuracy; also to give a sensible stopping point
			{
				geoff.stop();
			}
			else if(error<0)
			{
				geoff.backward();
				newSpeed = error/this.proportion;//Using inverse for backwards motion
			}
			else
			{
				geoff.forward();
			}
			geoff.setTravelSpeed(newSpeed);
			
		}
	}
	
	/**main method initialises objects and calls run method
	 * 
	 */
	public static void main(String [] args)
	{
		GeoffSetUp geoff = new GeoffSetUp();
		StayDistance keepOff = new StayDistance(geoff,20.0);
		keepOff.run();
	}
	

}
