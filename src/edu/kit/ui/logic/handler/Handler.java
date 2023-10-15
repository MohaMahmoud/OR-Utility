package edu.kit.ui.logic.handler;

import java.util.Scanner;

import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.Operation;
import edu.kit.ui.logic.factory.Factory;
import edu.kit.ui.operations.Result;
import edu.kit.ui.operations.core.ExitOperation;

public abstract class Handler {
    protected final Factory factory;
    protected final Scanner scanner;
    protected boolean running;

    public Handler(Factory factory, Scanner scanner) {
        this.factory = factory;
        this.scanner = scanner;
    }

    public abstract void start();

    public void exit() {
        running = false;
    }

    protected void executeOperation(String input) {
        // Match the input to the desired operation (if applicable).
        Operation operation = factory.match(input);

        if (operation != null) {
            try {
                final String output = operation.execute();
                if (output.equals(ExitOperation.EXIT_MESSAGE)) exit();
                Result.SUCCESS.print(output);
            } catch (OperationException exception) {
                Result.FAILURE.print(exception.getMessage());
            }
        } else {
            Result.FAILURE.print("Invalid operation. Use /help to get a list of all available operations.");
        }
    }
}
