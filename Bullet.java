
/******************************************************************
 * Description:
 * The Bullet class manages the moving and drawing of the
 * bullets.
 * 
 * @author (Anthony Cui) 
 * @version (v2.0)
 * @version (March, 2016)
 ******************************************************************/

import java.awt.*;

public class Bullet
{
    //instance fields
    int direction, x, y;
    int row, col;
    boolean remove = false;
    
    /******************************************************************
     * Description:
     * The Bullet constructor. Bullet knows its position on the board
     * and its direction of travel
     * 
     * Limitations: none
     *****************************************************************/
    public Bullet(int direction, int x, int y)
    {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }
    
    /******************************************************
     * Description:
     * Let the bullet draw itself, dependent on the its
     * direction value
     * 
     * Input: The Graphics reference to draw on
     * Return: none
     * Limitations: none
     ******************************************************/
    public void draw(Graphics2D g)
    {
        if(direction == 0 || direction == 1)
        {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, 3, 5);
        }
        else
        {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, 5, 3);
        }
    }
    
    /******************************************************
     * Description:
     * Update the bullets position
     * 
     * Input: none
     * Return: none
     * Limitations: none
     ******************************************************/
    public void update()
    {
        //update bullet position based on direction
        if(direction == 0) //north
            y -= 10;
        else if(direction == 1) //south
            y += 10;
        else if(direction == 2) //east
            x += 10;
        else if(direction == 3) //west
            x -= 10;
            
        //check if bullet is out of map
        if(x < 0)
            remove = true;
        else if(x > 800)
            remove = true;
        else if(y > 600)
            remove = true;
        else if(y < 0)
            remove = true;
    }
    
    /******************************************************
     * Description:
     * Returns boolean value of whether the bullet is out
     * of the map
     * 
     * Input: none
     * Return: boolean is bullet out of map
     * Limitations: none
     ******************************************************/
    public boolean isOutOfGame()
    {
        return remove;
    }
    
    /******************************************************
     * Description:
     * Determines row based on y value and returns row
     * 
     * Input: none
     * Return: int value of row
     * Limitations: none
     ******************************************************/
    public int getRow()
    {
        row = y/25;
        return row;
    }
    /******************************************************
     * Description:
     * Determines column based on x value and returns col
     * 
     * Input: none
     * Return: int value of column
     * Limitations: none
     ******************************************************/
    public int getCol()
    {
        col = x/25;
        return col;
    }
    
    /******************************************************
     * Description:
     * Getter for bullet direction
     * 
     * Input: none
     * Return: d
     * Limitations: none
     ******************************************************/
    public int getDirection()
    {
        return direction;
    }
}
