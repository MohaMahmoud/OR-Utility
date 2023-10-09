package src;

import java.util.List;
import src.LinearProgram;
import src.Constraint;


public class LinearProgramFormatter {
    private boolean hideZeroCoefficients = false;
    private String tempSeparator = " | ";
    private String tempObjectiveFunction = "OF: ";

    public boolean isHideZeroCoefficients() {
        return hideZeroCoefficients;
    }

    public void setHideZeroCoefficients(boolean hideZeroCoefficients) {
        this.hideZeroCoefficients = hideZeroCoefficients;
    }

    public String format(LinearProgram linearProgram) {
        final String separatorBetweenElements = tempSeparator;
        final String lineSeparator = "\n";
        final String shortcutOptimizationDirection = " (" + linearProgram.getOptimizationDirection() + ")";

        List<Constraint> constraints = linearProgram.getConstraints();
        List<Double> objectiveCoeffs = linearProgram.getObjectiveCoeffs();
        if (constraints.isEmpty()) {
            return formatObjectiveFunction(objectiveCoeffs, shortcutOptimizationDirection);
        }

        int numVariables = linearProgram.getNumVariables();
        StringBuilder matrixString = new StringBuilder();
        int[] maxWidths = calcMaxWidths(linearProgram);

        // objective function
        matrixString.append(tempObjectiveFunction);
        for (int i = 0; i < numVariables - 1; i++) {
            matrixString.append(formatCell(maxWidths[i], objectiveCoeffs.get(i), false))
                .append(separatorBetweenElements);
        }
        matrixString.append(formatCell(maxWidths[numVariables - 1], objectiveCoeffs.get(numVariables - 1), false));
        matrixString.append(shortcutOptimizationDirection).append(lineSeparator).append(lineSeparator);

        // constraints
        for (int i = 0; i < constraints.size(); i++) {
            Constraint constraint = constraints.get(i);
            List<Double> constraintCoeffs = constraint.getCoefficients();

            matrixString.append("C").append(i).append(": ");
            for (int j = 0; j < numVariables - 1; j++) {
                double coefficient = constraintCoeffs.get(j);
                matrixString.append(formatCell(maxWidths[j], coefficient, false))
                    .append(separatorBetweenElements);
            }
            matrixString.append(formatCell(maxWidths[numVariables - 1], constraintCoeffs.get(numVariables - 1), false));
            matrixString.append(" ").append(constraint.getOperator().getSign()).append(" ");
            if (constraint.getOperator().getSign().length() == 1) {
                matrixString.append(" "); // if its =
            }
            matrixString.append(formatCell(maxWidths[numVariables], constraint.getRightHandSide(), true))
                .append(lineSeparator);
        }

        return matrixString.toString();
    }

    private int[] calcMaxWidths(LinearProgram linearProgram) {
        int numVariables = linearProgram.getNumVariables();
        int[] maxWidths = new int[numVariables + 1];

        for (int i = 0; i < numVariables; i++) {
            maxWidths[i] = 0;
        }

        for (Constraint constraint : linearProgram.getConstraints()) {
            for (int j = 0; j < numVariables; j++) {
                double coefficient = constraint.getCoefficients().get(j);
                int width = String.valueOf(coefficient).length();
                maxWidths[j] = Math.max(maxWidths[j], width);
            }
        }

        for (Constraint constraint : linearProgram.getConstraints()) {
            double rhs = constraint.getRightHandSide();
            int width = String.valueOf(rhs).length();
            maxWidths[numVariables] = Math.max(maxWidths[numVariables], width);
        }

        return maxWidths;
    }

    private String formatObjectiveFunction(List<Double> objectiveCoeffs, String shortcutOptimizationDirection) {
        StringBuilder objectiveFunction = new StringBuilder(tempObjectiveFunction);
        for (int i = 0; i < objectiveCoeffs.size(); i++) {
            double coefficient = objectiveCoeffs.get(i);
            if (i > 0) {
                objectiveFunction.append(tempSeparator);
            }
            objectiveFunction.append(coefficient);
        }
        objectiveFunction.append(shortcutOptimizationDirection);
        return objectiveFunction.toString();
    }

    private String formatCell(int maxWidth, Double coefficient, boolean isRightHandSide) {
        if (hideZeroCoefficients && coefficient == 0.0 && !isRightHandSide) {
            return String.format("%" + maxWidth + "s", "");
        } else {
            return String.format("%" + maxWidth + "s", coefficient);
        }
    }
}
