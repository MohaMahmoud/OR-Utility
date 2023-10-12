package ui.util;

import java.util.List;

import model.ComparisonOperator;
import model.Constraint;
import model.LinearProgram;
import model.ObjectiveFunction;

public final class StringUtility {
    /**
     * Error message in case a utility class is instantiated. 
     */
    public static final String UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated.";

    /**
     * // TODO Javadoc
     */
    public static final String BR = System.lineSeparator();

    private static final String OBJECTIVE_FUNCTION_PREFIX = "OF: ";
    private static final String CONSTRAINT_PREFIX = "C%d: ";

    private static final String SEPARATOR = " | ";
    private static final String SPACE = " ";

    private StringUtility() throws IllegalAccessException {
        throw new IllegalAccessException(UTILITY_CLASS_INSTANTIATION);
    }

    public static String format(LinearProgram program) {
        // Get the objective functions and contrains (if applicable).
        ObjectiveFunction function = program.getObjectiveFunction();
        List<Constraint> constraints = program.getConstraints();

        // Calculate max width of the program.
        final int[] colWidths = calcColWidths(program);

        StringBuilder builder = new StringBuilder();

        // Extra step to format the program with more than 9 constrains.
        int label = OBJECTIVE_FUNCTION_PREFIX.length();
        if (constraints.size() > 10) {
            label = String.format(CONSTRAINT_PREFIX, constraints.size() - 1).length();
        }

        // Format the objective function.
        formatObjectiveFunction(builder, colWidths, label, function);

        // Format the constrains.
        if (!constraints.isEmpty()) {
            formatContrains(builder, colWidths, label, constraints);
        }

        return builder.toString().trim();
    }

    private static void formatObjectiveFunction(StringBuilder builder, int[] colWidths, int label, ObjectiveFunction function) {
        builder.append(OBJECTIVE_FUNCTION_PREFIX);
        builder.append(SPACE.repeat(label - OBJECTIVE_FUNCTION_PREFIX.length()));

        // Format the cells of all the coefficients.
        List<Double> coefficients = function.getDecisionVariables();
        formatCoefficients(builder, coefficients, colWidths);
        builder.append(function.getDirection().toString());
    }

    private static void formatContrains(StringBuilder builder, int[] colWidths, int label, List<Constraint> constraints) {
        // Add space after the objective function.
        builder.append(BR).append(BR);

        // Go through all contrains and format them.
        for (int i = 0; i < constraints.size(); i++) {
            Constraint constraint = constraints.get(i);
            List<Double> coefficients = constraint.getCoefficients();

            // Shifting the prefix if needed.
            final String prefix = String.format(CONSTRAINT_PREFIX, i);
            builder.append(prefix).append(SPACE.repeat(label - prefix.length()));

            // Format all coefficients of the left hand side.
            formatCoefficients(builder, coefficients, colWidths);

            // Append the operator and right hand side of the contraint.
            ComparisonOperator operator = constraint.getOperator();
            builder.append(operator.toString()).append(SPACE);

            if (operator.equals(ComparisonOperator.EQ)) {
                builder.append(SPACE);
            }

            // Format the right hand side.
            formatCell(builder, constraint.getRightHandSide(), colWidths[colWidths.length - 1], true);
            builder.append(BR);
        }
    }

    private static int[] calcColWidths(LinearProgram program) {
        // Adding 1 to length for the right hand side.
        int[] colWidths = new int[program.getVariableCount() + 1];

        // Filling column widths widths of the objective function.
        List<Double> coefficients = program.getObjectiveFunction().getDecisionVariables();
        if (!coefficients.isEmpty()) {
            for (int i = 0; i < program.getVariableCount(); i++) {
                colWidths[i] = String.valueOf(coefficients.get(i)).length();
            }
        }

        // Updating the widths of the columns with the contrains.
        for (Constraint constraint : program.getConstraints()) {
            List<Double> contraintCoefficients = constraint.getCoefficients();
            for (int i = 0; i < colWidths.length; i++) {
                final double value = (i < program.getVariableCount()) ? contraintCoefficients.get(i)
                        : constraint.getRightHandSide();
                final int width = String.valueOf(value).length();
                colWidths[i] = Math.max(colWidths[i], width);
            }
        }

        // Return the maximum width for each column.
        return colWidths;
    }

    private static void formatCoefficients(StringBuilder builder, List<Double> coefficients, int[] colWidths) {
        for (int j = 0; j < coefficients.size(); j++) {
            // Format each cell of the left hand side.
            formatCell(builder, coefficients.get(j), colWidths[j], false);
            if (j < coefficients.size() - 1) {
                builder.append(SEPARATOR);
            } else {
                builder.append(SPACE);
            }
        }
    }

    private static void formatCell(StringBuilder builder, Double coefficient, int colWidth, boolean showZeros) {
        final String formattedCoefficient;
        if (coefficient != 0.0 || showZeros) {
            formattedCoefficient = (coefficient % 1 == 0) ? String.format("%.0f", coefficient)
                    : String.valueOf(coefficient);
        } else {
            formattedCoefficient = "";
        }

        // Formats the cell to the corrent width and align the value to the right.
        builder.append(String.format("%" + colWidth + "s", formattedCoefficient));
    }
}
