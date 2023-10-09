package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.Constraint;
import src.OptimizationDirection;
import src.LinearProgramFormatter;


public class LinearProgram {
    private int numVariables;
    private OptimizationDirection optimizationDirection;
    private List<Double> objectiveCoeffs;
    private List<Constraint> constraints;
    private LinearProgramFormatter formatter;

    public LinearProgram(int numVariables, OptimizationDirection optimizationDirection) {
        this.numVariables = numVariables;
        this.optimizationDirection = optimizationDirection;
        objectiveCoeffs = new ArrayList<>(numVariables);
        this.constraints = new ArrayList<>();
        this.formatter = new LinearProgramFormatter();

        for (int i = 0; i < numVariables; i++) {
            objectiveCoeffs.add(0.0);
        }
    }

    public String toString() {
        return formatter.format(this);
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    public boolean isHideZeroCoefficients() {
        return formatter.isHideZeroCoefficients();
    }

    public void setHideZeroCoefficients(boolean hideZeroCoefficients) {
        formatter.setHideZeroCoefficients(hideZeroCoefficients);
    }

    public void setNumVariables(int numVariables) {
        this.numVariables = numVariables;
    }

    public int getNumVariables() {
        return numVariables;
    }

    public OptimizationDirection getOptimizationDirection() {
        return optimizationDirection;
    }

    public void setObjectiveCoeffs(List<Double> coefficients) {
        if (coefficients.size() != numVariables) {
            throw new IllegalArgumentException("Number of coefficients must match the number of variables");
        }
        this.objectiveCoeffs = coefficients;
    }

    public List<Double> getObjectiveCoeffs() {
        return objectiveCoeffs;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }
}

