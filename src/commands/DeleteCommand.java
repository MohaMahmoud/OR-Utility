package commands;

import model.LinearProgram;

public class DeleteCommand extends CommandExecutor {
    private final LinearProgram linearProgram;
    private final String[] commandParts;

    public DeleteCommand(LinearProgram linearProgram, String[] commandParts) {
        this.linearProgram = linearProgram;
        this.commandParts = commandParts;
    }

    @Override
    public void execute() {
        try {
            // Extract the index from the user's input after "/delete"
            int index = Integer.parseInt(commandParts[1]);

            if (index >= 0 && index < linearProgram.getConstraints().size()) {
                linearProgram.getConstraints().remove(index);
                System.out.println("Constraint deleted successfully.");
            } else {
                System.out.println("Invalid index. No constraint deleted.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid index. Use a numeric value.");
        }
    }
}



