package part3;

import java.awt.Rectangle;
import lejos.nxt.addon.NXTCam;

/** this class deals with reading all of the appropriate values form the nxtcam
 * 
 * @author charlie street, tom bates, vlad marian toncu
 *
 */
public class CameraReader {
	
	public final Rectangle BOUNDS;
	private final NXTCam camera;
	private final double CORRECT_DISTANCE;
	private final double significantSizeThreshold;
	
	/**constructor initialises attributes
	 * 
	 * @param camera the nxtcam being used
	 * @param correct the correct value being passed in
	 */
	public CameraReader(NXTCam camera, double correct)
	{
		this.BOUNDS = new Rectangle(0, 0, 177, 0);//the bounds of the nxtcam screen
		this.camera = camera;
		this.CORRECT_DISTANCE = correct;
		this.significantSizeThreshold = 8;
	}
	
	/**this method calculates the horizontal position of the object on the screen
	 * 
	 * @return the horizontal value
	 */
	public double calculateHorizontalMetric()
	{
		Rectangle boundingBox = camera.getRectangle(0);
		
		double x = boundingBox.getX() + (boundingBox.getWidth() / 2);
		double y = BOUNDS.getWidth() - ((boundingBox.getWidth() / 2) + boundingBox.getX());

		if (significant(boundingBox)){
			return y - x;
		}
		
		return 0;		
	}
	
	/**returns true if the rectangle is significant enough to not be noise.
	 * 
	 * @param boundingBox the box being picked up by the camera
	 * @return
	 */
	public boolean significant(Rectangle boundingBox)
	{
	
		return Math.max(boundingBox.getWidth(), boundingBox.getHeight()) >= significantSizeThreshold;
	}
	
	/**returning the horizontal value required
	 * 
	 * @return the horizontal metric reading
	 */
	public NullaryFunction<Double> getHorizontalMetric()
	{
		return new NullaryFunction<Double>() {
			
			@Override
			public Double apply()
			{
				return calculateHorizontalMetric();
			}
		};
	}
	
	/**method calculates the distance to the object
	 * 
	 * @return the distance
	 */
	public double calculateDistanceMetric()
	{
		Rectangle boundingBox = camera.getRectangle(0);
		
		if(significant(boundingBox))
		{
			return Math.max(boundingBox.getWidth(), boundingBox.getHeight());
		}
		
		return CORRECT_DISTANCE;
	}
	
	/**returns the distance calculated from calculateDistanceMetric
	 * 
	 * @return the distance value
	 */
	public NullaryFunction<Double> getDistanceMetric()
	{
		return new NullaryFunction<Double>() {
			
			@Override
			public Double apply() 
			{
				return calculateDistanceMetric();
			}
		};
	}
}
