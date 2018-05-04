/******************************************************************
 * Description:
 * 
 * The MapReader class scans a text file which holds the game map
 * layout, and saves the information in a two dimensional array.
 * 
 * @author (Anthony Cui) 
 * @version (v2.0)
 * @version (March, 2016)
 ******************************************************************/

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

public class MapReader
{
    // Instance Fields
    private String fileName;
    Tile[][] tiles;
    
    /******************************************************************
     * Description:
     * The MapReader constructor.
     * 
     * Limitations: none
     *****************************************************************/
    public MapReader()
    {
        //do nothing
    }
    
    /******************************************************
     * Description:
     * Set the file name
     * 
     * Input: fileName
     * Return: none
     * Limitations: none
     ******************************************************/
    public void setFileName(String name)
    {
        fileName = name;
    }
    
    /******************************************************
     * Description:
     * Read the play area layout info from the text file
     * and save it in the "tiles" array.
     * 
     * Input: none
     * Return: the "tiles" array
     * Limitations: none
     ******************************************************/
    public Tile[][] getTiles()
    {
        tiles = new Tile[24][32];
        Scanner fileScan = null;
        String text;
        
        try{
            fileScan = new Scanner( new File(fileName) );
            int i =0;
            
            while(fileScan.hasNext())
            {
                text = fileScan.nextLine();
                for(int j = 0; j < 32; j++)
                {
                    tiles[i][j] = new Tile(text.charAt(j), i, j);
                }
                i++;
            }
        }catch(IOException ioe){System.out.println("File not found");} // do nothing
        
        return tiles;
    }
}
