package ui.operations.algorithms.tools;

import java.util.ArrayList;
import java.util.List;

import model.ComparisonOperator;
import model.Constraint;
import model.LinearProgram;
import model.ObjectiveFunction;
import model.OptimizationDirection;
import ui.exceptions.OperationException;
import ui.logic.Operation;

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
        return program.getForm();
    }
}
