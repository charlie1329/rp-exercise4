package rp.robotics.whereAmI;

import lejos.robotics.RangeReadings;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.localisation.SensorModel;
import rp.robotics.mapping.Heading;

public class TomSensorModel implements SensorModel{

	private final Model model;
	
	public TomSensorModel(){
		model = new Model();
	}
	
	@Override
	public GridPositionDistribution updateAfterSensing(
			GridPositionDistribution _dist, Heading _heading,
			RangeReadings _readings) {
		
		return _dist;		
	}
}
