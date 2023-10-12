package ui.operations.core;

import ui.Operation;
import ui.OperationException;
import ui.OperationHandler;

public class ExitOperation extends Operation {
    private static final String NAME = "/exit";
    private static final String DESCRIPTION = "Exits the program.";

    private final OperationHandler handler;

    public ExitOperation(OperationHandler handler) {
        super(NAME, DESCRIPTION);
        this.handler = handler;
    }

    @Override
    public String execute(String[] args) throws OperationException {
        handler.exit();
        return SUCCESS;
    }
}
