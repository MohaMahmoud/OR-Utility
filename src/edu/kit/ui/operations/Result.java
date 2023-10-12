package edu.kit.ui.operations;

import edu.kit.ui.logic.Operation;

public enum Result {
    /**
     * The {@link Operation} did not end successfully.
     */
    FAILURE() {
        @Override
        public void print(String message) {
            System.err.println(ERROR_PREFIX + message);
        }
    },

    /**
     * The {@link Operation} did end successfully.
     */
    SUCCESS() {
        @Override
        public void print(String message) {
            System.out.println(message);
        }
    };

    private static final String ERROR_PREFIX = "Error: ";

    /**
     * Prints the result of the {@link Operation}.
     * 
     * @param message The message to be printed
     */
    public abstract void print(String message);
}
