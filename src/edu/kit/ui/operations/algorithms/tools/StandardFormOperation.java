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

        // If the objective function is a MIN function then change it to MAX.
        if (function.getDirection().equals(OptimizationDirection.MIN))
            function.negate();

        modifyConstraints();

        modifyDecisionVariables();

        return SUCCESS;
    }

    private void modifyConstraints() {
        // Change all constrains to <=.
        List<Constraint> constraints = new ArrayList<>();
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
                    constraints.add(geqConstraint);
                    break;
                default:
                    break;
            }
            constraints.add(constraint);
        }

        // Update the new constraints in the linear program.
        program.setConstraints(constraints);
    }

    private void modifyDecisionVariables() {
        ObjectiveFunction objectiveFunction = program.getObjectiveFunction();
        if (!objectiveFunction.areThereOnlyGeqSoloConstraints()) {
            List<DecisionVariable> decisionVariables = objectiveFunction.getDecisionVariables();

            for (int i = 0; i < decisionVariables.size(); i++) {
                DecisionVariable decisionVariable = decisionVariables.get(i);
                switch (decisionVariable.getOperator()) {
                    case LEQ:
                        decisionVariable.negateLeq();
                        for (Constraint constraint : program.getConstraints()) {
                            double oldCoefficient = constraint.getCoefficients().get(i);
                            decisionVariables.set(i, -oldCoefficient);
                        }
                        break;
                    case EQ:
                        // split constraint in <= and >=. x = x+ - x-
                        // TODO dadurch gibt es eine Variable mehr, überlegen wie wir damit umgehen (kennzeichnen bei der Objective Function und den Constraints?)
                        /*soloConstraint.setOperator(ComparisonOperator.LEQ);
                        SoloConstraint geqSoloConstraint = soloConstraint.copy();
                        geqSoloConstraint.setOperator(ComparisonOperator.GEQ);
                        soloConstraints.add(geqSoloConstraint);
                        soloConstraint.negateLeq();*/
                        // TODO in allen constraints und in der Objective Function ändern
                        break;
                    default:
                        break;
                }
                decisionVariables.add(decisionVariable);
            }
        }
    }
}
