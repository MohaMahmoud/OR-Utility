package edu.kit.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kit.ui.util.StringUtility;

/**
 * Represents a linear program consisting of an objective function and a list of constraints.
 *
 * @author Mohammad Mahmoud
 * @author Oleksandr Shchetsura
 */
public class LinearProgram {
    private int variableCount = 3;
    private final ObjectiveFunction objectiveFunction;
    private List<Constraint> constraints;

    /**
     * Constructs a LinearProgram with default values.
     */
    public LinearProgram() {
        this.objectiveFunction = new ObjectiveFunction(OptimizationDirection.MAX, variableCount);
        this.constraints = new ArrayList<>();
    }

    /**
     * Get the number of variables in the linear program.
     *
     * @return The number of decision variables.
     */
    public int getVariableCount() {
        return objectiveFunction.getDecisionVariables().size();
    }

    /**
     * Set the number of variables in the linear program. And update the objective function & all constraints.
     * Add zeros if the count got bigger or remove last variables, if it got smaller.
     *
     * @param variableCount The new number of decision variables.
     */
    public void setVariableCount(int variableCount) {

        if (variableCount != this.variableCount) {
            // Update the objective function.
            objectiveFunction.updateLength(variableCount);

            // Update the constraints.
            for (Constraint constraint : constraints) {
                constraint.updateLength(variableCount);
            }

            // Update the variable count.
            this.variableCount = variableCount;
        }
    }

    /**
     * Adds given amount of zeros as slack variables in objective function, all constraints, solo constraints.
     *
     * @param amount of slack variables.
     */
    public void addSlackVariables(int amount) {
        objectiveFunction.addSlackVariables(amount);

        // Update the constraints.
        for (Constraint constraint : constraints) {
            constraint.updateLength(variableCount + amount);
        }

        // Update the variable count.
        this.variableCount += amount;
    }

    /**
     * Get the objective function of the linear program.
     *
     * @return The objective function.
     */
    public ObjectiveFunction getObjectiveFunction() {
        return objectiveFunction;
    }

    /**
     * Get an unmodifiable list of constraints in the linear program.
     *
     * @return An unmodifiable list of constraints.
     */
    public List<Constraint> getConstraints() {
        return Collections.unmodifiableList(constraints);
    }

    /**
     * Set the constraints of the linear program.
     *
     * @param constraints The new list of constraints.
     */
    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    /**
     * Add a constraint to the linear program.
     *
     * @param constraint The constraint to add.
     */
    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    /**
     * Remove a constraint from the linear program by its index.
     *
     * @param index The index of the constraint to remove.
     */
    public void removeConstraint(int index) {
        constraints.remove(index);
    }

    /**
     * Get a string representation of the linear program using the StringUtility class.
     *
     * @return A formatted string representation of the linear program.
     */
    @Override
    public String toString() {
        return StringUtility.format(this);
    }
}

