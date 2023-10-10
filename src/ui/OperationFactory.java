package ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.LinearProgram;

public class OperationFactory {
    private Map<String, Operation> operations;

    public OperationFactory(LinearProgram program, Scanner scanner) {
        this.operations = new HashMap<>();
        initializeOperations(program, scanner);
    }

    public Operation match(String name) {
        if (name.isBlank() || !operations.containsKey(name)) return null;
        return operations.get(name);
    }

    private void initializeOperations(LinearProgram program, Scanner scanner) {
        operations.put("/dummydata", new DummyDataOperation(program));
        // ...
        // ... Add scanner to those that need a scanner.
        // ...
        operations.put("/help", new HelpOperation());
        operations.put("/exit", new ExitOperation());
    }
}
