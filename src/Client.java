import model.LinearProgram;
import ui.OperationHandler;

/**
 * Entry point to the application. Initializes the command-line handler
 * and starts the program.
 * 
 * @author Oleksandr Shchetsura
 * @author Mohammad Mahmoud
 * @version 1.1
 */
public class Client {

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
