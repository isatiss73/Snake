package controler;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Game;
import network.TCPClientMessage;
import network.TCPServerMessage;

/**
 * the controler of the game
 */
public class GameControler
{
	private int leftID;
	private int rightID;
	private boolean twoLocal;
	TCPServerMessage server;
	TCPClientMessage client;
	Thread serverThread;
	Thread clientThread;
	
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
		server = new TCPServerMessage();
		client = new TCPClientMessage();
	}
	
	/**
	 * create threads for client and server and start them
	 */
	public void startThreads() 
	{
		serverThread = new Thread(server);
		clientThread = new Thread(client);
		serverThread.start();
		clientThread.start();
	}
	
	/**
	 * stop the threads bro
	 */
	public void stopThreads()
	{
		client.sendMessage("exit");
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
        	client.sendMessage(keyText);
        }
	}
}
