package commands;

public enum CommandType {
    SHOW("/show", "Display the current state of the linear program."),
    HIDE_ZEROS("/hidezeros", "Toggle the display of zero coefficients in the linear program."),
    SET_NUMVAR("/setnumvar", "Set the number of variables in the objective function."),
    EDIT_OBJ("/editobj", "Edit the coefficients of the objective function."),
    ADD("/add", "Add a constraint to the linear program."),
    DELETE("/delete", "Delete a constraint from the linear program."),
    ADD_DUMMY("/dummydata", "Add dummy data to the linear program."),
    HELP("/help", "Get all commands and a short description."),
    EXIT("/exit", "Exit the linear program.");

    private final String commandString;
    //private final String commandUsage;
    private final String commandDescription;

    CommandType(String commandString, String commandDescription) {
        this.commandString = commandString;
        this.commandDescription = commandDescription;
    }

    public static CommandType fromString(String input) {
        for (CommandType commandType : values()) {
            if (input.startsWith(commandType.commandString)) {
                return commandType;
            }
        }
        return null;
    }

    public String getCommandString() {
        return commandString;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

    public static void showHelp() {
        for (CommandType commandType : values()) {
            System.out.println(commandType.commandString + " - " + commandType.commandDescription);
        }
    }
}



