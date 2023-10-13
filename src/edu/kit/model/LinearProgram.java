package edu.kit.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kit.ui.util.StringUtility;

public class LinearProgram {
    private int variableCount = 3;
    private final ObjectiveFunction objectiveFunction;
    private List<Constraint> constraints;

    public LinearProgram() {
        this.objectiveFunction = new ObjectiveFunction(OptimizationDirection.MAX, variableCount);
        this.constraints = new ArrayList<>();
    }

    public int getVariableCount() {
        return variableCount;
    }

    public void setVariableCount(int variableCount) {

        if (variableCount != this.variableCount) {
            // Update objective function.
            objectiveFunction.updateLength(variableCount);

            // Update constraints.
            for (Constraint constraint : constraints) {
                constraint.updateLength(variableCount);
            }

            // Update variable count.
            this.variableCount = variableCount;
        }

    }

    public ObjectiveFunction getObjectiveFunction() {
        return objectiveFunction;
    }

    public List<Constraint> getConstraints() {
        return Collections.unmodifiableList(constraints);
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    public void removeConstraint(int index) {
        constraints.remove(index);
    }

    @Override
    public String toString() {
        return StringUtility.format(this);
    }
}