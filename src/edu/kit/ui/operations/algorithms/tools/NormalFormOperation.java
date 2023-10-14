package edu.kit.ui.operations.algorithms.tools;

import java.util.ArrayList;
import java.util.List;

import edu.kit.model.ComparisonOperator;
import edu.kit.model.Constraint;
import edu.kit.model.LinearProgram;
import edu.kit.model.ProgramForm;
import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;

/**
 * An operation to convert a linear program to the normal form.
 */
public class NormalFormOperation extends Operation {
    private static final String NAME = "/normalform";
    private static final String DESCRIPTION = "Puts the linear program in the normal form.";

    private final LinearProgram program;
    private final Operation standardForm;
    private final Operation getForm;

    /**
     * Constructs a NormalFormOperation with the specified linear program, standard form operation, and get form operation.
     *
     * @param program      The linear program to convert to normal form.
     * @param standardForm The operation to put the program in standard form if needed.
     * @param getForm      The operation to determine the current form of the program.
     */
    public NormalFormOperation(LinearProgram program, Operation standardForm, Operation getForm) {
        super(NAME, DESCRIPTION);
        this.program = program;
        this.standardForm = standardForm;
        this.getForm = getForm;
    }

    /**
     * Execute the operation to convert the linear program to the normal form.
     *
     * @return A status message indicating the success of the operation.
     * @throws OperationException If an error occurs during execution.
     */
    @Override
    public String execute() throws OperationException {
        String currentForm = getForm.execute();

        // Check whether its already in normal or even canonical form
        if (currentForm.equals(ProgramForm.NORMAL.toString()) || currentForm.equals(ProgramForm.CANONICAL.toString())) {
            return SUCCESS;
        } else if (currentForm.equals(ProgramForm.DEFAULT.toString())) {
            // Change to standard mode first if it's still in default mode.
            standardForm.execute();
        }

        // Increase variableCount of the linear program by the number of constraints.
        List<Constraint> constraints = program.getConstraints();
        final int slackVariableCount = constraints.size();
        final int oldVariableCount = program.getVariableCount();
        program.setVariableCount(slackVariableCount + program.getVariableCount());

        // Put ones on the diagonal of the appended zeros matrix to turn it into a identity matrix.
        for (int i = 0; i < slackVariableCount; i++) {
            Constraint constraint = constraints.get(i);

            // Comlicated but necessary because of the nature of Doubles
            List<Double> coefficients = new ArrayList<>(constraint.getCoefficients());
            coefficients.set(i + oldVariableCount, 1.0);
            constraint.setCoefficients(coefficients);

            constraint.setOperator(ComparisonOperator.EQ);
        }

        return SUCCESS;
    }
}
