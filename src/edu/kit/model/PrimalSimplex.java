package edu.kit.model;

import java.util.List;

public class PrimalSimplex {
    private double[][] tableau;

    // row 0 and col 0
    private int[] nonBasicVariables;
    private int[] basicVariables;


    public PrimalSimplex(ObjectiveFunction function, List<Constraint> constraints, OptimizationDirection direction) {
        fillTableau(function, constraints);
        while (isOptimal()) {
            int pivotCol = choosePivotColumn();
            int pivotRow;
            if (stopRuleTwoViolated(pivotCol)) {
                pivotRow = choosePivotRow(pivotCol);
            } else {
                return;
                // Problem unlösbar wegen auf M unbeschränkter Zielfunktion.
            }
            exchangeStep(pivotCol, pivotRow);
        }
        // tableau[0][tableau[0].length - 1] = optimal value; last col combined with basicVariables -> valid basic solution
    }

    private void fillTableau(ObjectiveFunction function, List<Constraint> constraints) {}

    /**
     * Stop rule I. True, if all coefficients in objective line are positive -> value cant be increased.
     * @return
     */
    private boolean isOptimal() {
        return true;
    }

    /**
     * Return the column index of the lowest coefficient in the objective line.
     *
     * @return
     */
    private int choosePivotColumn() {
        return 0;
    }

    /**
     * Stop rule II. True, if all coefficients in pivotCol are negative -> not solveable because corresponding
     * decision variable can be increased without limit but still wont break the constraints.
     *
     * @param pivotCol
     * @return
     */
    private boolean stopRuleTwoViolated(int pivotCol) {
        return true;
    }

    /**
     * Divide each element in the last col by the corresponding element in the same row but in the pivot column.
     * Ignore elements in the pivot column <= 0. Choose the lowest quotient.
     *
     * @return
     */
    private int choosePivotRow(int pivotCol) {
        return 0;
    }

    /**
     *
     *
     * @param pivotCol
     * @param pivotRow
     */
    private void exchangeStep(int pivotCol, int pivotRow) {
        double[][] newTableau = new double[tableau.length][tableau[0].length];

        // switch basic and not basic variable
        int enteringNonBasicVariable = nonBasicVariables[pivotCol];
        int leavingBasicVariable = basicVariables[pivotRow];
        basicVariables[pivotRow] = enteringNonBasicVariable;
        nonBasicVariables[pivotCol] = leavingBasicVariable;

        // pe = tableau[pivotRow][pivotCol]
        double pivotElement = tableau[pivotRow][pivotCol];

        // new pe = 1/pe
        newTableau[pivotRow][pivotCol] = - pivotElement;

        // divide all elements in pivotRow (besides of pe) by pe
        for (int col = 0; col < tableau[0].length; col++) {
            if (col != pivotCol) {
                newTableau[pivotRow][col] = tableau[pivotRow][col] / pivotElement;
            }
        }

        // divide all elements in pivotCol (also in coefficient line but not pe itself!) by -pe
        for (int row = 0; row < tableau.length; row++) {
            if (row != pivotRow) {
                newTableau[row][pivotCol] = tableau[row][pivotCol] / -pivotElement;
            }
        }

        // calc everything else with the triangleRule a_ij'=a_ij-a_is*a_rj/pe (pe = a_rs)
        // in our case: newTableau[i][j] = tableau[i][j] - tableau[i][pivotCol] * tableau[pivotRow][j] / pe
        for (int row = 0; row < tableau.length; row++) {
            if (row != pivotRow) {
                for (int col = 0; col < tableau[0].length; col++) {
                    if (col != pivotCol) {
                        newTableau[row][col] = tableau[row][col] - (tableau[row][pivotCol] * tableau[pivotRow][col]) / pivotElement;
                    }
                }
            }
        }

        tableau = newTableau;
    }

    public String toString() {}

}
