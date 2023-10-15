package edu.kit;

import edu.kit.ui.util.StringUtility;

import java.util.Scanner;

import edu.kit.model.LinearProgram;
import edu.kit.ui.logic.handler.AlgorithmHandler;
import edu.kit.ui.logic.handler.SetupHandler;

/**
 * Entry point to the application. Initializes the command-line handler
 * and starts the program.
 * 
 * @author Oleksandr Shchetsura
 * @author Mohammad Mahmoud
 * @version 1.1
 */
public class Client {
    private Client() throws IllegalAccessException {
        throw new IllegalAccessException(StringUtility.UTILITY_CLASS_INSTANTIATION);
    }

    /**
     * Main method of the program.
     * 
     * @param args The command-line arguments (not used in this program)
     */
    public static void main(String[] args) {
        // Create an empty linear program with 3 variables.
        LinearProgram program = new LinearProgram();
        Scanner scanner = new Scanner(System.in);

        // Start the setup handler so the user can configure the linear program.
        SetupHandler setup = new SetupHandler(program, scanner);
        setup.start();

        // Start the algorithm handler in case the setup has been fully completed.
        if (setup.isFinished()) {
            AlgorithmHandler session = new AlgorithmHandler(program, scanner);
            session.start();
        }

        scanner.close();
    }
}
