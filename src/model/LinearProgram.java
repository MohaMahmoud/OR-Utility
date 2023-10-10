package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import view.LinearProgramFormatter;

public class LinearProgram {
    private int variableCount;
    private final ObjectiveFunction objectiveFunction;
    private List<Constraint> constraints;

    public LinearProgram(int variableCount, OptimizationDirection optimizationDirection) {
        this.variableCount = variableCount;
        this.objectiveFunction = new ObjectiveFunction(optimizationDirection, variableCount);
        this.constraints = new ArrayList<>();
    }

    public int getVariableCount() {
        return variableCount;
    }

    public void setVariableCount(int numVariables) {
        this.variableCount = numVariables;
    }

    public ObjectiveFunction getObjectiveFunction() {
        return objectiveFunction;
    }

    public List<Constraint> getConstraints() {
        return Collections.unmodifiableList(constraints);
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    public void removeConstraint(int index) {
        constraints.remove(index);
    }

    @Override
    public String toString() {
        return LinearProgramFormatter.format(this);
    }
}