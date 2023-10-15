package edu.kit.ui.logic.handler;

import java.util.Scanner;

import edu.kit.model.LinearProgram;
import edu.kit.ui.logic.factory.AlgorithmFactory;

public class AlgorithmHandler extends Handler {
    public AlgorithmHandler(LinearProgram program, Scanner scanner) {
        super(new AlgorithmFactory(program, scanner), scanner);
    }

    @Override
    public void start() {
        running = true;

        do {
            System.out.print("Enter desired algorithm: ");
            final String input = scanner.nextLine();

            executeOperation(input);
        } while (running);
    }
}
