/**
 * Class representing a tile.
 * Each tile has colors for big, medium, small rectangles,
 * and a stroke color.
 * How to use:
 * - Create a new Tile instance with specified colors or
 *   use the default constructor for an empty tile.
 * - Use the setter methods to change the color of each part
 *   of the tile or the color of the stroke.
 * - Use the getter methods to get the color of each part
 *   of the tile or the color of the stroke.
 *
 * @author Gal Arad
 */
package tiles;

import javafx.scene.paint.Color;

/** Class Tile
 *  Class representing a tile in the Tiles Game.
 *  Each tile has colors for big, medium, small rectangles,
 *  and a stroke color.
 */
public class Tile {
    private Color big;
    private Color medium;
    private Color small;
    private Color stroke;


    /**
     * Constructor to create a tile with specified colors
     * for big, medium, and small rectangles.
     *
     * @param big1    Color for the big rectangular.
     * @param medium1 Color for the medium rectangular.
     * @param small1  Color for the small rectangular.
     */
    public Tile(Color big1, Color medium1, Color small1) {
        big = big1;
        medium = medium1;
        small = small1;
        stroke = Color.WHITE;
    }

    /**
     * Default constructor to create an empty tile with all white colors.
     */
    public Tile(){
        big = Color.WHITE;
        medium = Color.WHITE;
        small = Color.WHITE;
        stroke = Color.WHITE;

    }

    /**
     * Setters to change the color of each part of the
     * tile or the color of the stroke.
     */
    public void setBig(Color color){
        big = color;
    }
    public void setMedium(Color color){
        medium = color;
    }
    public void setSmall(Color color){
        small = color;
    }
    public void setStroke(Color color){
        stroke = color;
    }

    /**
     * Getters to return the color of each part of
     tile or the color of the stroke.
     */
    public Color getBig() {
        return big;
    }
    public Color getMedium() {
        return medium;
    }
    public Color getSmall() {
        return small;
    }
    public Color getStroke() {
        return stroke;
    }


}
