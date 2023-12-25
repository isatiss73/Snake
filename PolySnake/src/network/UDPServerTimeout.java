package network;

import java.net.DatagramPacket;

public class UDPServerTimeout extends UDPServerBuilder implements Runnable
{
	public void run()
	{
		try
		{
			setConnection();
			req = new DatagramPacket(new byte[size], size);
			socket.receive(req);
			socket.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
			// e.printStackTrace();
		}
	}
}
