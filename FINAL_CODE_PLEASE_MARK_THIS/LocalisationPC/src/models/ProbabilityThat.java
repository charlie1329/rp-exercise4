package models;

import java.util.HashMap;

import lejos.util.Delay;

/**
 * 
 * Utilities for probability calculations.
 * 
 * @author txb457
 *
 */
public class ProbabilityThat {

	/**
	 * Standard normal distribution, table values represent area to the left of the z-score.
	 */
    private final HashMap<Float, Float> phi;

    /**
     * Creates a new ProbabilityThat instance and fills out its normal table with pre-calculated values.
     */
    public ProbabilityThat() {
        // <editor-fold desc="Z-Scores">
        phi = new HashMap<Float, Float>();
        
        phi.put(0.00f, 0.0000f);
        phi.put(0.10f, 0.0398f);
        phi.put(0.20f, 0.0793f);
        phi.put(0.30f, 0.1179f);
        phi.put(0.40f, 0.1554f);
        phi.put(0.50f, 0.1915f);
        phi.put(0.60f, 0.2257f);
        phi.put(0.70f, 0.2580f);
        phi.put(0.80f, 0.2881f);
        phi.put(0.90f, 0.3159f);
        phi.put(1.00f, 0.3413f);
        phi.put(1.10f, 0.3643f);
        phi.put(1.20f, 0.3849f);
        phi.put(1.30f, 0.4032f);
        phi.put(1.40f, 0.4192f);
        phi.put(1.50f, 0.4332f);
        phi.put(1.60f, 0.4452f);
        phi.put(1.70f, 0.4554f);
        phi.put(1.80f, 0.4641f);
        phi.put(1.90f, 0.4713f);
        phi.put(2.00f, 0.4772f);
        phi.put(2.10f, 0.4821f);
        phi.put(2.20f, 0.4861f);
        phi.put(2.30f, 0.4893f);
        phi.put(2.40f, 0.4918f);
        phi.put(2.50f, 0.4938f);
        phi.put(2.60f, 0.4953f);
        phi.put(2.70f, 0.4965f);
        phi.put(2.80f, 0.4974f);
        phi.put(2.90f, 0.4981f);
        phi.put(3.00f, 0.4987f);

        phi.put(0.01f, 0.0040f);
        phi.put(0.11f, 0.0438f);
        phi.put(0.21f, 0.0832f);
        phi.put(0.31f, 0.1217f);
        phi.put(0.41f, 0.1591f);
        phi.put(0.51f, 0.1950f);
        phi.put(0.61f, 0.2291f);
        phi.put(0.71f, 0.2611f);
        phi.put(0.81f, 0.2910f);
        phi.put(0.91f, 0.3186f);
        phi.put(1.01f, 0.3438f);
        phi.put(1.11f, 0.3665f);
        phi.put(1.21f, 0.3869f);
        phi.put(1.31f, 0.4049f);
        phi.put(1.41f, 0.4207f);
        phi.put(1.51f, 0.4345f);
        phi.put(1.61f, 0.4463f);
        phi.put(1.71f, 0.4564f);
        phi.put(1.81f, 0.4649f);
        phi.put(1.91f, 0.4719f);
        phi.put(2.01f, 0.4778f);
        phi.put(2.11f, 0.4826f);
        phi.put(2.21f, 0.4864f);
        phi.put(2.31f, 0.4896f);
        phi.put(2.41f, 0.4920f);
        phi.put(2.51f, 0.4940f);
        phi.put(2.61f, 0.4955f);
        phi.put(2.71f, 0.4966f);
        phi.put(2.81f, 0.4975f);
        phi.put(2.91f, 0.4982f);
        phi.put(3.01f, 0.4987f);

        phi.put(0.02f, 0.0080f);
        phi.put(0.12f, 0.0478f);
        phi.put(0.22f, 0.0871f);
        phi.put(0.32f, 0.1255f);
        phi.put(0.42f, 0.1628f);
        phi.put(0.52f, 0.1985f);
        phi.put(0.62f, 0.2324f);
        phi.put(0.72f, 0.2642f);
        phi.put(0.82f, 0.2939f);
        phi.put(0.92f, 0.3212f);
        phi.put(1.02f, 0.3461f);
        phi.put(1.12f, 0.3686f);
        phi.put(1.22f, 0.3888f);
        phi.put(1.32f, 0.4066f);
        phi.put(1.42f, 0.4222f);
        phi.put(1.52f, 0.4357f);
        phi.put(1.62f, 0.4474f);
        phi.put(1.72f, 0.4573f);
        phi.put(1.82f, 0.4656f);
        phi.put(1.92f, 0.4726f);
        phi.put(2.02f, 0.4783f);
        phi.put(2.12f, 0.4830f);
        phi.put(2.22f, 0.4868f);
        phi.put(2.32f, 0.4898f);
        phi.put(2.42f, 0.4922f);
        phi.put(2.52f, 0.4941f);
        phi.put(2.62f, 0.4956f);
        phi.put(2.72f, 0.4967f);
        phi.put(2.82f, 0.4976f);
        phi.put(2.92f, 0.4982f);
        phi.put(3.02f, 0.4987f);

        phi.put(0.03f, 0.0120f);
        phi.put(0.13f, 0.0517f);
        phi.put(0.23f, 0.0910f);
        phi.put(0.33f, 0.1293f);
        phi.put(0.43f, 0.1664f);
        phi.put(0.53f, 0.2019f);
        phi.put(0.63f, 0.2357f);
        phi.put(0.73f, 0.2673f);
        phi.put(0.83f, 0.2967f);
        phi.put(0.93f, 0.3238f);
        phi.put(1.03f, 0.3485f);
        phi.put(1.13f, 0.3708f);
        phi.put(1.23f, 0.3907f);
        phi.put(1.33f, 0.4082f);
        phi.put(1.43f, 0.4236f);
        phi.put(1.53f, 0.4370f);
        phi.put(1.63f, 0.4484f);
        phi.put(1.73f, 0.4582f);
        phi.put(1.83f, 0.4664f);
        phi.put(1.93f, 0.4732f);
        phi.put(2.03f, 0.4788f);
        phi.put(2.13f, 0.4834f);
        phi.put(2.23f, 0.4871f);
        phi.put(2.33f, 0.4901f);
        phi.put(2.43f, 0.4925f);
        phi.put(2.53f, 0.4943f);
        phi.put(2.63f, 0.4957f);
        phi.put(2.73f, 0.4968f);
        phi.put(2.83f, 0.4977f);
        phi.put(2.93f, 0.4983f);
        phi.put(3.03f, 0.4988f);

        phi.put(0.04f, 0.0160f);
        phi.put(0.14f, 0.0557f);
        phi.put(0.24f, 0.0948f);
        phi.put(0.34f, 0.1331f);
        phi.put(0.44f, 0.1700f);
        phi.put(0.54f, 0.2054f);
        phi.put(0.64f, 0.2389f);
        phi.put(0.74f, 0.2704f);
        phi.put(0.84f, 0.2995f);
        phi.put(0.94f, 0.3264f);
        phi.put(1.04f, 0.3508f);
        phi.put(1.14f, 0.3729f);
        phi.put(1.24f, 0.3925f);
        phi.put(1.34f, 0.4099f);
        phi.put(1.44f, 0.4251f);
        phi.put(1.54f, 0.4382f);
        phi.put(1.64f, 0.4495f);
        phi.put(1.74f, 0.4591f);
        phi.put(1.84f, 0.4671f);
        phi.put(1.94f, 0.4738f);
        phi.put(2.04f, 0.4793f);
        phi.put(2.14f, 0.4838f);
        phi.put(2.24f, 0.4875f);
        phi.put(2.34f, 0.4904f);
        phi.put(2.44f, 0.4927f);
        phi.put(2.54f, 0.4945f);
        phi.put(2.64f, 0.4959f);
        phi.put(2.74f, 0.4969f);
        phi.put(2.84f, 0.4977f);
        phi.put(2.94f, 0.4984f);
        phi.put(3.04f, 0.4988f);

        phi.put(0.05f, 0.0199f);
        phi.put(0.15f, 0.0596f);
        phi.put(0.25f, 0.0987f);
        phi.put(0.35f, 0.1368f);
        phi.put(0.45f, 0.1736f);
        phi.put(0.55f, 0.2088f);
        phi.put(0.65f, 0.2422f);
        phi.put(0.75f, 0.2734f);
        phi.put(0.85f, 0.3023f);
        phi.put(0.95f, 0.3289f);
        phi.put(1.05f, 0.3531f);
        phi.put(1.15f, 0.3749f);
        phi.put(1.25f, 0.3944f);
        phi.put(1.35f, 0.4115f);
        phi.put(1.45f, 0.4265f);
        phi.put(1.55f, 0.4394f);
        phi.put(1.65f, 0.4505f);
        phi.put(1.75f, 0.4599f);
        phi.put(1.85f, 0.4678f);
        phi.put(1.95f, 0.4744f);
        phi.put(2.05f, 0.4798f);
        phi.put(2.15f, 0.4842f);
        phi.put(2.25f, 0.4878f);
        phi.put(2.35f, 0.4906f);
        phi.put(2.45f, 0.4929f);
        phi.put(2.55f, 0.4946f);
        phi.put(2.65f, 0.4960f);
        phi.put(2.75f, 0.4970f);
        phi.put(2.85f, 0.4978f);
        phi.put(2.95f, 0.4984f);
        phi.put(3.05f, 0.4989f);

        phi.put(0.06f, 0.0239f);
        phi.put(0.16f, 0.0636f);
        phi.put(0.26f, 0.1026f);
        phi.put(0.36f, 0.1406f);
        phi.put(0.46f, 0.1772f);
        phi.put(0.56f, 0.2123f);
        phi.put(0.66f, 0.2454f);
        phi.put(0.76f, 0.2764f);
        phi.put(0.86f, 0.3051f);
        phi.put(0.96f, 0.3315f);
        phi.put(1.06f, 0.3554f);
        phi.put(1.16f, 0.3770f);
        phi.put(1.26f, 0.3962f);
        phi.put(1.36f, 0.4131f);
        phi.put(1.46f, 0.4279f);
        phi.put(1.56f, 0.4406f);
        phi.put(1.66f, 0.4515f);
        phi.put(1.76f, 0.4608f);
        phi.put(1.86f, 0.4686f);
        phi.put(1.96f, 0.4750f);
        phi.put(2.06f, 0.4803f);
        phi.put(2.16f, 0.4846f);
        phi.put(2.26f, 0.4881f);
        phi.put(2.36f, 0.4909f);
        phi.put(2.46f, 0.4931f);
        phi.put(2.56f, 0.4948f);
        phi.put(2.66f, 0.4961f);
        phi.put(2.76f, 0.4971f);
        phi.put(2.86f, 0.4979f);
        phi.put(2.96f, 0.4985f);
        phi.put(3.06f, 0.4989f);

        phi.put(0.07f, 0.0279f);
        phi.put(0.17f, 0.0675f);
        phi.put(0.27f, 0.1064f);
        phi.put(0.37f, 0.1443f);
        phi.put(0.47f, 0.1808f);
        phi.put(0.57f, 0.2157f);
        phi.put(0.67f, 0.2486f);
        phi.put(0.77f, 0.2794f);
        phi.put(0.87f, 0.3078f);
        phi.put(0.97f, 0.3340f);
        phi.put(1.07f, 0.3577f);
        phi.put(1.17f, 0.3790f);
        phi.put(1.27f, 0.3980f);
        phi.put(1.37f, 0.4147f);
        phi.put(1.47f, 0.4292f);
        phi.put(1.57f, 0.4418f);
        phi.put(1.67f, 0.4525f);
        phi.put(1.77f, 0.4616f);
        phi.put(1.87f, 0.4693f);
        phi.put(1.97f, 0.4756f);
        phi.put(2.07f, 0.4808f);
        phi.put(2.17f, 0.4850f);
        phi.put(2.27f, 0.4884f);
        phi.put(2.37f, 0.4911f);
        phi.put(2.47f, 0.4932f);
        phi.put(2.57f, 0.4949f);
        phi.put(2.67f, 0.4962f);
        phi.put(2.77f, 0.4972f);
        phi.put(2.87f, 0.4979f);
        phi.put(2.97f, 0.4985f);
        phi.put(3.07f, 0.4989f);

        phi.put(0.08f, 0.0319f);
        phi.put(0.18f, 0.0714f);
        phi.put(0.28f, 0.1103f);
        phi.put(0.38f, 0.1480f);
        phi.put(0.48f, 0.1844f);
        phi.put(0.58f, 0.2190f);
        phi.put(0.68f, 0.2517f);
        phi.put(0.78f, 0.2823f);
        phi.put(0.88f, 0.3106f);
        phi.put(0.98f, 0.3365f);
        phi.put(1.08f, 0.3599f);
        phi.put(1.18f, 0.3810f);
        phi.put(1.28f, 0.3997f);
        phi.put(1.38f, 0.4162f);
        phi.put(1.48f, 0.4306f);
        phi.put(1.58f, 0.4429f);
        phi.put(1.68f, 0.4535f);
        phi.put(1.78f, 0.4625f);
        phi.put(1.88f, 0.4699f);
        phi.put(1.98f, 0.4761f);
        phi.put(2.08f, 0.4812f);
        phi.put(2.18f, 0.4854f);
        phi.put(2.28f, 0.4887f);
        phi.put(2.38f, 0.4913f);
        phi.put(2.48f, 0.4934f);
        phi.put(2.58f, 0.4951f);
        phi.put(2.68f, 0.4963f);
        phi.put(2.78f, 0.4973f);
        phi.put(2.88f, 0.4980f);
        phi.put(2.98f, 0.4986f);
        phi.put(3.08f, 0.4990f);

        phi.put(0.09f, 0.0359f);
        phi.put(0.19f, 0.0753f);
        phi.put(0.29f, 0.1141f);
        phi.put(0.39f, 0.1517f);
        phi.put(0.49f, 0.1879f);
        phi.put(0.59f, 0.2224f);
        phi.put(0.69f, 0.2549f);
        phi.put(0.79f, 0.2852f);
        phi.put(0.89f, 0.3133f);
        phi.put(0.99f, 0.3389f);
        phi.put(1.09f, 0.3621f);
        phi.put(1.19f, 0.3830f);
        phi.put(1.29f, 0.4015f);
        phi.put(1.39f, 0.4177f);
        phi.put(1.49f, 0.4319f);
        phi.put(1.59f, 0.4441f);
        phi.put(1.69f, 0.4545f);
        phi.put(1.79f, 0.4633f);
        phi.put(1.89f, 0.4706f);
        phi.put(1.99f, 0.4767f);
        phi.put(2.09f, 0.4817f);
        phi.put(2.19f, 0.4857f);
        phi.put(2.29f, 0.4890f);
        phi.put(2.39f, 0.4916f);
        phi.put(2.49f, 0.4936f);
        phi.put(2.59f, 0.4952f);
        phi.put(2.69f, 0.4964f);
        phi.put(2.79f, 0.4974f);
        phi.put(2.89f, 0.4981f);
        phi.put(2.99f, 0.4986f);
        phi.put(3.09f, 0.4990f);
        
        
     

        // </editor-fold>
    }

    /**
     * Calculates the mean of a set of values.
     * 
     * @param values A set of values.
     * @return The mean of the given set of values.
     */
    private static float mean(float[]values){
        float sum = 0;

        for (float x : values)
            sum += x;

        return sum / values.length;
    }

    /**
     * Calculates the (population) variance of a set of values.
     * 
     * @param values A set of values.
     * @return The population variance of the given set of values.
     */
    private static float populationVariance(float[]values){
        float mean = mean(values);

        float sum = 0;
        for (float x : values) {
            sum += ((x - mean) * (x - mean));
        }

        return sum / values.length;
    }

    /**
     * Calculates the probability that a normally distributed variable is less than a value.
     * 
     * @param X		The upper bound (exclusive) of the test-region.
     * @param mean	The mean of the distribution.
     * @param stdv	The standard-deviation of the distribution.
     * @return Given a normally-distributed variable X~N(mean, stdv); p(x < X).
     */
    private float probabilityThatNormallyDistributedVariableIsLessThan(float X, float mean, float stdv){
        float y = (X - mean) / stdv;

        float lower = (float)Math.floor(Math.abs(y) * 100) / 100;
        float upper = (float)Math.ceil(Math.abs(y) * 100) / 100;

        float factor = (float)Math.abs(100 * y - Math.floor(100 * y));

       	Float lowerPhi = phi.get(lower);
        
        if (lowerPhi == null){
        	lowerPhi = 0.5f;
        }
        
        Float upperPhi = phi.get(upper);
        
        if (upperPhi == null){
        	upperPhi = 0.5f;
        }
        
        float lowerScore = 0.5f + (signum(y) * lowerPhi);
        float upperScore = 0.5f + (signum(y) * upperPhi);

        return lowerScore + (upperScore - lowerScore) * factor;
    }
    
    /**
     * Calculates the signum function of a value.
     * 
     * @param value The value.
     * @return 1 if the given value is greater than 0 or -1 if it is less than 0; otherwise, 0.
     */
    private static float signum(float value){
    	return ((value > 0) ? 1f : ((value < 0) ? -1f : value)); 
    }

    /**
     * Returns an 'event-syntax' representing a normally distributed variable with the mean and
     * standard deviation of a sample.
     * 
     * @param values A sample from a distribution.
     * @return An 'event-syntax' representing a normally distributed variable with the mean and
     * standard deviation of the given sample.
     */
    public EventSyntax NormallyDistributedVariable(float[] values){
        return NormallyDistributedVariable(mean(values), (float)Math.sqrt(populationVariance(values)));
    }

    /**
     * Returns an 'event-syntax' representing a normally distributed variable.
     * 
     * @param mean The mean of the distribution.
     * @param stdv The standard-deviation of the distribution.
     * @return An 'event-syntax' representing a normally distributed variable with the given
     * mean and variance.
     */
    public EventSyntax NormallyDistributedVariable(final float mean, final float stdv){
        return new EventSyntax() {
            @Override
            public float isLessThan(float X) {
                return probabilityThatNormallyDistributedVariableIsLessThan(X, mean, stdv);
            }

            @Override
            public float isGreaterThanOrEqualTo(float X) {
                return 1 - isLessThan(X);
            }

            @Override
            public EventRangeSyntax isWithinTheRange(final float lowerBoundInclusive){
                return new EventRangeSyntax() {
                    @Override
                    public float to(float upperBoundExclusive) {
                        return isLessThan(upperBoundExclusive) - isLessThan(lowerBoundInclusive);
                    }
                };
            }
        };
    }

    /**
     * Returns an 'event-syntax' representing a uniformly-distributed variable.
     * 
     * @param a The lower bound (inclusive) of the distribution.
     * @param b The upper bound (inclusive) of the distribution.
     * @return An 'event-syntax' representing a uniformly-distributed variable with the given
     * bounds.
     */
    public static EventSyntax UniformlyDistributedVariable(final float a, final float b){
        return new EventSyntax() {
            @Override
            public float isLessThan(float X) {
                return (X <= a) ? 0 : (X - a) / (b - a);
            }

            @Override
            public float isGreaterThanOrEqualTo(float X) {
                return 1 - isLessThan(X);
            }

            @Override
            public EventRangeSyntax isWithinTheRange(final float lowerBoundInclusive){
                return new EventRangeSyntax() {
                    @Override
                    public float to(float upperBoundExclusive) {
                        return isLessThan(upperBoundExclusive) - isLessThan(lowerBoundInclusive);
                    }
                };
            }
        };
    }

    /**
     * Represents a probability distribution, providing syntax to query various probabilities over
     * the distribution.
     * 
     * @author txb457
     *
     */
    public interface EventSyntax {
        public float isLessThan(float X);

        public float isGreaterThanOrEqualTo(float X);

        public EventRangeSyntax isWithinTheRange(final float lowerBoundInclusive);
    }

    /**
     * Used when specifying the upper-bound of a p(x within range) type query on distribution.
     * 
     * @author txb457
     *
     */
    public interface EventRangeSyntax {
        public float to(float upperBoundInclusive);
    }
}