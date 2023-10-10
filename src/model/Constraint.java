package model;
import java.util.Collections;
import java.util.List;

public class Constraint {
    private List<Double> coefficients;
    private ComparisonOperator operator;
    private double rightHandSide;

    public Constraint(List<Double> coefficients, ComparisonOperator operator, double rightHandSide) {
        this.coefficients = coefficients;
        this.operator = operator;
        this.rightHandSide = rightHandSide;
    }

    public List<Double> getCoefficients() {
        return Collections.unmodifiableList(coefficients);
    }


    public double getRightHandSide() {
        return rightHandSide;
    }

    public ComparisonOperator getOperator() {
        return operator;
    }

    public void setOperator(ComparisonOperator operator) {
        this.operator = operator;
    }

    public void negate() {
        // Negate the Operator.
        if (!operator.equals(ComparisonOperator.EQ)) {
            operator = (operator.equals(ComparisonOperator.LEQ)) ? ComparisonOperator.GEQ : ComparisonOperator.LEQ;
        }

        // Negate all the coefficients.
        for (int i = 0; i < coefficients.size(); i++) {
            coefficients.set(i, -coefficients.get(i));
        }

        // Negate the right hand side.
        rightHandSide = -rightHandSide;
    }
}
