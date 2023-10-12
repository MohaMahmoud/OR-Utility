package ui.operations.core;

import java.util.Collection;

import ui.Operation;
import ui.OperationException;
import ui.util.StringUtility;

public class HelpOperation extends Operation {
    private static final String NAME = "/help";
    private static final String DESCRIPTION = "Shows all available operations with a description.";

    private final Collection<Operation> operations;

    public HelpOperation(Collection<Operation> operations) {
        super(NAME, DESCRIPTION);
        this.operations = operations;
    }

    @Override
    public String execute(String[] args) throws OperationException {
        StringBuilder builder = new StringBuilder();
        for (Operation operation : operations) {
            builder.append(operation.toString()).append(StringUtility.BR);
        }
        return builder.toString().trim();
    }
}
