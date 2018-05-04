/******************************************************************
 * Description:
 * 
 * The Fighter class manages the moving and drawing of the fighter.
 * 
 * 
 * @author (Anthony Cui) 
 * @version (v2.0)
 * @version (March, 2016)
 ******************************************************************/
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

public class Fighter extends Character
{
    /******************************************************************
     * Description:
     * The Fighter constructor. Fighter knows its position on the board
     * 
     * Limitations: none
     *****************************************************************/
    public Fighter(Tile[][] tiles, int row, int col) 
    {
        super(tiles, row, col);
        
        try {
            imgE = ImageIO.read(new File("fighter_e.png"));
            imgW = ImageIO.read(new File("fighter_w.png"));
        } catch (IOException e) {
            System.out.println("fighter image doesn't exist");
        }
    }
}