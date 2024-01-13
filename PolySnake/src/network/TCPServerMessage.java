package network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import controler.GameControler;
import model.Game;

/**
 * the runnable class for the server to globally manage TCP
 */
public class TCPServerMessage extends TCPServerBuilder implements Runnable {
	/** the single instance */
	private static TCPServerMessage instance;
	/** store recieved messages as text */
	private String message;
	/** the game controler */
	private GameControler gamer;
	/** a connection for every single guest */
	private ArrayList<TCPServerArm> arms;
	/** a thread for every single connection */
	private ArrayList<Thread> threads;
	/** false where we close the connection */
	private boolean open;
	
	/**
	 * constructor for local test
	 */
	private TCPServerMessage() {
		this("localhost", 8080);
	}
	
	/**
	 * main constructor
	 * @param address address of the client
	 * @param port listening port
	 */
	private TCPServerMessage(String address, int port) {
		super(address, port);
		message = "";
		open = true;
		gamer = Game.getInstance().getControler();
		arms = new ArrayList<TCPServerArm>();
		threads = new ArrayList<Thread>();
	}
	
	/**
	 * get the single instance
	 * @return the instance huh
	 */
	public static TCPServerMessage getInstance() {
		if (instance == null)
			instance = new TCPServerMessage();
		return instance;
	}
	
	/**
	 * reset the connection with other informations
	 * @param address ip address
	 * @param port listening port
	 */
	public void reset(String address, int port) throws IOException {
		super.reset(address, port);
	}
	
	/**
	 * the thread running method
	 */
	@Override
	public void run() {
		try {
			System.out.println("TCP server head running on " + address + ':' + port);
			setSocket();
			
			// we accept every guest request and add it to the list
			while (open == true) {
				Socket socket = ss.accept();
				TCPServerArm arm = new TCPServerArm(socket);
				Thread thread = new Thread(arm);
				arms.add(arm);
				threads.add(thread);
				thread.start();
			}
			System.out.println("TCP server closing request");
			for (Thread thread : threads) {
				thread.interrupt();
			}
			in.close();
			socket.close();
			System.out.println("TCP server closed");
		}
		catch(IOException e) { 
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String text) {
		message = text;
		// send the message to every guest
		for (TCPServerArm arm : arms) {
			arm.sendMessage(text);
		}
		// manage a closing message
		if (text.equals("exit"))
			close();
	}
	
	/**
	 * try to close every connection
	 */
	public void close() {
		open = false;
	}
	
	/**
	 * get the last not null message recieved
	 * @return the message as a string (at least empty)
	 */
	public String getMessage() {
		return message;
	}
}
