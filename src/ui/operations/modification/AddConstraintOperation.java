package ui.operations.modification;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.ComparisonOperator;
import model.Constraint;
import model.LinearProgram;
import ui.exceptions.OperationException;
import ui.logic.Operation;
import ui.util.StringUtility;

public class AddConstraintOperation extends Operation {
    private static final String NAME = "/addconstraint";
    private static final String DESCRIPTION = "Add a new constraint to the linear program.";

    private final LinearProgram program;
    private final Scanner scanner;

    public AddConstraintOperation(LinearProgram program, Scanner scanner) {
        super(NAME, DESCRIPTION);
        this.program = program;
        this.scanner = scanner;
    }

    @Override
    public String execute() throws OperationException {
        // Message for user to input new constraint.
        System.out.print("Enter the coefficients of your new constraint separated by spaces: ");

        final String[] input = scanner.nextLine().split(StringUtility.SPACE);

        final int variableCount = program.getVariableCount();
        if (input.length != variableCount) {
            throw new OperationException("Invalid amount of coefficients. The linear program has " + variableCount + "variables.");
        } // TODO Maybe loop on wrong input instead of throwing and starting over?

        // Parse user input.
        List<Double> coefficients = new ArrayList<>();
        for (String coefficient : input) {
            try {
                coefficients.add(Double.parseDouble(coefficient));
            } catch (NumberFormatException exception) {
                throw new OperationException("Invalid input. Use only numeric values.");
            }
        }

        // Comparison operator input.
        System.out.print("Enter the comparison operator: ");
        ComparisonOperator operator = ComparisonOperator.parse(scanner.nextLine());

        if (operator == null) throw new OperationException("Invalid operator. Valid operators are: <=, = and >=.");

        // Right hand side input.
        System.out.print("Enter the right hand side of the constraint: ");
        final double rightHandSide;
        try {
            rightHandSide = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException exception) {
            throw new OperationException("Invalid input. Use only numeric values.");
        }

        program.addConstraint(new Constraint(coefficients, operator, rightHandSide));
        return SUCCESS;
    }
}
