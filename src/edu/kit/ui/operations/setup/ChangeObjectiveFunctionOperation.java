package edu.kit.ui.operations.setup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kit.model.ComparisonOperator;
import edu.kit.model.DecisionVariable;
import edu.kit.model.LinearProgram;
import edu.kit.model.OptimizationDirection;
import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;
import edu.kit.ui.util.StringUtility;

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
        // Message for user to input desired decision variables.
        System.out.print("Enter the decision variables of your new function separated by spaces:");

        // Parse user input.
        final String[] input = scanner.nextLine().split(StringUtility.SPACE);
        List<DecisionVariable> decisionVariables = new ArrayList<>();
        for (String decisionVariable : input) {
            int c = 0;
            try {
                decisionVariables
                        .add(new DecisionVariable(c, Double.parseDouble(decisionVariable), ComparisonOperator.GEQ));
                c++;
            } catch (NumberFormatException exception) {
                throw new OperationException("Invalid input. Use only numeric values.");
            }
        }

        // If the amount of variables in the input is different from the current count
        // ask user to proceed.
        if (input.length != program.getVariableCount()) {
            System.out.print(
                    "The amount of variables in your program will change. Type OK to proceed or anything else to cancel: ");
            if (!scanner.nextLine().equalsIgnoreCase("OK"))
                return "OPERATION CANCELED";
        }

        System.out.print("Enter the desired optimization direction (MAX or MIN): ");
        final String direction = scanner.nextLine();
        switch (direction) {
            case "MAX":
                program.getObjectiveFunction().setDirection(OptimizationDirection.MAX);
                break;
            case "MIN":
                program.getObjectiveFunction().setDirection(OptimizationDirection.MIN);
                break;
            default:
                throw new OperationException("Invalid optimization direction. Input MAX or MIN.");
        }

        // Finally change the decision variables.
        program.setVariableCount(input.length);
        program.getObjectiveFunction().setDecisionVariables(decisionVariables);
        return SUCCESS;
    }
}
