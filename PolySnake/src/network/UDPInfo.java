package network;

import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.SocketException;


public class UDPInfo extends UDPRWEmpty
{
	private SocketInfo info;

	UDPInfo()
	{
		info = new SocketInfo();
	}

	protected void socketInfo(String event, DatagramSocket socket) throws SocketException
	{
		if ((event != null) && (info != null))
		{
			info.clear();
			info.isIPV6 = isIPV6(socket.getInetAddress());
			info.lA = getAdressName(socket.getLocalAddress());
			info.lP = socket.getLocalPort();
			info.rA = getAdressName(socket.getInetAddress());
			info.rP = socket.getPort();
			info.closed = socket.isClosed();
			info.bounded = socket.isBound();
			info.connected = socket.isConnected();
			if (!info.closed)
			{
				info.tO = socket.getSoTimeout();
				info.bC = socket.getBroadcast();
				info.rU = socket.getReuseAddress();
				info.tC = Integer.toHexString(socket.getTrafficClass());
				info.sbS = socket.getSendBufferSize();
				info.rbS = socket.getReceiveBufferSize();
			}
			print(event);
		}
	}

	private String getAdressName(InetAddress iA)
	{
		if (iA != null)
			return iA.toString();
		return null;
	}

	private boolean isIPV6(InetAddress iA)
	{
		if (iA instanceof Inet6Address)
			return true;
		return false;
	}

	/** The internal print method. */ 
	private void print(String event)
	{ 
		if(info.closed) 
			System.out.println( 
				event+"\n" 
				+"IPV6: "+info.isIPV6+"\n" 
				+"local \tadress:"+info.lA+"\t port:"+info.lP+"\n"
				+"remote \tadress:"+info.rA+"\t port:"+info.rP+"\n" 
				+"bounded: "+info.bounded+"\n" 
				+"closed: "+info.closed+"\n" 
				+"connected: "+info.connected+"\n" 
			); 
		else 
			System.out.println(
				event+"\n" 
				+"IPV6: "+info.isIPV6+"\n" 
				+"local \tadress:"+info.lA+"\t port:"+info.lP+"\n"
				+"remote \tadress:"+info.rA+"\t port:"+info.rP+"\n" 
				+"bounded: "+info.bounded+"\n" 
				+"closed: "+info.closed+"\n" 
				+"connected: "+info.connected+"\n" 
				+"timeout:  "+info.tO+"\t  broadcast:  "+info.bC+"\t  reuse:  "+info.rU+"\t  traffic: "+info.tC+"\n" 
				+"buffer \tsend:"+info.sbS+"\treceive:"+info.rbS+"\n" 
			); 
	}
}
