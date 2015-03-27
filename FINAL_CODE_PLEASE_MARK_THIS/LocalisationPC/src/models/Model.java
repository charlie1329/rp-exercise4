package models;

import java.util.HashMap;

	/**
	 * Maintains a table of distance against the distribution parameters of the sensor readings
	 * at that distance. This facilitates queries such as 'probability that the distance to an
	 * obstacle is within some range given a reading'
	 * 
	 * @author txb475
	 */
    public class Model {
        HashMap<Double, ProbabilityThat.EventSyntax> sensorModel =
                new HashMap<Double, ProbabilityThat.EventSyntax>();

        private final ProbabilityThat probabilityThat = new ProbabilityThat();
        
        public Model(){
        	sensorModel.put(0.000, probabilityThat.NormallyDistributedVariable(0.102f, 3.00f));
            sensorModel.put(15.00, probabilityThat.NormallyDistributedVariable(14.75f, 3.21f));
            sensorModel.put(45.00, probabilityThat.NormallyDistributedVariable(46.07f, 3.37f));
            sensorModel.put(75.00, probabilityThat.NormallyDistributedVariable(75.53f, 3.35f));
            
            
            sensorModel.put(105.0, probabilityThat.NormallyDistributedVariable(105.0f, 3.40f));
            sensorModel.put(135.0, probabilityThat.NormallyDistributedVariable(135.0f, 3.60f));
            sensorModel.put(165.0, probabilityThat.NormallyDistributedVariable(165.0f, 3.80f));
            sensorModel.put(195.0, probabilityThat.NormallyDistributedVariable(195.0f, 4.00f));
            sensorModel.put(225.0, probabilityThat.NormallyDistributedVariable(225.0f, 4.20f));
            sensorModel.put(255.0, probabilityThat.NormallyDistributedVariable(255.0f, 4.40f));
        }

        /**
         * Represents the distribution of distances given a sensor-reading.
         * 
         * @param reading The sensor reading.
         * @return The distribution of distances given a sensor-reading.
         */
        public ProbabilityThat.EventSyntax probabilityGivenSensorReading(double reading){
        	 double lower = 15;
        	 for (double key = 15; key <= 255; key += 30){
        		 if (key > lower && key <= reading)
        			 lower = key;
        	 }
        	 
        	 double nearest = lower;
        	 if (lower < 255){
        	 	if (reading - lower > 15){
        	 		nearest += 30;
        	 	}
        	 }
        	 
        	 return sensorModel.get(nearest);
        }
    }