package edu.kit.ui.operations.setup;

import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;

public class FinishOperation extends Operation {

    private static final String NAME = "/finish";
    private static final String DESCRIPTION = "Completes the setup phase and prepares the linear program for algorithm usage.";
    public static final String FINISH_MESSAGE = "SETUP FINISHED";

    public FinishOperation() {
        super(NAME, DESCRIPTION);
    }

    @Override
    public String execute() throws OperationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
