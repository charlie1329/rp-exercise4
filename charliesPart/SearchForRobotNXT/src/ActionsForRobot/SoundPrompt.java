package ActionsForRobot;

import java.io.File;

import lejos.nxt.Button;
import lejos.nxt.Sound;

/**This is the class to signal when the robot has reached its destination
 * 
 * @author charlie street
 *
 */
public class SoundPrompt implements MovementChecker
{

	/**the run method plays the sample file on the robot
	 * 
	 */
	public void run()
	{
		Sound.setVolume(Sound.VOL_MAX);
		Sound.playSample(new File("aoogah.wav"));
		System.out.println("reached destination");
		Button.waitForAnyPress();
	}

	/**since this class represents the robot playing a sound this method returns false
	 * 
	 */
	@Override
	public boolean isMovement()
	{
		return false;
	}

}
