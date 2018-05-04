/******************************************************************
 * Description:
 * 
 * The GameMap class manages the drawing of a tile and handles whether 
 * the tile is occupied or not. There are two types of tile: floor
 * or wall.
 * 
 * @author (Anthony Cui) 
 * @version (v2.0)
 * @version (March, 2016)
 ******************************************************************/

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Tile
{
    // constants
    protected final char WALL = '#';
    protected final char FLOOR = '*';
    protected final int TILE_SIZE = 25;
    //instance variables
    protected int row;
    protected int col;
    protected char type;
    private boolean occupied;
    protected BufferedImage img;

    
    /******************************************************************
     * Description:
     * The Tile constructor.
     * 
     * Limitations: none
     *****************************************************************/
    public Tile(char type, int row, int col)
    {
        this.type = type;
        occupied = false;
        this.row = row;
        this.col = col;
        
        try{
            if(type == WALL){
                img = ImageIO.read(new File("wall.png"));
                occupied = true;
            }
            else if(type == FLOOR){
                img = ImageIO.read(new File("wall_s.png"));
                occupied = false;
            }
        }catch(IOException e){
            System.err.println("Image for Tile not Found...");
        }
    }
    
    /******************************************************
     * Description:
     * Getter method returns a boolean for whether the tile
     * is occupied or not
     * 
     * Input: none
     * Return: a boolean for tile occupation
     * Limitations: none
     ******************************************************/
    public boolean isOccupied()
    {
        return occupied;
    }

    /******************************************************
     * Description:
     * Setter method for occupation of a tile
     * 
     * Input: boolean for tile occupation
     * Return: none
     * Limitations: none
     ******************************************************/
    public void occupied(boolean flag)
    {
        occupied = flag;
    }
    
    /******************************************************
     * Description:
     * Returns the type of the tile
     * 
     * Input: none
     * Return: the type of the tile
     * Limitations: none
     ******************************************************/
    public char type()
    {
        return type;
    }

    /******************************************************
     * Description:
     * Let the tile draw itself
     * 
     * Input: The Graphics reference to draw on
     * Return: none
     * Limitations: none
     ******************************************************/
    public void draw(Graphics g)
    {      
        // drawing the image of the tile
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img,col*TILE_SIZE,row*TILE_SIZE, null);
    }

    /******************************************************
     * Description:
     * toString to print the tile
     * 
     * Input: none
     * Return: the tile type as a string
     * Limitations: none
     ******************************************************/
    public String toString()
    {
        return "" + type;
    }
}
