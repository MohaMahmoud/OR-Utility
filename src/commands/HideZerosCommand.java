package commands;

import src.LinearProgram;

public class HideZerosCommand extends CommandExecutor {
    private LinearProgram linearProgram;

    public HideZerosCommand(LinearProgram linearProgram) {
        this.linearProgram = linearProgram;
    }

    @Override
    public void execute() {
        toggleHideZeros();
    }

    private void toggleHideZeros() {
        linearProgram.setHideZeroCoefficients(!linearProgram.isHideZeroCoefficients());
        System.out.println("Zero coefficient display " + (linearProgram.isHideZeroCoefficients() ? "disabled" : "enabled") + ".");
    }
}
