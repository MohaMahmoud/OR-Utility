package commands;

import src.LinearProgram;
import src.Constraint;
import src.ComparisonOperator;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class AddDummyCommand extends CommandExecutor {
    private LinearProgram linearProgram;

    public AddDummyCommand(LinearProgram linearProgram) {
        this.linearProgram = linearProgram;
    }

    @Override
    public void execute() {
        linearProgram.setNumVariables(6);
        linearProgram.setObjectiveCoeffs(Arrays.asList(1.0, -2.0, 0.0, 3.0, 0.0, 1.5));

        addConstraintDirectly("1 2 0 0 1.5 3 = 10");
        addConstraintDirectly("0 0 1 -4 0 2 >= 5");
        addConstraintDirectly("3 1 0 -2 0 0 = 12");
        addConstraintDirectly("-1 0 2 0 -1 0 <= 8");
        addConstraintDirectly("0 0 0 0 0 0 >= -5");
        addConstraintDirectly("1 1 1 1 1 1 <= 20");
        addConstraintDirectly("-2 0 0 1 -2 0 >= 7");
        addConstraintDirectly("0 3 -2 0 0 1 <= 6");
        addConstraintDirectly("1 -1 0 0 0 -1 = 4");
    }

    private void addConstraintDirectly(String constraintString) {
        String[] commandParts = constraintString.split(" ");

        int numCoefficients = linearProgram.getNumVariables();
        List<Double> coefficients = new ArrayList<>();

        for (int i = 0; i < numCoefficients; i++) {
            try {
                double coefficient = Double.parseDouble(commandParts[i]);
                coefficients.add(coefficient);
            } catch (NumberFormatException e) {
                System.out.println("Invalid coefficient. Use numeric values.");
                return; // Exit if any coefficient is invalid
            }
        }

        String operatorStr = commandParts[numCoefficients];
        ComparisonOperator operator = parseOperator(operatorStr);

        if (operator != null) {
            try {
                double rightHandSide = Double.parseDouble(commandParts[numCoefficients + 1]);
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


