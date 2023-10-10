package commands;

    import java.util.Scanner;

import model.LinearProgram;

public class CommandFactory {
    private LinearProgram linearProgram;

    public CommandFactory(LinearProgram linearProgram) {
        this.linearProgram = linearProgram;
    }

    public CommandExecutor createCommandExecutor(CommandType commandType, Scanner scanner, String[] commandParts) {
        switch (commandType) {
            case ADD:
                return new AddCommand(linearProgram, commandParts);
            case EDIT_OBJ:
                return new EditObjCommand(scanner, linearProgram);
            case SET_NUMVAR:
                return new SetNumVarCommand(scanner, linearProgram);
            case SHOW:
                return new ShowCommand(linearProgram);
            case HIDE_ZEROS:
                return new HideZerosCommand(linearProgram);
            case ADD_DUMMY:
                return new AddDummyCommand(linearProgram);
            case DELETE:
                return new DeleteCommand(linearProgram, commandParts);
            case EXIT:
                return new ExitCommand();
            case HELP:
                return new HelpCommand();
            default:
                throw new IllegalArgumentException("Unknown command type: " + commandType);
        }
    }

    public static void showHelp() {
        for (CommandType commandType : CommandType.values()) {
            System.out.println(commandType.getCommandString() + " - " + commandType.getCommandDescription());
        }
    }
}

