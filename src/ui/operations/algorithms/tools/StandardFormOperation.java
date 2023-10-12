package ui.operations.algorithms.tools;

import java.util.ArrayList;
import java.util.List;

import model.ComparisonOperator;
import model.Constraint;
import model.LinearProgram;
import model.ObjectiveFunction;
import model.OptimizationDirection;
import ui.Operation;
import ui.OperationException;

public class StandardFormOperation extends Operation {
    private static final String NAME = "/standardform";
    private static final String DESCRIPTION = "Puts the linear program in the standard form.";

    private LinearProgram program;

    public StandardFormOperation(LinearProgram program) {
        super(NAME, DESCRIPTION);
        this.program = program;
    }

    @Override
    public String execute(String[] args) throws OperationException {
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
        return SUCCESS;
    }
}
