package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.LinearProgramFormatter;

public class LinearProgram {
    private int variableCount;
    private final ObjectiveFunction objectiveFunction;
    private List<Constraint> constraints;
    // private List<SoloConstraint> soloConstraints;

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

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    public void removeConstraint(int index) {
        constraints.remove(index);
    }

    /*private boolean areAllSoloConstraintsGeq() {
        for (SoloConstraint soloConstraint : constraints) {
            if (!soloConstraint.getOperator().equals(ComparisonOperator.GEQ)) {
                return false;
            }
        }
        return true;
    }*/

    public ProgramForm getMaxForm() {
        // if its a min objective function or solo constraints are not all >=
        if (objectiveFunction.getDirection().equals(OptimizationDirection.MIN) /*|| !areAllSoloConstraintsGeq()*/) {
            return ProgramForm.DEFAULT;
        }

        // check if there are only <= or = as operator and whether the right hand side only contains positive values
        boolean onlyLeq = true;
        boolean onlyEq = true;
        boolean rightSidePositive = true;
        for (Constraint constraint : constraints) {
            switch (constraint.getOperator()) {
                case ComparisonOperator.GEQ: return ProgramForm.DEFAULT;
                case ComparisonOperator.LEQ: onlyEq = false;
                case ComparisonOperator.EQ: onlyLeq = false;
            }

            if (constraint.getRightHandSide() < 0.0) {
                rightSidePositive = false;
            }
        }

        if (!(onlyLeq || onlyEq)) {
            return ProgramForm.DEFAULT;
        }

        if (onlyLeq) {
            return ProgramForm.Standard;
        } else {
            // only eq's
            return (rightSidePositive) ? ProgramForm.CANONICAL : ProgramForm.NORMAL;
        }
    }

    @Override
    public String toString() {
        return LinearProgramFormatter.format(this);
    }
}
