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

    public ComparisonOperator getOperator() {
        return operator;
    }

    public double getRightHandSide() {
        return rightHandSide;
    }
}
