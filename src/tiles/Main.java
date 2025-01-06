/**
 * Main class for the Tiles Game.
 * This class is the entry point of the application.
 * It initializes and starts the JavaFX application, creating
 * the game board and setting up the user interface.
 * How to use:
 * - Run the `main` method to launch the Tiles Game.*
 * @author Gal Arad
 */

package tiles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class for the Tiles Game.
 */
public class Main extends Application {

    /**
     * Launches the JavaFX application.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {launch(args);}

    /**
     * Initializes and starts the JavaFX application, creating
     * the game board and setting up the user interface.
     * @param stage the stage for the application.
     */
    public void start(Stage stage) {
        stage.setTitle("Tiles Game");
        Board board = new Board();
        Scene scene = new Scene(board, 515, 450);
        stage.setScene(scene);
        stage.show();
    }
}