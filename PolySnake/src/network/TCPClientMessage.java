package network;

import java.io.IOException;

public class TCPClientMessage extends TCPClientBuilder implements Runnable {	
	private String message;
	
	public TCPClientMessage() {
		this("localhost", 8080);
	}
	
	public TCPClientMessage(String address, int port) {
		super(address, port);
		message = null;
	}
	
	public void run() {
		try {
			System.out.println("TCP client running on " + address + ':' + port);
			setSocket();
			out = socket.getOutputStream();
			int loop = 2000;
			loopWriteMessage(out, loop);
			out.close();
			socket.close();
		}
		catch(IOException e) { 
			System.out.println("IOException TCPClientLMessage" + e.getMessage()); 
		}
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
