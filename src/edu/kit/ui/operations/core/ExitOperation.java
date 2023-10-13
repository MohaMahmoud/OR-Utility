package edu.kit.ui.operations.core;

import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;
import edu.kit.ui.logic.OperationHandler;

public class ExitOperation extends Operation {
    private static final String NAME = "/exit";
    private static final String DESCRIPTION = "Exits the program.";

    private final OperationHandler handler;

    public ExitOperation(OperationHandler handler) {
        super(NAME, DESCRIPTION);
        this.handler = handler;
    }

    @Override
    public String execute() throws OperationException {
        handler.exit();
        return SUCCESS;
    }
}
