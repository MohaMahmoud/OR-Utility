package src;

import commands.CommandLineHandler;
import src.OptimizationDirection;
import src.LinearProgram;


public class Main {
    public static void main(String[] args) {
        int numVariables = 3;
        OptimizationDirection optimizationDirection = OptimizationDirection.MAX;

        LinearProgram linearProgram = new LinearProgram(numVariables, optimizationDirection);
        CommandLineHandler commandLineHandler = new CommandLineHandler(linearProgram);

        commandLineHandler.start();
    }
}

