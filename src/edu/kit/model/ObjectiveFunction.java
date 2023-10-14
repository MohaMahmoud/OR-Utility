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
    private boolean hasSlackVariables;
    // structure = original, not slack
    private int amountOfStructureVariables;

    /**
     * Constructs an ObjectiveFunction with the specified optimization direction and variable count.
     *
     * @param direction      The optimization direction (MAX or MIN).
     * @param variableCount  The number of decision variables in the objective function.
     */
    public ObjectiveFunction(OptimizationDirection direction, int variableCount) {
        this.direction = direction;
        this.decisionVariables = new ArrayList<>(variableCount);
        hasSlackVariables = false;

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
     * Use only before NormalFormOperation.
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
     * Use only before NormalFormOperation.
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
     * Use only before NormalFormOperation.
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
     * Adds given amount of zeroes to the decision variables and stores the indices of newly added slack variables.
     *
     * @param amount of slack variables.
     */
    public void addSlackVariables(int amount) {
        int originalVariableAmount = decisionVariables.size();
        hasSlackVariables = true;
        amountOfStructureVariables = originalVariableAmount;
        updateLength(originalVariableAmount + amount);
    }

    /**
     * Returns true, if function has slack variables.
     *
     * @return whether function has slack variables.
     */
    public boolean hasSlackVariables() {
        return hasSlackVariables;
    }

    /**
     * Returns the index of the first slack variable or unreachable index, if there are no slack variables.
     *
     * @return the index of the first slack variable.
     */
    public int getAmountOfStructureVariables() {
        return amountOfStructureVariables;
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
