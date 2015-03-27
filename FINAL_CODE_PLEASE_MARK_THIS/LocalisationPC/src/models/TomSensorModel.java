package models;


import lejos.robotics.RangeReadings;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.localisation.SensorModel;
import rp.robotics.mapping.Heading;

public class TomSensorModel implements SensorModel{

	// If true, diagnostic information on probabilities is printed to the standard output stream.
	private final boolean DEBUG = false;
	
	// Represents the offset from the robots position of the sensor, in the direction that the robot is facing.
	private final float   OFFSET = 5.5f;
	
	// Represents which sensor the SensorModel is modelling, Sonar (in simulation) or Optical (irl).
	private final Sensor  SENSOR = Sensor.Optical;
	
	/**
	 * Represents a type of sensor.
	 * @author txb457
	 *
	 */
	enum Sensor { Optical, Sonar };
	
	private final Model model;
	
	public TomSensorModel(){
		model = new Model();
	}
	
	/**
	 * Given a set of 'probabilities of being in a location' and a heading, update the probabilities
	 * based on a received sensor reading, reasoning 'What is the probability of being an assumed
	 * distance away from an obstacle given i'm reading this distance'.
	 */
	@Override
	public GridPositionDistribution updateAfterSensing(
			GridPositionDistribution _dist, Heading _heading,
			RangeReadings _readings) {
		
		double reading = _readings.get(0).getRange();
		
		if (reading >= (SENSOR == Sensor.Optical ? 90 : 255))
			return _dist;
		
		for (int x = 0; x < _dist.getGridWidth(); x++){
			for (int y = 0; y < _dist.getGridHeight(); y++){
				
				// 'Correct' distance to obstacle at (x, y)
				double dist = _dist.getGridMap().rangeToObstacleFromGridPosition(
						x, y,
						Heading.toDegrees(_heading)
				);

				double probability = model.probabilityGivenSensorReading(reading)
						.isWithinTheRange((float)Math.max(0, ((SENSOR == Sensor.Sonar) ? dist : (dist - OFFSET)) - 15))
						.to((float)Math.min((SENSOR == Sensor.Optical ? 90 : 255), ((SENSOR == Sensor.Sonar) ? dist : (dist - OFFSET)) + 15));
				
				if (DEBUG && probability > 0) {
					System.out.println(
							"p(" + reading + " | {" + x + ", " + y + "}) = " + probability * 100 + "%%"
					);
				}
				
				_dist.setProbability(x, y, (float)(_dist.getProbability(x, y) * probability));
			}
		}
		
		if (DEBUG){
			System.out.println("");
		}
		
		_dist.normalise();
		return _dist;
	}
}