package view;

import commands.CommandLineHandler;
import model.LinearProgram;
import model.OptimizationDirection;

/**
 * Main entry point to the OR-Utility program
 */
public class Main {
    public static void main(String[] args) {
        int numVariables = 3;
        OptimizationDirection optimizationDirection = OptimizationDirection.MAX;

        LinearProgram linearProgram = new LinearProgram(numVariables, optimizationDirection);
        CommandLineHandler commandLineHandler = new CommandLineHandler(linearProgram);

        commandLineHandler.start();
    }
}

