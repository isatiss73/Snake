package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPServer implements Runnable
{
	private InetSocketAddress isA;		// the address
	private DatagramSocket s;			// the socket object
	private DatagramPacket req, rep; 	// to prepare the request and reply messages
	private final int size = 2048;		// the default size for the buffer array
	
	/** The builder. */
	UDPServer()
	{
		isA = new InetSocketAddress("localhost", 8080);
		s = null;
		req = rep = null;
	}

	/** The main run method for threading. */ 
	public void run()
	{ 
		try
		{ 
			s = new DatagramSocket(isA.getPort()); 
			req = new DatagramPacket(new byte[size],size); 
			s.receive(req); 
			System.out.println("request received"); 
			rep = new DatagramPacket(new byte[size],0,size,req.getSocketAddress()); 
			s.send(rep); 
			System.out.println("reply sent"); 
			s.close();
		} 
		catch(IOException e) 
		{
			System.out.println("IOException UDPServer");
		}
	}
}
