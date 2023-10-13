package edu.kit.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectiveFunction {
    private OptimizationDirection direction;
    private List<DecisionVariable> decisionVariables;


    public ObjectiveFunction(OptimizationDirection direction, int variableCount) {
        this.direction = direction;
        this.decisionVariables = new ArrayList<>(variableCount);
        for (int i = 0; i < variableCount; i++) {
            decisionVariables.add(new DecisionVariable(i, 0.0, ComparisonOperator.GEQ));
        }
    }

    public List<DecisionVariable> getDecisionVariables() {
        return Collections.unmodifiableList(decisionVariables);
    }

    public List<Double> getCoefficients() {
        List<Double> coefficients = new ArrayList<>();
        for (DecisionVariable variable : decisionVariables) {
            coefficients.add(variable.getCoefficient());
        }
        return coefficients;
    }

    public void setDecisionVariables(List<DecisionVariable> decisionVariables) {
        this.decisionVariables = decisionVariables;
    }

    public OptimizationDirection getDirection() {
        return direction;
    }

    public void setDirection(OptimizationDirection direction) {
        this.direction = direction;
    }

    public void negate() {
        // Change the direction of the objective function;
        direction = (direction.equals(OptimizationDirection.MAX)) ? OptimizationDirection.MIN : OptimizationDirection.MAX;

        // Negate all the decision variables.
        for (int i = 0; i < decisionVariables.size(); i++) {
            DecisionVariable decisionVariable = decisionVariables.get(i);
            decisionVariable.setCoefficient(-decisionVariable.getCoefficient());
        }
    }

    public void updateLength(int newLength) {
        int oldLength = decisionVariables.size();

        if (oldLength < newLength) {
            List<DecisionVariable> updatedDecisionVariables = new ArrayList<>(decisionVariables);
            for (int i = oldLength; i < newLength; i++) {
                updatedDecisionVariables.add(new DecisionVariable(i, 0.0, ComparisonOperator.GEQ));
            }
            decisionVariables = updatedDecisionVariables;
        } else if (oldLength > newLength) {
            List<DecisionVariable> updatedDecisionVariables = decisionVariables.subList(0, newLength);
            decisionVariables = new ArrayList<>(updatedDecisionVariables);
        }
        // Do nothing if old length equals new length
    }

    public boolean areThereOnlyGeqSoloConstraints() {
        for (DecisionVariable decisionVariable : decisionVariables) {
            if (!decisionVariable.getOperator().equals(ComparisonOperator.GEQ)){
                return false;
            }
        }
        return true;
    }

}
