package network;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;


public class UDPClientBuilder extends UDPInfo
{
	protected InetSocketAddress isA;     
	protected DatagramSocket socket; 
	protected DatagramPacket req, rep;
	public final int timeout = 1000;
	public final int size = 2048;
	
	UDPClientBuilder() 
	{
		isA = null; 
		socket = null;
		req = rep = null;
	} 
	
	protected void setConnection() throws IOException
	{
		socket = new DatagramSocket();
		socket.setSoTimeout(timeout);
		isA = new InetSocketAddress("localhost", 8080);
		// isA = new InetSocketAddress("192.168.0.53", 8090); // messages au prof
		// isA = new InetSocketAddress("192.168.24.158", 8090); // messages a Alexandre
		/** we can include more setting, later ... */
	}
}
