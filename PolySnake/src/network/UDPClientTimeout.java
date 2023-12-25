package network;

import java.net.DatagramPacket;

public class UDPClientTimeout extends UDPClientBuilder implements Runnable
{
	public void run()
	{
		try
		{
			setConnection();
			rep = new DatagramPacket(new byte[size], size);
			socket.receive(rep);
			socket.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
			// e.printStackTrace();
		}
	}
}
