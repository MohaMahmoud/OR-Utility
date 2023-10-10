package ui;

import java.util.Scanner;

import model.LinearProgram;
import ui.commands.CommandExecutor;
import ui.commands.CommandFactory;
import ui.commands.CommandType;

public class CommandLineHandler {
    private Scanner scanner;
    private CommandFactory commandFactory;

    public CommandLineHandler(LinearProgram linearProgram) {
        this.scanner = new Scanner(System.in);
        this.commandFactory = new CommandFactory(linearProgram);
    }

    public void start() {
        try {
            while (true) {
                System.out.print("Enter command: ");
                String commandStr = scanner.nextLine();
                String[] commandParts = commandStr.split(" "); // Parse input into an array of strings
                CommandType commandType = CommandType.fromString(commandParts[0]);

                if (commandType != null) {
                    CommandExecutor executor = commandFactory.createCommandExecutor(commandType, scanner, commandParts);
                    executor.execute();
                } else {
                    System.out.println("Invalid command. Use /help to get a list of all available commands.");
                }
            }
        } finally {
            scanner.close();
        }
    }
}

