package edu.kit.model;

public class DecisionVariable {
    private final int index;
    // coefficient in objective function
    private Double coefficient;
    private ComparisonOperator operator;
    private boolean negated;
    private boolean split;

    public DecisionVariable(int coefficientIndex, double coefficient, ComparisonOperator operator) {
        this.index = coefficientIndex;
        this.coefficient = coefficient;
        this.operator = operator;
        negated = false;
        split = false;
    }

    public int getIndex() {
        return index;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public ComparisonOperator getOperator() {
        return operator;
    }

    public void setOperator(ComparisonOperator operator) {
        this.operator = operator;
    }

    // use only if its <=
    public void negateLeq() {
        if (operator.equals(ComparisonOperator.LEQ)) {
            operator = ComparisonOperator.GEQ;
            coefficient = -coefficient;
            negated = true;
        }
    }

    public boolean isNegated() {
        return negated;
    }

    public void split() {
        if (operator.equals(ComparisonOperator.EQ)) {
            split = true;
        }
    }

    public boolean isSplit() {
        return split;
    }
}
