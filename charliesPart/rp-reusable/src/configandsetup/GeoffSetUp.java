package configandsetup;




import lejos.nxt.LightSensor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.NXTCam;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;

/** class will set up the robot as a differential pilot and will contain methods for getting wheels etc
 * 
 * @author Charlie Street
 *
 */
public class GeoffSetUp extends DifferentialPilot
{
	private TouchSensor touch1;
	private TouchSensor touch4;
	private UltrasonicSensor ultra;
	private OpticalDistanceSensor optic;
	private LightSensor light_left;
	private LightSensor light_right;
	private NXTCam camera;
	private boolean run;
	/**constructor creates a differential pilot according to our robots spec (for now)
	 * it also sets up a touch sensor for our robot
	 */
	public GeoffSetUp()
	{
		super(GeoffConfigs.WHEEL_DIAMETER,GeoffConfigs.TRACK_WIDTH,
				GeoffConfigs.LEFT_WHEEL,GeoffConfigs.RIGHT_WHEEL);
		
		 this.touch1 = new TouchSensor(GeoffConfigs.TOUCHPORT1);
		 this.touch4 = new TouchSensor(GeoffConfigs.TOUCHPORT4);
		 this.ultra = new UltrasonicSensor(GeoffConfigs.ULTRASONICPORT);
		 this.optic = new OpticalDistanceSensor(GeoffConfigs.OPTICALPORT);
		 this.light_left = new LightSensor(GeoffConfigs.LIGHT_LEFT);
		 this.light_right = new LightSensor(GeoffConfigs.LIGHT_RIGHT);
		 this.camera = new NXTCam(GeoffConfigs.CAM_PORT);
		 this.run = true;
	}
	
	/** returns the position of the left wheel making life easier when making bot move
	 * 
	 * @return the left wheel motor
	 */
	public NXTRegulatedMotor getLeftWheel()
	{
		return GeoffConfigs.LEFT_WHEEL;
	}
	
	/** returns the position of the right wheel making life easier when making bot move
	 * 
	 * @return the right wheel motor
	 */
	public NXTRegulatedMotor getRightWheel()
	{
		return GeoffConfigs.RIGHT_WHEEL;
	}
	
	/** wrapper method for isPressed in touch sensor class
	 * 
	 * @return whether the current touchSensor is pressed
	 */
	public boolean isPressed1()
	{
		return touch1.isPressed();
	}
	
	/** this method is a wrapper method for the isPressed method in our second touch sensor
	 * 
	 * @return a boolean of whether the sensor is pressed
	 */
	public boolean isPressed4()
	{
		return touch4.isPressed();
	}
	
	/**wrapper method that returns the distance to an object
	 * 
	 * @return the distance (255 if nothing)
	 */
	public int getDistance()
	{
		return this.ultra.getDistance();
	}
	
	/** wrapper method returns distance to an object using optical distance sensor
	 * NOTE: according to API this returns in mm so it has been changed to cm and also a double for easier usage
	 * @return the distance to the object
	 */
	public double getOpticalDistance()
	{
		double cmDistance = (double)this.optic.getDistance()/10.0;
		return cmDistance;
	}
	/**returns field giving insight into running status of program
	 * 
	 * @return the current run value
	 */
	public boolean getRun()
	{
		return this.run;
	}
	
	/**changes value of run attribute
	 * 
	 * @param run the new value
	 */
	public void setRun(boolean run)
	{
		this.run = run;
	}
	
	/**method returns the left light sensor
	 * 
	 * @return the left sensor
	 */
	public LightSensor getLeftLight()
	{
		return this.light_left;
	}
	
	/**method returns the right light sensor
	 * 
	 * @return the right light sensor
	 */
	public LightSensor getRightLight()
	{
		return this.light_right;
	}
	
	/**method returns the NXTCam object in use with the robot
	 * 
	 * @return the camera object
	 */
	public NXTCam getCamera()
	{
		return this.camera;
	}
	
}
