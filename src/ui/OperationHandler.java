package ui;

import java.util.Arrays;
import java.util.Scanner;

import model.LinearProgram;

public class OperationHandler {
    private static final String SEPARATOR = " ";
    
    private OperationFactory factory;
    private boolean running = false;

    public OperationHandler(LinearProgram linearProgram) {
        this.factory = new OperationFactory(linearProgram);
    }

    public void start() {
        running = true;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Enter desired operation: ");
            final String input = scanner.nextLine();

            executeOperation(input);
        } while (running);
        scanner.close();
    }

    public void exit() {
        running = false;
    }

    private void executeOperation(String input) {        
        // Split the imput in command name and command arguments.
        String[] splitOperation = input.trim().split(SEPARATOR);
        
        String name = splitOperation[0];
        String[] arguments = Arrays.copyOfRange(splitOperation, 1, splitOperation.length);

        // Match the input to the desired operation (if applicable).
        Operation operation = factory.match(name);

        if (operation != null) {
            try {
                final String output = operation.execute(arguments);
                Result.SUCCESS.print(output);
            } catch (OperationException exception) {
                Result.FAILURE.print(exception.getMessage());
            }
        } else {
            Result.FAILURE.print("Invalid operation. Use /help to get a list of all available operations.");
        }
    }
}
