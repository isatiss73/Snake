package controler;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Game;
import network.TCPClientMessage;
import network.TCPMessage;
import network.TCPServerMessage;

/**
 * the controler of the game
 */
public class GameControler {
	/** simply the single game instance */
	private Game game;
	/** id of the first local player (0 if host) */
	private int leftID;
	/** id of the second local player( -1 if not, never 0) */
	private int rightID;
	/** true if there are two local players */
	private boolean twoLocal;
	/** true if you are host, false if you are guest */
	private boolean isHost;
	/** TCP server manager for host */
	private TCPServerMessage server;
	/** TCP client manager for everyone */
	private TCPClientMessage client;
	/** TCP ha ba non il faut l'enlever je pense */
	private TCPMessage tcp;
	/** qu'est-ce que c'est que cela ? */
	private Thread thread;
	
	/**
	 * unique constructor
	 * @param leftID id of the first local player (0 if host)
	 * @param twoLocal true if there are two local players
	 */
	public GameControler(int leftID, int rightID) {
		game = Game.getInstance();
		server = TCPServerMessage.getInstance();
		client = new TCPClientMessage();
		setIDs(leftID, rightID);
		// guests = new ArrayList<GuestProfile>();
	}
	
	/**
	 * reset TCP client informations
	 * @param Adresse omg it's in french
	 * @param port not Amsterdam
	 */
	public void reset(String Adresse, int port) {
		try {
			if(client != null) {
				client.reset(Adresse, port);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get the left player id
	 * @return the left player id
	 */
	public int getLeftID() {
		return leftID;
	}
	
	/**
	 * get if there are two local players
	 * @return true if there are two local
	 */
	public boolean isTwoLocal() {
		return twoLocal;
	}
	
	/**
	 * create threads and start them
	 */
	public void startThreads() {
		/*for (GuestProfile guest : guests) {
			guest.startThreads();
		}*/
		thread = new Thread(tcp);
		thread.start();
	}
	
	/**
	 * stop the threads bro
	 */
	public void stopThreads() {
		/*for (GuestProfile guest : guests) {
			guest.stopThreads();
		}*/
		if (thread != null)
			thread.interrupt();
	}
	
	/**
	 * change ID for the two local players
	 * @param left ID for the left player >= 0
	 * @param twoLocal true if there are two local players
	 */
	public void setIDs(int left, int right) {
		if (left >= 0) {
			leftID = left;
			if (right > 0) {
				rightID = right;
				twoLocal = true;
			}
			else {
				twoLocal = false;
				if (right == -1) {
					isHost = true;
					tcp = server;
				}
				else {
					isHost = false;
					tcp = client;
				}
			}
		}
	}
	
	/**
	 * add a guest to the list
	 * @param address device address
	 * @param port connection port
	 */
	/*public void addGuest(String address, int serverPort, int clientPort)
	{
		GuestProfile profile = new GuestProfile(address, serverPort, clientPort);
		guests.add(profile);
		profile.startThreads();
	}*/
	
	/**
	 * remove every guest from the list
	 */
	/*public void clearGuests()
	{
		// guests.clear();
	}*/
	
	/**
	 * send a message to every connected device
	 * @param message text message to send
	 */
	public void sendMessage(String message)
	{
		/*for (GuestProfile guest : guests) {
			guest.sendMessage(message);
		}*/
		server.sendMessage(message);
	}
	
	/**
	 * recieve a message for someone else and interpret it
	 * @param message the recieved message
	 */
	public void recieveMessage(String message) {
        boolean interesting = true;
        String[] split = message.split(":");
        
        // inputs management
        if (split.length > 1) {
	        message = split[1];
	        int id = Integer.valueOf(split[0]);
	        
	        // left player management
	        switch(message) {
	        case "D", "Right"-> game.getPlayer(id).setDirection( 1,  0);
	        case "Q", "Left" -> game.getPlayer(id).setDirection(-1,  0);
	        case "S", "Down" -> game.getPlayer(id).setDirection( 0,  1);
	        case "Z", "Up"   -> game.getPlayer(id).setDirection( 0, -1);
	        default          -> interesting = false;
	        }
	        
			if (interesting && leftID == 0)
				sendMessage(message);
        }
        else {
        	// other messages...
        }
        if (isHost && interesting) {
        	// send to every guest
        }
	}
	
	/**
	 * listener for key release on game
	 * @param event the key event
	 */
	public void keyReleased(KeyEvent event) {
		KeyCode keyCode = event.getCode();
        String keyText = keyCode.getName();
        boolean interesting = true;
        
        // left player management
        switch(keyText) {
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
        if (!interesting && twoLocal) {
        	interesting = true;
        	switch(keyText) {
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
        if (interesting) {
        	System.out.println("interesting : " + keyText);
        	sendMessage(keyText);
        }
	}
}
