package commands;

import src.LinearProgram;

public class ShowCommand extends CommandExecutor {
    private final LinearProgram linearProgram;

    public ShowCommand(LinearProgram linearProgram) {
        this.linearProgram = linearProgram;
    }

    @Override
    public void execute() {
        System.out.println(linearProgram);
    }
}



