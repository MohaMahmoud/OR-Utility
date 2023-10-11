package ui.commands.algortihms.utility;


import model.LinearProgram;

public class GetMaxFormCommand {}
    extends CommandExecutor {
    private final LinearProgram linearProgram;

    public GetMaxFormCommand(LinearProgram linearProgram) {
        this.linearProgram = linearProgram;
    }

    @Override
    public void execute() {
        System.out.println(linearProgram.getMaxForm());
    }
}