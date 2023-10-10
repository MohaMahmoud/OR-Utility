package ui;

import model.LinearProgram;

public class DummyDataOperation extends Operation {

    private static final String NAME = "/dummydata";
    private static final String DESCRIPTION = "Adds dummy data to the linear program.";

    private LinearProgram program;

    public DummyDataOperation(LinearProgram program) {
        super(NAME, DESCRIPTION);
        this.program = program;
    }

    @Override
    public String execute(String[] args) throws OperationException {
        return null;
    }
    
}
