package edu.kit.ui.operations.core;

import java.util.Collection;

import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;
import edu.kit.ui.util.StringUtility;

public class HelpOperation extends Operation {
    private static final String NAME = "/help";
    private static final String DESCRIPTION = "Shows all available operations with a description.";

    private final Collection<Operation> operations;

    public HelpOperation(Collection<Operation> operations) {
        super(NAME, DESCRIPTION);
        this.operations = operations;
    }

    @Override
    public String execute() throws OperationException {
        StringBuilder builder = new StringBuilder();
        for (Operation operation : operations) {
            builder.append(operation.toString()).append(StringUtility.BR);
        }
        return builder.toString().trim();
    }
}
