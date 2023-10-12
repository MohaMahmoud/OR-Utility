package ui.operations.core;

import model.LinearProgram;
import ui.exceptions.OperationException;
import ui.logic.Operation;

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
