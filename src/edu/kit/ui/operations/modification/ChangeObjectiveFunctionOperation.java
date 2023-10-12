package edu.kit.ui.operations.modification;

import java.util.Scanner;

import edu.kit.model.LinearProgram;
import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;

public class ChangeObjectiveFunctionOperation extends Operation {
    private static final String NAME = "/changeobjectivefunction";
    private static final String DESCRIPTION = "Changes the objective function of the linear program.";

    private final LinearProgram program;
    private final Scanner scanner;

    public ChangeObjectiveFunctionOperation(LinearProgram program, Scanner scanner) {
        super(NAME, DESCRIPTION);
        this.program = program;
        this.scanner = scanner;
    }

    @Override
    public String execute() throws OperationException {
        // TODO unfinished. HIGH PRIO
        scanner.nextLine();
        program.getObjectiveFunction();
        // TODO Auch überlegen ob der User nicht doch mehr Freiheiten haben soll.
        return SUCCESS;
    }
}
