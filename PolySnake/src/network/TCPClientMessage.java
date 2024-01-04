package network;

import java.io.IOException;

/**
 * the runnable class to send messages with TCP as a client
 */
public class TCPClientMessage extends TCPClientBuilder implements Runnable {
	
	/**
	 * constructor for local test
	 */
	public TCPClientMessage() {
		this("localhost", 8080);
	}
	
	/**
	 * main constructor
	 * @param address address of the server
	 * @param port listening port
	 */
	public TCPClientMessage(String address, int port) {
		super(address, port);
	}
	
	public void run() {
		try {
			System.out.println("TCP client running on " + address + ':' + port);
			setSocket();
			out = socket.getOutputStream();
			// loopWriteMessage(out, 2000);
			/*while (!message.equals("exit")) {
				if (message != "") {
					System.out.println("sending : " + message);
					writeMessage(out, message);
					message = "";
				}
			}
			writeMessage(out, message);
			out.close();
			socket.close();
			System.out.println("TCP client closed");*/
		}
		catch(IOException e) { 
			e.printStackTrace();
		}
	}
	
	/**
	 * give a message to send to the server
	 * @param message text to send, 'exit' to stop
	 */
	public void sendMessage(String message) {
		System.out.println("sending : " + message);
		try
		{
			writeMessage(out, message);
			if (message.equals("exit")) {
				out.close();
				socket.close();
				System.out.println("TCP client closed");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
