package controler;

import network.TCPClientMessage;
import network.TCPServerMessage;

public class HostControler extends GameControler
{
	TCPServerMessage server;
	TCPClientMessage client;
	Thread serverThread;
	Thread clientThread;
	int leftID;
	
	public HostControler(int leftID)
	{
		super(leftID);
	}
	
	public HostControler(int leftID, int rightID)
	{
		super(leftID, rightID);
	}
	
	/**
	 * recieve a message for a guest and broadcast it if interesting
	 */
	public boolean recieveMessage(String message)
	{
		boolean res = super.recieveMessage(message);
		if (res)
			sendMessage(message);
		return res;
	}
}
