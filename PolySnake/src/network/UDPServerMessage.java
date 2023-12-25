package network;

import java.net.DatagramPacket;

public class UDPServerMessage extends UDPServerBuilder implements Runnable
{
	private int wait = 0;
	private final int patience = 10;
	String message;
	
	private void print(Object o)
	{
		System.out.print("server: ");
		System.out.println(o);
	}

	public void run()
	{
		print(patience + " boucles de patience");
		while (wait < patience)
		{
			try
			{
				print("try");
				setConnection();
				req = new DatagramPacket(new byte[size], 0, size, isA.getAddress(), isA.getPort());
				socket.send(req);
				message = getMessage(req);
				print(message.length());
				if (message.length() > 0)
				{
					print(message);
					wait = 0;
				}
				socket.close();
				Thread.sleep(1000);
			} catch (Exception e)
			{
				print(e);
			}
			wait++;
			if (wait == patience/2) print("reseau silencieux");
		}
		print("deconnection");
	}
}
