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
	
	protected void setSocket() throws IOException {
		isA = new InetSocketAddress(address, port);
		socket = new Socket(address, port);
		socket.setSoTimeout(1000);
		setStreamBuffer(socket.getReceiveBufferSize());
	}
}