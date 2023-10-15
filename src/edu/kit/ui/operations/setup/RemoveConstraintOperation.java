package edu.kit.ui.operations.setup;

import java.util.Scanner;

import edu.kit.model.LinearProgram;
import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;

public class RemoveConstraintOperation extends Operation {
    private static final String NAME = "/removeconstraint";
    private static final String DESCRIPTION = "Removes a constraint from the linear program.";

    private final LinearProgram program;
    private final Scanner scanner;

    public RemoveConstraintOperation(LinearProgram program, Scanner scanner) {
        super(NAME, DESCRIPTION);
        this.program = program;
        this.scanner = scanner;
    }

    @Override
    public String execute() throws OperationException {
        // Message for user to input the index of the constraint to delete.
        System.out.println("Enter the index of the constraint to be removed: ");

        // Parse user input.
        final int index;
        try {
            index = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {
            throw new OperationException("Invalid input. Use a numeric value.");
        }

        if (index < 0 || index > program.getConstraints().size() - 1) {
            throw new OperationException("Invalid index. There is no constraint with this index.");
        }

        program.removeConstraint(index);
        return SUCCESS;
    }
}
