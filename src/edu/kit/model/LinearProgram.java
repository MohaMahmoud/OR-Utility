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

    public void setVariableCount(int numVariables) {
        this.variableCount = numVariables;
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

    /*
    private boolean areAllSoloConstraintsGeq() {
        for (SoloConstraint soloConstraint : constraints) {
            if (!soloConstraint.getOperator().equals(ComparisonOperator.GEQ)) {
                return false;
            }
        }
        return true;
    }
    */

    public ProgramForm getForm() {
        // if its a min objective function or solo constraints are not all >=
        if (objectiveFunction.getDirection().equals(OptimizationDirection.MIN) /* TODO || !areAllSoloConstraintsGeq()*/) {
            return ProgramForm.DEFAULT;
        }

        // check if there are only <= or = as operator and whether the right hand side only contains positive values
        boolean onlyLeq = true;
        boolean onlyEq = true;
        boolean rightSidePositive = true;
        for (Constraint constraint : constraints) {
            switch (constraint.getOperator()) {
                case GEQ: return ProgramForm.DEFAULT;
                case LEQ: onlyEq = false;
                case EQ: onlyLeq = false;
            }

            if (rightSidePositive && constraint.getRightHandSide() < 0.0) {
                rightSidePositive = false;
            }
        }

        if (!(onlyLeq || onlyEq)) {
            return ProgramForm.DEFAULT;
        }

        if (onlyLeq) {
            return ProgramForm.STANDARD;
        } else { // only eq's
            return (rightSidePositive) ? ProgramForm.CANONICAL : ProgramForm.NORMAL;
        }
    }

    @Override
    public String toString() {
        return StringUtility.format(this);
    }
}
