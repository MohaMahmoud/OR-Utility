package edu.kit.ui.operations.algorithms;

import edu.kit.model.LinearProgram;
import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;

public class PrimalSimplexOperation extends Operation {

    private static final String NAME = "/simplex";
    private static final String DESCRIPTION = "Finds the optimal solution for a linear problem if possible.";

    private final LinearProgram program;

    public PrimalSimplexOperation(LinearProgram program) {
        super(NAME, DESCRIPTION);
        this.program = program;
    }

    @Override
    public String execute() throws OperationException {
        return null;
    }
}
