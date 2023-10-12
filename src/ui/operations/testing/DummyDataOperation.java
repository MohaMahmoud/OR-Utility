package ui.operations.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.ComparisonOperator;
import model.Constraint;
import model.LinearProgram;
import ui.Operation;
import ui.OperationException;

public class DummyDataOperation extends Operation {
    private static final String NAME = "/dummydata";
    private static final String DESCRIPTION = "Adds dummy data to the linear program.";

    private final LinearProgram program;

    public DummyDataOperation(LinearProgram program) {
        super(NAME, DESCRIPTION);
        this.program = program;
    }

    @Override
    public String execute(String[] args) throws OperationException {
        program.setVariableCount(6);
        program.getObjectiveFunction().setDecisionVariables(Arrays.asList(1.2, -2.0, 0.0, 3.0, -1.0, 1.5));

        // Constraints
        addConstraintDirectly("1.0 2.5 0.0 0.0 1.5 3.3 = 10.5");
        addConstraintDirectly("0.0 0.0 1.8 -4.7 0.0 2.2 >= 5.3");
        addConstraintDirectly("3.2 1.1 0.0 -2.4 0.0 0.0 = 12.7");
        addConstraintDirectly("-1.6 0.0 2.9 0.0 -1.8 0.0 <= 8.6");
        addConstraintDirectly("0.0 0.0 0.0 0.0 0.0 0.0 >= -5.2");
        addConstraintDirectly("1.7 1.6 1.5 1.4 1.3 1.2 <= 20.0");
        addConstraintDirectly("-2.2 0.0 0.0 1.9 -2.1 0.0 >= 7.4");
        addConstraintDirectly("0.0 3.7 -2.8 0.0 0.0 1.9 <= 6.5");
        addConstraintDirectly("1.3 -1.7 0.0 0.0 0.0 -1.2 = 4.9");
        addConstraintDirectly("-3.3 -2.4 0.0 0.0 -1.5 -3.7 <= -15.8"); // Negative integers and negative doubles
        addConstraintDirectly("0.0 0.0 0.0 0.0 0.0 0.0 = 0.0"); // Zero constraint
        addConstraintDirectly("0.0 0.0 0.0 0.0 0.0 1.6 = 5.4"); // Right-hand side positive, one variable non-zero
        addConstraintDirectly("0.0 0.0 0.0 0.0 0.0 0.0 >= 10.0"); // Right-hand side positive, all variables zero
        addConstraintDirectly("1.8 0.0 0.0 0.0 0.0 0.0 <= -2.1"); // Right-hand side negative, one variable non-zero
        addConstraintDirectly("0.0 0.0 0.0 0.0 0.0 0.0 <= -5.9"); // Right-hand side negative, all variables zero
        addConstraintDirectly("-1.2 0.0 0.0 0.0 0.0 0.0 = 3.8"); // Right-hand side positive, one variable non-zero
                                                                 // (negative coefficient)
        return SUCCESS;
    }

    private void addConstraintDirectly(String constraintString) {
        String[] commandParts = constraintString.split(" ");

        int numCoefficients = program.getVariableCount();
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
                program.addConstraint(constraint);
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
