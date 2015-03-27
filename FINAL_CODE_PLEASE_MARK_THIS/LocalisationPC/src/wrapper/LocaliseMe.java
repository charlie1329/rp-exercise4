package wrapper;

import lejos.robotics.RangeReadings;
import models.TomSensorModel;
import models.VladPerfectActionModel;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.mapping.Heading;
import rp.robotics.mapping.IGridMap;

/**this class wraps up all localisation components into one simple
 * to use class for the robot to work on
 * @author charlie street
 *
 */
public class LocaliseMe {

	private GridPositionDistribution distribution;
	private TomSensorModel sensorModel;
	private VladPerfectActionModel actionModel;
	
	/**this constructor initialises all fields
	 * 
	 */
	public LocaliseMe(IGridMap grid)
	{
		this.distribution = new GridPositionDistribution(grid);
		this.sensorModel = new TomSensorModel();
		this.actionModel = new VladPerfectActionModel();
	}
	
	/**this method uses the localisation models to update the grid position distribution
	 * 
	 * @param reading the current reading from the sensor
	 */
	public void update(float reading, Heading heading)
	{
		this.distribution = this.actionModel.updateAfterMove(distribution, heading);
		this.distribution = this.sensorModel.updateAfterSensing(distribution, heading, floatToReading(reading));
	}

	/**this method converts a float reading possibly from a distance sensor and converts it to a range reader
	 * 
	 * @param reading the float reading 
	 * @return a range reader for that value
	 */
	private RangeReadings floatToReading(float reading)
	{
		RangeReadings range = new RangeReadings(1);
		range.setRange(0,0,reading);
		return range;
	}
	
	/**this method returns the grid map position with which there is highest probability of being
	 * at a certain point
	 * @return the highest value from the distribution
	 */
	public LocationProbability itsAllAboutTheConfidence()
	{
		int width = this.distribution.getGridWidth();
		int height = this.distribution.getGridHeight();
		float highest = 0;//default start value, can't be lower than 0
		LocationProbability location = new LocationProbability(0,0,0);//default
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				float current = this.distribution.getProbability(i,j);
				if(current > highest)//if new value
				{
					highest = current;
					location = new LocationProbability(i,j,highest);//new position with highest probability
				}
			}
		}
		
		return location;
	}
	
	/**method returns the grid position distribution
	 * 
	 * @return the grid position distribution
	 */
	public GridPositionDistribution getDistribution()
	{
		return this.distribution;
	}
}
