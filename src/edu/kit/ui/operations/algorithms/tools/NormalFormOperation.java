package edu.kit.ui.operations.algorithms.tools;

import java.util.ArrayList;
import java.util.List;

import edu.kit.model.ComparisonOperator;
import edu.kit.model.Constraint;
import edu.kit.model.LinearProgram;
import edu.kit.model.ProgramForm;
import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;

public class NormalFormOperation extends Operation {
    private static final String NAME = "/normalform";
    private static final String DESCRIPTION = "Puts the linear program in the normal form.";

    private final LinearProgram program;
    private final Operation standardForm;
    private final Operation getForm;

    public NormalFormOperation(LinearProgram program, Operation standardForm, Operation getForm) {
        super(NAME, DESCRIPTION);
        this.program = program;
        this.standardForm = standardForm;
        this.getForm = getForm;
    }

    @Override
    public String execute() throws OperationException {
        String currentForm = getForm.execute();
        // Put the linear program in standard form.
        if (currentForm.equals(ProgramForm.NORMAL.toString()) || currentForm.equals(ProgramForm.CANONICAL.toString())) {
            // Already done
            return SUCCESS;
        } else if (currentForm.equals(ProgramForm.DEFAULT.toString())) {
            // Change to standard mode first, if its still in default mode
            standardForm.execute();
        }

        // Increase variableCount of the linear program by the amount of constraints.
        List<Constraint> constraints = program.getConstraints();
        final int slackVariableAmount = constraints.size();
        final int oldVariableAmount = program.getVariableCount();
        program.setVariableCount(oldVariableAmount + slackVariableAmount);

        // Extend constraint matrix with an identity function of the corresponding size.
        for (int i = 0; i < slackVariableAmount; i++) {
            Constraint constraint = constraints.get(i);

            List<Double> coefficients = new ArrayList<>(constraint.getCoefficients());
            coefficients.set(i + oldVariableAmount, 1.0);
            constraint.setCoefficients(coefficients);
            /*for (int j = 0; j < slackVariableAmount; j++) {
                // Add the slack variables.
                if (i == j) coefficients.add(1.0);
                else coefficients.add(0.0);
            }
            constraint.setCoefficients(coefficients);*/

            // Set operator to EQ.
            constraint.setOperator(ComparisonOperator.EQ);
        }

        // Extend constraint matrix with an identity function of the corresponding size.
        for (int i = 0; i < slackVariableAmount; i++) {
            Constraint constraint = constraints.get(i);
            List<Double> coefficients = new ArrayList<>(constraint.getCoefficients());
            for (int j = 0; j < slackVariableAmount; j++) {
                // Add the slack variables.
                if (i == j) coefficients.add(1.0);
                else coefficients.add(0.0);
            }
            constraint.setCoefficients(coefficients);

            // Set operator to EQ.
            constraint.setOperator(ComparisonOperator.EQ);
        }

        return SUCCESS;
    }
}