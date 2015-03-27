

import configandsetup.*;
import lejos.nxt.Button;
import lejos.util.Delay;

/**class reads robots distance
 * 
 * @author charlie street
 *
 */
public class DistanceReader {

	private GeoffSetUp geoff;
	public DistanceReader() {
		this.geoff = new GeoffSetUp();
	}
	
	public void run()
	{
		for(int i = 0; i < 10; i++)
		{
			Button.waitForAnyPress();
			System.out.println(geoff.getOpticalDistance());
			Delay.msDelay(2000);
		}
	}
	
	public static void main(String[] args)
	{
		DistanceReader dis = new DistanceReader();
		dis.run();
	}

}
