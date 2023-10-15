package edu.kit.ui.operations.core;

import edu.kit.ui.logic.Operation;
import edu.kit.ui.exceptions.OperationException;

public class ExitOperation extends Operation {
    private static final String NAME = "/exit";
    private static final String DESCRIPTION = "Exits the program.";
    public static final String EXIT_MESSAGE = "PROGRAM EXITED";

    public ExitOperation() {
        super(NAME, DESCRIPTION);
    }

    @Override
    public String execute() throws OperationException {
        return EXIT_MESSAGE;
    }
}
