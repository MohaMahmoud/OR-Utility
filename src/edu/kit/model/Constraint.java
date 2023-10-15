package edu.kit.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a constraint in a linear program.
 *
 * @author Mohammad Mahmoud
 * @author Oleksandr Shchetsura
 */
public class Constraint {
    private List<Double> coefficients;
    private ComparisonOperator operator;
    private double rightHandSide;

    /**
     * Constructs a constraint with the specified coefficients, operator, and right-hand side value.
     *
     * @param coefficients    The coefficients of the constraint.
     * @param operator        The comparison operator (LEQ, GEQ, or EQ).
     * @param rightHandSide   The right-hand side value of the constraint.
     */
    public Constraint(List<Double> coefficients, ComparisonOperator operator, double rightHandSide) {
        this.coefficients = coefficients;
        this.operator = operator;
        this.rightHandSide = rightHandSide;
    }

    /**
     * Get an unmodifiable list of coefficients in the constraint.
     *
     * @return An unmodifiable list of coefficients.
     */
    public List<Double> getCoefficients() {
        return Collections.unmodifiableList(coefficients);
    }

    /**
     * Set the coefficients of the constraint.
     *
     * @param coefficients The new list of coefficients.
     */
    public void setCoefficients(List<Double> coefficients) {
        this.coefficients = coefficients;
    }

    /**
     * Set a specific coefficient by index.
     *
     * @param index         The index of the coefficient to be updated.
     * @param newCoefficient The new coefficient value.
     */
    public void setCoefficient(int index, double newCoefficient) {
        List<Double> updatedCoefficients = new ArrayList<>();

        for (int j = 0; j < coefficients.size(); j++) {
            if (index == j) {
                updatedCoefficients.add(newCoefficient);
            } else {
                updatedCoefficients.add(coefficients.get(j));
            }
        }

        coefficients = updatedCoefficients;
    }

    /**
     * Get the comparison operator (LEQ, GEQ, or EQ) of the constraint.
     *
     * @return The comparison operator.
     */
    public ComparisonOperator getOperator() {
        return operator;
    }

    /**
     * Set the comparison operator (LEQ, GEQ, or EQ) of the constraint.
     *
     * @param operator The new comparison operator.
     */
    public void setOperator(ComparisonOperator operator) {
        this.operator = operator;
    }

    /**
     * Get the right-hand side value of the constraint.
     *
     * @return The right-hand side value.
     */
    public double getRightHandSide() {
        return rightHandSide;
    }

    /**
     * Negate the constraint by changing the operator and negating the coefficients and right-hand side.
     */
    public void negate() {
        // Negate the operator.
        if (!operator.equals(ComparisonOperator.EQ)) {
            operator = (operator.equals(ComparisonOperator.LEQ)) ? ComparisonOperator.GEQ : ComparisonOperator.LEQ;
        }

        // Negate all the coefficients.
        for (int i = 0; i < coefficients.size(); i++) {
            coefficients.set(i, -coefficients.get(i));
        }

        // Negate the right-hand side.
        if (rightHandSide != 0.0) rightHandSide = -rightHandSide;
    }

    /**
     * Update the length of coefficients to match the new length.
     *
     * @param newLength The new length of coefficients.
     */
    public void updateLength(int newLength) {
        int oldLength = coefficients.size();

        if (oldLength < newLength) {
            List<Double> updatedDecisionVariables = new ArrayList<>(coefficients);
            for (int i = oldLength; i < newLength; i++) {
                updatedDecisionVariables.add(0.0);
            }
            coefficients = updatedDecisionVariables;
        } else if (oldLength > newLength) {
            List<Double> updatedDecisionVariables = coefficients.subList(0, newLength);
            coefficients = new ArrayList<>(updatedDecisionVariables);
        }
        // Do nothing if old length equals new length
    }

    /**
     * Create a deep copy of the constraint.
     *
     * @return A deep copy of the constraint.
     */
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

