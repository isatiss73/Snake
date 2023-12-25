package network;

import java.net.*;
import java.io.*;


public class UDPServerBuilder extends UDPRWEmpty
{
	protected InetSocketAddress isA;		// the address
	protected DatagramSocket socket;				// the socket object
	protected DatagramPacket req, rep; 		// to prepare the request and reply messages
	public final int timeout = 1000;
	public final int size = 2048;		// the default size for the buffer array
	
	/** The builder. */
	UDPServerBuilder()
	{
		isA = new InetSocketAddress("localhost", 8080); // messages a moi-meme
		// isA = new InetSocketAddress("192.168.24.158", 8090); // messages a Alexandre
		socket = null;
		req = rep = null;
	}
	
	protected void setConnection() throws IOException
	{
		socket = new DatagramSocket(isA.getPort());
		socket.setSoTimeout(timeout);
		req = new DatagramPacket(new byte[size],size);
		/** we can include more setting, later ... */ 
	}
}
