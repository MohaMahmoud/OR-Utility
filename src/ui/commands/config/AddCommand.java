package ui.commands.config;

import model.ComparisonOperator;
import model.Constraint;
import model.LinearProgram;
import ui.commands.CommandExecutor;

import java.util.ArrayList;
import java.util.List;

public class AddCommand extends CommandExecutor {
    private LinearProgram linearProgram;
    private String[] commandParts;

    public AddCommand(LinearProgram linearProgram, String[] commandParts) {
        this.linearProgram = linearProgram;
        this.commandParts = commandParts;
    }

    @Override
    public void execute() {
        int numCoefficients = linearProgram.getVariableCount();
        List<Double> coefficients = new ArrayList<>();

        for (int i = 1; i <= numCoefficients; i++) {
            try {
                double coefficient = Double.parseDouble(commandParts[i]);
                coefficients.add(coefficient);
            } catch (NumberFormatException e) {
                System.out.println("Invalid coefficient. Use numeric values.");
                return; // Exit if any coefficient is invalid
            }
        }

        String operatorStr = commandParts[numCoefficients + 1];
        ComparisonOperator operator = parseOperator(operatorStr);

        if (operator != null) {
            try {
                double rightHandSide = Double.parseDouble(commandParts[numCoefficients + 2]);
                Constraint constraint = new Constraint(coefficients, operator, rightHandSide);
                linearProgram.addConstraint(constraint);
                System.out.println("Constraint added successfully.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid right-hand side value. Use a numeric value.");
            }
        }
    }

    private ComparisonOperator parseOperator(String operatorStr) {
        switch (operatorStr) {
            case "<=":
                return ComparisonOperator.LEQ;
            case "=":
                return ComparisonOperator.EQ;
            case ">=":
                return ComparisonOperator.GEQ;
            default:
                System.out.println("Invalid operator. Use <=, =, or >=.");
                return null;
        }
    }
}
