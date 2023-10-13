package edu.kit.ui.util;

import java.util.ArrayList;
import java.util.List;

import edu.kit.model.*;

public final class StringUtility {
    /**
     * Error message in case a utility class is instantiated. 
     */
    public static final String UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated.";

    /**
     * // TODO Javadoc
     */
    public static final String BR = System.lineSeparator();

    /**
     * // TODO Javadoc
     */
    public static final String SPACE = " ";

    private static final String OBJECTIVE_FUNCTION_PREFIX = "OF: ";
    private static final String CONSTRAINT_PREFIX = "C%d: ";

    private static final String SEPARATOR = " | ";


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
            formatContraints(builder, colWidths, label, constraints);
        }

        // Format solo constraints.
        formatDecisionVariables(builder, function.getDecisionVariables());

        return builder.toString().trim();
    }

    private static void formatObjectiveFunction(StringBuilder builder, int[] colWidths, int label, ObjectiveFunction function) {
        builder.append(OBJECTIVE_FUNCTION_PREFIX);
        builder.append(SPACE.repeat(label - OBJECTIVE_FUNCTION_PREFIX.length()));

        // Format the cells of all the coefficients.
        List<Double> coefficients = function.getCoefficients();
        formatCoefficients(builder, coefficients, colWidths);
        builder.append(function.getDirection().toString());
    }

    private static void formatContraints(StringBuilder builder, int[] colWidths, int label, List<Constraint> constraints) {
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

    private static final String SOLOCONSTRAINTSEPARATOR = ", ";
    private static final String COEFFICIENT = "x";
    private static final String NEGATIVE_MARKED = "-";
    private static final String POSITIVE_MARKED = "+";
    private static void formatDecisionVariables(StringBuilder builder, List<DecisionVariable> decisionVariables) {
        // Split constraints up in <=, >= and =
        StringBuilder leqSoloConstraints = new StringBuilder();
        StringBuilder geqSoloConstraints = new StringBuilder();
        StringBuilder eqSoloConstraints = new StringBuilder();
        for (DecisionVariable decisionVariable : decisionVariables) {
            switch (decisionVariable.getOperator()) {
                case GEQ:
                    formatCoefficient(geqSoloConstraints, decisionVariable);
                    break;
                case LEQ:
                    formatCoefficient(leqSoloConstraints, decisionVariable);
                    break;
                case EQ:
                    if (!decisionVariable.isSplit()) {
                        formatCoefficient(eqSoloConstraints, decisionVariable);
                    } else {
                        // x = x+ - x- (both positive)
                        formatCoefficient(geqSoloConstraints, decisionVariable);
                        builder.append(POSITIVE_MARKED);
                        formatCoefficient(geqSoloConstraints, decisionVariable);
                        builder.append(NEGATIVE_MARKED);
                    }
                    break;
            }
        }

        // Append SoloConstraints grouped by operator.
        formatSoloConstraintLine(builder, geqSoloConstraints, ComparisonOperator.GEQ);
        formatSoloConstraintLine(builder, leqSoloConstraints, ComparisonOperator.LEQ);
        formatSoloConstraintLine(builder, eqSoloConstraints, ComparisonOperator.EQ);
    }

    private static void formatCoefficient(StringBuilder soloConstraintGroup, DecisionVariable decisionVariable) {
        if (!soloConstraintGroup.isEmpty()) {
            soloConstraintGroup.append(SOLOCONSTRAINTSEPARATOR);
        }
        soloConstraintGroup.append(COEFFICIENT);
        soloConstraintGroup.append(decisionVariable.getIndex());
        if (decisionVariable.isNegated()) {
            soloConstraintGroup.append(NEGATIVE_MARKED);
        }
    }
    private static void formatSoloConstraintLine(StringBuilder builder, StringBuilder groupedConstraints, ComparisonOperator operator) {
        if (!groupedConstraints.isEmpty()) {
            builder.append(BR).append(groupedConstraints).append(SPACE).append(operator).append(SPACE).append(0);
        }
    }

    private static int[] calcColWidths(LinearProgram program) {
        // Adding 1 to length for the right hand side.
        int[] colWidths = new int[program.getVariableCount() + 1];

        // Filling column widths widths of the objective function.
        List<DecisionVariable> coefficients = program.getObjectiveFunction().getDecisionVariables();
        // TODO decision variable split?
        if (!coefficients.isEmpty()) {
            for (int i = 0; i < program.getVariableCount(); i++) {
                String formattedCoefficient = removeTailingZeros(coefficients.get(i).getCoefficient(), true);
                colWidths[i] = formattedCoefficient.length();
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

    private static void formatCoefficients(StringBuilder builder, List<Double> decisionVariables, int[] colWidths) {
        for (int j = 0; j < decisionVariables.size(); j++) {
            // Format each cell of the left hand side.
            formatCell(builder, decisionVariables.get(j), colWidths[j], false);
            if (j < decisionVariables.size() - 1) {
                builder.append(SEPARATOR);
            } else {
                builder.append(SPACE);
            }
        }
    }

    private static void formatCell(StringBuilder builder, Double coefficient, int colWidth, boolean showZeros) {

        // Formats the cell to the corrent width and align the value to the right.
        builder.append(String.format("%" + colWidth + "s", removeTailingZeros(coefficient, showZeros)));
    }

    private static String removeTailingZeros(Double coefficient, boolean showZeros) {
        if (coefficient != 0.0 || showZeros) {
            return (coefficient % 1 == 0) ? String.format("%.0f", coefficient) : String.valueOf(coefficient);
        }
        return "";
    } // TODO Check why the formatting is still with too many white spaces
}
