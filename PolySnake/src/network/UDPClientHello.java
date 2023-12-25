package network;

import java.io.IOException;
import java.net.DatagramPacket;


public class UDPClientHello extends UDPClientBuilder implements Runnable
{
	public void run()
	{ 
		try
		{ 
			setConnection(); 
			req = new DatagramPacket(new byte[size],0,size,isA.getAddress(),isA.getPort()); 
			socket.send(req); 
			System.out.println("request sent"); 
			rep = new DatagramPacket(new byte[size],size); 
			socket.receive(rep); 
			System.out.println("reply received"); 
			socket.close(); 
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
