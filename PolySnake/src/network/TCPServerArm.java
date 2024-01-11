package network;

import java.io.IOException;
import java.net.Socket;

import controler.GameControler;
import model.Game;

public class TCPServerArm extends TCPServerBuilder implements Runnable {
	private String message;
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
	
	public void run() {
		gamer = Game.getInstance().getControler();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
