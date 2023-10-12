package ui.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.LinearProgram;
import ui.operations.algorithms.tools.StandardFormOperation;
import ui.operations.core.ExitOperation;
import ui.operations.core.HelpOperation;
import ui.operations.core.ShowOperation;
import ui.operations.modification.AddConstraintOperation;
import ui.operations.modification.ChangeVariableCountOperation;
import ui.operations.modification.RemoveConstraintOperation;
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

        // User commands.
        operations.put("/addconstraint", new AddConstraintOperation(program, scanner));
        operations.put("/removeconstraint", new RemoveConstraintOperation(program, scanner));
        operations.put("/changevariablecount", new ChangeVariableCountOperation(program, scanner));
        //operations.put("/changeobjectivefunction", null);
        // ...
        // ...
        // ...
        operations.put("/standardform", new StandardFormOperation(program));
        operations.put("/show", new ShowOperation(program));
        operations.put("/help", new HelpOperation(operations.values()));
        operations.put("/exit", new ExitOperation(handler));
        // TODO Check the order if it makes sense.
        // TODO unfinished
    }
}
