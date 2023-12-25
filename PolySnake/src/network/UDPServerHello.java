package network;

import java.io.IOException;
import java.net.DatagramPacket;

public class UDPServerHello extends UDPServerBuilder implements Runnable
{
	public void run()
	{ 
		try
		{ 
			setConnection();
			socket.receive(req);
			System.out.println("request received"); 
			rep = new DatagramPacket(new byte[size],0,size,req.getSocketAddress()); 
			socket.send(rep); 
			System.out.println("reply sent");
			socket.close();
		} 
		catch(IOException e) 
		{
			System.out.println("IOException UDPServer");
		}
	}
}
