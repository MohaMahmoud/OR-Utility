package commands;

import src.LinearProgram;

import java.util.List;
import java.util.Scanner;

public class EditObjCommand extends CommandExecutor {
    private final Scanner scanner;
    private final LinearProgram linearProgram;

    public EditObjCommand(Scanner scanner, LinearProgram linearProgram) {
        this.scanner = scanner;
        this.linearProgram = linearProgram;
    }

    @Override
    public void execute() {
        System.out.print("Enter new coefficients for the objective function (space-separated): ");
        String coeffInput = scanner.nextLine();
        String[] coeffStrings = coeffInput.split(" ");

        List<Double> objectiveCoeffs = linearProgram.getObjectiveCoeffs();
        int numVariables = objectiveCoeffs.size();

        if (coeffStrings.length != numVariables) {
            System.out.println("Invalid number of coefficients. Expected " + numVariables + ".");
            return;
        }

        for (int i = 0; i < numVariables; i++) {
            try {
                double coefficient = Double.parseDouble(coeffStrings[i]);
                objectiveCoeffs.set(i, coefficient);
            } catch (NumberFormatException e) {
                System.out.println("Invalid coefficient. Use numeric values.");
                return;
            }
        }

        System.out.println("Objective function coefficients updated successfully.");
    }
}

