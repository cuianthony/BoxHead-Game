/******************************************************************
 * Description:
 * 
 * The GameMap class manages the drawing of the play area, the 
 * fighter and the zombies. It also manages user interaction
 * such as key presses (UP, DOWN, LEFT, RIGHT, SPACE)
 * 
 * @author (Anthony Cui) 
 * @version (v2.0)
 * @version (March, 2016)
 ******************************************************************/

import java.util.ArrayList;
import java.util.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import sun.audio.*;
import java.io.*;

public class GameMap extends JPanel
{
    // instance fields 
    protected final int N = 0;
    protected final int S = 1;
    protected final int E = 2;
    protected final int W = 3;
    private int direction = 2;
    public static final int MAP_WIDTH = 800;
    public static final int MAP_HEIGHT = 600;
    public static final int SQUARE = 25;
    private final int MAX_ZOMBIE = 10;
    public Tile[][] mapTiles;
    
    //arraylist to hold zombies
    private ArrayList<Zombie> zombies;
    private int numZombies = 0;
    private int zombiesKilled = 0;
    private Zombie zombie = null;
    
    //arraylist to hold bullets
    private ArrayList<Bullet> bullets;
    private Bullet bullet = null;
    private boolean timerStart = true;
    
    private Fighter fighter = null;
    public String mapName = "map.txt";
    private Timer bulletTimer, zombieTimer;
    private int newZombieFreq = 0;
    private int fireRange = 0;
    
    private GamePanel gPanel;
    
    /******************************************************************
     * Description:
     * The GameMap constructor.
     * 
     * Limitations: none
     *****************************************************************/
    public GameMap(GamePanel gamePanel)
    {
        setPreferredSize(new Dimension(MAP_WIDTH,MAP_HEIGHT));
        
        gPanel = gamePanel;
        
        //Get the map of the play area
        getMap();
        //add key listener to handle LEFT, RIGHT, UP and DOWN key presses
        addKeyListener(new Listener());
        setFocusable(true);
        
        //When bullet timer expires, update bullets and animate with 
        //moveBullet
        bulletTimer = new Timer(1000,moveBullet);
        bulletTimer.setRepeats(true);
        bulletTimer.setDelay(10);
        
        //bullet array
        bullets = new ArrayList<Bullet>();
        
        //array list to hold zombies
        zombies = new ArrayList<Zombie>();
        //timer used for animation of zombie movement
        zombieTimer = new Timer(1000,moveZombie);
        zombieTimer.setRepeats(true);
        zombieTimer.setDelay(250);
        zombieTimer.start();
        
    }

    /******************************************************
     * Description:
     * Lay out the play area. The layout is saved in a text 
     * file.
     * 
     * Input: none
     * Return: none
     * Limitations: none
     ******************************************************/
    public void getMap()
    {
        MapReader mr = new MapReader();
        mr.setFileName(mapName);
        mapTiles = mr.getTiles();
        //position the fighter at the center of the map
        fighter = new Fighter(mapTiles,12,16);
        repaint();
    }
    
    /******************************************************
     * Description:
     * Start a new game by clearing bullets, zombies and
     * resetting the map.
     * 
     * Input: none
     * Return: none
     * Limitations: none
     ******************************************************/
    public void newGame()
    {
        numZombies = 0;
        zombiesKilled = 0;
        gPanel.updateKills(zombiesKilled);
        bullets.clear();
        zombies.clear();
        zombieTimer.stop();
        getMap();
        zombieTimer.start();
    }
    
    /******************************************************
     * Description:
     * Draw the play area, which includes the floor, 
     * surrounding walls, fighter, zombies and bullets.
     * 
     * Input: Graphics reference to draw on
     * Return: none
     * Limitations: none
     ******************************************************/
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        
        //draw the floor and walls
        paintMap(g);
        //draw the fighter
        fighter.draw(g2d);
        //draw existing zombies
        for(Zombie z: zombies)
        {
            z.draw(g2d);
        }
        
        //draw existing bullets
        for(Bullet bullet : bullets)
        {
            bullet.draw(g2d);
        }
    }

     /******************************************************
     * Description:
     * Draw the background, including the floor and the 
     * surrounding walls.
     * 
     * Input: Graphics reference to draw on
     * Return: none
     * Limitations: none
     ******************************************************/
    private void paintMap(Graphics g)
    {
        //tile based map
        for(int row =0; row<24; row++) {
            for(int col =0; col<32; col++) {
                mapTiles[row][col].draw(g);
            }
        }
    }
    
    /******************************************************
     * Description:
     * Returns number of zombies killed as integer
     * 
     * Input: none
     * Return: int number of zombies killed
     * Limitations: none
     ******************************************************/
    public int getZombieKills()
    {
        return zombiesKilled;
    }
    
    /******************************************************
     * Description:
     * private class Listener implements KeyListener. Based
     * on the key code, tells the fighter to move in the
     * desired direction or fires a bullet.
     * 
     * Limitations: can only process one key press at a time
     ******************************************************/
    private class Listener implements KeyListener
    {
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_UP)
            {
                fighter.move(N);
                direction = N;
            }
            else if(key == KeyEvent.VK_DOWN)
            {
                fighter.move(S);
                direction = S;
            }
            else if(key == KeyEvent.VK_LEFT)
            {
                fighter.move(W);
                direction = W;
            }
            else if(key == KeyEvent.VK_RIGHT)
            {
                fighter.move(E);
                direction = E;
            }
            else if(key == KeyEvent.VK_SPACE)
            {
                //create bullet at fighter's location
                bullet = new Bullet(direction, fighter.getCol()*25, fighter.getRow()*25);
                bullets.add(bullets.size(), bullet);
                
                //start animation
                if(timerStart)
                {
                    bulletTimer.start();
                    timerStart = false;
                }
            }
            
            repaint();
        }

        public void keyReleased(KeyEvent e)
        {
            //do nothing
        }

        public void keyTyped(KeyEvent e)
        {
            //do nothing
        }
    }
    
    /******************************************************
     * Description:
     * The moveZombie action listener is called every time
     * the zombie timer expires. For each zombie in the play area,
     * it will choose a new position for the zombie that 
     * moves it one step closer to the player
     * 
     ******************************************************/
    ActionListener moveZombie = new ActionListener()
    {
        public void actionPerformed(ActionEvent evt)
        {
            int fighterRow, fighterCol;
            
            //After 10 expirations of the timer, create a new zombie
            if(newZombieFreq%10 == 0)
            {
                newZombieFreq = 0;
                Zombie newZombie = new Zombie(mapTiles);
                zombies.add(newZombie);
                numZombies++;
            }
            newZombieFreq++;
            
            fighterRow = fighter.getRow();
            fighterCol = fighter.getCol();
            //Move each zombie to a new position that is one step closer 
            //to the fighter
            for(Zombie z: zombies)
            {
                z.follow(fighterRow, fighterCol);
            }
            repaint();
            
            //play the zombie sound
            if (newZombieFreq %10 == 0){
                playSound();
            }
        }
    };
    
    /******************************************************
     * Description:
     * The moveBullet action listener is called every time
     * the bullet timer expires. Every existing bullet has
     * its position updated. If a bullet goes out of the map,
     * it is removed. If a bullet hits a zombie, it is removed.
     * If a zombie is hit enough times, it dies and is removed.
     * 
     ******************************************************/
    ActionListener moveBullet = new ActionListener()
    {
        public void actionPerformed(ActionEvent evt)
        {
            //Use of ListIterators allows more effective processing
            //of arraylists
            ListIterator<Bullet> itr1 = bullets.listIterator();
            Bullet bullet;
            Zombie zombie;
            
            //Looping through existing bullets
            while(itr1.hasNext())
            {
                bullet = itr1.next();
                bullet.update();
                boolean isBulletRemoved = false;
                ListIterator<Zombie> itr2 = zombies.listIterator();
                //looping through existing zombies
                while(itr2.hasNext())
                {
                    zombie = itr2.next();
                    //check for collision between bullet and zombie
                    if(zombie.getRow() == bullet.getRow() && zombie.getCol() == bullet.getCol())
                    {
                        zombie.hit();
                        if(zombie.getHealth() <= 0)
                        {
                            zombie.dead();
                            itr2.remove();
                            zombiesKilled++;
                            gPanel.updateKills(zombiesKilled);
                        }
                        
                        itr1.remove();
                        isBulletRemoved = true;
                        
                        break;
                    }
                }
                
                //if bullet is out of map, it is removed
                if(!isBulletRemoved && bullet.isOutOfGame())
                {
                    itr1.remove();
                }
            }
            
            repaint();
        }
    };
    
     /******************************************************
     * Description:
     * Play the zombie sound
     * 
     * Input: none
     * Return: none
     * Limitations: none
     ******************************************************/
    static void playSound()
    {
        try
        {
            String soundFile = "ZombieMoan.wav";
            InputStream in = new FileInputStream(soundFile);
            
            AudioStream audioStream = new AudioStream(in);
            
            AudioPlayer.player.start(audioStream);
        }
        catch(Exception e)
        {
            System.out.println("Sound file exception");
        }
    }
}
