package edu.kit.ui.logic.factory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.kit.model.LinearProgram;
import edu.kit.ui.logic.Operation;
import edu.kit.ui.operations.core.ExitOperation;
import edu.kit.ui.operations.core.HelpOperation;
import edu.kit.ui.operations.core.ShowOperation;

public abstract class Factory {
    private Map<String, Operation> operations = new LinkedHashMap<>();

    public Factory(LinearProgram program, Scanner scanner) {
        for (Operation operation : InitializeOperations(program, scanner)) {
            operations.put(operation.getName(), operation);
        }

        Operation show = new ShowOperation(program);
        Operation help = new HelpOperation(operations.values());
        Operation exit = new ExitOperation();

        operations.put(show.getName(), show);
        operations.put(help.getName(), help);
        operations.put(exit.getName(), exit);
    }

    public Operation match(String name) {
        if (name.isBlank() || !operations.containsKey(name))
            return null;
        return operations.get(name);
    }

    protected abstract List<Operation> InitializeOperations(LinearProgram program, Scanner scanner);
}
