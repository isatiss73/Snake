package network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClientBuilder extends TCPMessage {
	Socket socket;
	InetSocketAddress isA;
	OutputStream out;
	String address;
	int port;
	
	TCPClientBuilder() {
		this("localhost", 8080);
	}
	
	TCPClientBuilder(String address, int port) {
		this.address = address;
		this.port = port;
		socket = null;
		isA = null;
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
	
	protected void setSocket() throws IOException {
		isA = new InetSocketAddress(address, port);
		socket = new Socket(address, port);
		// socket.setSoTimeout(1000);
		setStreamBuffer(socket.getReceiveBufferSize());
	}
}