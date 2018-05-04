/******************************************************************
 * Description:
 * 
 * The GamePanel class extends JPanel. It partitions the UI into
 * two areas. The main centrl area is the Game map, the bottom area 
 * holds some game information as well as a button to start a new
 * game.
 * 
 * @author (Anthony Cui) 
 * @version (v2.0)
 * @version (March, 2016)
 ******************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel
{
    //instance fields
    private JPanel gPanel, controlPanel, leftPanel;
    private CharPanel charPanel;
    private GameMap map;
    
    //define constants
    private final int GAME_WIDTH = 900;
    private final int GAME_HEIGHT = 700;
    
    private final int DP_WIDTH = 900;
    private final int DP_HEIGHT = 600;
    
    private final int IP_WIDTH = 1100;
    private final int IP_HEIGHT = 200;
    
    /******************************************************************
     * Description:
     * The GamePanel constructor.
     * 
     * Limitations: none
     *****************************************************************/
    public GamePanel()
    {
        setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        //Set up game panel
        gPanel = new JPanel();
        map = new GameMap(this);
        charPanel = new CharPanel(map);
        gPanel.add(map);
        gPanel.setPreferredSize(new Dimension(DP_WIDTH,DP_HEIGHT));
        
        //Put the game map and player's info on the left side
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(gPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        leftPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        leftPanel.add(charPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        
        //pack things together
        add(Box.createRigidArea(new Dimension(15,0)));
        add(leftPanel);
        add(Box.createRigidArea(new Dimension(15,0)));
    }
    
    /******************************************************
     * Description:
     * Calls update method of charPanel to increment the
     * value displayed for number of zombies killed when
     * a zombie dies
     * 
     * Input: number of zombies killed
     * Return: none
     * Limitations: none
     ******************************************************/
    public void updateKills(int numKills)
    {
        charPanel.update(numKills);
    }
}
