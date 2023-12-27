package network;

import java.io.IOException;

public class TCPServerMessage extends TCPServerBuilder implements Runnable {
	private String message;
	
	public TCPServerMessage() {
		super();
	}
	
	public TCPServerMessage(String address, int port) {
		super(address, port);
	}
	
	public void run() {
		try
		{
			System.out.println("TCP server running on " + address + ':' + port);
			setSocket();
			socket = ss.accept();
			in = socket.getInputStream();
			String msIn;
			int nbLoop = 0;
			long compteur = 0;
			do {
				compteur += count();
				msIn = readMessage(in);
				System.out.println(msIn);
				nbLoop++;
			} while (in.read() != -1);
			System.out.println("\nIterations : " + nbLoop + "\nDonnees : " + compteur);
			in.close();
			socket.close();
		}
		catch(IOException e) { 
			System.out.println("IOException TCPServerLMessage" + e.getMessage()); 
		}
	}
	
	public String getMessage() {
		return message;
	}
}
