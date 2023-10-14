package edu.kit.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the objective function in a linear program.
 *
 * @author Mohammad Mahmoud
 * @author Oleksandr Shchetsura
 */
public class ObjectiveFunction {
    private OptimizationDirection direction;
    private List<DecisionVariable> decisionVariables;

    /**
     * Constructs an ObjectiveFunction with the specified optimization direction and variable count.
     *
     * @param direction      The optimization direction (MAX or MIN).
     * @param variableCount  The number of decision variables in the objective function.
     */
    public ObjectiveFunction(OptimizationDirection direction, int variableCount) {
        this.direction = direction;
        this.decisionVariables = new ArrayList<>(variableCount);

        // Initialize decision variables with default values.
        for (int i = 0; i < variableCount; i++) {
            decisionVariables.add(new DecisionVariable(i, 0.0, ComparisonOperator.GEQ));
        }
    }

    /**
     * Get the optimization direction (MAX or MIN).
     *
     * @return The optimization direction.
     */
    public OptimizationDirection getDirection() {
        return direction;
    }

    /**
     * Set the optimization direction (MAX or MIN).
     *
     * @param direction The new optimization direction.
     */
    public void setDirection(OptimizationDirection direction) {
        this.direction = direction;
    }

    /**
     * Get an unmodifiable list of decision variables in the objective function.
     *
     * @return An unmodifiable list of decision variables.
     */
    public List<DecisionVariable> getDecisionVariables() {
        return Collections.unmodifiableList(decisionVariables);
    }

    /**
     * Set the decision variables in the objective function.
     *
     * @param decisionVariables The new list of decision variables.
     */
    public void setDecisionVariables(List<DecisionVariable> decisionVariables) {
        this.decisionVariables = decisionVariables;
    }

    /**
     * Get the coefficients of all decision variables in the objective function.
     *
     * @return A list of coefficients.
     */
    public List<Double> getCoefficients() {
        List<Double> coefficients = new ArrayList<>();
        for (DecisionVariable variable : decisionVariables) {
            coefficients.add(variable.getCoefficient());
        }
        return coefficients;
    }

    /**
     * Negate the objective function by changing the direction and negating the coefficients of decision variables.
     */
    public void negate() {
        // Change the direction of the objective function;
        direction = (direction.equals(OptimizationDirection.MAX)) ? OptimizationDirection.MIN : OptimizationDirection.MAX;

        // Negate all the decision variables.
        for (DecisionVariable decisionVariable : decisionVariables) {
            decisionVariable.setCoefficient(-decisionVariable.getCoefficient());
        }
    }

    /**
     * Update the length of decision variables to match the new length.
     *
     * @param newLength The new length of decision variables.
     */
    public void updateLength(int newLength) {
        int oldLength = decisionVariables.size();

        if (oldLength < newLength) {
            List<DecisionVariable> updatedDecisionVariables = new ArrayList<>(decisionVariables);
            for (int i = oldLength; i < newLength; i++) {
                updatedDecisionVariables.add(new DecisionVariable(i, 0.0, ComparisonOperator.GEQ));
            }
            decisionVariables = updatedDecisionVariables;
        } else if (oldLength > newLength) {
            decisionVariables = new ArrayList<>(decisionVariables.subList(0, newLength));
        }
        // Do nothing if old length equals new length
    }

    /**
     * Check if there are less than or equal (LEQ) solo constraints in the objective function.
     *
     * @return True if there are LEQ solo constraints, otherwise false.
     */
    public boolean areThereLeqSoloConstraints() {
        for (DecisionVariable decisionVariable : decisionVariables) {
            if (decisionVariable.getOperator().equals(ComparisonOperator.LEQ)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if there are only greater than or equal (GEQ) solo constraints in the objective function.
     *
     * @return True if all constraints are GEQ, otherwise false.
     */
    public boolean areThereOnlyGeqSoloConstraints() {
        for (DecisionVariable decisionVariable : decisionVariables) {
            if (!decisionVariable.getOperator().equals(ComparisonOperator.GEQ)) {
                return false;
            }
        }
        return true;
    }
}
