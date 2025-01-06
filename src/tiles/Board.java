/**
 * Represents the game board for game.
 * This class manages the tiles, handles mouse clicks, updates the UI,
 * and checks for game completion.
 * How to use:
 * - Create a new Board instance to initialize and start the game.
 * - Interact with the game board through mouse clicks as specified in the game rules.
 *
 * @author Gal Arad
 */
package tiles;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * Represents the game board for the game.
 */
public class Board extends GridPane {
    private static final int ROWS = 6;
    private static final int COLUMNS = 5;

    private final Tile[][] TILES;
    private Tile selectedTile;
    private int score = 0;
    private int bestScore = 0;

    /**
     * Creates a new game board with tiles, initializes it,
     * populates it, and creates a score box.
     */
    public Board() {
        TILES = new Tile[ROWS][COLUMNS];
        initializeBoard();
        populateBoard();
        createScoreBox();

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleMouseClicked(event);
            }
        });
    }

    private static Color randomColor(){
        // This function return a random color
        Random random = new Random();
        int num = random.nextInt(5);
        return switch (num) {
            case 0 -> Color.BLUE;
            case 1 -> Color.RED;
            case 2 -> Color.YELLOW;
            case 3 -> Color.GREEN;
            default -> Color.PURPLE;
        };
    }

    private static ArrayList<Color> ListOfColors() {
        // Creating a list of 15 random colors.
        ArrayList<Color> colorList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            colorList.add(randomColor());
        }
        return colorList;
    }

    private void createScoreBox() {
        // This method creates the score box text on the gui
        Text scoreText = new Text("Score: " + score);
        Text highScore = new Text("High Score: " + bestScore);
        scoreText.setFont(Font.font(20));
        highScore.setFont(Font.font(20));

        setConstraints(scoreText, COLUMNS, 0);
        setConstraints(highScore, COLUMNS, 1);

        getChildren().addAll(scoreText, highScore);
    }
    private void initializeBoard() {
        // This fills the TILES[][] in "empty" tiles
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                TILES[row][col] = new Tile();
            }
        }
    }
    private void populateBoard() {
        // This method fill the board with the tiles

        //creating random list of 15 colors to each rec
        ArrayList<Color> bigColors = ListOfColors();
        ArrayList<Color> mediumColors = ListOfColors();
        ArrayList<Color> smallColors = ListOfColors();

        //cloning each list
        ArrayList<Color> bigColors2 = new ArrayList<>(bigColors);
        ArrayList<Color> mediumColors2 = new ArrayList<>(mediumColors);
        ArrayList<Color> smallColor2 = new ArrayList<>(smallColors);

        //Shuffling each clone
        Collections.shuffle(bigColors2);
        Collections.shuffle(mediumColors2);
        Collections.shuffle(smallColor2);

        ArrayList<Tile> allTiles = new ArrayList<Tile>();

        // adding 15 random colored tiles to the allTiles array
        for (int i = 0; i<15; i ++){
            allTiles.add(new Tile(bigColors.remove(0),
                    mediumColors.remove(0),
                    smallColors.remove(0)));
        }
        // adding the matching (but shuffled) 15 random colored
        // tiles to the allTiles array
        for (int i = 0; i<15; i ++){
            allTiles.add(new Tile(bigColors2.remove(0),
                    mediumColors2.remove(0),
                    smallColor2.remove(0)));
        }
        Collections.shuffle(allTiles); // shuffling the tiles

        // adding them the tiles from allTiles to TILES[][]
        for (int row = 0; row < ROWS; row ++){
            for (int col = 0; col < COLUMNS; col++){
                TILES[row][col] = allTiles.remove(0);
            }
        }
        // Add tiles to the UI
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                add(createTileView(TILES[row][col]), col, row);
            }
        }
    }

    private StackPane createTileView(Tile tile) {
        StackPane stackPane = new StackPane();
        Rectangle bigRect = new Rectangle(75, 75, tile.getBig());
        bigRect.setStroke(tile.getStroke());
        Rectangle mediumRect = new Rectangle(50, 50, tile.getMedium());
        mediumRect.setStroke(tile.getStroke());
        Rectangle smallRect = new Rectangle(25, 25, tile.getSmall());
        smallRect.setStroke(tile.getStroke());
        stackPane.getChildren().addAll(bigRect, mediumRect, smallRect);
        return stackPane;
    }

    private void handleMouseClicked(MouseEvent event) {

        Node clickedNode = event.getPickResult().getIntersectedNode();
        clickedNode.getParent();
        double clickedX = event.getX();
        double clickedY = event.getY();

        if ((clickedX <= 375) && (clickedY <= 450)) {

            // every rectangular has width and height of 75.
            //So I am dividing without reminding to understand which rectangular
            // has been pressed
            int tileX = (int)(clickedX/75);
            int tileY = (int)(clickedY/75);

            Tile clickedTile = findTile(tileX,tileY);

            if (clickedTile != null) { // Check if the tile is found

                if (selectedTile == null) {
                    selectedTile = clickedTile;
                    selectedTile.setStroke(Color.BLACK);
                    updateUI();

                } else {
                    if (tilesMatch(selectedTile, clickedTile)) {
                        // Handle matched tiles (increase score)
                        score++;
                        if(score > bestScore){
                            bestScore = score;
                        }

                        // Draw in white anything that matches
                        eliminateMatches(selectedTile,clickedTile);

                        // if tile is completed (all white), setting the SelectedTile
                        // to null else change the selected tile to the next
                        if (allWhite(clickedTile)){
                            selectedTile.setStroke(Color.WHITE);
                            clickedTile.setStroke(Color.WHITE);
                            selectedTile = null;
                        }
                        else {
                            selectedTile.setStroke(Color.WHITE);
                            selectedTile = clickedTile;
                            selectedTile.setStroke(Color.BLACK);
                        }
                        updateUI();
                        gameOver(TILES);

                    } else {
                        selectedTile.setStroke(Color.WHITE);
                        selectedTile = null;
                        score = 0;
                        updateUI();

                    }
                }
            }
        }
    }

    private void updateUI() {
        getChildren().clear();  // Clear the existing UI elements
        // Add tiles to the UI
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                add(createTileView(TILES[row][col]), col, row);
            }
        }
        createScoreBox();
    }

    private void gameOver (Tile[][] ls){
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (!(allWhite(ls[row][col]))) {
                    return;
                }
            }
        }
        showGameOverAlert();
    }
    private boolean allWhite(Tile tile){
        // checking if all field of the tiles are white / completed
        return tile.getBig().equals(Color.WHITE) &&
                tile.getMedium().equals(Color.WHITE) &&
                tile.getSmall().equals(Color.WHITE);
    }

    private Tile findTile(int x, int y) {
        Tile tile = TILES[y][x];
        return tile;
    }
    private void eliminateMatches(Tile tile1, Tile tile2){
        if(tile1.getBig().equals(tile2.getBig())){
            tile1.setBig(Color.WHITE);
            tile2.setBig(Color.WHITE);
        }
        if(tile1.getMedium().equals(tile2.getMedium())){
            tile1.setMedium(Color.WHITE);
            tile2.setMedium(Color.WHITE);
        }
        if ((tile1.getSmall().equals(tile2.getSmall()))){
            tile1.setSmall(Color.WHITE);
            tile2.setSmall(Color.WHITE);
        }
    }

    private boolean tilesMatch(Tile tile1, Tile tile2) {
        // check that the same tile has not been pressed twice;
        if (tile1 == tile2){
            return false;
        }
        // Check if any parts of the tiles match
        // Making sure it does not match white with white
        return ((tile1.getBig().equals(tile2.getBig()) &&
                        !(tile1.getBig().equals(Color.WHITE))) ||
                (tile1.getMedium().equals(tile2.getMedium()) &&
                        !(tile1.getMedium().equals(Color.WHITE))) ||
                (tile1.getSmall().equals(tile2.getSmall()) &&
                        !(tile1.getSmall().equals(Color.WHITE))));
    }
    private void showGameOverAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("You Win!");
        alert.setHeaderText("Congratulations!");
        alert.setContentText("You've completed the game.");
        alert.showAndWait();
    }
}
