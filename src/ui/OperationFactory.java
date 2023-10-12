package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.LinearProgram;
import ui.operations.algorithms.tools.StandardFormOperation;
import ui.operations.core.ExitOperation;
import ui.operations.core.HelpOperation;
import ui.operations.core.ShowOperation;
import ui.operations.testing.DummyDataOperation;

public class OperationFactory {
    private Map<String, Operation> operations;

    public OperationFactory(OperationHandler handler, LinearProgram program, Scanner scanner) {
        this.operations = new HashMap<>();
        initializeOperations(handler, program, scanner);
    }

    public Operation match(String name) {
        if (name.isBlank() || !operations.containsKey(name)) return null;
        return operations.get(name);
    }

    private void initializeOperations(OperationHandler handler, LinearProgram program, Scanner scanner) {
        // Used primarly for testing.
        operations.put("/dummydata", new DummyDataOperation(program));
        // TODO unfinished
        //operations.put("/addConstraint", null);
        //operations.put("/removeConstraint", null);
        // ...
        // ...
        // ... Add scanner to those that need a scanner.
        // ...
        operations.put("/standardform", new StandardFormOperation(program));
        operations.put("/show", new ShowOperation(program));
        operations.put("/exit", new ExitOperation(handler));
        operations.put("/help", new HelpOperation(operations.values()));
    }
}
