package GenericListeners;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import configandsetup.*;//allows me to use geoffSetUp and GeoffConfigs

/**class provides a generic listener for exiting the program at the press of a button
 * 
 * @author Charlie Street
 *
 */

public class ExitListener implements ButtonListener
{
	private GeoffSetUp geoff;
	
	/**constructor initialises geoffSetUp object
	 * 
	 * @param geoff the object being passed in
	 */
	public ExitListener(GeoffSetUp geoff)
	{
		this.geoff = geoff;
	}

	/**method stops program by setting run to false and stopping robot just to be sure
	 * 
	 */

	public void buttonPressed(Button b) {
		this.geoff.setRun(false);
		this.geoff.stop();
		
	}
	
	/**method does nothing but is required by interface
	 * 
	 */

	public void buttonReleased(Button b) {
		
	}
	

}
