package edu.kit.model;
import java.util.ArrayList;
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

    public void setCoefficients(List<Double> coefficients) {
        this.coefficients = coefficients;
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
        if (rightHandSide != 0.0) rightHandSide = -rightHandSide;
    }

    public Constraint copy() {
        // Copy all coefficients.
        List<Double> copiedCoefficients = new ArrayList<>(coefficients.size());
        for (Double coefficient : coefficients) {
            copiedCoefficients.add(Double.valueOf(coefficient));
        }

        // Return the deep copy.
        return new Constraint(copiedCoefficients, this.operator, this.rightHandSide);
    }
}
