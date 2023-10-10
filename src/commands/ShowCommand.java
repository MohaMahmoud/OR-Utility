package commands;

import model.LinearProgram;

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



