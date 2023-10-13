package edu.kit.ui.exceptions;

import edu.kit.ui.logic.Operation;

/**
 * This class signals that a {@link Operation} is not valid.
 * 
 * @author Mohammad Mahmoud
 * @version 1.0
 */
public class OperationException extends Exception {

    /**
     * Constructs a new {@link OperationException}.
     * 
     * @param message The message of the exception
     */
    public OperationException(String message) {
        super(message);
    }
}
