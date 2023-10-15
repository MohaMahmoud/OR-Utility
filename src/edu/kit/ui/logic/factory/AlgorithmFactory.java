package edu.kit.ui.logic.factory;

import java.util.List;
import java.util.Scanner;

import edu.kit.model.LinearProgram;
import edu.kit.ui.logic.Operation;

public class AlgorithmFactory extends Factory {

    public AlgorithmFactory(LinearProgram program, Scanner scanner) {
        super(program, scanner);
    }

    @Override
    protected List<Operation> InitializeOperations(LinearProgram program, Scanner scanner) {
        return List.of(
                //new PrimalSimplex(program),
                //new DualSimplex(program)
                );
    }
}
