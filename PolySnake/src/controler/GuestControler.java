package controler;

import network.TCPClientMessage;
import network.TCPServerMessage;

public class GuestControler extends GameControler
{
	TCPServerMessage server;
	TCPClientMessage client;
	Thread serverThread;
	Thread clientThread;
	
	public GuestControler(int leftID)
	{
		super(leftID);
	}
	
	public GuestControler(int leftID, int rightID)
	{
		super(leftID, rightID);
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
	 * send a message to every connected device
	 * @param message text message to send
	 */
	public void sendMessage(String message)
	{
		client.sendMessage(message);
	}
}
