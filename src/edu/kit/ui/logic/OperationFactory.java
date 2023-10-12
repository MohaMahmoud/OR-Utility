package edu.kit.ui.logic;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Scanner;

import edu.kit.model.LinearProgram;
import edu.kit.ui.operations.algorithms.tools.GetFormOperation;
import edu.kit.ui.operations.algorithms.tools.StandardFormOperation;
import edu.kit.ui.operations.core.ExitOperation;
import edu.kit.ui.operations.core.HelpOperation;
import edu.kit.ui.operations.core.ShowOperation;
import edu.kit.ui.operations.modification.AddConstraintOperation;
import edu.kit.ui.operations.modification.ChangeVariableCountOperation;
import edu.kit.ui.operations.modification.RemoveConstraintOperation;
import edu.kit.ui.operations.testing.DummyDataOperation;

public class OperationFactory {
    private Map<String, Operation> operations;

    public OperationFactory(OperationHandler handler, LinearProgram program, Scanner scanner) {
        this.operations = new LinkedHashMap<>();
        initializeOperations(handler, program, scanner);
    }

    public Operation match(String name) {
        if (name.isBlank() || !operations.containsKey(name)) return null;
        return operations.get(name);
    }

    private void initializeOperations(OperationHandler handler, LinearProgram program, Scanner scanner) {
        // Linear program configs
        operations.put("/show", new ShowOperation(program));
        operations.put("/changevariablecount", new ChangeVariableCountOperation(program, scanner));
        //operations.put("/changeobjectivefunction", null);
        operations.put("/addconstraint", new AddConstraintOperation(program, scanner));
        operations.put("/removeconstraint", new RemoveConstraintOperation(program, scanner));

        // ...
        // ...
        // ...

        // Algorithmic commands
        operations.put("/getform", new GetFormOperation(program));
        operations.put("/standardform", new StandardFormOperation(program));

        // Used primarly for testing.
        operations.put("/dummydata", new DummyDataOperation(program));

        // Core commands.
        operations.put("/help", new HelpOperation(operations.values()));
        operations.put("/exit", new ExitOperation(handler));
    }
}
