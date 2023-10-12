package edu.kit.ui.operations.modification;

import java.util.Scanner;

import edu.kit.model.LinearProgram;
import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;

public class ChangeVariableCountOperation extends Operation {
    private static final String NAME = "/changevariablecount";
    private static final String DESCRIPTION = "Changes the variable count of the linear program.";

    private final LinearProgram program;
    private final Scanner scanner;

    public ChangeVariableCountOperation(LinearProgram program, Scanner scanner) {
        super(NAME, DESCRIPTION);
        this.program = program;
        this.scanner = scanner;
    }

    @Override
    public String execute() throws OperationException {
        // Message for user to input new variable count.
        System.out.print("Enter new desired variable count: ");

        // Parse user input.
        final int variableCount;
        try {
            variableCount = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {
            throw new OperationException("Invalid input. Use a numeric value.");
        }
        if (variableCount < 0) throw new OperationException("Invalid value. Use a positive integer.");

        // TODO Was machen wenn variblen weggestrichen werden?
        program.setVariableCount(variableCount);
        return SUCCESS;
    }
}
