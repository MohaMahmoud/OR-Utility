package edu.kit.ui.operations.algorithms.tools;

import java.util.ArrayList;
import java.util.List;

import edu.kit.model.*;
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

        modifyObjectiveFunction(function);
        modifyConstraints();
        modifyDecisionVariables(function);

        return SUCCESS;
    }

    private void modifyObjectiveFunction(ObjectiveFunction function) {
        // If the objective function is a MIN function then change it to MAX.
        if (function.getDirection().equals(OptimizationDirection.MIN)) {
            function.negate();
        }
    }

    private void modifyConstraints() {
        // Change all constrains to <=.
        List<Constraint> modifiedConstraints = new ArrayList<>();

        // modifiedConstraints.add(constraint); bei jedem angegeben, für die Reihenfolge
        // bei (=)
        for (Constraint constraint : program.getConstraints()) {
            switch (constraint.getOperator()) {
                case GEQ:
                    constraint.negate();
                    modifiedConstraints.add(constraint);
                    break;
                case EQ:
                    // split constraint in <= and >=.
                    Constraint geqConstraint = constraint.copy();

                    constraint.setOperator(ComparisonOperator.LEQ);
                    modifiedConstraints.add(constraint);

                    geqConstraint.setOperator(ComparisonOperator.GEQ);
                    geqConstraint.negate();
                    modifiedConstraints.add(geqConstraint);
                    break;
                default:
                    modifiedConstraints.add(constraint);
                    break;
            }
        }

        // Update the new constraints in the linear program.
        program.setConstraints(modifiedConstraints);
    }

    private void modifyDecisionVariables(ObjectiveFunction function) {
        if (!function.areThereOnlyGeqSoloConstraints()) {
            List<DecisionVariable> decisionVariables = function.getDecisionVariables();

            for (int i = 0; i < decisionVariables.size(); i++) {
                DecisionVariable decisionVariable = decisionVariables.get(i);
                switch (decisionVariable.getOperator()) {
                    case LEQ:
                        decisionVariable.negateLeq();

                        // Change the coefficient in every constraint
                        for (Constraint constraint : program.getConstraints()) {
                            constraint.setCoefficient(i, -constraint.getCoefficients().get(i));
                        }
                        break;
                    case EQ:
                        // x = x+ - x-
                        decisionVariable.split();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
