package rp.robotics.whereAmI;

import java.util.HashMap;

    public class Model {
        HashMap<Double, ProbabilityThat.EventSyntax> sensorModel =
                new HashMap<Double, ProbabilityThat.EventSyntax>();

        private final ProbabilityThat probabilityThat = new ProbabilityThat();
        
        public Model(){
            sensorModel.put(7.50,  probabilityThat.NormallyDistributedVariable(04.45, 2.58));
            sensorModel.put(22.50, probabilityThat.NormallyDistributedVariable(22.61, 3.06));
            sensorModel.put(37.50, probabilityThat.NormallyDistributedVariable(36.22, 3.71));
            sensorModel.put(52.50, probabilityThat.NormallyDistributedVariable(52.70, 3.25));
            sensorModel.put(67.50, probabilityThat.NormallyDistributedVariable(67.45, 2.50));
            sensorModel.put(82.50, probabilityThat.NormallyDistributedVariable(80.12, 0.54));
        }

        public ProbabilityThat.EventSyntax probabilityGivenSensorReading(double reading){
            double lower = 7.5;
            for (double key = 7.5; key <= 82.50; key += 15){
                if (key > lower && key <= reading)
                    lower = key;
            }

            double nearest = lower;
            if (lower < 82.5){
                if (reading - lower > 7.5){
                    nearest += 15;
                }
            }

            return sensorModel.get(nearest);
        }
    }
