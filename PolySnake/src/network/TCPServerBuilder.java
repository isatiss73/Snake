package network;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerBuilder extends TCPMessage {
	Socket socket;
	InetSocketAddress isA;
	ServerSocket ss;
	InputStream in;
	String address;
	int port;
	
	TCPServerBuilder() {
		this("localhost", 8080);
	}
	
	TCPServerBuilder(String address, int port) {
		this.address = address;
		this.port = port;
		ss = null;
		socket = null;
		isA = null;
		in = null;
	}
	
	protected void setSocket() throws IOException {
		isA = new InetSocketAddress(address ,port);
		ss = new ServerSocket(port);
		socket = new Socket(address, port);
		setStreamBuffer(ss.getReceiveBufferSize());
	}
}