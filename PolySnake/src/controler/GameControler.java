package controler;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Game;

/**
 * the controler of the game
 */
public class GameControler
{
	/** id of the first local player (0 if host) */
	private int leftID;
	/** true if there are two local players */
	private boolean twoLocal;
	/** list of guests if you are host, only the host else */
	ArrayList<GuestProfile> guests;
	
	/**
	 * unique constructor
	 * @param leftID id of the first local player (0 if host)
	 * @param twoLocal true if there are two local players
	 */
	public GameControler(int leftID, boolean twoLocal)
	{
		setIDs(leftID, twoLocal);
		guests = new ArrayList<GuestProfile>();
	}
	
	/**
	 * create threads and start them
	 */
	public void startThreads() 
	{
		for (GuestProfile guest : guests) {
			guest.startThreads();
		}
	}
	
	/**
	 * stop the threads bro
	 */
	public void stopThreads()
	{
		for (GuestProfile guest : guests) {
			guest.stopThreads();
		}
	}
	
	/**
	 * change ID for the two local players
	 * @param left ID for the left player >= 0
	 * @param twoLocal true if there are two local players
	 */
	public void setIDs(int left, boolean twoLocal)
	{
		if (left >= 0)
		{
			leftID = left;
			this.twoLocal = twoLocal;
		}
	}
	
	/**
	 * add a guest to the list
	 * @param address device address
	 * @param port connection port
	 */
	public void addGuest(String address, int port)
	{
		GuestProfile profile = new GuestProfile(address, port);
		guests.add(profile);
		profile.startThreads();
	}
	
	/**
	 * remove every guest from the list
	 */
	public void clearGuests()
	{
		guests.clear();
	}
	
	/**
	 * send a message to every connected device
	 * @param message text message to send
	 */
	public void sendMessage(String message)
	{
		for (GuestProfile guest : guests) {
			guest.sendMessage(message);
		}
	}
	
	/**
	 * recieve a message for someone else and interpret it
	 * @param message the recieved message
	 */
	public void recieveMessage(String message)
	{
		Game game = Game.getInstance();
        boolean interesting = true;
        String[] split = message.split(":");
        
        // inputs management
        if (split.length > 1)
        {
	        message = split[1];
	        int id = Integer.valueOf(split[0]);
	        
	        // left player management
	        switch(message)
	        {
	        case "D":
	        case "Right":
	        	game.getPlayer(id).setDirection(1, 0);
	        	break;
	        case "Q":
	        case "Left":
	        	game.getPlayer(id).setDirection(-1, 0);
	        	break;
	        case "S":
	        case "Down":
	        	game.getPlayer(id).setDirection(0, 1);
	        	break;
	        case "Z":
	        case "Up":
	        	game.getPlayer(id).setDirection(0, -1);
	        	break;
	        default:
	        	interesting = false;
	        	break;
	        }
	        
			if (interesting && leftID == 0)
				sendMessage(message);
        }
        else
        {
        	// other messages...
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
