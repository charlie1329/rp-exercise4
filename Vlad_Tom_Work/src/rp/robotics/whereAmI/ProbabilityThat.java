package rp.robotics.whereAmI;

import java.util.HashMap;

    public class ProbabilityThat {

        private final HashMap<Double, Double> phi;

        public ProbabilityThat() {
            // <editor-fold desc="Z-Scores">

            phi = new HashMap<Double, Double>();

            phi.put(0.00, 0.0000);
            phi.put(0.10, 0.0398);
            phi.put(0.20, 0.0793);
            phi.put(0.30, 0.1179);
            phi.put(0.40, 0.1554);
            phi.put(0.50, 0.1915);
            phi.put(0.60, 0.2257);
            phi.put(0.70, 0.2580);
            phi.put(0.80, 0.2881);
            phi.put(0.90, 0.3159);
            phi.put(1.00, 0.3413);
            phi.put(1.10, 0.3643);
            phi.put(1.20, 0.3849);
            phi.put(1.30, 0.4032);
            phi.put(1.40, 0.4192);
            phi.put(1.50, 0.4332);
            phi.put(1.60, 0.4452);
            phi.put(1.70, 0.4554);
            phi.put(1.80, 0.4641);
            phi.put(1.90, 0.4713);
            phi.put(2.00, 0.4772);
            phi.put(2.10, 0.4821);
            phi.put(2.20, 0.4861);
            phi.put(2.30, 0.4893);
            phi.put(2.40, 0.4918);
            phi.put(2.50, 0.4938);
            phi.put(2.60, 0.4953);
            phi.put(2.70, 0.4965);
            phi.put(2.80, 0.4974);
            phi.put(2.90, 0.4981);
            phi.put(3.00, 0.4987);

            phi.put(0.01, 0.0040);
            phi.put(0.11, 0.0438);
            phi.put(0.21, 0.0832);
            phi.put(0.31, 0.1217);
            phi.put(0.41, 0.1591);
            phi.put(0.51, 0.1950);
            phi.put(0.61, 0.2291);
            phi.put(0.71, 0.2611);
            phi.put(0.81, 0.2910);
            phi.put(0.91, 0.3186);
            phi.put(1.01, 0.3438);
            phi.put(1.11, 0.3665);
            phi.put(1.21, 0.3869);
            phi.put(1.31, 0.4049);
            phi.put(1.41, 0.4207);
            phi.put(1.51, 0.4345);
            phi.put(1.61, 0.4463);
            phi.put(1.71, 0.4564);
            phi.put(1.81, 0.4649);
            phi.put(1.91, 0.4719);
            phi.put(2.01, 0.4778);
            phi.put(2.11, 0.4826);
            phi.put(2.21, 0.4864);
            phi.put(2.31, 0.4896);
            phi.put(2.41, 0.4920);
            phi.put(2.51, 0.4940);
            phi.put(2.61, 0.4955);
            phi.put(2.71, 0.4966);
            phi.put(2.81, 0.4975);
            phi.put(2.91, 0.4982);
            phi.put(3.01, 0.4987);

            phi.put(0.02, 0.0080);
            phi.put(0.12, 0.0478);
            phi.put(0.22, 0.0871);
            phi.put(0.32, 0.1255);
            phi.put(0.42, 0.1628);
            phi.put(0.52, 0.1985);
            phi.put(0.62, 0.2324);
            phi.put(0.72, 0.2642);
            phi.put(0.82, 0.2939);
            phi.put(0.92, 0.3212);
            phi.put(1.02, 0.3461);
            phi.put(1.12, 0.3686);
            phi.put(1.22, 0.3888);
            phi.put(1.32, 0.4066);
            phi.put(1.42, 0.4222);
            phi.put(1.52, 0.4357);
            phi.put(1.62, 0.4474);
            phi.put(1.72, 0.4573);
            phi.put(1.82, 0.4656);
            phi.put(1.92, 0.4726);
            phi.put(2.02, 0.4783);
            phi.put(2.12, 0.4830);
            phi.put(2.22, 0.4868);
            phi.put(2.32, 0.4898);
            phi.put(2.42, 0.4922);
            phi.put(2.52, 0.4941);
            phi.put(2.62, 0.4956);
            phi.put(2.72, 0.4967);
            phi.put(2.82, 0.4976);
            phi.put(2.92, 0.4982);
            phi.put(3.02, 0.4987);

            phi.put(0.03, 0.0120);
            phi.put(0.13, 0.0517);
            phi.put(0.23, 0.0910);
            phi.put(0.33, 0.1293);
            phi.put(0.43, 0.1664);
            phi.put(0.53, 0.2019);
            phi.put(0.63, 0.2357);
            phi.put(0.73, 0.2673);
            phi.put(0.83, 0.2967);
            phi.put(0.93, 0.3238);
            phi.put(1.03, 0.3485);
            phi.put(1.13, 0.3708);
            phi.put(1.23, 0.3907);
            phi.put(1.33, 0.4082);
            phi.put(1.43, 0.4236);
            phi.put(1.53, 0.4370);
            phi.put(1.63, 0.4484);
            phi.put(1.73, 0.4582);
            phi.put(1.83, 0.4664);
            phi.put(1.93, 0.4732);
            phi.put(2.03, 0.4788);
            phi.put(2.13, 0.4834);
            phi.put(2.23, 0.4871);
            phi.put(2.33, 0.4901);
            phi.put(2.43, 0.4925);
            phi.put(2.53, 0.4943);
            phi.put(2.63, 0.4957);
            phi.put(2.73, 0.4968);
            phi.put(2.83, 0.4977);
            phi.put(2.93, 0.4983);
            phi.put(3.03, 0.4988);

            phi.put(0.04, 0.0160);
            phi.put(0.14, 0.0557);
            phi.put(0.24, 0.0948);
            phi.put(0.34, 0.1331);
            phi.put(0.44, 0.1700);
            phi.put(0.54, 0.2054);
            phi.put(0.64, 0.2389);
            phi.put(0.74, 0.2704);
            phi.put(0.84, 0.2995);
            phi.put(0.94, 0.3264);
            phi.put(1.04, 0.3508);
            phi.put(1.14, 0.3729);
            phi.put(1.24, 0.3925);
            phi.put(1.34, 0.4099);
            phi.put(1.44, 0.4251);
            phi.put(1.54, 0.4382);
            phi.put(1.64, 0.4495);
            phi.put(1.74, 0.4591);
            phi.put(1.84, 0.4671);
            phi.put(1.94, 0.4738);
            phi.put(2.04, 0.4793);
            phi.put(2.14, 0.4838);
            phi.put(2.24, 0.4875);
            phi.put(2.34, 0.4904);
            phi.put(2.44, 0.4927);
            phi.put(2.54, 0.4945);
            phi.put(2.64, 0.4959);
            phi.put(2.74, 0.4969);
            phi.put(2.84, 0.4977);
            phi.put(2.94, 0.4984);
            phi.put(3.04, 0.4988);

            phi.put(0.05, 0.0199);
            phi.put(0.15, 0.0596);
            phi.put(0.25, 0.0987);
            phi.put(0.35, 0.1368);
            phi.put(0.45, 0.1736);
            phi.put(0.55, 0.2088);
            phi.put(0.65, 0.2422);
            phi.put(0.75, 0.2734);
            phi.put(0.85, 0.3023);
            phi.put(0.95, 0.3289);
            phi.put(1.05, 0.3531);
            phi.put(1.15, 0.3749);
            phi.put(1.25, 0.3944);
            phi.put(1.35, 0.4115);
            phi.put(1.45, 0.4265);
            phi.put(1.55, 0.4394);
            phi.put(1.65, 0.4505);
            phi.put(1.75, 0.4599);
            phi.put(1.85, 0.4678);
            phi.put(1.95, 0.4744);
            phi.put(2.05, 0.4798);
            phi.put(2.15, 0.4842);
            phi.put(2.25, 0.4878);
            phi.put(2.35, 0.4906);
            phi.put(2.45, 0.4929);
            phi.put(2.55, 0.4946);
            phi.put(2.65, 0.4960);
            phi.put(2.75, 0.4970);
            phi.put(2.85, 0.4978);
            phi.put(2.95, 0.4984);
            phi.put(3.05, 0.4989);

            phi.put(0.06, 0.0239);
            phi.put(0.16, 0.0636);
            phi.put(0.26, 0.1026);
            phi.put(0.36, 0.1406);
            phi.put(0.46, 0.1772);
            phi.put(0.56, 0.2123);
            phi.put(0.66, 0.2454);
            phi.put(0.76, 0.2764);
            phi.put(0.86, 0.3051);
            phi.put(0.96, 0.3315);
            phi.put(1.06, 0.3554);
            phi.put(1.16, 0.3770);
            phi.put(1.26, 0.3962);
            phi.put(1.36, 0.4131);
            phi.put(1.46, 0.4279);
            phi.put(1.56, 0.4406);
            phi.put(1.66, 0.4515);
            phi.put(1.76, 0.4608);
            phi.put(1.86, 0.4686);
            phi.put(1.96, 0.4750);
            phi.put(2.06, 0.4803);
            phi.put(2.16, 0.4846);
            phi.put(2.26, 0.4881);
            phi.put(2.36, 0.4909);
            phi.put(2.46, 0.4931);
            phi.put(2.56, 0.4948);
            phi.put(2.66, 0.4961);
            phi.put(2.76, 0.4971);
            phi.put(2.86, 0.4979);
            phi.put(2.96, 0.4985);
            phi.put(3.06, 0.4989);

            phi.put(0.07, 0.0279);
            phi.put(0.17, 0.0675);
            phi.put(0.27, 0.1064);
            phi.put(0.37, 0.1443);
            phi.put(0.47, 0.1808);
            phi.put(0.57, 0.2157);
            phi.put(0.67, 0.2486);
            phi.put(0.77, 0.2794);
            phi.put(0.87, 0.3078);
            phi.put(0.97, 0.3340);
            phi.put(1.07, 0.3577);
            phi.put(1.17, 0.3790);
            phi.put(1.27, 0.3980);
            phi.put(1.37, 0.4147);
            phi.put(1.47, 0.4292);
            phi.put(1.57, 0.4418);
            phi.put(1.67, 0.4525);
            phi.put(1.77, 0.4616);
            phi.put(1.87, 0.4693);
            phi.put(1.97, 0.4756);
            phi.put(2.07, 0.4808);
            phi.put(2.17, 0.4850);
            phi.put(2.27, 0.4884);
            phi.put(2.37, 0.4911);
            phi.put(2.47, 0.4932);
            phi.put(2.57, 0.4949);
            phi.put(2.67, 0.4962);
            phi.put(2.77, 0.4972);
            phi.put(2.87, 0.4979);
            phi.put(2.97, 0.4985);
            phi.put(3.07, 0.4989);

            phi.put(0.08, 0.0319);
            phi.put(0.18, 0.0714);
            phi.put(0.28, 0.1103);
            phi.put(0.38, 0.1480);
            phi.put(0.48, 0.1844);
            phi.put(0.58, 0.2190);
            phi.put(0.68, 0.2517);
            phi.put(0.78, 0.2823);
            phi.put(0.88, 0.3106);
            phi.put(0.98, 0.3365);
            phi.put(1.08, 0.3599);
            phi.put(1.18, 0.3810);
            phi.put(1.28, 0.3997);
            phi.put(1.38, 0.4162);
            phi.put(1.48, 0.4306);
            phi.put(1.58, 0.4429);
            phi.put(1.68, 0.4535);
            phi.put(1.78, 0.4625);
            phi.put(1.88, 0.4699);
            phi.put(1.98, 0.4761);
            phi.put(2.08, 0.4812);
            phi.put(2.18, 0.4854);
            phi.put(2.28, 0.4887);
            phi.put(2.38, 0.4913);
            phi.put(2.48, 0.4934);
            phi.put(2.58, 0.4951);
            phi.put(2.68, 0.4963);
            phi.put(2.78, 0.4973);
            phi.put(2.88, 0.4980);
            phi.put(2.98, 0.4986);
            phi.put(3.08, 0.4990);

            phi.put(0.09, 0.0359);
            phi.put(0.19, 0.0753);
            phi.put(0.29, 0.1141);
            phi.put(0.39, 0.1517);
            phi.put(0.49, 0.1879);
            phi.put(0.59, 0.2224);
            phi.put(0.69, 0.2549);
            phi.put(0.79, 0.2852);
            phi.put(0.89, 0.3133);
            phi.put(0.99, 0.3389);
            phi.put(1.09, 0.3621);
            phi.put(1.19, 0.3830);
            phi.put(1.29, 0.4015);
            phi.put(1.39, 0.4177);
            phi.put(1.49, 0.4319);
            phi.put(1.59, 0.4441);
            phi.put(1.69, 0.4545);
            phi.put(1.79, 0.4633);
            phi.put(1.89, 0.4706);
            phi.put(1.99, 0.4767);
            phi.put(2.09, 0.4817);
            phi.put(2.19, 0.4857);
            phi.put(2.29, 0.4890);
            phi.put(2.39, 0.4916);
            phi.put(2.49, 0.4936);
            phi.put(2.59, 0.4952);
            phi.put(2.69, 0.4964);
            phi.put(2.79, 0.4974);
            phi.put(2.89, 0.4981);
            phi.put(2.99, 0.4986);
            phi.put(3.09, 0.4990);

            // </editor-fold>
        }

        private static double mean(double...values){
            double Σ = 0;

            for (double x : values)
                Σ += x;

            return Σ / values.length;
        }

        private static double populationVariance(double...values){
            double mean = mean(values);

            double Σ = 0;
            for (double x : values) {
                Σ += ((x - mean) * (x - mean));
            }

            return Σ / values.length;
        }

        private double probabilityThatNormallyDistributedVariableIsLessThan(double X, double mean, double σ){
            double y = (X - mean) / σ;

            double lower = Math.floor(Math.abs(y) * 100) / 100;
            double upper = Math.ceil(Math.abs(y) * 100) / 100;

            double factor = Math.abs(100 * y - Math.floor(100 * y));

            double lowerScore = 0.5 + (Math.signum(y) * phi.getOrDefault(lower, 0.5));
            double upperScore = 0.5 + (Math.signum(y) * phi.getOrDefault(upper, 0.5));

            return lowerScore + (upperScore - lowerScore) * factor;
        }

        public EventSyntax NormallyDistributedVariable(double... values){
            return NormallyDistributedVariable(mean(values), Math.sqrt(populationVariance(values)));
        }

        public EventSyntax NormallyDistributedVariable(final double mean, final double σ){
            return new EventSyntax() {
                @Override
                public double isLessThan(double X) {
                    return probabilityThatNormallyDistributedVariableIsLessThan(X, mean, σ);
                }

                @Override
                public double isGreaterThanOrEqualTo(double X) {
                    return 1 - isLessThan(X);
                }

                @Override
                public EventRangeSyntax isWithinTheRange(final double lowerBoundInclusive){
                    return new EventRangeSyntax() {
                        @Override
                        public double to(double upperBoundExclusive) {
                            return isLessThan(upperBoundExclusive) - isLessThan(lowerBoundInclusive);
                        }
                    };
                }
            };
        }

        public static EventSyntax UniformlyDistributedVariable(final double a, final double b){
            return new EventSyntax() {
                @Override
                public double isLessThan(double X) {
                    return (X <= a) ? 0 : (X - a) / (b - a);
                }

                @Override
                public double isGreaterThanOrEqualTo(double X) {
                    return 1 - isLessThan(X);
                }

                @Override
                public EventRangeSyntax isWithinTheRange(final double lowerBoundInclusive){
                    return new EventRangeSyntax() {
                        @Override
                        public double to(double upperBoundExclusive) {
                            return isLessThan(upperBoundExclusive) - isLessThan(lowerBoundInclusive);
                        }
                    };
                }
            };
        }

        public interface EventSyntax {
            public double isLessThan(double X);

            public double isGreaterThanOrEqualTo(double X);

            public EventRangeSyntax isWithinTheRange(final double lowerBoundInclusive);
        }

        public interface EventRangeSyntax {
            public double to(double upperBoundInclusive);
        }
    }