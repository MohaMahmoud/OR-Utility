package ui.commands.algortihms.utility;

import java.util.ArrayList;
import java.util.List;

import model.ComparisonOperator;
import model.Constraint;
import model.LinearProgram;
import model.ObjectiveFunction;
import model.OptimizationDirection;

public class StandardFormCommand {
    private LinearProgram program;

    public StandardFormCommand(LinearProgram program) {
        this.program = program;
    }

    public void execute() {
        ObjectiveFunction function = program.getObjectiveFunction();

        // If the objective function is a MIN function then change it to MAX.
        if (function.getDirection().equals(OptimizationDirection.MIN)) {
            function.negate();
        }

        // Change all constrains to <=
        List<Constraint> constrains = program.getConstraints();
        List<Constraint> refactoredConstraints = new ArrayList<>();
        for (Constraint constraint : constrains) {
            switch (constraint.getOperator()) {
                case ComparisonOperator.GEQ:
                    constraint.negate();
                    break;
                case ComparisonOperator.EQ:
                    // split = in <= and >=
                    constraint.setOperator(ComparisonOperator.LEQ);
                    Constraint geqConstraint = constraint.copy();

                    // negate the >= constraint
                    geqConstraint.negate();
                    refactoredConstraints.add(geqConstraint);
                    break;
            }
            // in every case the (updated) constraint has to be added
            refactoredConstraints.add(constraint);
        }
        program.setConstraints(refactoredConstraints);

        /*
        // Change all solo constraints to >=
        List<Constraint> soloConstrains = program.getSoloConstraints();
        List<Constraint> refactoredSoloConstraints = new ArrayList<>();
        for (Constraint constraint : soloConstrains) {
            switch (constraint.getOperator()) {
                case ComparisonOperator.LEQ:
                    // TODO error
                    break
                case ComparisonOperator.EQ:
                    // TODO x = x- + x+
            }
            // in every case the (updated) constraint has to be added
            refactoredSoloConstraints.add(constraint);
        }
        program.setSoloConstraints(refactoredConstraints);*/
    }
}