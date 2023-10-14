package edu.kit.ui.operations.algorithms.tools;

import edu.kit.model.*;
import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;

/**
 * An operation to determine the form of a linear program.
 */
public class GetFormOperation extends Operation {
    private static final String NAME = "/getform";
    private static final String DESCRIPTION = "Returns the highest form of the linear program.";

    private final LinearProgram program;

    /**
     * Constructs a GetFormOperation with the specified linear program.
     *
     * @param program The linear program to analyze.
     */
    public GetFormOperation(LinearProgram program) {
        super(NAME, DESCRIPTION);
        this.program = program;
    }

    /**
     * Execute the operation and return the highest form of the linear program.
     *
     * @return The highest form of the linear program.
     * @throws OperationException If an error occurs during execution.
     */
    @Override
    public String execute() throws OperationException {
        ObjectiveFunction objectiveFunction = program.getObjectiveFunction();

        // Check if it's a minimization objective function or solo constraints are not all >= or =.
        if (objectiveFunction.getDirection().equals(OptimizationDirection.MIN) || objectiveFunction.areThereLeqSoloConstraints()) {
            return ProgramForm.DEFAULT.toString();
        }

        // Check if there are only <= or = as operators and whether the right-hand side only contains positive values.
        boolean onlyLeq = true;
        boolean onlyEq = true;
        boolean rightSidePositive = true;
        for (Constraint constraint : program.getConstraints()) {
            switch (constraint.getOperator()) {
                case GEQ:
                    return ProgramForm.DEFAULT.toString();
                case LEQ:
                    onlyEq = false;
                    break;
                case EQ:
                    onlyLeq = false;
                    break;
            }

            if (rightSidePositive && constraint.getRightHandSide() < 0.0) {
                rightSidePositive = false;
            }
        }

        if (!(onlyLeq || onlyEq)) {
            return ProgramForm.DEFAULT.toString();
        }

        if (onlyLeq) {
            return ProgramForm.STANDARD.toString();
        } else { // Only eq's
            return (rightSidePositive ? ProgramForm.CANONICAL : ProgramForm.NORMAL).toString();
        }
    }
}

