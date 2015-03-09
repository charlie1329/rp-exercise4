package configandsetup;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

/** this class contains all of the configurations for our robot geoff
 * saving time when setting him up (differential pilot constructor)
 * @author Charlie Street
 *
 */
public class GeoffConfigs
{
	public static final double WHEEL_DIAMETER = 5.6;//false values for now just setting up class
	public static final double TRACK_WIDTH = 15.2;//space between wheels
	public static final NXTRegulatedMotor LEFT_WHEEL = Motor.B;
	public static final NXTRegulatedMotor RIGHT_WHEEL = Motor.C;
	public static final SensorPort TOUCHPORT1 = SensorPort.S1;
	public static final SensorPort TOUCHPORT4 = SensorPort.S4;
	public static final SensorPort ULTRASONICPORT = SensorPort.S2;
	public static final SensorPort OPTICALPORT = SensorPort.S2;
	public static final SensorPort LIGHT_LEFT = SensorPort.S1;
	public static final SensorPort LIGHT_RIGHT = SensorPort.S4;
	public static final SensorPort CAM_PORT = SensorPort.S2;
}

