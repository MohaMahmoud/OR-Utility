package edu.kit.ui.operations.core;

import edu.kit.model.LinearProgram;
import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;

public class ShowOperation extends Operation {
    private static final String NAME = "/show";
    private static final String DESCRIPTION = "Displays the current state of the linear program.";

    private final LinearProgram program;

    public ShowOperation(LinearProgram program) {
        super(NAME, DESCRIPTION);
        this.program = program;
    }

    @Override
    public String execute() throws OperationException {
        return program.toString();
    }

}
