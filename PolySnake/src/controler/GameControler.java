package controler;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Game;

/**
 * the controler of the game
 */
public class GameControler
{
	protected int leftID = 1;
	protected int guests;
	protected boolean twoLocal;
	
	/**
	 * constructor with one single local player
	 * @param leftID
	 */
	public GameControler(int leftID)
	{
		this(leftID, -1);
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
	 * create threads and start them
	 */
	public void startThreads() 
	{
		
	}
	
	/**
	 * stop the threads bro
	 */
	public void stopThreads()
	{
		
	}
	
	/**
	 * send a message to every connected device
	 * @param message text message to send
	 */
	public void sendMessage(String message)
	{
		
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
				twoLocal = true;
			}
			else
			{
				twoLocal = false;
			}
		}
	}
	
	/**
	 * recieve a message for someone else and interpret it
	 * @param message the recieved message
	 * @return true if the message is interesting
	 */
	public boolean recieveMessage(String message)
	{
		Game game = Game.getInstance();
        boolean interesting = true;
        String[] split = message.split(":");
        message = split[1];
        int id = Integer.valueOf(split[0]);
        
        // left player management
        switch(message)
        {
        case "D":
        	game.getPlayer(id).setDirection(1, 0);
        	break;
        case "Q":
        	game.getPlayer(id).setDirection(-1, 0);
        	break;
        case "S":
        	game.getPlayer(id).setDirection(0, 1);
        	break;
        case "Z":
        	game.getPlayer(id).setDirection(0, -1);
        	break;
        default:
        	interesting = false;
        	break;
        }
        // right player management
        if (!interesting && twoLocal)
        {
        	interesting = true;
        	int rightID = leftID + 1;
        	switch(message)
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
            	interesting = false;
            	break;
            }
        }
        
        return interesting;
	}
	
	/**
	 * listener for key release on game
	 * @param event the key event
	 */
	public void keyReleased(KeyEvent event)
	{
		KeyCode keyCode = event.getCode();
        String keyText = keyCode.getName();
        Game game = Game.getInstance();
        boolean interesting = true;
        
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
        	interesting = false;
        	break;
        }
        // right player management
        if (!interesting && twoLocal)
        {
        	interesting = true;
        	int rightID = leftID + 1;
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
            	interesting = false;
            	break;
            }
        }
        // we send the key text to the server
        if (interesting)
        {
        	System.out.println("interesting : " + keyText);
        	sendMessage(keyText);
        }
	}
}
