package network;

import java.io.IOException;


public class UDPClientInfo extends UDPClientBuilder implements Runnable
{
	public void run()
	{
		try
		{
			setConnection();
			socketInfo("client sets the connection", socket);
			socket.close();
			socketInfo("client closes the connection", socket); 
		}
		catch(IOException e)
		{
			System.out.println("IOException UDPClientInfo");
		} 
	}
}
