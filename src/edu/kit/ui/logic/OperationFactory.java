package edu.kit.ui.logic;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import edu.kit.model.LinearProgram;
import edu.kit.ui.operations.core.ShowOperation;
import edu.kit.ui.operations.core.HelpOperation;
import edu.kit.ui.operations.core.ExitOperation;
import edu.kit.ui.operations.modification.ChangeVariableCountOperation;
import edu.kit.ui.operations.modification.ChangeObjectiveFunctionOperation;
import edu.kit.ui.operations.modification.AddConstraintOperation;
import edu.kit.ui.operations.modification.RemoveConstraintOperation;
import edu.kit.ui.operations.algorithms.tools.GetFormOperation;
import edu.kit.ui.operations.algorithms.tools.StandardFormOperation;
import edu.kit.ui.operations.algorithms.tools.NormalFormOperation;
import edu.kit.ui.operations.testing.DummyDataOperation;

public class OperationFactory {
    private Map<String, Operation> operations = new LinkedHashMap<>();

    public OperationFactory(OperationHandler handler, LinearProgram program, Scanner scanner) {
        for (Operation operation : createOperations(handler, program, scanner)) {
            operations.put(operation.getName(), operation);
        }
    }

    public Operation match(String name) {
        if (name.isBlank() || !operations.containsKey(name))
            return null;
        return operations.get(name);
    }

    private List<Operation> createOperations(OperationHandler handler, LinearProgram program, Scanner scanner) {
        // Listing all available operations.
        return List.of(
            // Visiual representation.
            new ShowOperation(program),

            // Linear program configuration operations.
            new ChangeVariableCountOperation(program, scanner),
            new ChangeObjectiveFunctionOperation(program, scanner),
            new AddConstraintOperation(program, scanner),
            new RemoveConstraintOperation(program, scanner),

            // ...
            // ... Coming soon...
            // ...

            // Tools used for algorithms.
            new GetFormOperation(program),
            new StandardFormOperation(program),
            new NormalFormOperation(program, new StandardFormOperation(program), new GetFormOperation(program)),

            // algorithms.
            // library.add(new SimplexOperation(program));

            // Used primarly for testing.
            new DummyDataOperation(program),

            // Core commands.
            new HelpOperation(operations.values()),
            new ExitOperation(handler)
        );
    }
}
