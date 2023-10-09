package commands;

public class HelpCommand extends CommandExecutor {
    @Override
    public void execute() {
        CommandType.showHelp();
    }
}