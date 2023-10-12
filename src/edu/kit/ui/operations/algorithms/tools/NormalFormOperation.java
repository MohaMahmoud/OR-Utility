package edu.kit.ui.operations.algorithms.tools;

import edu.kit.model.LinearProgram;
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
        // TODO

        return SUCCESS;
    }
}
