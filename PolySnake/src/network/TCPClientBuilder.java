package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * base for TCP client classes
 */
public class TCPClientBuilder extends TCPMessage {
	protected Socket socket;
	protected InetSocketAddress isA;
	protected InputStream in;
	protected OutputStream out;
	protected String address;
	protected int port;
	
	/**
	 * only useful for testing
	 */
	TCPClientBuilder() {
		this("localhost", 8080);
	}
	
	/**
	 * the only real constructor
	 * @param address listening address
	 * @param port listening port
	 */
	TCPClientBuilder(String address, int port) {
		this.address = address;
		this.port = port;
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
		isA = new InetSocketAddress(address, port);
		socket = new Socket(address, port);
		in = socket.getInputStream();
		out = socket.getOutputStream();
		setStreamBuffer(socket.getReceiveBufferSize());
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