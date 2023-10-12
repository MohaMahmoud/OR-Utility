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

public class NormalFormOperation extends Operation {
    private static final String NAME = "/normalform";
    private static final String DESCRIPTION = "Puts the linear program in the normal form.";

    private final LinearProgram program;

    public NormalFormOperation(LinearProgram program) {
        super(NAME, DESCRIPTION);
        this.program = program;
    }

    @Override
    public String execute() throws OperationException {
        // if GetFormCommand == DEFAULT -> StandardFormCommand...
        // else if GetFormCommand == Standard:

        // OF um n Nullen erweitern wobei n die Anzahl der constraints ist

        // Extend constraint matrix with a slackVariableAmount Identityfunction
        List<Constraint> constraints = program.getConstraints();
        int slackVariableAmount = constraints.size();

        for (int i = 0; i < slackVariableAmount; i++) {
            Constraint constraint = constraints.get(i);
            List<Double> coefficients = new ArrayList<>(constraint.getCoefficients());
            for (int j = 0; j < slackVariableAmount; j++) {
                if (j == i) {
                    // slack variable
                    coefficients.add(1.0);
                } else {
                    coefficients.add(0.0);
                }
            }
            constraint.setCoefficients(coefficients);

            // Set operator to =
            constraint.setOperator(ComparisonOperator.EQ);
        }

        
        return SUCCESS;
    }
}