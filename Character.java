/******************************************************************
 * Description:
 * 
 * The Character class manages the moving and drawing of a Character.
 * A charater can be a fighter or a zombie.
 * 
 * @author (Anthony Cui) 
 * @version (v2.0)
 * @version (March, 2016)
 ******************************************************************/
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.io.File;
import javax.imageio.ImageIO;

public class Character
{
    //instance fields and defining constants
    protected final int N = 0;
    protected final int S = 1;
    protected final int E = 2;
    protected final int W = 3;
    protected final int NW = 4;
    protected final int NE = 5;
    protected final int SE = 6;
    protected final int SW = 7;
    protected final int TILE_SIZE = 25;
    protected int row, col;
    protected Tile[][] mapTiles;
    protected BufferedImage imgE;
    protected BufferedImage imgW;
    protected int imageDirection = E;

    /******************************************************************
     * Description:
     * The Character constructor.
     * 
     * Limitations: none
     *****************************************************************/
    public Character(Tile[][] tiles, int row, int col)
    {
        this.mapTiles = tiles;
        this.row = row;
        this.col = col;
        tiles[row][col].occupied(true);
    }

    /******************************************************
     * Description:
     * Getter to return the row number 
     * 
     * Input: none
     * Return: row number
     * Limitations: none
     ******************************************************/
    public int getRow()
    {
        return row;
    }

    /******************************************************
     * Description:
     * Getter to return the column number 
     * 
     * Input: none
     * Return: column number
     * Limitations: none
     ******************************************************/
    public int getCol()
    {
        return col;
    }

    /******************************************************
     * Description:
     * Setter to set the row and collum numbers
     * 
     * Input: int values for row and col
     * Return: none
     * Limitations: none
     ******************************************************/
    public void setRowCol(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    
    /******************************************************
     * Description:
     * Let the character draw itself. Depending on the 
     * direction of the character either an
     * east-facing image or west-facing image is used
     * 
     * Input: The Graphics reference to draw on
     * Return: none
     * Limitations: none
     ******************************************************/
    public void draw(Graphics2D g) 
    {
        if(imageDirection == E){
            g.drawImage(imgE,col*TILE_SIZE,row*TILE_SIZE,null);
        } else {
            g.drawImage(imgW,col*TILE_SIZE,row*TILE_SIZE,null);
        }
    }
    
    /******************************************************
     * Description:
     * Update the coordinates of the character based on
     * the direction of movement. If the targeted position is 
     * occupied, no change will be made
     * 
     * Input: the direction to move towards
     * Return: none
     * Limitations: none
     ******************************************************/
    public void move(int dir) 
    {        
        switch (dir){
            case N:
               if(!mapTiles[row-1][col].isOccupied())
               {
                   mapTiles[row][col].occupied(false);
                   mapTiles[row-1][col].occupied(true);
                   row = row -1;
               }  
               break;
            case S:
               if(!mapTiles[row+1][col].isOccupied())
               {
                   mapTiles[row][col].occupied(false);
                   mapTiles[row+1][col].occupied(true);
                   row = row + 1;
               }  
               break;
            case W:
               if(!mapTiles[row][col-1].isOccupied())
               {
                   mapTiles[row][col].occupied(false);
                   mapTiles[row][col-1].occupied(true);
                   col = col -1;
               }  
               break;
            case E:
               if(!mapTiles[row][col+1].isOccupied())
               {
                   mapTiles[row][col].occupied(false);
                   mapTiles[row][col+1].occupied(true);
                   col = col +1;
               }
               break;
            case NW:
               if(!mapTiles[row-1][col-1].isOccupied())
               {
                   mapTiles[row][col].occupied(false);
                   mapTiles[row-1][col-1].occupied(true);
                   row = row -1;
                   col = col -1;
               }  
               break;
            case NE:
               if(!mapTiles[row-1][col+1].isOccupied())
               {
                   mapTiles[row][col].occupied(false);
                   mapTiles[row-1][col+1].occupied(true);
                   row = row - 1;
                   col = col +1;
               }  
               break;
            case SE:
               if(!mapTiles[row+1][col+1].isOccupied())
               {
                   mapTiles[row][col].occupied(false);
                   mapTiles[row+1][col+1].occupied(true);
                   row = row+1;
                   col = col +1;
               }  
               break;
            case SW:
               if(!mapTiles[row+1][col-1].isOccupied())
               {
                   mapTiles[row][col].occupied(false);
                   mapTiles[row+1][col-1].occupied(true);
                   row = row +1;
                   col = col -1;
               }
               break;
            default:
        }
        
        if (dir == E || dir == NE || dir == SE) {
            imageDirection = E;
        } else if (dir == W || dir == NW ||dir == SW) {
            imageDirection = W;
        }
    }
}
