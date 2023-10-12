package edu.kit.ui.operations.algorithms.tools;

import java.util.ArrayList;
import java.util.List;

import edu.kit.model.ComparisonOperator;
import edu.kit.model.Constraint;
import edu.kit.model.LinearProgram;
import edu.kit.model.ObjectiveFunction;
import edu.kit.model.OptimizationDirection;
import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;

public class StandardFormOperation extends Operation {
    private static final String NAME = "/standardform";
    private static final String DESCRIPTION = "Puts the linear program in the standard form.";

    private final LinearProgram program;

    public StandardFormOperation(LinearProgram program) {
        super(NAME, DESCRIPTION);
        this.program = program;
    }

    @Override
    public String execute() throws OperationException {
        ObjectiveFunction function = program.getObjectiveFunction();

        // If the objective function is a MIN function then change it to MAX.
        if (function.getDirection().equals(OptimizationDirection.MIN))
            function.negate();

        // Change all constrains to <=.
        List<Constraint> constrains = new ArrayList<>();
        for (Constraint constraint : program.getConstraints()) {
            switch (constraint.getOperator()) {
                case GEQ:
                    constraint.negate();
                    break;
                case EQ:
                    // split constraint in <= and >=.
                    constraint.setOperator(ComparisonOperator.LEQ);
                    Constraint geqConstraint = constraint.copy();
                    geqConstraint.setOperator(ComparisonOperator.GEQ);
                    geqConstraint.negate();
                    constrains.add(geqConstraint);
                    break;
                default:
                    break;
            }
            constrains.add(constraint);
        }

        // Update the new constraints in the linear program.
        program.setConstraints(constrains);

        /*
        // Change all solo constraints to >=
        List<Constraint> refactoredSoloConstraints = new ArrayList<>();
        for (Constraint constraint : program.getSoloConstraints()) {
            switch (constraint.getOperator()) {
                case ComparisonOperator.LEQ:
                    // TODO error
                    break
                case ComparisonOperator.EQ:
                    // TODO x = x- + x+
            }
            refactoredSoloConstraints.add(constraint);
        }
        program.setSoloConstraints(refactoredConstraints);
        */

        return SUCCESS;
    }
}
