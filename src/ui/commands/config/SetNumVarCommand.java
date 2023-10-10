package ui.commands.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Constraint;
import model.LinearProgram;
import ui.commands.CommandExecutor;

public class SetNumVarCommand extends CommandExecutor {
    private LinearProgram linearProgram;
    private Scanner scanner;

    public SetNumVarCommand(Scanner scanner, LinearProgram linearProgram) {
        this.linearProgram = linearProgram;
        this.scanner = scanner;
    }

    public void execute() {
        System.out.print("Enter the number of variables in the objective function: ");
        int numVariables;

        try {
            numVariables = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Use a numeric value.");
            return;
        }

        if (numVariables <= 0) {
            System.out.println("Invalid number of variables. Use a positive integer.");
            return;
        }

        // Adjust constraints when changing the number of variables
        int oldNumVariables = linearProgram.getVariableCount();
        linearProgram.setVariableCount(numVariables);

        if (oldNumVariables < numVariables) {
            // Add zeros to constraints for added variables
            addZerosForAddedVariables(numVariables);
        } else if (oldNumVariables > numVariables) {
            // Remove extra variables from constraints
            removeExtraVariables(numVariables);
        }

        // Prompt the user to enter the coefficients for the updated objective function
        System.out.println("Enter the coefficients for the updated objective function:");
        linearProgram.getObjectiveFunction().setDecisionVariables(enterObjectiveCoefficients(numVariables));
        System.out.println("Objective function coefficients updated successfully.");
    }

    private List<Double> enterObjectiveCoefficients(int numVariables) {
        System.out.println("Enter the coefficients for the updated objective function (space-separated):");
        String coeffInput = scanner.nextLine();
        String[] coeffStrings = coeffInput.split(" ");

        if (coeffStrings.length != numVariables) {
            System.out.println("Invalid number of coefficients. Expected " + numVariables + ".");
            return null;
        }

        return parseCoefficients(coeffStrings);
    }

    private List<Double> parseCoefficients(String[] coeffStrings) {
        try {
            // Parse and return the coefficients as a list of Doubles
            List<Double> coefficients = new ArrayList<>();
            for (String coeffStr : coeffStrings) {
                double coefficient = Double.parseDouble(coeffStr);
                coefficients.add(coefficient);
            }
            return coefficients;
        } catch (NumberFormatException e) {
            System.out.println("Invalid coefficient. Use numeric values.");
            return null;
        }
    }

    private void addZerosForAddedVariables(int numVariables) {
        for (Constraint constraint : linearProgram.getConstraints()) {
            while (constraint.getCoefficients().size() < numVariables) {
                constraint.getCoefficients().add(0.0);
            }
        }
    }

    private void removeExtraVariables(int numVariables) {
        for (Constraint constraint : linearProgram.getConstraints()) {
            while (constraint.getCoefficients().size() > numVariables) {
                constraint.getCoefficients().remove(constraint.getCoefficients().size() - 1);
            }
        }
    }
}
