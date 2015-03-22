package rp.robotics.whereAmI;

import lejos.robotics.RangeReadings;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.localisation.SensorModel;
import rp.robotics.mapping.Heading;

public class TomSensorModel implements SensorModel{

	private final boolean DEBUG = true;
	
	private final Model model;
	
	public TomSensorModel(){
		model = new Model();
	}
	
	@Override
	public GridPositionDistribution updateAfterSensing(
			GridPositionDistribution _dist, Heading _heading,
			RangeReadings _readings) {
		
		double reading = _readings.get(0).getRange();
		
		if (reading == 255)
			return _dist;
		
		for (int x = 0; x < _dist.getGridWidth(); x++){
			for (int y = 0; y < _dist.getGridHeight(); y++){
				
				// 'Correct' distance to obstacle at (x, y)
				double dist = _dist.getGridMap().rangeToObstacleFromGridPosition(
						x, y,
						Heading.toDegrees(_heading)
				);

				double probability = model.probabilityGivenSensorReading(reading)
						.isWithinTheRange(Math.max(0, dist - 15))
						.to(Math.min(255, dist + 15));
				
				if (DEBUG && probability > 0) {
					System.out.println(
						String.format(
							"p(%1.3G | {%d, %d}) = %1.3G%%",
								reading,
								x, y,
								probability * 100
						)
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
