package edu.kit.ui.operations.algorithms.tools;

import edu.kit.model.LinearProgram;
import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;

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
        return program.getForm().toString();
    }
}
