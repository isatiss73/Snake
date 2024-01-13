package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	}
	
	/**
	 * the only real constructor
	 * @param address listening address
	 * @param port listening port
	 */
	TCPServerBuilder(String address, int port) {
		this.address = address;
		this.port = port;
		ss = null;
		socket = null;
		isA = null;
		in = null;
		out = null;
	}
	
	/**
	 * reset the address and port then update the socket connection
	 * @param newAddress new address
	 * @param newPort new port
	 * @throws IOException error with the socket
	 */
	public void reset(String newAddress, int newPort) throws IOException {
		address = newAddress;
		port = newPort;
		setSocket();
	}
	
	/**
	 * create the socket and that things you know
	 * @throws IOException probably something terrible
	 */
	protected void setSocket() throws IOException {
		isA = new InetSocketAddress(address ,port);
		ss = new ServerSocket(port);
		socket = new Socket(address, port);
		in = socket.getInputStream();
		out = socket.getOutputStream();
		setStreamBuffer(ss.getReceiveBufferSize());
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