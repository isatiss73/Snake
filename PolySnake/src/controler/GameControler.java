package controler;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Game;

public class GameControler
{
	private int leftID;
	private int rightID;
	private boolean twoLocal;
	
	/**
	 * constructor with one single local player
	 * @param leftID
	 */
	public GameControler(int leftID)
	{
		setIDs(leftID, -1);
	}
	
	/**
	 * constructor with two local players
	 * @param leftID
	 * @param rightID
	 */
	public GameControler(int leftID, int rightID)
	{
		setIDs(leftID, rightID);
	}
	
	/**
	 * change ID for the two local players
	 * @param left ID for the left player >= 0
	 * @param right ID for the right player, -1 if no right player
	 */
	public void setIDs(int left, int right)
	{
		if (left >= 0)
		{
			leftID = left;
			if (right >= 0)
			{
				rightID = right;
				twoLocal = true;
			}
			else
			{
				twoLocal = false;
			}
		}
	}
	
	/**
	 * listener for key release on game
	 * @param event the key event
	 */
	public void keyReleased(KeyEvent event)
	{
		KeyCode keyCode = event.getCode();
        String keyText = keyCode.getName();
        //System.out.println("_" + keyText + "_");
        Game game = Game.getInstance();
        boolean left = true;
        
        // left player management
        switch(keyText)
        {
        case "D":
        	game.getPlayer(leftID).setDirection(1, 0);
        	break;
        case "Q":
        	game.getPlayer(leftID).setDirection(-1, 0);
        	break;
        case "S":
        	game.getPlayer(leftID).setDirection(0, 1);
        	break;
        case "Z":
        	game.getPlayer(leftID).setDirection(0, -1);
        	break;
        default:
        	left = false;
        	break;
        }
        // right player management
        if (!left && twoLocal)
        {
        	switch(keyText)
            {
            case "Right":
            	game.getPlayer(rightID).setDirection(1, 0);
            	break;
            case "Left":
            	game.getPlayer(rightID).setDirection(-1, 0);
            	break;
            case "Down":
            	game.getPlayer(rightID).setDirection(0, 1);
            	break;
            case "Up":
            	game.getPlayer(rightID).setDirection(0, -1);
            	break;
            default:
            	left = false;
            	break;
            }
        }
	}
}
