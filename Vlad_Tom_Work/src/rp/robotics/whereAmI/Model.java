package rp.robotics.whereAmI;

import java.util.HashMap;

import rp.robotics.whereAmI.ProbabilityThat.EventRangeSyntax;

    public class Model {
        HashMap<Double, ProbabilityThat.EventSyntax> sensorModel =
                new HashMap<Double, ProbabilityThat.EventSyntax>();

        private final ProbabilityThat probabilityThat = new ProbabilityThat();
        
        public Model(){
        	sensorModel.put(0.000, probabilityThat.NormallyDistributedVariable(0.102, 3.00));
            sensorModel.put(15.00, probabilityThat.NormallyDistributedVariable(14.75, 3.21));
            sensorModel.put(45.00, probabilityThat.NormallyDistributedVariable(46.07, 3.37));
            sensorModel.put(75.00, probabilityThat.NormallyDistributedVariable(75.53, 3.35));
            
            
            sensorModel.put(105.0, probabilityThat.NormallyDistributedVariable(105.0, 3.40));
            sensorModel.put(135.0, probabilityThat.NormallyDistributedVariable(135.0, 3.60));
            sensorModel.put(165.0, probabilityThat.NormallyDistributedVariable(165.0, 3.80));
            sensorModel.put(195.0, probabilityThat.NormallyDistributedVariable(195.0, 4.00));
            sensorModel.put(225.0, probabilityThat.NormallyDistributedVariable(225.0, 4.20));
            sensorModel.put(255.0, probabilityThat.NormallyDistributedVariable(255.0, 4.40));
        }

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
