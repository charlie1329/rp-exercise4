package part3;


/**class is a proportion-integral-derivative controller.
 * uses info on a signal to predict where it will go in the future
 * @author tom bates
 *
 */

public class PID
{
	
	private final NullaryFunction<Double> sensor;
    private final double setPoint;
    private final double kp;// Constant of proportionality for the error term.
    private final double ki;// Constant of proportionality for the integral term.
    private final double kd; // Constant of proportionality for the derivative term.
    private double integral = 0;
    private double derivative = 0;
    private double lastError = 0;
    
    /**constructor initialises attributes
     * 
     * @param sensor the signal
     * @param setPoint the signal set point
     * @param kp error constant
     * @param ki integral constant
     * @param kd the derivative constant
     */
    public PID(NullaryFunction<Double> sensor, double setPoint, double kp, double ki, double kd)
    {
        this.sensor   = sensor;
        this.setPoint = setPoint;

        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }

    /**evaluate predicts the next value with a given time step
     * 
     * @return the next value
     */
    public double evaluate()
    {
        double reading = sensor.apply();
        double error   = reading - setPoint;

        integral += error;
        integral *= 0.95;

        derivative = error - lastError;
        lastError  = error;

        return kp * error + ki * integral + kd * derivative;
    }
}