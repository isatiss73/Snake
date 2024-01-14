package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * base for TCP server classes
 */
public class TCPServerBuilder extends TCPMessage {
	protected Socket socket;
	protected InetSocketAddress isA;
	protected ServerSocket ss;
	protected InputStream in;
	protected OutputStream out;
	protected String address;
	protected int port;
	
	/**
	 * only useful for testing
	 */
	TCPServerBuilder() {
		this("localhost", 8080);
		System.out.println("TCPServerBuilder default constructor");
	}
	
	/**
	 * the only real constructor
	 * @param address listening address
	 * @param port listening port
	 */
	TCPServerBuilder(String address, int port) {
		super();
		try
		{
			reset(address, port);
		} catch (IOException e)
		{
			System.out.println("j'espere que tout va bien pour toi");
		}
	}
	
	/**
	 * reset the address and port then update the socket connection
	 * @param newAddress new address
	 * @param newPort new port
	 * @return true if it is successfull
	 * @throws IOException error with the socket
	 */
	public boolean reset(String newAddress, int newPort) throws IOException {
		if (available(newPort)) {
			address = newAddress;
			port = newPort;
			if (in != null)
				in.close();
			if (out != null)
				out.close();
			if (ss != null)
				ss.close();
			if (socket != null)
				socket.close();
			System.out.println("TCP server reset on " + address + ':' + port);
			return true;
		}
		return false;
	}
	
	/**
	 * create the socket and that things you know
	 * @throws IOException probably something terrible
	 */
	protected void setSocket() throws IOException {
		System.out.println("TCP socket setting on " + address + ':' + port);
		isA = new InetSocketAddress(address ,port);
		ss = new ServerSocket(port);
		socket = new Socket(address, port);
		in = socket.getInputStream();
		out = socket.getOutputStream();
		setStreamBuffer(ss.getReceiveBufferSize());
	}
	
	/**
	 * get the availability of a TCP port
	 * @param port port you are interested in
	 * @return true if the port is available
	 */
	public static boolean available(int port) {

	    ServerSocket ss = null;
	    DatagramSocket ds = null;
	    try {
	        ss = new ServerSocket(port);
	        ss.setReuseAddress(true);
	        ds = new DatagramSocket(port);
	        ds.setReuseAddress(true);
	        return true;
	    } catch (IOException e) {
	    } finally {
	        if (ds != null)
	        	ds.close();

	        if (ss != null) {
	            try {
	                ss.close();
	            } catch (IOException e) {
	            }
	        }
	    }

	    return false;
	}
	
	/**
	 * get the connection ip address
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * get the connection port
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
}