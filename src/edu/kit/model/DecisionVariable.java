package edu.kit.model;

/**
 * Represents a decision variable in a linear program.
 *
 * @author Oleksandr Shchetsura
 */
public class DecisionVariable {
    private int displayIndex;
    // Coefficient in the objective function.
    private Double coefficient;
    // Operator for the Solo Constraint
    private ComparisonOperator operator;
    // true, if its a slack variable
    private boolean slack;
    // x <= 0 -> x- >= 0
    private boolean negated;
    // x = 0 -> x+, x- >= 0
    private boolean split;

    /**
     * Constructs a DecisionVariable with the specified coefficient index, coefficient, and operator.
     *
     * @param coefficientIndex The index of the coefficient in the linear program.
     * @param coefficient      The coefficient value in the objective function.
     * @param operator         The comparison operator associated with the variable.
     */
    public DecisionVariable(int coefficientIndex, double coefficient, ComparisonOperator operator) {
        this.displayIndex = coefficientIndex + 1;
        this.coefficient = coefficient;
        this.operator = operator;
        slack = false;
        negated = false;
        split = false;
    }

    /**
     * Get the index to display with the decision variable. (x1...)
     *
     * @return The display index of the variable.
     */
    public int getDisplayIndex() {
        return displayIndex;
    }

    /**
     * Get the coefficient of the decision variable in the objective function.
     *
     * @return The coefficient value.
     */
    public Double getCoefficient() {
        return coefficient;
    }

    /**
     * Set the coefficient of the decision variable in the objective function.
     *
     * @param coefficient The new coefficient value.
     */
    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    /**
     * Get the comparison operator associated with the decision variable.
     *
     * @return The comparison operator.
     */
    public ComparisonOperator getOperator() {
        return operator;
    }

    /**
     * Set the comparison operator associated with the decision variable.
     *
     * @param operator The new comparison operator.
     */
    public void setOperator(ComparisonOperator operator) {
        this.operator = operator;
    }

    /**
     * Change variable type to slack and change display index.
     *
     * @index of the slack variable
     */
    public void turnSlack(int index) {
        slack = true;
        displayIndex = index + 1;
    }

    /**
     * Returns true, if variable is a slack variable.
     *
     * @return whether variable is a slack variable.
     */
    public boolean isSlack() {
        return slack;
    }

    /**
     * Negate the decision variable's coefficient if the operator is "LEQ" (less than or equal).
     * This is used to convert "LEQ" to "GEQ".
     */
    public void negateLeq() {
        if (operator.equals(ComparisonOperator.LEQ)) {
            operator = ComparisonOperator.GEQ;
            coefficient = -coefficient;
            negated = true;
        }
    }

    /**
     * Check if the decision variable's coefficient has been negated.
     *
     * @return True if the coefficient has been negated, otherwise false.
     */
    public boolean isNegated() {
        return negated;
    }

    /**
     * Split the decision variable if the operator is "EQ" (equal).
     * This is used to represent a variable as the difference of two non-negative variables.
     */
    public void split() {
        if (operator.equals(ComparisonOperator.EQ)) {
            split = true;
        }
    }

    /**
     * Check if the decision variable has been split.
     *
     * @return True if the variable has been split, otherwise false.
     */
    public boolean isSplit() {
        return split;
    }
}
