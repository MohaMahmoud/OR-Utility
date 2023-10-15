package edu.kit.ui.logic.handler;

import java.util.Scanner;

import edu.kit.model.LinearProgram;
import edu.kit.ui.exceptions.OperationException;
import edu.kit.ui.logic.factory.SetupFactory;
import edu.kit.ui.operations.Result;
import edu.kit.ui.operations.setup.FinishOperation;

public class SetupHandler extends Handler {

    private static final String WELCOME_MESSAGE = "Welcome to the Operations Research Utility program!";

    private boolean finished;

    public SetupHandler(LinearProgram program, Scanner scanner) {
        super(new SetupFactory(program, scanner), scanner);
    }

    @Override
    public void start() {
        running = true;

        // Welcome message to the user including list of commands
        userGuide();

        do {
            System.out.print("Enter desired operation: ");
            final String input = scanner.nextLine();

            if (input.equals(FinishOperation.FINISH_MESSAGE)) {
                finished = true;
                return; // TODO Ultra scheiße umgesetzt :(
            }
            executeOperation(input);
        } while (running && !finished);
    }

    public boolean isFinished() {
        return finished;
    }

    private void userGuide() {
        System.out.println(WELCOME_MESSAGE + System.lineSeparator());

        try {
            System.out.println(factory.match("/show").execute() + System.lineSeparator());
            System.out.println(factory.match("/help").execute() + System.lineSeparator());
        } catch (OperationException exception) {
            Result.FAILURE.print(exception.getMessage());
        }
    }
}
