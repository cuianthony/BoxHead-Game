/******************************************************************
 * Description:
 * 
 * The Character Panel class extends JPanel. It is used 
 * to display game info and holds a new game button. The info is 
 * updated in real time as the game is played.
 *  
 * @author (Anthony Cui) 
 * @version (v2.0)
 * @version (March, 2016)
 ******************************************************************/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class CharPanel extends JPanel
{
    //instance fields and defining constants
    private final int IP_WIDTH = 1060;
    private final int IP_HEIGHT = 200;
    
    //these widgets are used to build the CharPanel
    private JPanel playerP1, playerInfo;
    
    private JLabel playerImage;
    private JLabel zombiesKilled;
    private JButton newGame;
    
    private BufferedImage playerImage1;
    
    private GameMap map;
    
    /******************************************************************
     * Description:
     * The CharPanel constructor.
     * 
     * Limitations: none
     *****************************************************************/
    public CharPanel(GameMap map)
    {       
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        this.map = map;
        
        //read images from png file
        try{
            playerImage1 = ImageIO.read(new File("Fighter_e.png"));
        }catch(IOException e){
            System.err.println("Image for Tile not Found...");
        }
        
        
        //add player image to label
        playerImage = new JLabel(new ImageIcon(playerImage1));
        
        zombiesKilled = new JLabel("Number of zombies killed: 0");
        zombiesKilled.setForeground(Color.RED);
        zombiesKilled.setFont(new Font("Arial", Font.BOLD, 14));
        
        newGame = new JButton("New Game");
        newGame.setFocusable(false);
        //add button listener for new game button
        ButtonListener listener = new ButtonListener();
        newGame.addActionListener(listener);
        
        //pack everything together
        add(playerImage);
        add(Box.createRigidArea(new Dimension(30,0)));
        add(zombiesKilled);
        add(Box.createRigidArea(new Dimension(30,0)));
        add(newGame);
    }

    /******************************************************************
     * Description:
     * The ButtonListener class implement the actions needed to be taken 
     * when "New Game" button is pressed by calling the newGame method of
     * the GameMap
     * 
     * Limitations: none
     *****************************************************************/
    private class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(event.getSource() == newGame)
            {
                map.newGame();
            }
        }
    }
    
    /******************************************************************
     * Description:
     * Updates the zombiesKilled label when a zombie dies.
     * 
     * Limitations: none
     *****************************************************************/
    void update(int zombieKills)
    {
        zombiesKilled.setText("Number of zombies killed: " +zombieKills);
    }
}
