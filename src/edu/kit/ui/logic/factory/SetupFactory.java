package edu.kit.ui.logic.factory;

import java.util.List;
import java.util.Scanner;

import edu.kit.model.LinearProgram;
import edu.kit.ui.logic.Operation;
import edu.kit.ui.operations.setup.AddConstraintOperation;
import edu.kit.ui.operations.setup.ChangeObjectiveFunctionOperation;
import edu.kit.ui.operations.setup.ChangeVariableCountOperation;
import edu.kit.ui.operations.setup.FinishOperation;
import edu.kit.ui.operations.setup.RemoveConstraintOperation;
import edu.kit.ui.operations.testing.DummyDataOperation;

public class SetupFactory extends Factory {
    public SetupFactory(LinearProgram program, Scanner scanner) {
        super(program, scanner);
    }

    @Override
    protected List<Operation> InitializeOperations(LinearProgram program, Scanner scanner) {
        return List.of(
            new DummyDataOperation(program),
            new ChangeVariableCountOperation(program, scanner),
            new ChangeObjectiveFunctionOperation(program, scanner),
            new AddConstraintOperation(program, scanner),
            new RemoveConstraintOperation(program, scanner),
            new FinishOperation());
    }
}
