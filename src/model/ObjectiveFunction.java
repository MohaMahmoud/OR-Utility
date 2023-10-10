package model;

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
}