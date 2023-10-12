package edu.kit;
import edu.kit.model.LinearProgram;
import edu.kit.ui.logic.OperationHandler;
import edu.kit.ui.util.StringUtility;

/**
 * Entry point to the application. Initializes the command-line handler
 * and starts the program.
 * 
 * @author Oleksandr Shchetsura
 * @author Mohammad Mahmoud
 * @version 1.1
 */
public class Client {
    private Client() throws IllegalAccessException {
        throw new IllegalAccessException(StringUtility.UTILITY_CLASS_INSTANTIATION);
    }

    /**
     * Main method of the program.
     * 
     * @param args The command-line arguments (not used in this program)
     */
    public static void main(String[] args) {
        OperationHandler session = new OperationHandler(new LinearProgram());
        session.start();
    }
}
