package commands;

public class ExitCommand extends CommandExecutor {
    @Override
    public void execute() {
        System.exit(0);
    }
}
