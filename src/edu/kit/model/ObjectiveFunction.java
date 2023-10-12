package edu.kit.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectiveFunction {
    private OptimizationDirection direction;
    private List<Double> decisionVariables;

    public ObjectiveFunction(OptimizationDirection direction, int variableCount) {
        this.direction = direction;
        this.decisionVariables = new ArrayList<>(variableCount);
        for (int i = 0; i < variableCount; i++) {
            decisionVariables.add(0.0);
        }
    }

    public List<Double> getDecisionVariables() {
        return Collections.unmodifiableList(decisionVariables);
    }

    public void setDecisionVariables(List<Double> decisionVariables) {
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
            decisionVariables.set(i, -decisionVariables.get(i));
        }
    }

    public void updateLength(int newLength) {
        int oldLength = decisionVariables.size();

        if (oldLength < newLength) {
            List<Double> updatedDecisionVariables = new ArrayList<>(decisionVariables);
            for (int i = oldLength; i < newLength; i++) {
                updatedDecisionVariables.add(0.0);
            }
            decisionVariables = updatedDecisionVariables;
        } else if (oldLength > newLength) {
            List<Double> updatedDecisionVariables = decisionVariables.subList(0, newLength);
            decisionVariables = new ArrayList<>(updatedDecisionVariables);
        }
        // Do nothing if old length equals new length
    }

}
