package edu.kit.ui.operations.algorithms.tools;

import edu.kit.model.*;
import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;

public class GetFormOperation extends Operation {
    private static final String NAME = "/getform";
    private static final String DESCRIPTION = "Returns the highest form of the linear program.";

    private final LinearProgram program;

    public GetFormOperation(LinearProgram program) {
        super(NAME, DESCRIPTION);
        this.program = program;
    }

    @Override
    public String execute() throws OperationException {
        // if its a min objective function or solo constraints are not all >=
        if (program.getObjectiveFunction().getDirection().equals(OptimizationDirection.MIN)) {
            return ProgramForm.DEFAULT.toString();
        }

        // check if there are only <= or = as operator and whether the right hand side only contains positive values
        boolean onlyLeq = true;
        boolean onlyEq = true;
        boolean rightSidePositive = true;
        for (Constraint constraint : program.getConstraints()) {
            switch (constraint.getOperator()) {
                case GEQ: return ProgramForm.DEFAULT.toString();
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
        } else { // only eq's
            return (rightSidePositive ? ProgramForm.CANONICAL : ProgramForm.NORMAL).toString();
        }
    }
}
