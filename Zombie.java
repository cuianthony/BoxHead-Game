/******************************************************************
 * Description:
 * 
 * The Zombie class manages the moving and drawing of a zombie.
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
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Color;

public class Zombie extends Character
{
    //instance fields
    private static Random ran = new Random();
    int health = 3;
    
    /******************************************************************
     * Description:
     * The Zombie constructor.
     * 
     * Limitations: none
     *****************************************************************/
    public Zombie(Tile[][] tiles)
    {
        super(tiles, 0, 0);
        int r = ran.nextInt(4);
        
        //choose a random position for the new zombie
        if( r ==0 || r==1)
        {
            col = 15 + r*2;
            row = 0;
        } else if (r==2 || r==3){
            col = 15 + (r-2)*2;
            row = 23;
        }
        
        
        try {
            imgE = ImageIO.read(new File("zombie_e.png"));
            imgW = ImageIO.read(new File("zombie_w.png"));
        } catch (IOException e) {
            System.out.println("zombie image doesn't exist");
        }
    }
    
    /******************************************************
     * Description:
     * When a zombie dies, changes the status of the last
     * tile it occupied to unoccupied so that other
     * characters can move onto the space.
     * 
     * Input: none
     * Return: none
     * Limitations: none
     ******************************************************/
    public void dead()
    {
        mapTiles[row][col].occupied(false);
    }
    
    /******************************************************
     * Description:
     * Decrement zombie health when hit by bullet
     * 
     * Input: none
     * Return: none
     * Limitations: none
     ******************************************************/
    public void hit()
    {
        health--;
    }
    
    /******************************************************
     * Description:
     * getter to return zombie health
     * 
     * Input: none
     * Return: zombie health as int
     * Limitations: none
     ******************************************************/
    public int getHealth()
    {
        return health;
    }

    /******************************************************
     * Description:
     * This method implements the algorithm that moves the
     * zombie towards the player's position
     * 1. The player position can change in real time, the zombie
     *    always targets the new position
     * 2. A random number is used to determine whether the zombie
     *    will be moved in the current turn. This will improve the
     *    animation when many zombies are moving
     * 3. A zombie can't move to a position that is already occupied
     *    by another zombie. If this is case, the algorithm tries
     *    to find a neaby unoccupied position to move the zombie
     * 
     * Input: the player's position
     * Return: none
     * Limitations: none
     ******************************************************/
    public void follow(int targetRow, int targetCol)
    {
        Tile[][] t = mapTiles;
        int r = ran.nextInt(2);
        //randomly decide if the zombie will skip a turn and
        //remain stationary
        if(r == 0) return;
        
        if (row == targetRow && col == targetCol) return;
        
        if (row > targetRow ){
            if (col > targetCol){
                if(!t[row-1][col-1].isOccupied()) {
                    move(NW);
                } else if (!t[row][col-1].isOccupied()) {
                    move(W);
                } else {
                    move(N);
                }
            } else if (col < targetCol) {
                if(!t[row-1][col+1].isOccupied()) {
                    move(NE);
                } else if (!t[row][col+1].isOccupied()) {
                    move(E);
                } else {
                    move(N);
                }                
            } else {
                if(!t[row-1][col].isOccupied()) {
                    move(N);
                } else if(!t[row-1][col+1].isOccupied()){
                    move(NE);
                } else {
                    move(NW);
                }
            }
        }else if (row < targetRow){
            if (col > targetCol){
                if(!t[row+1][col-1].isOccupied()) {
                    move(SW);
                } else if (!t[row][col-1].isOccupied()) {
                    move(W);
                } else {
                    move(S);
                }
            } else if (col < targetCol) {
                if(!t[row+1][col+1].isOccupied()) {
                    move(SE);
                } else if (!t[row][col+1].isOccupied()) {
                    move(E);
                } else {
                    move(S);
                }                
            } else {
                if(!t[row+1][col].isOccupied()) {
                    move(S);
                } else if(!t[row+1][col-1].isOccupied()){
                    move(SW);
                } else {
                    move(SE);
                }
            }
        } else { //row == targetRow
            if (col > targetCol){
                if(!t[row][col-1].isOccupied()) {
                    move(W);
                } else if (!t[row-1][col-1].isOccupied()) {
                    move(NW);
                } else {
                    move(SW);
                }
            } else if (col < targetCol) {
                if(!t[row][col+1].isOccupied()) {
                    move(E);
                } else if (!t[row-1][col+1].isOccupied()) {
                    move(NE);
                } else {
                    move(SE);
                }                
            } 
        }
    }
}
