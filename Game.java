/************************************************************************************
 * DESCRIPTION:
 * The Game class acts as the driver class to start the program. It creates the
 * main JFrame "Zombie Game" and adds a GamePanel.
 * 
 * Please see the README file for the Project Description, User Instructions, 
 * Planning and next steps for the project
 * 
 * @author (Anthony Cui)
 * @version (v2.0)
 * @date (March 2015)
 ************************************************************************************/

import javax.swing.JFrame;

public class Game
{
  public static void main (String[] args)
  {
      JFrame frame = new JFrame("Zombie Game");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      frame.getContentPane().add(new GamePanel());
      
      frame.setResizable(false);
      frame.pack();
      frame.setVisible(true);
      frame.setLocationRelativeTo(null);
      
      System.out.println();
  }
}
