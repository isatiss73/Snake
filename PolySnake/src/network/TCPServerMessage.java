package network;

import java.io.IOException;

import controler.GameControler;
import model.Game;

/**
 * the runnable class to recieve messages with TCP as a server
 */
public class TCPServerMessage extends TCPServerBuilder implements Runnable {
	
	private String message;
	private GameControler gamer;
	
	/**
	 * constructor for local test
	 */
	public TCPServerMessage() {
		this("localhost", 8080);
	}
	
	/**
	 * main constructor
	 * @param address address of the client
	 * @param port listening port
	 */
	public TCPServerMessage(String address, int port) {
		super(address, port);
		message = "";
		gamer = Game.getInstance().getControler();
	}
	
	public void run() {
		try
		{
			System.out.println("TCP server running on " + address + ':' + port);
			setSocket();
			socket = ss.accept();
			in = socket.getInputStream();
			String msIn = "";
			int nbLoop = 0;
			long compteur = 0;
			// do {
			while (!msIn.equals("exit")) {
				compteur += count();
				msIn = readMessage(in);
				if (msIn != null)
				{
					System.out.println(msIn);
					gamer.recieveMessage(msIn);
				}
				else
					msIn = "";
				nbLoop++;
			} // while (in.read() != -1);
			System.out.println("\nIterations : " + nbLoop + "\nDonnees : " + compteur);
			in.close();
			socket.close();
			System.out.println("TCP server closed");
		}
		catch(IOException e) { 
			e.printStackTrace();
		}
	}
	
	/**
	 * get the last not null message recieved
	 * @return the message as a string (at least empty)
	 */
	public String getMessage() {
		return message;
	}
}
