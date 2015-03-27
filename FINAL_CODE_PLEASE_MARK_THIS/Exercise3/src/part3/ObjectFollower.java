package part3;


import GenericListeners.ExitListener;
import lejos.nxt.Button;
import configandsetup.GeoffSetUp;


/** this class allows us to track a coloured object using the nxtcam
 * 
 * @author tom bates charlie street, vlad marian toncu 
 *
 */
public class ObjectFollower
{
	
	private final PID horizontalPID;
	private final PID distancePID;
	private final CameraReader camera;
	private final GeoffSetUp geoff;
	private double rotateSpeed;
	private double travelSpeed;
	private double correctDistance;
	
	/** constructor initialises attributes
	 * 
	 * @param geoff the robot being used
	 */
	public ObjectFollower(GeoffSetUp geoff)
	{
		this.correctDistance = 65;
		this.geoff = geoff;
		this.camera = new CameraReader(this.geoff.getCamera(), correctDistance);
		this.horizontalPID = new PID(this.camera.getHorizontalMetric(), 0, 1, 0.0006, 0.001);
		this.distancePID   = new PID(this.camera.getDistanceMetric(), correctDistance, 0.25, 0.0005, 0.001);
		this.rotateSpeed = 0;
		this.travelSpeed = 0;
		
		Button.ESCAPE.addButtonListener(new ExitListener(geoff));//setting up generic exit listener
	}
	
	
	/**method to run when program executed
	 * method will either rotate or travel to track the ball depending on values from the PIDs
	 */
	public void run()
	{
		Button.waitForAnyPress();
		while(geoff.getRun())
		{
			double distanceThreshold = 10;
			
			travelSpeed = -distancePID.evaluate();
			geoff.setTravelSpeed(travelSpeed);
			
			System.out.println(Math.round(travelSpeed));
			
			if (Math.abs(travelSpeed) > distanceThreshold)
			{			
				if (travelSpeed > 0)
				{
					geoff.forward();
				}
				else
				{
					geoff.backward();
				}
			}
			else
			{
				rotateSpeed =  horizontalPID.evaluate();
				geoff.setRotateSpeed(rotateSpeed * 0.4);
				
				if (rotateSpeed > 0)
				{
					geoff.rotateLeft();
				}
				else
				{
					geoff.rotateRight();
				}
			}
		}
	}
	
	
	/**main method just creates two objects and runs the program
	 * 
	 */
	public static void main(String[] args)
	{
		GeoffSetUp geoff = new GeoffSetUp();
		ObjectFollower follow = new ObjectFollower(geoff);
		follow.run();
		
	}
}
