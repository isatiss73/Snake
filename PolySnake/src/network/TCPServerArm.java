package network;

import java.io.IOException;
import java.net.Socket;

import controler.GameControler;
import model.Game;

/**
 * runnable TCP connector between the host and a single guest
 */
public class TCPServerArm extends TCPServerBuilder implements Runnable {
	/** text message buffer */
	private String message;
	/** the game controler */
	private GameControler gamer;
	
	/**
	 * main constructor
	 * @param socket the existant socket to connect to
	 */
	public TCPServerArm(Socket socket) {
		super(socket.getInetAddress().getHostName(), socket.getPort());
		this.socket = socket;
		message = "";
		gamer = Game.getInstance().getControler();
	}
	
	/**
	 * the thread running method
	 */
	@Override
	public void run() {
		System.out.println("TCP server arm running on " + address + ':' + port);
		
		try {
			setSocket();
			in = socket.getInputStream();
			String msIn = "";
			int nbLoop = 0;
			long compteur = 0;
			// do {
			while (!msIn.equals("exit")) {
				compteur += count();
				msIn = readMessage(in);
				if (msIn != null) {
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
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * give a message to send to the client
	 * @param message text to send, 'exit' to stop
	 */
	public void sendMessage(String text) {
		System.out.println("arm sending : " + text);
		try
		{
			if (out != null) {
				writeMessage(out, text);
				if (text.equals("exit")) {
					out.close();
					socket.close();
					System.out.println("TCP client closed");
				}
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
