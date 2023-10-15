package edu.kit.ui.util;

import java.util.List;

import edu.kit.model.*;

import static java.lang.Math.max;

/**
 * A utility class for formatting linear programming models.
 *
 * @author Mohammad Mahmoud
 * @author Oleksandr Shchetsura
 */
public final class StringUtility {
    public static final String UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated.";
    public static final String BR = System.lineSeparator();
    public static final String SPACE = " ";
    private static final String OBJECTIVE_FUNCTION_PREFIX = "OF: ";
    private static final String CONSTRAINT_PREFIX = "C%d: ";

    private static final String SEPARATOR = " | ";
    private static final String DECISION_VARIABLE = "x";
    private static final String SLACK_VARIABLE = "s";
    private static final String NEGATIVE = "-";
    private static final String POSITIVE = "+";
    private static final String DECISION_VARIABLE_SEPARATOR = ", ";

    /**
     * Private constructor to prevent instantiation of the utility class.
     *
     * @throws IllegalAccessException If the class is instantiated.
     */
    private StringUtility() throws IllegalAccessException {
        throw new IllegalAccessException(UTILITY_CLASS_INSTANTIATION);
    }

    /**
     * Format a linear program.
     *
     * @param program The linear program.
     * @return The formatted linear program as a string.
     */
    public static String format(LinearProgram program) {
        StringBuilder builder = new StringBuilder();

        // Get the objective functions and constraints (if applicable).
        ObjectiveFunction function = program.getObjectiveFunction();
        List<Constraint> constraints = program.getConstraints();

        // Calculate the maximum width of matrix columns..
        final int[] colWidths = calcColWidths(program);

        int label = OBJECTIVE_FUNCTION_PREFIX.length();
        // An extra step to format the program with more than 9 constraints.
        if (constraints.size() > 10) {
            label = String.format(CONSTRAINT_PREFIX, constraints.size() - 1).length();
        }

        //     x1  x2 ... xn
        formatHeaderLine(builder, colWidths, label, function);
        // OF:  1   2 ... n (MAX)
        formatObjectiveFunction(builder, colWidths, label, function);
        // C0:  1 | 2 ... n >= 5
        if (!constraints.isEmpty()) {
            formatConstraints(builder, colWidths, label, constraints);
        }
        // x1, x2, ... x3 >= 0
        formatSoloConstraints(builder, function);

        return builder.toString();
    }

    /**
     * Format the header line for decision variables.
     *    x1  x2 ... xn
     *
     * @param builder           The StringBuilder to which the formatted string is appended.
     * @param colWidths          An array of column widths.
     * @param label              The label for formatting.
     * @param function           The objective function.
     */
    private static void formatHeaderLine(StringBuilder builder, int[] colWidths, int label, ObjectiveFunction function) {
        builder.append(SPACE.repeat(label));

        // Format the cells of all the coefficients.
        List<DecisionVariable> decisionVariables = function.getDecisionVariables();
        for (int i = 0; i < decisionVariables.size(); i++) {
            builder.append(String.format("%" + colWidths[i] + "s", getDecisionVariableAsString(decisionVariables.get(i))));
            builder.append(SPACE.repeat(SEPARATOR.length()));
        }
    }

    /**
     * Format the objective function.
     * OF:  1   2 ... n (MAX)
     *
     * @param builder           The StringBuilder to which the formatted string is appended.
     * @param colWidths          An array of column widths.
     * @param label              The label for formatting.
     * @param function           The objective function.
     */
    private static void formatObjectiveFunction(StringBuilder builder, int[] colWidths, int label, ObjectiveFunction function) {
        builder.append(BR);
        builder.append(OBJECTIVE_FUNCTION_PREFIX).append(SPACE.repeat(label - OBJECTIVE_FUNCTION_PREFIX.length()));

        // Format the cells of all the coefficients.
        formatCoefficientLine(builder, function.getCoefficients(), colWidths);
        builder.append(function.getDirection().toString());
    }

    /**
     * Format the constraints.
     * C0:  1 | 2 ... n >= 5
     *
     * @param builder           The StringBuilder to which the formatted string is appended.
     * @param colWidths          An array of column widths.
     * @param label              The label for formatting.
     * @param constraints        The list of constraints to be formatted.
     */
    private static void formatConstraints(StringBuilder builder, int[] colWidths, int label, List<Constraint> constraints) {
        // Add empty line after the objective function.
        builder.append(BR).append(BR);

        // Go through all constraints and format them.
        for (int i = 0; i < constraints.size(); i++) {
            Constraint constraint = constraints.get(i);

            // Shifting the prefix if needed.
            final String prefix = String.format(CONSTRAINT_PREFIX, i);
            builder.append(prefix).append(SPACE.repeat(label - prefix.length()));

            // Format all coefficients of the left-hand side.
            formatCoefficientLine(builder, constraint.getCoefficients(), colWidths);

            // Append the operator and right-hand side of the constraint.
            String operator = constraint.getOperator().toString();
            // 3-length -> only one space after <= and >=, but two after =
            builder.append(operator).append(SPACE.repeat(3 - operator.length()));

            // Format the right-hand side.
            formatCell(builder, constraint.getRightHandSide(), colWidths[colWidths.length - 1], true);
            builder.append(BR);
        }
    }

    /**
     * Format the solo constraints.
     * x1, x2, ... x3 >= 0
     *
     * @param builder            The StringBuilder to which the formatted string is appended.
     * @param decisionVariables  The list of decision variables.
     */
    private static void formatSoloConstraints(StringBuilder builder, ObjectiveFunction function) {
        // Split constraints up into <=, >=, and = constraints and create a separat line for each of them.
        StringBuilder leqSoloConstraints = new StringBuilder();
        StringBuilder geqSoloConstraints = new StringBuilder();
        StringBuilder eqSoloConstraints = new StringBuilder();

        // not reachable
        int indexOfFirstSlackVariable = function.getAmountOfStructureVariables();

        for (DecisionVariable decisionVariable : function.getDecisionVariables()) {
            switch (decisionVariable.getOperator()) {
                case GEQ:
                    formatSoloCoefficient(geqSoloConstraints, decisionVariable);
                    break;
                case LEQ:
                    formatSoloCoefficient(leqSoloConstraints, decisionVariable);
                    break;
                case EQ:
                    if (!decisionVariable.isSplit()) {
                        formatSoloCoefficient(eqSoloConstraints, decisionVariable);
                    } else {
                        // x = 0 -> = x+ - x- -> x+, x- >= 0
                        formatSoloCoefficient(geqSoloConstraints, decisionVariable);
                        geqSoloConstraints.append(POSITIVE);
                        formatSoloCoefficient(geqSoloConstraints, decisionVariable);
                        geqSoloConstraints.append(NEGATIVE);
                    }
                    break;
            }
        }

        // Append Solo Constraints grouped by operator.
        formatSoloConstraintLine(builder, geqSoloConstraints, ComparisonOperator.GEQ);
        formatSoloConstraintLine(builder, leqSoloConstraints, ComparisonOperator.LEQ);
        formatSoloConstraintLine(builder, eqSoloConstraints, ComparisonOperator.EQ);
    }

    /**
     * Format the coefficients of decision variables in the left-hand side of a constraint.
     *
     * @param builder           The StringBuilder to which the formatted string is appended.
     * @param decisionVariables The list of coefficients of decision variables.
     * @param colWidths          An array of column widths.
     */
    private static void formatCoefficientLine(StringBuilder builder, List<Double> decisionVariables, int[] colWidths) {
        // Left hand side of constraints or objective function
        for (int j = 0; j < decisionVariables.size(); j++) {
            formatCell(builder, decisionVariables.get(j), colWidths[j], false);
            if (j < decisionVariables.size() - 1) {
                builder.append(SEPARATOR);
            } else {
                builder.append(SPACE);
            }
        }
    }

    /**
     * Format a cell in the output string.
     *
     * @param builder     The StringBuilder to which the formatted string is appended.
     * @param coefficient The coefficient value to be formatted.
     * @param colWidth    The width of the column.
     * @param showZeros   Whether to show zero coefficients.
     */
    private static void formatCell(StringBuilder builder, Double coefficient, int colWidth, boolean showZeros) {
        // Formats the cell to the correct width and aligns the value to the right.
        builder.append(String.format("%" + colWidth + "s", removeTrailingZeros(coefficient, showZeros)));
    }

    /**
     * Remove trailing zeros from a coefficient while formatting.
     *
     * @param coefficient The coefficient value to be formatted.
     * @param showZeros   Whether to show zero coefficients.
     * @return The formatted coefficient value as a string.
     */
    private static String removeTrailingZeros(Double coefficient, boolean showZeros) {
        if (coefficient != 0.0 || showZeros) {
            return (coefficient % 1 == 0) ? String.format("%.0f", coefficient) : String.valueOf(coefficient);
        }
        return "";
    }

    /**
     * Format a coefficient of the decision variable in a solo constraint and append it to the builder.
     *
     * @param soloConstraintGroup The StringBuilder to which the formatted string is appended.
     * @param variable    The decision variable.
     */
    private static void formatSoloCoefficient(StringBuilder soloConstraintGroup, DecisionVariable variable) {
        // If its not the first coefficient in the list, add separator first.
        if (!soloConstraintGroup.isEmpty()) {
            soloConstraintGroup.append(DECISION_VARIABLE_SEPARATOR);
        }
        soloConstraintGroup.append(getDecisionVariableAsString(variable));
    }

    /**
     * Format and append a line of solo constraints for a specific operator.
     *
     * @param builder             The StringBuilder to which the formatted string is appended.
     * @param groupedConstraints   The grouped solo constraints as a StringBuilder.
     * @param operator            The comparison operator.
     */
    private static void formatSoloConstraintLine(StringBuilder builder, StringBuilder groupedConstraints, ComparisonOperator operator) {
        if (!groupedConstraints.isEmpty()) {
            builder.append(BR).append(groupedConstraints).append(SPACE).append(operator).append(SPACE).append(0);
        }
    }

    /**
     * Calculate the column widths for formatting.
     *
     * @param program The linear program.
     * @return An array of maximum column widths.
     */
    private static int[] calcColWidths(LinearProgram program) {
        // Adding 1 to the length for the right-hand side.
        int[] colWidths = new int[program.getVariableCount() + 1];
        ObjectiveFunction function = program.getObjectiveFunction();

        List<DecisionVariable> variables = function.getDecisionVariables();

        // First calculating the width required by header and objective function.
        if (!variables.isEmpty()) {
            for (int i = 0; i < variables.size(); i++) {
                DecisionVariable variable = variables.get(i);

                // Header line
                String formattedVariable = getDecisionVariableAsString(variable);
                // Objective function line
                String formattedCoefficient = removeTrailingZeros(variable.getCoefficient(), true);

                colWidths[i] = max(formattedVariable.length(), formattedCoefficient.length());
            }
        }

        // Updating the widths of the columns with the constraints.
        for (Constraint constraint : program.getConstraints()) {
            for (int i = 0; i < colWidths.length; i++) {
                final double value = (i < program.getVariableCount()) ? constraint.getCoefficients().get(i)
                    : constraint.getRightHandSide();
                final int width = String.valueOf(value).length();
                colWidths[i] = max(colWidths[i], width);
            }
        }

        // Return the maximum width for each column.
        return colWidths;
    }

    /**
     * Get the string representation of a decision variable.
     *
     * @param variable The decision variable.
     * @param index    The index of the variable.
     * @return The formatted string representation of the decision variable.
     */
    private static String getDecisionVariableAsString(DecisionVariable variable) {
        // Should start with x1 instead of x0
        int displayIndex = variable.getDisplayIndex();

        // if its not a slack variable
        if (variable.isSlack()) {
            return SLACK_VARIABLE + displayIndex;
        } else {
            String firstPart = DECISION_VARIABLE + displayIndex;

            if (variable.isNegated()) {
                return firstPart + NEGATIVE;
            } else {
                // TODO ... bessere Darstellung?
                // x0 or x0+-
                return (variable.isSplit()) ? firstPart + POSITIVE + NEGATIVE: firstPart;
            }
        }
    }
}

