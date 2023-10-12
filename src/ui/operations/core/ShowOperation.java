package ui.operations.core;

import model.LinearProgram;
import ui.Operation;
import ui.OperationException;

public class ShowOperation extends Operation {
    private static final String NAME = "/show";
    private static final String DESCRIPTION = "Displays the current state of the linear program.";

    private final LinearProgram program;

    public ShowOperation(LinearProgram program) {
        super(NAME, DESCRIPTION);
        this.program = program;
    }

    @Override
    public String execute(String[] args) throws OperationException {
        return program.toString();
    }

}
