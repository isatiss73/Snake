package controler;

import java.io.IOException;

import network.TCPClientMessage;
import network.TCPServerMessage;

/**
 * server and client thread and runnable for a profile
 */
public class GuestProfile
{
	/** TCP server manager */
	private TCPServerMessage server;
	/** TCP client manager */
	private TCPClientMessage client;
	/** thread to recieve data */
	private Thread serverThread;
	/** thread to send data */
	private Thread clientThread;
	
	/**
	 * the only constructor
	 * @param address address to speak to
	 * @param port connection port
	 */
	public GuestProfile(String address, int serverPort, int clientPort) {
		server = new TCPServerMessage(address, serverPort);
		client = new TCPClientMessage(address, clientPort);
	}
	
	/**
	 * reset everything
	 * @param address new device address
	 * @param port new connection port
	 * @throws IOException an exception probably
	 */
	public void reset(String address, int serverPort, int clientPort) throws IOException {
		serverThread.interrupt();
		clientThread.interrupt();
		server.reset(address, serverPort);
		client.reset(address, clientPort);
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
		clientThread.interrupt();
		serverThread.interrupt();
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
