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
        List<Constraint> constrains = program.getConstraints();

        // If the objective function is a MIN function then change it to MAX.
        if (function.getDirection().equals(OptimizationDirection.MIN)) {
            function.negate();
        }

        // Change all constrains to <=.
        List<Constraint> standajhsdgfjha = new ArrayList<>();
        for (Constraint constraint : constrains) {
            if (constraint.getOperator().equals(ComparisonOperator.LEQ)) {
                standajhsdgfjha.add(constraint);
            } else if (constraint.getOperator().equals(ComparisonOperator.GEQ)) {
                constraint.negate();
                standajhsdgfjha.add(constraint);
            } else {
                constraint.setOperator(ComparisonOperator.LEQ);
                standajhsdgfjha.add(constraint);
                //TODO unfinished
            }
        }
    }
}