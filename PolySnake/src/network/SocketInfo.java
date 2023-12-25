package network;


public class SocketInfo
{
	public String lA, rA, tC;
	public int lP, rP, sbS, rbS, tO;
	public boolean isIPV6, bounded, closed, connected, rU, bC;

	public SocketInfo()
	{
		clear();
	}

	public void clear()
	{
		lA = rA = tC = null;
		lP = rP = sbS = rbS = tO = -1;
		isIPV6 = bounded = closed = connected = bC = rU = false;
	}
}
